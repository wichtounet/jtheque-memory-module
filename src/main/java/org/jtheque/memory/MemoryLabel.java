package org.jtheque.memory;

/*
 * This file is part of JTheque.
 *
 * JTheque is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 *
 * JTheque is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JTheque.  If not, see <http://www.gnu.org/licenses/>.
 */

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.view.able.IViewManager;

import javax.swing.JLabel;
import java.awt.Color;

/**
 * A label to display the memory.
 *
 * @author Baptiste Wicht
 */
public final class MemoryLabel extends JLabel implements MemoryListener {
    private static final long serialVersionUID = -3011636720087557992L;
    private static final int KILO = 1024;

    /**
     * Construct a new MemoryLabel.
     */
    public MemoryLabel() {
        super();

        setForeground(Color.white);
        //setFont(getFont().deriveFont(Font.BOLD));

        updateText();
    }

    @Override
    public void memoryUsageChanged() {
        updateText();

        Managers.getManager(IViewManager.class).refresh(this);
    }

    /**
     * Update the text of the label.
     */
    private void updateText() {
        setText(MemoryModule.getMemoryUsed() / KILO + " KB / "
                + MemoryModule.getMemoryCommitted() / KILO + " KB");
    }
}