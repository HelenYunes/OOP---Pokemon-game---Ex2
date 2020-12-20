package gameClient;

import api.edge_data;
import api.geo_location;

import java.util.Objects;

public class Pokemon {
    private edge_data _edge;
    private double _value;
    private int _type;
    private geo_location _pos;
    private int _id;

    public Pokemon(geo_location pos, int key, double value, int t, edge_data e) {
        this._id = key;
        this._pos = pos;
        this._value = value;
        this._type = t;
        this._edge = e;
    }

    public double getValue() {
        return _value;
    }

    public int getType() {
        return _type;
    }

    public void setType(int _type) {
        this._type = _type;
    }

    public edge_data get_edge() {
        return _edge;
    }

    public void set_edge(edge_data _edge) {
        this._edge = _edge;
    }

    public int get_id() {
        return _id;
    }

    public geo_location getLocation() {
        return _pos;
    }

    @Override
    public String toString() {
        return "Pokemon{" +
                "_type=" + _type +
                ", _value=" + _value +
                ", _edge=" + _edge +
                ", _id=" + _id +
                ", _pos=" + _pos +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pokemon pokemon = (Pokemon) o;
        return Double.compare(pokemon._value, _value) == 0 &&
                _type == pokemon._type &&
                get_id() == pokemon.get_id() &&
                Objects.equals(get_edge(), pokemon.get_edge()) &&
                Objects.equals(_pos, pokemon._pos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(get_edge(), _value, _type, _pos, get_id());
    }
}
