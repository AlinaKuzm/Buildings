package buildings;

import buildings.dwelling.DwellingFactory;
import buildings.interfaces.*;
import buildings.office.OfficeFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileReader;

public class SwingForm extends JFrame {
    private JPanel rootPanel;
    private JPanel infoPanel;
    private JLabel buildingLabel;
    private JScrollPane buildingLayoutScrollPane;
    private JPanel buildingLayoutPanel;
    private Building building;

    private final Font FONT = new Font("TimesRoman", Font.BOLD, 14);

    public SwingForm() {
        setContentPane(rootPanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // размеры окна
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        setSize(screenWidth / 2, screenHeight / 2);
        setLocation(screenWidth / 4, screenHeight / 4);

        JMenuBar menuBar = createMenuBar();
        setJMenuBar(menuBar);

        buildingLayoutPanel.setLayout(new BoxLayout(buildingLayoutPanel, BoxLayout.Y_AXIS));
        buildingLayoutPanel.setBackground(Color.WHITE);

        setVisible(true);
    }


    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu mainMenu = new JMenu("Menu");
        mainMenu.setFont(FONT);


        JMenu fileMenu = new JMenu("File");
        fileMenu.setFont(FONT);
        JMenuItem openDwelling = new JMenuItem("Open dwelling...");
        JMenuItem openOffice = new JMenuItem("Open office building...");
        openDwelling.setFont(FONT);
        openOffice.setFont(FONT);
        openDwelling.addActionListener(fileMenuItemActionListener(new DwellingFactory()));
        openOffice.addActionListener(fileMenuItemActionListener(new OfficeFactory()));
        fileMenu.add(openDwelling);
        fileMenu.add(openOffice);
        mainMenu.add(fileMenu);


        JMenu lookAndFeelMenu = new JMenu("Look&Feel");
        lookAndFeelMenu.setFont(FONT);
        ButtonGroup buttonGroup = new ButtonGroup();
        UIManager.LookAndFeelInfo[] styles = UIManager.getInstalledLookAndFeels();
        for (UIManager.LookAndFeelInfo style : styles) {
            JRadioButtonMenuItem item = new JRadioButtonMenuItem(style.getName());
            item.setFont(FONT);
            item.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        UIManager.setLookAndFeel(style.getClassName());
                        SwingUtilities.updateComponentTreeUI(getContentPane());
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Ошибка");
                        ex.printStackTrace();
                    }
                }
            });
            buttonGroup.add(item);
            lookAndFeelMenu.add(item);
        }
        mainMenu.add(lookAndFeelMenu);

        menuBar.add(mainMenu);
        return menuBar;
    }


    private ActionListener fileMenuItemActionListener(BuildingFactory factory) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                Buildings.setBuildingFactory(factory);


                JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
                fileChooser.setDialogTitle("Выберите файл");
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    try (FileReader in = new FileReader(fileChooser.getSelectedFile())) {
                        building = Buildings.readBuilding(in);


                        buildingLabel.setText(String.format("Selected building: %s, floors: %d, area: %.1f",
                                building.getClass().getSimpleName(), building.getFloorsCount(), building.getSpacesSquare()));
                        createBuildingLayout();
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Ошибка чтения здания");
                        e.printStackTrace();
                    }
                } else if (result == JFileChooser.ERROR_OPTION) {
                    JOptionPane.showMessageDialog(null, "Ошибка открытия файла");
                }
            }
        };
    }


    private void createBuildingLayout() {
        int floorCount = building.getFloorsCount();


        for (int i = floorCount; i > 0; i--) {
            JPanel panel = createFloorPanel(i - 1);
            buildingLayoutPanel.add(panel);
            buildingLayoutScrollPane.revalidate();
        }
    }


    private JPanel createFloorPanel(int indexOfFloor) {
        Floor floor = building.getFloor(indexOfFloor);
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.setBackground(Color.BLACK);
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.setMaximumSize(new Dimension(120, 40));

        panel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                panel.setToolTipText(String.format("Selected floor: %d, spaces count: %d, area: %.1f",
                        indexOfFloor + 1, floor.getSpacesCount(), floor.getSpacesSquare()));
            }


            @Override
            public void mouseExited(MouseEvent e) {
            }
        });


        int indexOfSpaceInBuilding = 1;
        for (int i = 0; i < indexOfFloor; i++)
            indexOfSpaceInBuilding += building.getFloor(i).getSpacesCount();


        panel.add(Box.createRigidArea(new Dimension(2, 0)));
        int spaceCount = floor.getSpacesCount();
        for (int i = 0; i < spaceCount; i++) {
            JButton button = createSpaceJButton(floor.getSpace(i), indexOfSpaceInBuilding);


            Dimension dimension = panel.getMaximumSize();
            panel.setMaximumSize(new Dimension(dimension.width + 120, 40));

            panel.add(button);
            panel.add(Box.createRigidArea(new Dimension(5, 0)));
            indexOfSpaceInBuilding++;
        }

        return panel;
    }


    private JButton createSpaceJButton(Space space, int indexOfSpace) {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(50, 20));
        button.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                button.setToolTipText(String.format("Selected space: %d, rooms count: %d, area: %.1f",
                        indexOfSpace, space.getRoomCount(), space.getSquare()));
            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        return button;
    }

    public static void main(String[] args) {
        new SwingForm();
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        rootPanel = new JPanel();
        rootPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        infoPanel = new JPanel();
        infoPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        infoPanel.setBackground(new Color(-1));
        rootPanel.add(infoPanel, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        buildingLabel = new JLabel();
        Font buildingLabelFont = this.$$$getFont$$$("Times New Roman", Font.PLAIN, 20, buildingLabel.getFont());
        if (buildingLabelFont != null) buildingLabel.setFont(buildingLabelFont);
        buildingLabel.setText("");
        infoPanel.add(buildingLabel, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        rootPanel.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        buildingLayoutScrollPane = new JScrollPane();
        rootPanel.add(buildingLayoutScrollPane, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        buildingLayoutPanel = new JPanel();
        buildingLayoutPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        buildingLayoutPanel.setBackground(new Color(-1973534));
        buildingLayoutScrollPane.setViewportView(buildingLayoutPanel);
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        return new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return rootPanel;
    }

}