package club.ccit.home.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.LoadState;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import club.ccit.home.R;

/**
 * FileName: LoadStateAdapter
 *
 * @author: 张帅威
 * Date: 2022/5/17 16:14
 * Description:
 * Version:
 */
public class FooterLoadStateAdapter extends androidx.paging.LoadStateAdapter<FooterLoadStateAdapter.ViewHolder> {
    private View.OnClickListener mRetryCallback;

    public FooterLoadStateAdapter(View.OnClickListener mRetryCallback) {
        this.mRetryCallback = mRetryCallback;
    }

    @Override
    public void onBindViewHolder(@NotNull ViewHolder viewHolder, @NotNull LoadState loadState) {
        viewHolder.bind(loadState);
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup viewGroup, @NotNull LoadState loadState) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_load_state, viewGroup, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout mProgressBar, noData;
        private TextView mErrorMsg;
        private TextView mRetry;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mProgressBar = itemView.findViewById(R.id.progressBar);
            mErrorMsg = itemView.findViewById(R.id.errorMsg);
            mRetry = itemView.findViewById(R.id.retryButton);
            noData = itemView.findViewById(R.id.noData);
            mRetry.setOnClickListener(mRetryCallback);
        }

        public void bind(LoadState loadState) {
            if (loadState instanceof LoadState.Error) {
                LoadState.Error loadStateError = (LoadState.Error) loadState;
                mErrorMsg.setText(loadStateError.getError().getLocalizedMessage());
            }
            mProgressBar.setVisibility(loadState instanceof LoadState.Loading
                    ? View.VISIBLE : View.GONE);
            mRetry.setVisibility(loadState instanceof LoadState.Error
                    ? View.VISIBLE : View.GONE);
            mErrorMsg.setVisibility(loadState instanceof LoadState.Error
                    ? View.VISIBLE : View.GONE);
            noData.setVisibility(loadState instanceof LoadState.NotLoading
                    ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public boolean displayLoadStateAsItem(@NonNull LoadState loadState) {
        return super.displayLoadStateAsItem(loadState) || (loadState instanceof LoadState.NotLoading && loadState.getEndOfPaginationReached());
    }
}
