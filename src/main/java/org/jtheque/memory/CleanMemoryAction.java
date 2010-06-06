package org.jtheque.memory;

import org.jtheque.images.able.IResourceService;
import org.jtheque.ui.utils.actions.JThequeSimpleAction;

import java.awt.event.ActionEvent;

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
 * Action to clean the memory.
 *
 * @author Baptiste Wicht
 */
final class CleanMemoryAction extends JThequeSimpleAction {
	private final MemoryModule memoryModule;

	public CleanMemoryAction(IResourceService resourceService, MemoryModule memoryModule) {
        super();
		
		this.memoryModule = memoryModule;

		setIcon(resourceService.getIcon("jtheque-memory-module-icon"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        memoryModule.cleanMemory();
    }
}