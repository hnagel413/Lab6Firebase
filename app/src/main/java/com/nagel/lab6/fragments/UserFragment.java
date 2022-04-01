package com.nagel.lab6.fragments;

import android.os.Bundle;
import android.view.*;
import androidx.annotation.*;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.*;
import com.nagel.lab6.R;
import com.nagel.lab6.UserRecyclerAdapter;
import com.nagel.lab6.viewmodel.UserViewModel;

public class UserFragment extends Fragment {
    private UserViewModel userViewModel;
    private UserRecyclerAdapter userRecyclerAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user,container,false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        userRecyclerAdapter = new UserRecyclerAdapter();
        recyclerView.setAdapter(userRecyclerAdapter);
        return view;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        requireActivity().setTitle(getString(R.string.us_info));
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.getUserLiveData().observe(this, userArrayList ->
                userRecyclerAdapter.updateUserList(userArrayList));
        userViewModel.getLoggedOutMutableLiveData().observe(this, loggedOut ->{
            if(loggedOut){
                if(getView() != null) Navigation.findNavController(getView())
                        .navigate(R.id.action_userFragment_to_loginFragment);
            }
        });
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_user, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logOut){
            userViewModel.logOut();
        }
        return false;
    }
}
