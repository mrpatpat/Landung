package oot.landung.tournament.donutslayers.model;

import oot.landung.tournament.donutslayers.model.Spielfeld.Feld;

public class ZugSetzen extends Zug {

    public ZugSetzen(Feld zu) {
        super(zu);
    }
    
    public ZugSetzen(String koords, Spielfeld spielfeld){
    	
    	super(koords, spielfeld);
    	
    }

    @Override
    public String toString() {
        return "auf " + zu.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(obj == null)
            return false;
        if(getClass() != obj.getClass())
            return false;
        ZugSetzen other = (ZugSetzen) obj;
        if(zu == null) {
            if(other.zu != null)
                return false;
        } else if(!zu.equals(other.zu))
            return false;
        return true;
    }

	@Override
	public String inKoordinatenDarstellung() {
		
		return zu.toString();
	}
}
