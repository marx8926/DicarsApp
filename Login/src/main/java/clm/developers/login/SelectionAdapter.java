package clm.developers.login;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;


public class SelectionAdapter extends ArrayAdapter<String> {
    private ArrayList<Integer> mSelection = new ArrayList<Integer>();

    public SelectionAdapter(Context context, int resource,
                            int textViewResourceId, List<String> objects){
        super(context, resource, textViewResourceId, objects);
    }

    public void setNewSelection(int position){
        mSelection.add(position);
        notifyDataSetChanged();
    }

    public ArrayList<Integer> getCurrentCheckedPosition() {
        return mSelection;
    }

    public void  removeSelection(int position){
        mSelection.remove(Integer.valueOf(position));
        notifyDataSetChanged();
    }

    public void clearSelection(){
        mSelection = new ArrayList<Integer>();
        notifyDataSetChanged();
    }

    public int getSelectionCount(){
        return  mSelection.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = super.getView(position, convertView, parent);
        v.setBackgroundColor(getContext().getResources().getColor(android.R.color.transparent));

        if (mSelection.contains(position)){
            v.setBackgroundColor(getContext().getResources().getColor(android.R.color.tab_indicator_text));
        }

        return v;
    }
}
