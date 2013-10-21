package clm.developers.login;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class CLista_Agregar extends ArrayList<CProducto> implements Parcelable {

    public CLista_Agregar(){

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        int size = this.size();
        dest.writeInt(size);

        for (int i = 0; i < size; i++){
            CProducto objProducto = this.get(i);
            dest.writeString(objProducto.getNombre());
            dest.writeString(objProducto.getCantidad());
        }

    }

    public CLista_Agregar(Parcel in){
        readfromParcel(in);
    }

    private void readfromParcel(Parcel in) {
     this.clear();
        //Leemos el tamaño del array
        for (int i=0; i < size(); i++){
            CProducto objProducto = new CProducto();
            objProducto.setNombre(in.readString());
            objProducto.setCantidad(in.readString());
            this.add(objProducto);
        }
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){


        @Override
        public CLista_Agregar createFromParcel(Parcel in) {
            return new CLista_Agregar(in);
        }

        @Override
        public Object[] newArray(int size) {
            return new Object[0];
        }
    };

}
