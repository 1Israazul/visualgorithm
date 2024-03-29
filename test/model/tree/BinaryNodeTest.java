/*
 * BinaryNodeTest.java v0.10 08/02/09
 *
 * Visualgorithm
 * Copyright (C) Hannier, Pironin, Rigoni (visualgo@googlegroups.com)
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

package model.tree;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import model.tree.RedBlackNode.RedBlackNodeColor;
import org.junit.Before;
import org.junit.Test;

/**
 * Test of the three types of binary nodes : binary search node, AVL node and
 * red black node.
 * 
 * @author Julien Hannier
 * @version 0.10 08/02/09
 */
public class BinaryNodeTest {

    private IBinarySearchNode bsNode;

    private IAVLNode avlNode;

    private IRedBlackNode rbNode1;

    private IRedBlackNode rbNode2;

    @Before
    public void setUp() {
        bsNode = new BinarySearchNode(13);
        avlNode = new AVLNode(3);
        rbNode1 = new RedBlackNode(23);
        rbNode2 = new RedBlackNode(33, RedBlackNodeColor.BLACK);
    }

    @Test
    public void testBSN() {
        assertEquals(bsNode.getKey(), 13);
        assertNull(bsNode.getFather());
        assertNull(bsNode.getRight());
        assertNull(bsNode.getLeft());
        assertEquals(new BinarySearchNode(-2).getKey(), -2);

        bsNode.setKey(-1);
        assertEquals(bsNode.getKey(), -1);

        bsNode.setKey(46);
        assertEquals(bsNode.getKey(), 46);

        bsNode.setKey(100);
        assertEquals(bsNode.getKey(), 100);

        try {
            bsNode.setFather(new AVLNode(0));
            assertEquals(bsNode.getFather() instanceof BinarySearchNode, true);
        } catch (IllegalArgumentException e) {
        }
        try {
            bsNode.setRight(new AVLNode(0));
            assertEquals(bsNode.getRight() instanceof BinarySearchNode, true);
        } catch (IllegalArgumentException e) {
        }
        try {
            bsNode.setLeft(new AVLNode(0));
            assertEquals(bsNode.getLeft() instanceof BinarySearchNode, true);
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void testAVLN() {
        assertEquals(avlNode.getKey(), 3);
        assertNull(avlNode.getFather());
        assertNull(avlNode.getRight());
        assertNull(avlNode.getLeft());
        assertEquals(avlNode.getAVLHeight(), 0);
        assertEquals(new AVLNode(-2).getKey(), -2);

        avlNode.setKey(-1);
        assertEquals(avlNode.getKey(), -1);

        avlNode.setKey(0);
        assertEquals(avlNode.getKey(), 0);

        avlNode.setKey(100);
        assertEquals(avlNode.getKey(), 100);

        try {
            avlNode.setFather(new BinarySearchNode(0));
            assertEquals(bsNode.getFather() instanceof AVLNode, true);
        } catch (IllegalArgumentException e) {
        }
        try {
            avlNode.setRight(new BinarySearchNode(0));
            assertEquals(bsNode.getRight() instanceof AVLNode, true);
        } catch (IllegalArgumentException e) {
        }
        try {
            avlNode.setLeft(new BinarySearchNode(0));
            assertEquals(bsNode.getLeft() instanceof AVLNode, true);
        } catch (IllegalArgumentException e) {
        }
        avlNode.setKey(4);
        avlNode.setFather(new AVLNode(5));
        avlNode.setRight(new AVLNode(7));
        avlNode.computeAndSetHeight();
        assertEquals(avlNode.getKey(), 4);
        assertEquals(avlNode.getFather().getKey(), 5);
        assertEquals(avlNode.getRight().getKey(), 7);
        assertEquals(avlNode.getAVLHeight(), 1);
        assertEquals(avlNode.computeBalanceFactor(), 1);

        avlNode.setLeft(new AVLNode(2));
        avlNode.computeAndSetHeight();
        assertEquals(avlNode.getLeft().getKey(), 2);
        assertEquals(avlNode.getAVLHeight(), 1);
        assertEquals(avlNode.computeBalanceFactor(), 0);

        avlNode.getFather().setLeft(avlNode);
        avlNode.getFather().computeAndSetHeight();
        assertEquals(avlNode.getFather().getAVLHeight(), 2);
        assertEquals(avlNode.getFather().computeBalanceFactor(), -2);
    }

    @Test
    public void testRBN() {
        assertEquals(rbNode1.getKey(), 23);
        assertNull(rbNode1.getFather());
        assertNull(rbNode1.getRight());
        assertNull(rbNode1.getLeft());
        assertEquals(rbNode1.getColor(), RedBlackNodeColor.RED);
        assertEquals(rbNode1.isRed(), true);
        assertEquals(rbNode1.isBlack(), false);
        assertEquals(rbNode1.calculateLeftBlackHeight(), 0);
        assertEquals(rbNode1.calculateRightBlackHeight(), 0);
        assertEquals(new RedBlackNode(-2).getKey(), -2);

        rbNode1.setKey(-1);
        assertEquals(rbNode1.getKey(), -1);

        rbNode1.setKey(99);
        assertEquals(rbNode1.getKey(), 99);

        rbNode1.setKey(100);
        assertEquals(rbNode1.getKey(), 100);

        try {
            rbNode1.setFather(new BinarySearchNode(0));
            assertEquals(bsNode.getFather() instanceof RedBlackNode, true);
        } catch (IllegalArgumentException e) {
        }
        try {
            rbNode1.setRight(new BinarySearchNode(0));
            assertEquals(bsNode.getRight() instanceof RedBlackNode, true);
        } catch (IllegalArgumentException e) {
        }
        try {
            rbNode1.setLeft(new BinarySearchNode(0));
            assertEquals(bsNode.getLeft() instanceof RedBlackNode, true);
        } catch (IllegalArgumentException e) {
        }
        assertEquals(rbNode2.getKey(), 33);
        assertNull(rbNode2.getFather());
        assertNull(rbNode2.getRight());
        assertNull(rbNode2.getLeft());
        assertEquals(rbNode2.getColor(), RedBlackNodeColor.BLACK);
        assertEquals(rbNode2.calculateLeftBlackHeight(), 0);
        assertEquals(rbNode2.calculateRightBlackHeight(), 0);

        rbNode2.setRight(new RedBlackNode(43));
        assertEquals(rbNode2.getRight().getColor(), RedBlackNodeColor.RED);

        rbNode2.getRight().setColor(RedBlackNodeColor.BLACK);
        rbNode2.setRight(new RedBlackNode(43, RedBlackNodeColor.BLACK));
        rbNode2.setLeft(new RedBlackNode(13));
        assertEquals(rbNode2.getRight().getKey(), 43);
        assertEquals(rbNode2.getRight().getColor(), RedBlackNodeColor.BLACK);
        assertEquals(rbNode2.getLeft().getKey(), 13);
        assertEquals(rbNode2.getLeft().getColor(), RedBlackNodeColor.RED);

        rbNode2.getLeft().setLeft(new RedBlackNode(1, RedBlackNodeColor.BLACK));
        assertEquals(rbNode2.getLeft().getLeft().getKey(), 1);
        assertEquals(rbNode2.getLeft().getLeft().getColor(),
                RedBlackNodeColor.BLACK);
        assertEquals(rbNode2.calculateLeftBlackHeight(), 1);
        assertEquals(rbNode2.calculateRightBlackHeight(), 1);
    }
}
