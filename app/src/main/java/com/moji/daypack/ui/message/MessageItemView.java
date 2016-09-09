package com.moji.daypack.ui.message;

import android.content.Context;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.moji.daypack.R;
import com.moji.daypack.data.model.Message;
import com.moji.daypack.data.model.impl.SystemMessageContent;
import com.moji.daypack.ui.common.adapter.IAdapterView;
import com.moji.daypack.util.HtmlUtils;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 7/21/16
 * Time: 6:01 PM
 * Desc: MessageItemView
 */
public class MessageItemView extends RelativeLayout implements IAdapterView<Message> {
    private static final String DATE_FORMATTER = "yyyy-MM-dd HH:mm";

    @Bind(R.id.text_view_message)
    TextView textViewMessage;
    @Bind(R.id.text_view_time)
    TextView textViewTime;

    private SimpleDateFormat mDateFormatter;

    public MessageItemView(Context context) {
        super(context);
        View.inflate(context, R.layout.item_message, this);
        ButterKnife.bind(this);
        mDateFormatter = new SimpleDateFormat(DATE_FORMATTER, Locale.getDefault());
    }

    @Override
    public void bind(Message message, int position) {
        if (message.getContent() instanceof SystemMessageContent) {
            SystemMessageContent messageContent = (SystemMessageContent) message.getContent();
            String content = messageContent.getTitle();
            List<String> urls = HtmlUtils.getUrlsFromHtml(content);
            if (urls.size() > 0) {
                messageContent.setLink(urls.get(0));
            }
            textViewMessage.setText(getPureMessageContent(content));
        }
        textViewTime.setText(mDateFormatter.format(message.getCreatedAt()));
    }

    private Spannable getPureMessageContent(String originalContent) {
        Spanned spannedHtmlText = Html.fromHtml(originalContent);
        String pureContent = spannedHtmlText.toString();
        final String SPAN_TO_REMOVE = "\n\n";
        if (pureContent.endsWith(SPAN_TO_REMOVE)) {
            SpannableStringBuilder stringBuilder = new SpannableStringBuilder(spannedHtmlText);
            stringBuilder.replace(stringBuilder.length() - SPAN_TO_REMOVE.length(), stringBuilder.length(), "");
            return stringBuilder;
        }
        return new SpannableStringBuilder(spannedHtmlText.toString().replaceAll(SPAN_TO_REMOVE, ""));
    }
}
