package com.kaanburaksener.bench.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

import com.kaanburaksener.bench.ui.fragment.BrowseRequestsFragment;
import com.kaanburaksener.bench.ui.fragment.MakeRequestFragment;
import com.kaanburaksener.bench.ui.fragment.MyAccountFragment;

import com.kaanburaksener.bench.R;

/**
 * Created by kaanburaksener on 30/03/16.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    Context context;
    FragmentManager fragmentManager;
    int numbOfTabs;

    final int[] ICONS = new int[] {
            R.drawable.ic_browse_requests,
            R.drawable.ic_make_request,
            R.drawable.ic_my_account
    };

    public ViewPagerAdapter(FragmentManager fragmentManager, int numbOfTabs, Context context) {
        super(fragmentManager);
        this.fragmentManager = fragmentManager;
        this.context = context;
        this.numbOfTabs = numbOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0) {
            BrowseRequestsFragment browseRequestsFragment = new BrowseRequestsFragment();
            return browseRequestsFragment;
        } else if(position == 1) {
            MakeRequestFragment makeRequestFragment = new MakeRequestFragment();
            return makeRequestFragment;
        } else {
            MyAccountFragment myAccountFragment = new MyAccountFragment();
            return myAccountFragment;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Drawable image = ContextCompat.getDrawable(context, ICONS[position]);
        image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
        SpannableString sb = new SpannableString(" ");
        ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sb;
    }

    @Override
    public int getCount() {
        return numbOfTabs;
    }
}
