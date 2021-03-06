package qlife.ui.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import qlife.R;
import qlife.Util;
import qlife.interfaces.IQLActionbarFragment;
import qlife.interfaces.IQLDrawerItem;


/**
 * Created by Carson on 02/12/2016.
 * Holds information pertinent to students
 */
public class StudentToolsFragment extends Fragment implements IQLActionbarFragment, IQLDrawerItem {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_student_tools, container, false);
        setActionbarTitle();

        final FragmentManager fm = getActivity().getSupportFragmentManager();

        CardView emergContactsCard = (CardView) v.findViewById(R.id.emerg_contacts_card);
        CardView engContactsCard = (CardView) v.findViewById(R.id.eng_contacts_card);
        CardView counsellingCard = (CardView) v.findViewById(R.id.counselling_card);
        CardView careerCard = (CardView) v.findViewById(R.id.career_card);
        CardView solusCard = (CardView) v.findViewById(R.id.solus_card);
        CardView outlookCard = (CardView) v.findViewById(R.id.outlook_card);
        CardView onqCard = (CardView) v.findViewById(R.id.onq_card);

        emergContactsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fm.beginTransaction().addToBackStack(null).replace(R.id.content_frame, new EmergContactsFragment()).commit();
            }
        });
        engContactsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fm.beginTransaction().addToBackStack(null).replace(R.id.content_frame, new EngContactsFragment()).commit();
            }
        });
        counsellingCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startBrowser(getString(R.string.counselling_url));
            }
        });
        careerCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startBrowser(getString(R.string.career_url));
            }
        });

        solusCard.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startBrowser(getString(R.string.solus_url));
            }
        });
        outlookCard.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startBrowser(getString(R.string.microsoft_url));
            }
        });
        onqCard.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startBrowser(getString(R.string.queens_url));
            }
        });
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        selectDrawer();
    }

    @Override
    public void onPause() {
        super.onPause();
        deselectDrawer();
    }

    private void startBrowser(String url) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    @Override
    public void setActionbarTitle() {
        Util.setActionbarTitle(getString(R.string.fragment_student_tools), (AppCompatActivity) getActivity());
    }

    @Override
    public void deselectDrawer() {
        Util.setDrawerItemSelected(getActivity(), R.id.nav_tools, false);
    }

    @Override
    public void selectDrawer() {
        Util.setDrawerItemSelected(getActivity(), R.id.nav_tools, true);
    }
}