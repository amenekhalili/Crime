package com.example.crime.controller.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crime.Model.Crime;
import com.example.crime.R;
import com.example.crime.Repository.CrimeDBRepository;
import com.example.crime.Repository.IRepository;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.UUID;


public class CrimeListFragment extends Fragment {

    public static final String SUBTITLE_VISIBILITY = "subtitleVisibility";
    public static final String ARG_USERNAME = "ARG_USERNAME";
    private RecyclerView mRecyclerView;
    private IRepository mCrimeRepository;
    private CrimeAdapter crimeAdapter;
    private boolean isSubtitleVisible = false;
    private String UserName;
    private Callbacks mCallbacks;
    private List<Crime> mCrimes;


    public static CrimeListFragment newInstance(String UserName) {

        Bundle args = new Bundle();
        args.putString(ARG_USERNAME, UserName);
        CrimeListFragment fragment = new CrimeListFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public CrimeListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCrimeRepository = CrimeDBRepository.getIsInstance(getActivity());
        UserName = getArguments().getString(ARG_USERNAME, null);
        setHasOptionsMenu(true);
        if (savedInstanceState != null)
            isSubtitleVisible = savedInstanceState.getBoolean(SUBTITLE_VISIBILITY);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);

        findViews(view);
        initViews();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu);
        MenuItem item = menu.findItem(R.id.menu_item_subtitle_crime);
        setTxtSubTitle(item);

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof Callbacks)
            mCallbacks = (Callbacks) context;
        else {
            throw new ClassCastException(context.toString()
                    + "must implement onFragmentInteractionListener");

        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.meu_item_add_crime:
                Crime crime = new Crime();
                mCrimeRepository.insertCrime(crime);
                mCallbacks.onCrimeSelected(crime);
                updateUI();

                return true;
            case R.id.menu_item_subtitle_crime:
                isSubtitleVisible = !isSubtitleVisible;
                updateSubtitle();
                setTxtSubTitle(item);
                return true;
            case R.id.remove_multiple_Crime:
                List<Crime> crimes = mCrimeRepository.getCrimes();
                for (int i = 0; i < crimes.size(); i++) {
                    UUID id = crimes.get(i).getId();
                    if (mCrimeRepository.getCrime(id).isChecked())
                        mCrimeRepository.deleteCrime(crimes.get(i));

                }

                int size = mCrimeRepository.sizeList();
                mCallbacks.setListPage(size);
                updateUI();
                //  setListPage();
                return true;
            case R.id.menu_sselect_all:
                List<Crime> crimes1 = mCrimeRepository.getCrimes();

                for (int i = 0; i < crimes1.size(); i++) {
                    UUID id = crimes1.get(i).getId();
                    Crime crime1 = mCrimeRepository.getCrime(id);
                    crime1.setChecked(true);
                    mCrimeRepository.updateCrime(crime1);

                }
                int size1 = mCrimeRepository.sizeList();
                mCallbacks.setListPage(size1);
                updateUI();
                // setListPage();
                return true;
            case R.id.menu_unselect_all:

                List<Crime> crimes2 = mCrimeRepository.getCrimes();

                for (int i = 0; i < crimes2.size(); i++) {
                    UUID id = crimes2.get(i).getId();
                    Crime crime1 = mCrimeRepository.getCrime(id);
                    crime1.setChecked(false);
                    mCrimeRepository.updateCrime(crime1);

                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }




    private void setTxtSubTitle(@NonNull MenuItem item) {
        item.setTitle(isSubtitleVisible ? R.string.subtitle_item_hide :
                R.string.subtitle_item_show);
    }

    private void updateSubtitle() {
        int size = mCrimeRepository.sizeList();
        String numberOfCrimes = isSubtitleVisible ? size + " Crime" : null;
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(numberOfCrimes);
        activity.getSupportActionBar().setSubtitle(UserName);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean(SUBTITLE_VISIBILITY, isSubtitleVisible);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
        updateSubtitle();

    }

    public void updateUI() {
        List<Crime> crimes = mCrimeRepository.getCrimes();
        if (crimeAdapter == null) {
            crimeAdapter = new CrimeAdapter(crimes);
            mRecyclerView.setAdapter(crimeAdapter);
        } else {
            crimeAdapter.setCrimes(crimes);
            crimeAdapter.notifyDataSetChanged();
        }

    }






    private void findViews(View view) {
        mRecyclerView = view.findViewById(R.id.list_recyclerview);
    }

    private void initViews() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();


        final ItemTouchHelper mHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback
                (0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT){
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {



                final int position = viewHolder.getAdapterPosition();
                final Crime  crime = mCrimes.get(position);
                mCrimes.remove(position);
                crimeAdapter.notifyDataSetChanged();
                mCrimeRepository.deleteCrime(crime);
                mCallbacks.setListPage(mCrimeRepository.sizeList());

              final Snackbar snackbar = Snackbar.make(getView(),"delete After 5 seconds",Snackbar.LENGTH_SHORT);
              snackbar.setAction("UNDO", new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                     /* mCrimes.add(position , crime);
                      crimeAdapter.notifyDataSetChanged();
                      mRecyclerView.scrollToPosition(position);
                      mCrimeRepository.insertCrime(crime);*/

                  }
              });
              snackbar.show();


             }
        });
        mHelper.attachToRecyclerView(mRecyclerView);


    }

    private class CrimeHolder extends RecyclerView.ViewHolder {

        private TextView mTextViewTitle;
        private TextView mTextViewDate;
        private ImageView mImageViewSolved;
        private CheckBox mCheckBoxDelete;
        private Crime mCrime;

        public CrimeHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewTitle = itemView.findViewById(R.id.row_title_crime);
            mTextViewDate = itemView.findViewById(R.id.row_date_crime);
            mImageViewSolved = itemView.findViewById(R.id.image_solved);
            mCheckBoxDelete = itemView.findViewById(R.id.checkBox_delete);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mCallbacks.onCrimeSelected(mCrime);

                }
            });

            mCheckBoxDelete.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    mCrime.setChecked(isChecked);
                    mCrimeRepository.updateCrime(mCrime);

                }
            });
        }

        public void bindCrime(Crime crime) {
            mTextViewTitle.setText(crime.getTitle());
            mTextViewDate.setText(crime.getDate().toString());
            mCrime = crime;
            mImageViewSolved.setVisibility(crime.isSolved() ? View.VISIBLE : View.GONE);
            mCheckBoxDelete.setChecked(crime.isChecked() ? true : false);
        }


    }


    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {

     //  private List<Crime> mCrimes;

        public List<Crime> getCrimes() {
            return mCrimes;
        }

        public void setCrimes(List<Crime> crimes) {
            mCrimes = crimes;
        }

        public CrimeAdapter(List<Crime> crimes) {
            mCrimes = crimes;
        }





        @Override
        public int getItemCount() {
            return mCrimes.size();
        }

        @NonNull
        @Override
        public CrimeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.crime_row_list, parent, false);

            CrimeHolder crimeHolder = new CrimeHolder(view);
            return crimeHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull CrimeHolder holder, int position) {
            Crime crime = mCrimes.get(position);
            holder.bindCrime(crime);
        }


    }





    public interface Callbacks {

        void onCrimeSelected(Crime crime);
        void setListPage(int size);
    }

}

