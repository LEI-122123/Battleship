package iscteiul.ista;

import static iscteiul.ista.battleship.Compass.*;
import static org.junit.jupiter.api.Assertions.*;

import iscteiul.ista.battleship.*;

import org.junit.jupiter.api.Test;

import java.util.List;

public class ShipTest
{
    static class DummyShip extends Ship
    {
        public DummyShip( String category, Compass bearing, IPosition pos )
        {
            super( category, bearing, pos );
        }

        @Override
        public String getCategory()
        {
            return super.getCategory();
        }

        @Override
        public Integer getSize()
        {
            return 0;
        }

        @Override
        public List<IPosition> getPositions()
        {
            return super.getPositions();
        }

        @Override
        public IPosition getPosition()
        {
            return super.getPosition();
        }

        @Override
        public Compass getBearing()
        {
            return super.getBearing();
        }

        @Override
        public boolean stillFloating()
        {
            return super.stillFloating();
        }

        @Override
        public int getTopMostPos()
        {
            return super.getTopMostPos();
        }

        @Override
        public int getBottomMostPos()
        {
            return super.getBottomMostPos();
        }

        @Override
        public int getLeftMostPos()
        {
            return super.getLeftMostPos();
        }

        @Override
        public int getRightMostPos()
        {
            return super.getRightMostPos();
        }

        @Override
        public boolean occupies( IPosition pos )
        {
            return super.occupies( pos );
        }

        @Override
        public boolean tooCloseTo( IShip other )
        {
            return super.tooCloseTo( other );
        }

        @Override
        public boolean tooCloseTo( IPosition pos )
        {
            return super.tooCloseTo( pos );
        }

        @Override
        public void shoot( IPosition pos )
        {
            super.shoot( pos );
        }
    }

    @Test
    void NullShip() {
        final Ship validShip = new DummyShip( "other", UNKNOWN, new Position( 0, 0));

        assertNotNull( validShip );

        assertThrows(AssertionError.class, () -> {
            new DummyShip("other", null, new Position(0,0));
        });

        assertThrows(AssertionError.class, () -> {
            new DummyShip("other", UNKNOWN, null);
        });

        //assertTrue( invalidShip.getSize() > 0 );
    }

}
