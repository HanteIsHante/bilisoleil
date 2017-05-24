package com.yoyiyi.soleil.ui.fragment.home;

import android.support.v7.widget.GridLayoutManager;

import com.yoyiyi.soleil.R;
import com.yoyiyi.soleil.base.BaseRefreshFragment;
import com.yoyiyi.soleil.bean.live.HomeLive;
import com.yoyiyi.soleil.mvp.contract.home.LiveContract;
import com.yoyiyi.soleil.mvp.presenter.home.LivePresenter;
import com.yoyiyi.soleil.ui.adapter.home.section.live.LiveBannerSection;
import com.yoyiyi.soleil.ui.adapter.home.section.live.LiveEntranceSection;
import com.yoyiyi.soleil.ui.widget.section.SectionedRVAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zzq  作者 E-mail:   soleilyoyiyi@gmail.com
 * @date 创建时间：2017/5/23 14:23
 * 描述:推荐
 */

public class LiveFragment extends BaseRefreshFragment<LivePresenter, HomeLive.DataBean.PartitionsBean> implements LiveContract.View {

    private SectionedRVAdapter mSectionedAdapter;
    private List<HomeLive.DataBean.BannerBean> mBannerList = new ArrayList<>();

    public static LiveFragment newInstance() {
        return new LiveFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_recommend;
    }

    @Override
    protected void lazyLoadData() {
        mPresenter.getLiveData();
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void initRecyclerView() {
        mSectionedAdapter = new SectionedRVAdapter();
        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (mSectionedAdapter.getSectionItemViewType(position)) {
                    case SectionedRVAdapter.VIEW_TYPE_HEADER:
                        return 2;//2格
                    case SectionedRVAdapter.VIEW_TYPE_FOOTER:
                        return 2;//2格
                    default:
                        return 1;
                }
            }
        });
        mRecycler.setLayoutManager(mLayoutManager);
        mRecycler.setAdapter(mSectionedAdapter);
    }


    @Override
    protected void clear() {
        mBannerList.clear();
    }

    @Override
    public void showLive(HomeLive homeLive) {
        mList.addAll(homeLive.data.partitions);
        mBannerList.addAll(homeLive.data.banner);
        finishTask();
    }

    @Override
    protected void clearData() {
        super.clearData();
        mSectionedAdapter.removeAllSections();
    }

    @Override
    protected void finishTask() {
        mSectionedAdapter.addSection(new LiveBannerSection(mBannerList));
        mSectionedAdapter.addSection(new LiveEntranceSection());
        mSectionedAdapter.notifyDataSetChanged();
    }

}
