/*
 * GraphicUserInterface.java v1.00 16/06/08
 *
 * Visualgorithm
 * Copyright (C) Hannier, Pironin, Rigoni (bx1gl@googlegroups.com)
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package swing;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;

import model.Model;
import model.ModelEvent;
import model.ModelEvent.ModelEventType;
import model.datastructure.DataStructureType;
import view.IModelView;
import controller.PrincipalController;

/**
 * Definition of the principal view.
 * 
 * @author Julien Hannier
 * @author Pierre Pironin
 * @author Damien Rigoni
 * @version 1.00 16/06/08
 * @see IModelView
 */
public class GraphicUserInterface extends JFrame implements IModelView {
    
    private static final long serialVersionUID = 1L;

    private PrincipalController controller = null;
    
    private JMenuBar menuBar;
    
    private JTabbedPane tabbedPane;

    /**
     * Builds the principal view.
     * 
     * @param model the model
     * @param c the controller
     */
    public GraphicUserInterface(Model model, PrincipalController c) {
        super("Visualgorithm");
        controller = c;
        menuBar = createMenuBar(model);
        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        menuBar.setBorder(
            BorderFactory.createEmptyBorder(2, 0, 2, 0));
        setJMenuBar(menuBar);
        add(tabbedPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = 
            Toolkit.getDefaultToolkit().getScreenSize();
        setSize( screenSize.width * 9/10, screenSize.height * 9/10);
    }
    
    private JComponent getTabView(int index) {
        return (JComponent)
            controller.getTabController(index).getView();
    }

    private JMenuBar createMenuBar(Model model) {
        JMenuBar menuBar = new JMenuBar();
        JMenu file = createFileMenu(model);
        JMenu edit = createEditMenu(model);
        JMenu trees = createTreesMenu(model);
        JMenu algorithms = createAlgorithmsMenu(model);
        
        menuBar.add(file);
        menuBar.add(edit);
        menuBar.add(trees);
        menuBar.add(algorithms);
        return menuBar;
    }
    
    private JMenu createFileMenu(Model model) {
        JMenu file = new JMenu("File");
        JMenuItem load = new JMenuItem("Load");
        JMenuItem save = new JMenuItem("Save");
        JMenuItem exit = new JMenuItem("Exit");
        
        load.setAccelerator(KeyStroke.getKeyStroke(
            KeyEvent.VK_L, ActionEvent.CTRL_MASK));
        save.setAccelerator(KeyStroke.getKeyStroke(
            KeyEvent.VK_S, ActionEvent.CTRL_MASK));        
        exit.setAccelerator(KeyStroke.getKeyStroke(
            KeyEvent.VK_X, ActionEvent.CTRL_MASK));
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                controller.exitSoftware();
            }
        });
        file.add(load);
        file.add(save);
        file.add(exit);
        return file;
    }
    
    private JMenu createEditMenu(Model model) {
        JMenu tools = new JMenu("Edit");
        JMenu language = new JMenu("Language");
        JMenuItem preferences = new JMenuItem("Preferences");
        
        tools.add(language);
        tools.add(preferences);
        return tools;
    }

    private JMenu createTreesMenu(Model model) {
        JMenu trees = new JMenu("Trees");
        JMenu newTree = new JMenu("New");
        JMenu notes = new JMenu("Notes");
        JMenuItem randomTree = new JMenuItem("Random Tree");
        JMenuItem avlTree = new JMenuItem("AVL Tree");
        JMenuItem binarySearchTree = new JMenuItem(
            "Binary Search Tree");
        JMenuItem redBlackTree = new JMenuItem("Red " +
                "Black Tree");
        
        avlTree.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                int index = tabbedPane.getTabCount();
                controller.addTabDataStructure(
                    DataStructureType.AVLTREE, index);
            }
        });
        binarySearchTree.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                int index = tabbedPane.getTabCount();
                controller.addTabDataStructure(
                    DataStructureType.BINARYSEARCHTREE, index);
            }
        });
        redBlackTree.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                int index = tabbedPane.getTabCount();
                controller.addTabDataStructure(
                    DataStructureType.REDBLACKTREE, index);
            }
        });
        newTree.add(randomTree);
        newTree.add(binarySearchTree);
        newTree.add(avlTree);
        newTree.add(redBlackTree);
        trees.add(newTree);
        trees.add(notes);
        return trees;
    }
    
    private JMenu createAlgorithmsMenu(Model model) {
        JMenu algorithms = new JMenu("Algorithms");
        JMenu apply = new JMenu("Apply");
        JMenu notes = new JMenu("Notes");
        
        algorithms.add(apply);
        algorithms.add(notes);
        return algorithms;
    }
    
    @Override
    public void displayView() {
        setVisible(true);
    }

    @Override
    public void closeView() {
        dispose();
    }
    
    @Override
    public void modelChanged(ModelEvent event) {
        if (event.getType() == ModelEventType.ADD) {
            int numTab = tabbedPane.getTabCount();
            tabbedPane.addTab("New "+event.getDataStructureType(),
                getTabView(numTab));
            tabbedPane.setTabComponentAt(numTab,
                new TabCloseButton(tabbedPane, controller));
            tabbedPane.setSelectedIndex(numTab);
        } else if (event.getType() == ModelEventType.EXIT) {
            System.exit(0);
        } else if (event.getType() == ModelEventType.DELETE) {
            int i = event.getIndex();
            if (i != -1) {
                tabbedPane.remove(i);
            }
        }
    }
}