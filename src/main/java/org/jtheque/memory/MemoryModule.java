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

import org.jdesktop.swingx.event.WeakEventListenerList;
import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.module.annotations.Module;
import org.jtheque.core.managers.module.annotations.Plug;
import org.jtheque.core.managers.module.annotations.UnPlug;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.core.managers.view.able.components.StateBarComponent;
import org.jtheque.core.managers.view.able.components.StateBarComponent.Position;
import org.jtheque.utils.ui.SwingUtils;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryUsage;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A module to display the memory used/committed in the state bar.
 *
 * @author Baptiste Wicht
 */
@Module(id = "jtheque-memory-module", i18n = "classpath:org/jtheque/memory/i18n/memory",
        version = "1.4.1", core = "2.0.2", jarFile = "jtheque-memory-module-1.4.1.jar", 
        updateURL = "http://jtheque.developpez.com/public/versions/MemoryModule.versions")
public final class MemoryModule {
    /**
     * The timer to manager the memory task.
     */
    private static final Timer MEMORY_MANAGEMENT_TIMER = new Timer();

    /**
     * The list to manage the listeners.
     */
    private static final WeakEventListenerList LISTENERS = new WeakEventListenerList();

    /**
     * The state bar component who display the memory.
     */
    private StateBarComponent component;

    /**
     * The base name for the resources.
     */
    public static final String IMAGE_BASE_NAME = "org/jtheque/memory/images";

    private static final int TIMER_DELAY = 1000 * 15;
    private static final int TIMER_PERIOD = 1000 * 60;

    /**
     * Plug the module.
     */
    @Plug
    public void plug() {
        MEMORY_MANAGEMENT_TIMER.scheduleAtFixedRate(new MemoryTask(), TIMER_DELAY, TIMER_PERIOD);

        SwingUtils.inEdt(new Runnable() {
            @Override
            public void run() {
                component = new StateBarComponent(new MemoryPanel(), Position.RIGHT);
                Managers.getManager(IViewManager.class).addStateBarComponent(component);
            }
        });
    }

    /**
     * Un plug the module.
     */
    @UnPlug
    public void unplug() {
        Managers.getManager(IViewManager.class).removeStateBarComponent(component);

        if (MEMORY_MANAGEMENT_TIMER != null) {
            MEMORY_MANAGEMENT_TIMER.cancel();
        }

        for (MemoryListener l : LISTENERS.getListeners(MemoryListener.class)) {
            removeMemoryListener(l);
        }
    }

    /**
     * Add a memory listener.
     *
     * @param listener The listener to add.
     */
    public static void addMemoryListener(MemoryListener listener) {
        LISTENERS.add(MemoryListener.class, listener);
    }

    /**
     * Remove a memory listener.
     *
     * @param listener The listener to remove
     */
    private static void removeMemoryListener(MemoryListener listener) {
        LISTENERS.remove(MemoryListener.class, listener);
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
     * Return the memory used.
     *
     * @return The amount of memory used by the application.
     */
    public static long getMemoryUsed() {
        return getMemoryUsage().getUsed();
    }

    /**
     * Return the memory committed.
     *
     * @return The amount of memory committed by the application.
     */
    public static long getMemoryCommitted() {
        return getMemoryUsage().getCommitted();
    }

    /**
     * Clean the memory, force run the gc.
     */
    public static void cleanMemory() {
        for (int i = 0; i < 5; i++) {
            System.gc();
        }

        MEMORY_MANAGEMENT_TIMER.schedule(new MemoryTask(), 5);
    }

    /**
     * A timer task to calculate the memory usage and avert the listeners of the change.
     *
     * @author Baptiste Wicht
     */
    private static final class MemoryTask extends TimerTask {
        @Override
        public void run() {
            fireMemoryChanged();
        }

        /**
         * Fire a memory event when the function has been updated.
         */
        private static void fireMemoryChanged() {
            MemoryListener[] listeners = LISTENERS.getListeners(MemoryListener.class);

            for (MemoryListener listener : listeners) {
                listener.memoryUsageChanged();
            }
        }
    }
}