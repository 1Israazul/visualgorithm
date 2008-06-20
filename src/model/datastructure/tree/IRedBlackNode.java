/*
 * IRedBlackNode.java v1.00 19/05/08
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

package model.datastructure.tree;

import model.datastructure.tree.RedBlackNode.RedBlackNodeColor;

/**
 * Additional methods of the nodes of red black trees.
 * 
 * @author Julien Hannier
 * @author Pierre Pironin
 * @author Damien Rigoni
 * @version 1.00 19/05/08
 * @see IBinarySearchNode
 */
public interface IRedBlackNode<N extends IRedBlackNode<N>> extends
        IBinarySearchNode<N> {

    /**
     * Returns the color of the red black node.
     * 
     * @return the color of the red black node
     */
    public RedBlackNodeColor getColor();

    /**
     * Replaces the color of the red black node by the new color.
     * 
     * @param color the new color of the red black node
     */
    public void setColor(RedBlackNodeColor color);

    /**
     * Returns true if the node is red or else false.
     * 
     * @return true if the node is red or else false
     */
    public boolean isRed();

    /**
     * Retourne true if the node is black or else false.
     * 
     * @return true if the node is black or else false
     */
    public boolean isBlack();
}
