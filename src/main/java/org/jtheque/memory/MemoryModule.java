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

import org.jtheque.core.utils.WeakEventListenerList;
import org.jtheque.modules.utils.SwingModule;

import javax.annotation.PreDestroy;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryUsage;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A module to display the memory used/committed in the state bar.
 *
 * @author Baptiste Wicht
 */
public final class MemoryModule extends SwingModule {
    private static final int TIMER_DELAY = 1000;
    private static final int TIMER_PERIOD = 1000 * 60;

    /**
     * The timer to manager the memory task.
     */
    private final Timer memoryManagementTimer = new Timer();

    /**
     * The list to manage the listeners.
     */
    private final WeakEventListenerList listeners = new WeakEventListenerList();

    /**
     * Construct a new MemoryModule.
     */
    public MemoryModule(String[] edtBeans) {
        super("org.jtheque.memory", edtBeans);

        memoryManagementTimer.scheduleAtFixedRate(new MemoryTask(), TIMER_DELAY, TIMER_PERIOD);
    }

    @PreDestroy
    public void stop() {
        if (memoryManagementTimer != null) {
            memoryManagementTimer.cancel();
        }

        listeners.removeAll(MemoryListener.class);
    }

    /**
     * Add a memory listener.
     *
     * @param listener The listener to add.
     */
    public void addMemoryListener(MemoryListener listener) {
        listeners.add(MemoryListener.class, listener);
    }

    /**
     * Clean the memory, force run the gc.
     */
    public void cleanMemory() {
        for (int i = 0; i < 5; i++) {
            System.gc();
        }

        fireMemoryChanged();
    }

    /**
     * Fire a memory event when the function has been updated.
     */
    private void fireMemoryChanged() {
        for (MemoryListener listener : listeners.getListeners(MemoryListener.class)) {
            listener.memoryUsageChanged(getMemoryUsage().getCommitted(), getMemoryUsage().getUsed());
        }
    }

    /**
     * Return the memory usage of the application.
     *
     * @return The current memory usage.
     */
    private static MemoryUsage getMemoryUsage() {
        return ManagementFactory.getMemoryMXBean().getNonHeapMemoryUsage();
    }

    /**
     * A timer task to calculate the memory usage and avert the listeners of the change.
     *
     * @author Baptiste Wicht
     */
    private final class MemoryTask extends TimerTask {
        @Override
        public void run() {
            fireMemoryChanged();
        }
    }
}