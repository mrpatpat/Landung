package oot.landung.tournament.donutslayers.model;

import oot.landung.tournament.donutslayers.model.Spielfeld.Feld;

public class ZugZiehen extends Zug {
    private Feld von;
    private Feld dazwischen;

    public Feld getVon() {
        return von;
    }

    public void setVon(Feld von) {
        this.von = von;
    }

    public Feld getDazwischen() {
        return dazwischen;
    }

    public void setDazwischen(Feld dazwischen) {
        this.dazwischen = dazwischen;
    }

    public ZugZiehen(Feld von, Feld zu, Feld dazwischen) {
        super(zu);
        this.von = von;
        this.dazwischen = dazwischen;
    }

    /**
     * Kostruktor fuer ZugZiehen, falls ueber die Konsoleneingabe der Zug
     * erstellt wird.
     * 
     * @param spielfeld Das aktuelle Spielfeld.
     * @param zug Der Zug als String mit Anfang und Zielposition
     */
    public ZugZiehen(Spielfeld spielfeld, String zug) {
        super(zug.substring(2, 4), spielfeld);
        this.von = spielfeld.getFeld(zug.substring(0, 2));

        // muss speziell geholt werden, wenn ueber die Tastatur eingelesen wird.
        this.dazwischen = Spielfeld.holeFeldDazwischen(von, zu);
    }

    public String toString() {
        return "von " + getVon().toString() + " nach " + getZu().toString();
    }

    @Override
    public boolean equals(Object obj) {

        if(this == obj)
            return true;
        if(obj == null)
            return false;
        if(getClass() != obj.getClass())
            return false;
        ZugZiehen other = (ZugZiehen) obj;
        if(dazwischen == null) {
            if(other.dazwischen != null)
                return false;
        } else if(!dazwischen.equals(other.dazwischen))
            return false;
        if(von == null) {
            if(other.von != null)
                return false;
        } else if(!von.equals(other.von))
            return false;
        if(zu == null) {
            if(other.zu != null)
                return false;
        } else if(!zu.equals(other.zu))
            return false;
        return true;
    }

	@Override
	public String inKoordinatenDarstellung() {
		
		return this.getVon().toString() + this.getZu().toString();
		
	}

}
