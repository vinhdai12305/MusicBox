package com.aicloudflare.musicbox;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.List;

public class OptionAdapter extends ArrayAdapter<OptionItem> {

    public OptionAdapter(@NonNull Context context, @NonNull List<OptionItem> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item_option, parent, false);
        }

        // Lấy đối tượng tùy chọn (OptionItem) ở vị trí hiện tại
        OptionItem currentOption = getItem(position);

        // --- CODE ĐÃ ĐƯỢC SỬA LẠI CHO ĐÚNG ---

        // 1. Ánh xạ các View từ layout (kích hoạt lại các dòng đã bị vô hiệu hóa)
        ImageView iconView = listItemView.findViewById(R.id.iv_option_icon);
        TextView textView = listItemView.findViewById(R.id.tv_option_text);

        // 2. Kiểm tra để đảm bảo đối tượng không null trước khi sử dụng
        if (currentOption != null) {
            // 3. Đặt icon và text cho các View tương ứng
            iconView.setImageResource(currentOption.getIconId());
            textView.setText(currentOption.getText());
        }

        return listItemView;
    }
}
