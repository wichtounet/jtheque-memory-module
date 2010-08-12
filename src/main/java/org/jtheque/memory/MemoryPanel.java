package org.jtheque.memory;

import org.jtheque.images.ImageService;
import org.jtheque.ui.components.Borders;
import org.jtheque.views.components.StateBarComponent;

import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;

/*
 * Copyright JTheque (Baptiste Wicht)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * A panel to display the memory and a button to clean the memory.
 *
 * @author Baptiste Wicht
 */
public final class MemoryPanel extends JPanel implements StateBarComponent {
    private static final int BUTTON_SIZE = 20;

    public MemoryPanel(ImageService imageService, MemoryModule memoryModule) {
        super();

        setLayout(new FlowLayout(FlowLayout.LEFT, 3, 1));
        setOpaque(false);
        setBorder(Borders.EMPTY_BORDER);

        MemoryLabel label = new MemoryLabel();

        memoryModule.addMemoryListener(label);

        add(label);

        AbstractButton button = new JButton(new CleanMemoryAction(imageService, memoryModule));

        button.setMargin(new Insets(0, 0, 0, 0));
        button.setSize(new Dimension(BUTTON_SIZE, BUTTON_SIZE));
        button.setPreferredSize(new Dimension(BUTTON_SIZE, BUTTON_SIZE));
        button.setMaximumSize(new Dimension(BUTTON_SIZE, BUTTON_SIZE));

        add(button);

        add(Box.createRigidArea(new Dimension(5, BUTTON_SIZE)));
    }

    @Override
    public Component getComponent() {
        return this;
    }

    @Override
    public Position getPosition() {
        return Position.RIGHT;
    }
}
