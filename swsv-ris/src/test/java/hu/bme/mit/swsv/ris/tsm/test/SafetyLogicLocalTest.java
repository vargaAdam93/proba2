package hu.bme.mit.swsv.ris.tsm.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import hu.bme.mit.swsv.ris.common.Direction;
import hu.bme.mit.swsv.ris.common.NeighborTsmStatus;
import hu.bme.mit.swsv.ris.common.SectionControl;
import hu.bme.mit.swsv.ris.common.SectionOccupancy;
import hu.bme.mit.swsv.ris.common.SideTriple;
import hu.bme.mit.swsv.ris.common.logging.LogEntry;
import hu.bme.mit.swsv.ris.common.logging.LoggerWrapper;
import hu.bme.mit.swsv.ris.signals.HeartBeatSignal;
import hu.bme.mit.swsv.ris.signals.SectionOccupancySignal;
import hu.bme.mit.swsv.ris.signals.TurnoutDirectionSignal;
import hu.bme.mit.swsv.ris.tsm.SignalMapper;
import hu.bme.mit.swsv.ris.tsm.impl.SafetyLogicImpl;

public class SafetyLogicLocalTest {

	// Local variables
	SafetyLogicImpl TestClass;
	SideTriple<SectionOccupancy> sectionOccupancies;
	LoggerWrapper LogMock;
	SignalMapper MapperMocked;

	@Before
	public void initTests() {
		LogMock = Mockito.mock(LoggerWrapper.class);

		Mockito.doNothing().when(LogMock).log(Mockito.any(LogEntry.class), Mockito.any(Object.class));
	}

	public class SignalMapper_Ext implements SignalMapper {
		public SideTriple<SectionControl> decision;

		@Override
		public void receiveSectionOccupancy(final SectionOccupancySignal signal) {
			// TODO Auto-generated method stub

		}

		@Override
		public void receiveTurnoutDirection(final TurnoutDirectionSignal signal) {
			// TODO Auto-generated method stub

		}

		@Override
		public void receiveHeartBeat(final HeartBeatSignal signal) {
			// TODO Auto-generated method stub

		}

		@Override
		public void sendControl(final SideTriple<SectionControl> decision) {

			this.decision = decision;
		}

		@Override
		public void sendStatus(final SideTriple<NeighborTsmStatus> statusForNeighbors) {
			// TODO Auto-generated method stub

		}

	}

	@Test
	public void TurnoutStraight_DivegentOccupied_ExpectedDivergentDisable() {
		// Init part
		final SignalMapper_Ext result = new SignalMapper_Ext();
		sectionOccupancies = SideTriple.of(SectionOccupancy.FREE, SectionOccupancy.FREE, SectionOccupancy.OCCUPIED);
		final Direction turnoutDirection = Direction.DIVERGENT;

		TestClass = new SafetyLogicImpl(sectionOccupancies, turnoutDirection, null, LogMock);

		TestClass.setSignalMapper(result);

		// Send signal to makeDecision()
		TestClass.turnoutDirectionChanged(Direction.STRAIGHT);

		// Assertion part
		assertEquals(SectionControl.DISABLED, result.decision.getDivergent());
	}

	@Test
	public void TurnoutDivergent_StraigtOccupied_ExpectedStraigtDisable() {
		// Init part
		final SignalMapper_Ext result = new SignalMapper_Ext();
		sectionOccupancies = SideTriple.of(SectionOccupancy.FREE, SectionOccupancy.OCCUPIED, SectionOccupancy.FREE);

		final Direction turnoutDirection = Direction.STRAIGHT;

		TestClass = new SafetyLogicImpl(sectionOccupancies, turnoutDirection, null, LogMock);

		TestClass.setSignalMapper(result);

		// Send signal to makeDecision()
		TestClass.turnoutDirectionChanged(Direction.DIVERGENT);

		// Assertion part
		assertEquals(SectionControl.DISABLED, result.decision.getStraight());
	}

	@Test
	public void TurnoutStraight_FacingOccupied_StraigtOccupied_ExpectedFacingDisabled_And_StraightDisabled() {
		// Init part
		final SignalMapper_Ext result = new SignalMapper_Ext();
		sectionOccupancies = SideTriple.of(SectionOccupancy.OCCUPIED, SectionOccupancy.OCCUPIED, SectionOccupancy.FREE);

		final Direction turnoutDirection = Direction.DIVERGENT;

		TestClass = new SafetyLogicImpl(sectionOccupancies, turnoutDirection, null, LogMock);

		TestClass.setSignalMapper(result);

		// Send signal to makeDecision()
		TestClass.turnoutDirectionChanged(Direction.STRAIGHT);

		assertEquals(SectionControl.DISABLED, result.decision.getStraight());
		assertEquals(SectionControl.DISABLED, result.decision.getFacing());
	}

	@Test
	public void TurnoutDivergent_FacingOccupied_DivegentOccupied_ExpectedFacingDisabled_And_DivergentDisabled() {
		// Init part
		final SignalMapper_Ext result = new SignalMapper_Ext();
		sectionOccupancies = SideTriple.of(SectionOccupancy.OCCUPIED, SectionOccupancy.FREE, SectionOccupancy.OCCUPIED);

		final Direction turnoutDirection = Direction.STRAIGHT;

		TestClass = new SafetyLogicImpl(sectionOccupancies, turnoutDirection, null, LogMock);

		TestClass.setSignalMapper(result);

		// Send signal to makeDecision()
		TestClass.turnoutDirectionChanged(Direction.DIVERGENT);

		// Assertion part
		assertEquals(SectionControl.DISABLED, result.decision.getDivergent());
		assertEquals(SectionControl.DISABLED, result.decision.getFacing());

	}

	@Test
	public void TurnoutDivergent_FacingOccupied_StraightOccupied_DivegentOccupied_ExpectedFacingDisabled_And_DivergentDisabled() {
		// Init part
		final SignalMapper_Ext result = new SignalMapper_Ext();
		sectionOccupancies = SideTriple.of(SectionOccupancy.OCCUPIED, SectionOccupancy.OCCUPIED,
				SectionOccupancy.OCCUPIED);

		final Direction turnoutDirection = Direction.DIVERGENT;

		TestClass = new SafetyLogicImpl(sectionOccupancies, turnoutDirection, null, LogMock);

		TestClass.setSignalMapper(result);

		// Send signal to makeDecision()
		TestClass.turnoutDirectionChanged(Direction.STRAIGHT);

		// Assertion part
		assertEquals(SectionControl.DISABLED, result.decision.getDivergent());
		assertEquals(SectionControl.DISABLED, result.decision.getFacing());

	}

	@Test
	public void TurnoutDivergent_FacingOccupied_DivegentFree_ExpectedFacingEnabled_And_DivergentEnable() {
		// Init part
		final SignalMapper_Ext result = new SignalMapper_Ext();
		sectionOccupancies = SideTriple.of(SectionOccupancy.OCCUPIED, SectionOccupancy.FREE, SectionOccupancy.FREE);

		final Direction turnoutDirection = Direction.DIVERGENT;

		TestClass = new SafetyLogicImpl(sectionOccupancies, turnoutDirection, null, LogMock);

		TestClass.setSignalMapper(result);

		// Send signal to makeDecision()
		TestClass.turnoutDirectionChanged(Direction.STRAIGHT);

		// Assertion part
		assertEquals(SectionControl.ENABLED, result.decision.getDivergent());
		assertEquals(SectionControl.ENABLED, result.decision.getFacing());

	}

	@Test
	public void TurnoutStraight_FacingOccupied_DivegentOccupied_ExpectedFacingEnabled_And_DivergentEnabled() {
		// Init part
		final SignalMapper_Ext result = new SignalMapper_Ext();
		sectionOccupancies = SideTriple.of(SectionOccupancy.OCCUPIED, SectionOccupancy.OCCUPIED, SectionOccupancy.FREE);

		final Direction turnoutDirection = Direction.STRAIGHT;

		TestClass = new SafetyLogicImpl(sectionOccupancies, turnoutDirection, null, LogMock);

		TestClass.setSignalMapper(result);

		// Send signal to makeDecision()
		TestClass.turnoutDirectionChanged(Direction.DIVERGENT);

		// Assertion part
		assertEquals(SectionControl.ENABLED, result.decision.getDivergent());
		assertEquals(SectionControl.ENABLED, result.decision.getFacing());

	}

}
