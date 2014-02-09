/* Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.snaker.designer.utils;

import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.snaker.designer.Activator;

/**
 * @author yuqs
 * @version 1.0
 */
public class LogUtils {
	public static void logInfo(String message) {
        log(IStatus.INFO, IStatus.OK, message, null);
    }
 
    public static void logError(Throwable exception) {
        logError("Unexpected Exception", exception);
    }
 
    public static void logError(String message, Throwable exception) {
        log(IStatus.ERROR, IStatus.OK, message, exception);
    }
 
    public static void log(int severity, int code, String message,
            Throwable exception) {
        log(createStatus(severity, code, message, exception));
    }
 
    public static IStatus createStatus(int severity, int code, String message,
            Throwable exception) {
        return new Status(severity, Activator.PLUGIN_ID, code, message,
                        exception);
    }
 
    public static void log(IStatus status) {
        ILog log = Activator.getDefault().getLog();
        log.log(status);
    }

}
