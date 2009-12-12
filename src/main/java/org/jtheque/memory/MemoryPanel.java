package org.jtheque.memory;

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.resource.IResourceManager;
import org.jtheque.core.managers.resource.ImageType;
import org.jtheque.core.managers.view.impl.actions.JThequeSimpleAction;
import org.jtheque.core.utils.ui.Borders;
import org.jtheque.memory.actions.CleanMemoryAction;

import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;

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

/**
 * A panel to display the memory and a button to clean the memory.
 *
 * @author Baptiste Wicht
 */
final class MemoryPanel extends JPanel {
    private static final int BUTTON_SIZE = 20;

    /**
     * Construct a new MemoryPanel.
     */
    MemoryPanel() {
        super();

        build();
    }

    /**
     * Build the panel.
     */
    private void build() {
        FlowLayout layout = new FlowLayout();
        layout.setHgap(3);
        layout.setVgap(1);
        layout.setAlignment(FlowLayout.LEFT);

        setLayout(layout);
        setOpaque(false);
        setBorder(Borders.EMPTY_BORDER);

        MemoryLabel label = new MemoryLabel();

        MemoryModule.addMemoryListener(label);

        add(label);

        JThequeSimpleAction action = new CleanMemoryAction();

        ImageIcon icon = Managers.getManager(IResourceManager.class).getIcon(MemoryModule.IMAGE_BASE_NAME, "bin", ImageType.PNG);

        action.setIcon(icon);

        AbstractButton button = new JButton(action);

        button.setMargin(new Insets(0, 0, 0, 0));
        button.setSize(new Dimension(BUTTON_SIZE, BUTTON_SIZE));
        button.setPreferredSize(new Dimension(BUTTON_SIZE, BUTTON_SIZE));
        button.setMaximumSize(new Dimension(BUTTON_SIZE, BUTTON_SIZE));

        add(button);

        add(Box.createRigidArea(new Dimension(5, BUTTON_SIZE)));
    }
}
