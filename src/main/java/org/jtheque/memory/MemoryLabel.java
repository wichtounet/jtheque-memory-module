package org.jtheque.memory;

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

import org.jtheque.utils.ui.SwingUtils;

import javax.swing.JLabel;

import java.awt.Color;

/**
 * A label to display the memory.
 *
 * @author Baptiste Wicht
 */
public final class MemoryLabel extends JLabel implements MemoryListener {
    private static final int KILO = 1024;

    /**
     * Construct a new MemoryLabel.
     */
    public MemoryLabel() {
        super();

        setForeground(Color.white);
    }

    @Override
    public void memoryUsageChanged(long memoryCommitted, long memoryUsed) {
        setText(memoryUsed / KILO + " KB / " + memoryCommitted / KILO + " KB");

        SwingUtils.refresh(this);
    }
}