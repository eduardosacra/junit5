
package org.junit.gen5.launcher;

import org.junit.gen5.engine.TestDescriptor;
import org.junit.gen5.engine.TestExecutionListener;
import org.junit.gen5.engine.TestPlanExecutionListener;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author Stefan Bechtold
 * @author Sam Brannen
 * @since 5.0
 */
public class TestListenerRegistry {

	private final List<TestExecutionListener> testExecutionListeners = new LinkedList<>();

	private final List<TestPlanExecutionListener> testPlanExecutionListeners = new LinkedList<>();


	public void registerTestExecutionListener(TestExecutionListener listener) {
		testExecutionListeners.add(listener);
	}

	public void registerTestPlanExecutionListener(TestPlanExecutionListener listener) {
		testPlanExecutionListeners.add(listener);
	}

	public Iterable<TestExecutionListener> lookupTestExecutionListeners() {
		return testExecutionListeners;
	}

	public Iterable<TestPlanExecutionListener> lookupTestPlanExecutionListeners() {
		return testPlanExecutionListeners;
	}

	public void notifyTestExecutionListeners(Consumer<TestExecutionListener> consumer) {
		lookupTestExecutionListeners().forEach(consumer);
	}

	public void notifyTestPlanExecutionListeners(Consumer<TestPlanExecutionListener> consumer) {
		lookupTestPlanExecutionListeners().forEach(consumer);
	}

	public TestExecutionListener getCompositeTestExecutionListener() {
		return new CompositeTestExecutionListener();
	}

	public class CompositeTestExecutionListener implements TestExecutionListener {

		@Override
		public void dynamicTestFound(TestDescriptor testDescriptor) {
			notifyTestExecutionListeners(
					testExecutionListener -> testExecutionListener.dynamicTestFound(testDescriptor)
			);
		}

		@Override
		public void testStarted(TestDescriptor testDescriptor) {
			notifyTestExecutionListeners(
					testExecutionListener -> testExecutionListener.testStarted(testDescriptor)
			);

		}

		@Override
		public void testSkipped(TestDescriptor testDescriptor, Throwable t) {
			notifyTestExecutionListeners(
					testExecutionListener -> testExecutionListener.testSkipped(testDescriptor, t)
			);

		}

		@Override
		public void testAborted(TestDescriptor testDescriptor, Throwable t) {
			notifyTestExecutionListeners(
					testExecutionListener -> testExecutionListener.testAborted(testDescriptor, t)
			);

		}

		@Override
		public void testFailed(TestDescriptor testDescriptor, Throwable t) {
			notifyTestExecutionListeners(
					testExecutionListener -> testExecutionListener.testFailed(testDescriptor, t)
			);

		}

		@Override
		public void testSucceeded(TestDescriptor testDescriptor) {
			notifyTestExecutionListeners(
					testExecutionListener -> testExecutionListener.testSucceeded(testDescriptor)
			);

		}

	}
}
