package com.sfb.baselib.widget.form;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sfb.baselib.R;
import com.sfb.baselib.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class LinearView extends LinearLayout implements View.OnClickListener {

    private static final int TYPE_OF_TEXT = 1;//正常文本表单类型
    private static final int TYPE_OF_ADD_CHILD = 2;//添加子项类型

    private static final int GRAVITY_LEFT = 1;//居左
    private static final int GRAVITY_RIGHT = 2;//居右
    private static final int GRAVITY_TOP = 3;//居上
    private static final int GRAVITY_BOTTOM = 4;//居下
    private static final int GRAVITY_CENTER = 5;//居中

    private final int SUFFIX_TEXT_ID = 0x110;//后缀文本的ID
    private final int FIRST_SUFFIX_IMAGE_ID = SUFFIX_TEXT_ID + 1;//第一个后缀图片的ID
    private final int SECOND_SUFFIX_IMAGE_ID = FIRST_SUFFIX_IMAGE_ID + 1;//第二个后缀图片的ID
    private final int LAYOUT_ADD_ID = SECOND_SUFFIX_IMAGE_ID + 1;//添加子项的ID

    private int type = TYPE_OF_TEXT;//表单类型
    private int linearBackground;//背景
    private String catalog;//目录
    private String title;//标题
    private String content;//内容
    private String hint;//hint
    private String suffix;//后缀文本
    private int titleColor;//标题文本颜色
    private int contentColor;//内容文本颜色
    private int suffixColor;//后缀文本颜色
    private int titleSize;//标题文本大小
    private int contentSize;//内容文本大小
    private int suffixSize;//后缀文本大小
    private int contentHeight;//内容文本高度
    private boolean isSuffixBox;//后缀文本框
    private boolean isRequired = false;//是否必选
    private boolean isInputAble = false;//是否可输入
    private boolean isDetail = false;//item是否显示详情
    private int maxLength;//content最大长度
    private int headerImage;//左边图标
    private int firstImage;//第一张图
    private int secondImage;//第二张图
    private int imageSize;//图片大小
    private int imagePadding;//图片padding
    private int linearPaddingLeft;//linear左边的padding
    private int linearPaddingRight;//linear右边的padding
    private int linearPaddingTop;//linear上边的padding
    private int linearPaddingBottom;//linear下边的padding
    private int titleGravity;//内容重心
    private int contentGravity;//内容重心
    private int inputType;
    private TextWatcher watcher;

    private TextView mTvTitle;
    private TextView mTvSuffix;
    private View mContentView;
    private LinearLayout mLayoutChild;
    private TextView mTvRedPoint;

    private int m3dp;//3dp
    private int m8dp;//8dp
    private int m10dp;//10dp
    private int m12dp;//12dp
    private int m26dp;//26dp
    private int m46dp;//46dp

    private LinearClickListener linearClickListener;

    public void setLinearClickListener(LinearClickListener linearClickListener) {
        this.linearClickListener = linearClickListener;
    }

    public LinearView(Context context) {
        super(context);
        //初始化数据
        initData(context, null);
    }

    public LinearView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //初始化数据
        initData(context, attrs);
        //设置 isDetail
        setDetail(isDetail);
        //初始化视图
        initView(context);
    }

    /**
     * 初始化数据
     *
     * @param context Context
     * @param attrs   AttributeSet
     */
    private void initData(Context context, @Nullable AttributeSet attrs) {
        this.setOrientation(VERTICAL);
        this.m3dp = CommonUtils.dp2px(context, 3);
        this.m8dp = CommonUtils.dp2px(context, 8);
        this.m10dp = CommonUtils.dp2px(context, 10);
        this.m12dp = CommonUtils.dp2px(context, 12);
        this.m26dp = CommonUtils.dp2px(context, 26);
        this.m46dp = CommonUtils.dp2px(context, 46);
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LinearView);
            type = typedArray.getInt(R.styleable.LinearView_linear_type, TYPE_OF_TEXT);
            linearBackground = typedArray.getResourceId(R.styleable.LinearView_linear_background, type == TYPE_OF_TEXT ? R.drawable.shape_stroke_bottom : R.color.white);
            catalog = typedArray.getString(R.styleable.LinearView_linear_catalog);
            title = typedArray.getString(R.styleable.LinearView_linear_title);
            content = typedArray.getString(R.styleable.LinearView_linear_content);
            hint = typedArray.getString(R.styleable.LinearView_linear_hint);
            suffix = typedArray.getString(R.styleable.LinearView_linear_suffix);
            titleColor = typedArray.getColor(R.styleable.LinearView_linear_title_color, context.getResources().getColor(R.color.text_black));
            contentColor = typedArray.getColor(R.styleable.LinearView_linear_content_color, context.getResources().getColor(R.color.text_color));
            suffixColor = typedArray.getColor(R.styleable.LinearView_linear_suffix_color, context.getResources().getColor(R.color.main_color));
            titleSize = typedArray.getInt(R.styleable.LinearView_linear_title_size, 14);
            contentSize = typedArray.getInt(R.styleable.LinearView_linear_content_size, 14);
            suffixSize = typedArray.getInt(R.styleable.LinearView_linear_suffix_size, 14);
            contentHeight = typedArray.getDimensionPixelSize(R.styleable.LinearView_linear_content_height, 0);
            isSuffixBox = typedArray.getBoolean(R.styleable.LinearView_linear_suffix_box, false);
            isRequired = typedArray.getBoolean(R.styleable.LinearView_linear_required, false);
            isInputAble = typedArray.getBoolean(R.styleable.LinearView_linear_input_able, false);
            isDetail = typedArray.getBoolean(R.styleable.LinearView_linear_item_is_detail, false);
            maxLength = typedArray.getInt(R.styleable.LinearView_linear_max_length, 0);
            headerImage = typedArray.getResourceId(R.styleable.LinearView_linear_header_image, 0);
            firstImage = typedArray.getResourceId(R.styleable.LinearView_linear_first_image, 0);
            secondImage = typedArray.getResourceId(R.styleable.LinearView_linear_second_image, 0);
            imageSize = typedArray.getDimensionPixelSize(R.styleable.LinearView_linear_image_size, m26dp);
            imagePadding = typedArray.getDimensionPixelSize(R.styleable.LinearView_linear_image_padding, m3dp);
            linearPaddingLeft = typedArray.getDimensionPixelSize(R.styleable.LinearView_linear_padding_left, m12dp);
            linearPaddingRight = typedArray.getDimensionPixelSize(R.styleable.LinearView_linear_padding_right, m12dp);
            linearPaddingTop = typedArray.getDimensionPixelSize(R.styleable.LinearView_linear_padding_top, m12dp);
            linearPaddingBottom = typedArray.getDimensionPixelSize(R.styleable.LinearView_linear_padding_bottom, m12dp);
            titleGravity = typedArray.getInt(R.styleable.LinearView_linear_title_gravity, GRAVITY_CENTER);
            contentGravity = typedArray.getInt(R.styleable.LinearView_linear_content_gravity, GRAVITY_LEFT);
            typedArray.recycle();
        } else {
            type = TYPE_OF_TEXT;
            linearBackground = type == TYPE_OF_TEXT ? R.drawable.shape_stroke_bottom : R.color.white;
            titleColor = context.getResources().getColor(R.color.text_black);
            contentColor = context.getResources().getColor(R.color.text_color);
            suffixColor = context.getResources().getColor(R.color.main_color);
            titleSize = 14;
            contentSize = 14;
            suffixSize = 14;
            imageSize = m26dp;
            imagePadding = m3dp;
            linearPaddingLeft = m12dp;
            linearPaddingRight = m12dp;
            linearPaddingTop = m12dp;
            linearPaddingBottom = m12dp;
            titleGravity = GRAVITY_CENTER;
            contentGravity = GRAVITY_LEFT;
        }
    }

    /**
     * 初始化视图
     *
     * @param context Context
     */
    private void initView(Context context) {
        switch (type) {
            case TYPE_OF_TEXT:
                //初始化正常文本表单类型
                initTypeOfText(context);
                break;
            case TYPE_OF_ADD_CHILD:
                //初始化视图(添加子项类型)
                initTypeOfAddChild(context);
                break;
            default:
                break;
        }
    }

    /**
     * 初始化正常文本表单类型
     *
     * @param context Context
     */
    private void initTypeOfText(Context context) {
        //创建目录
        if (!TextUtils.isEmpty(catalog)) {
            TextView mTvCatalog = createCatalog(context, catalog);
            this.addView(mTvCatalog);
        }
        //创建item
        LinearLayout layout = createItemLayout(context);
        this.addView(layout);
        //创建标题
        if (!TextUtils.isEmpty(title)) {
            RelativeLayout mTitleLayout = createTitleView(context, title, isRequired);
            layout.addView(mTitleLayout);
        }
        //创建内容视图
        if (isInputAble) {
            mContentView = createEditTextContent(context, content, hint);
            setInputType(inputType);
            addTextChangedListener(watcher);
            layout.addView(mContentView);
        } else {
            mContentView = createTextViewContent(context, content);
            layout.addView(mContentView);
        }
        //创建后缀文本
        mTvSuffix = createSuffixTextView(context, suffix);
        mTvSuffix.setOnClickListener(this);
        mTvSuffix.setId(SUFFIX_TEXT_ID);
        if (isSuffixBox && !TextUtils.isEmpty(suffix)) {
            mTvSuffix.setBackgroundResource(R.drawable.shape_blue_stroke_round);
        }
        layout.addView(mTvSuffix);
        //创建header图标
        if (headerImage != 0) {
            ImageView mIvHeader = createImageView(context, headerImage, 1);
            layout.addView(mIvHeader, 0);
        }
        //创建第一张后缀图片
        if (firstImage != 0) {
            ImageView mIvFirstSuffix = createImageView(context, firstImage, 2);
            mIvFirstSuffix.setId(FIRST_SUFFIX_IMAGE_ID);
            mIvFirstSuffix.setOnClickListener(this);
            layout.addView(mIvFirstSuffix);
        }
        //创建第二张后缀图片
        if (secondImage != 0) {
            ImageView mIvSecondSuffix = createImageView(context, secondImage, 3);
            mIvSecondSuffix.setId(SECOND_SUFFIX_IMAGE_ID);
            mIvSecondSuffix.setOnClickListener(this);
            layout.addView(mIvSecondSuffix);
        }
        //设置最大文本长度
        setMaxLength(maxLength);
        //设置点击事件
        this.setOnClickListener(this);
    }

    /**
     * 初始化视图(添加子项类型)
     *
     * @param context Context
     */
    private void initTypeOfAddChild(Context context) {
        mLayoutChild = new LinearLayout(context);
        mLayoutChild.setOrientation(LinearLayout.VERTICAL);
        this.addView(mLayoutChild);
        //创建目录
        if (!TextUtils.isEmpty(catalog)) {
            TextView mTvCatalog = createCatalog(context, catalog);
            mLayoutChild.addView(mTvCatalog);
        }
        if (!isDetail) {
            //创建添加项
            LinearLayout mLayoutAdd = new LinearLayout(context);
            mLayoutAdd.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            mLayoutAdd.setOrientation(LinearLayout.HORIZONTAL);
            mLayoutAdd.setGravity(Gravity.CENTER);
            mLayoutAdd.setPadding(0, m12dp, 0, m12dp);
            mLayoutAdd.setBackgroundResource(linearBackground);
            mLayoutAdd.setId(LAYOUT_ADD_ID);
            mLayoutAdd.setOnClickListener(this);
            mLayoutChild.addView(mLayoutAdd);

            //创建添加图片
            ImageView mIvAdd = new ImageView(context);
            mIvAdd.setLayoutParams(new LayoutParams(m26dp, m26dp));
            mIvAdd.setImageResource(R.drawable.ic_add);
            mLayoutAdd.addView(mIvAdd);
            //创建描述文本
            TextView mTvAdd = new TextView(context);
            mTvAdd.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            mTvAdd.setTextColor(getResources().getColor(R.color.main_color));
            mTvAdd.setText(content);
            mLayoutAdd.addView(mTvAdd);
        }
    }

    /**
     * 创建Item
     *
     * @param context Context
     * @return LinearLayout
     */
    @NonNull
    private LinearLayout createItemLayout(Context context) {
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setBackgroundResource(linearBackground);
        layout.setGravity(Gravity.CENTER_VERTICAL);
        layout.setPadding(linearPaddingLeft, linearPaddingTop, linearPaddingRight, linearPaddingBottom);
        layout.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return layout;
    }

    /**
     * 创建目录
     *
     * @param context Context
     * @return TextView
     */
    @NonNull
    private TextView createCatalog(Context context, String catalog) {
        TextView textView = new TextView(context);
        textView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, m26dp));
        textView.setTextSize(14);
        textView.setTextColor(context.getResources().getColor(R.color.gray_98));
        textView.setBackgroundColor(context.getResources().getColor(R.color.gray_f0));
        textView.setText(catalog);
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setPadding(m12dp, 0, 0, 0);
        return textView;
    }

    /**
     * 创建标题
     *
     * @param context    Context
     * @param title      String
     * @param isRequired boolean
     * @return TextView
     */
    @SuppressLint("ResourceType")
    @NonNull
    private RelativeLayout createTitleView(Context context, String title, boolean isRequired) {
        RelativeLayout layout = new RelativeLayout(context);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.rightMargin = m12dp;
        switch (titleGravity) {
            case GRAVITY_LEFT:
                params.gravity = Gravity.START;
                break;
            case GRAVITY_RIGHT:
                params.gravity = Gravity.END;
                break;
            case GRAVITY_TOP:
                params.gravity = Gravity.TOP;
                break;
            case GRAVITY_BOTTOM:
                params.gravity = Gravity.BOTTOM;
                break;
            case GRAVITY_CENTER:
                params.gravity = Gravity.CENTER_VERTICAL;
                break;
            default:
                params.gravity = Gravity.CENTER_VERTICAL;
                break;
        }
        layout.setLayoutParams(params);

        mTvTitle = new TextView(context);
        mTvTitle.setTextSize(titleSize);
        mTvTitle.setTextColor(titleColor);
        RelativeLayout.LayoutParams titleParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        titleParams.addRule(RelativeLayout.CENTER_VERTICAL);
        mTvTitle.setLayoutParams(titleParams);
        setCompoundDrawable(mTvTitle, isRequired);
        mTvTitle.setText(title);
        mTvTitle.setId(500);
        layout.addView(mTvTitle);

        if (!isRequired) {
            mTvRedPoint = new TextView(context);
            RelativeLayout.LayoutParams redParams = new RelativeLayout.LayoutParams(m3dp * 2, m3dp * 2);
            redParams.addRule(RelativeLayout.RIGHT_OF, mTvTitle.getId());
            redParams.leftMargin = m3dp;
            mTvRedPoint.setLayoutParams(redParams);
            mTvRedPoint.setBackgroundResource(R.drawable.shape_red_circle);
            mTvRedPoint.setVisibility(GONE);
            layout.addView(mTvRedPoint);
        }
        return layout;
    }

    /**
     * 创建文本框类型的内容视图
     *
     * @param context Context
     * @param content String
     * @return TextView
     */
    @NonNull
    private TextView createTextViewContent(Context context, String content) {
        TextView textView = new TextView(context);
        textView.setTextSize(contentSize);
        textView.setTextColor(contentColor);
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setBackgroundColor(Color.WHITE);
        LayoutParams params = new LayoutParams(0, contentHeight == 0 ? ViewGroup.LayoutParams.WRAP_CONTENT : contentHeight);
        params.weight = 1;
        params.rightMargin = m12dp;
        textView.setLayoutParams(params);
        textView.setText(content);
        switch (contentGravity) {
            case GRAVITY_LEFT:
                textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.START);
                break;
            case GRAVITY_RIGHT:
                textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.END);
                break;
            case GRAVITY_TOP:
                textView.setGravity(Gravity.TOP | Gravity.START);
                break;
            case GRAVITY_BOTTOM:
                textView.setGravity(Gravity.BOTTOM | Gravity.START);
                break;
            case GRAVITY_CENTER:
                textView.setGravity(Gravity.CENTER);
                break;
            default:
                textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.START);
                break;
        }
        return textView;
    }

    /**
     * 创建编辑框类型的内容视图
     *
     * @param context Context
     * @param content String
     * @param hint    String
     * @return EditText
     */
    @NonNull
    private EditText createEditTextContent(Context context, String content, String hint) {
        EditText editText = new EditText(context);
        editText.setTextSize(contentSize);
        editText.setTextColor(contentColor);
        editText.setGravity(Gravity.CENTER_VERTICAL);
        editText.setBackgroundColor(Color.WHITE);
        editText.setPadding(0, 0, 0, 0);
        LayoutParams params = new LayoutParams(0, contentHeight == 0 ? ViewGroup.LayoutParams.WRAP_CONTENT : contentHeight);
        params.weight = 1;
        params.rightMargin = m12dp;
        editText.setLayoutParams(params);
        editText.setText(content);
        editText.setHint(hint);
        switch (contentGravity) {
            case GRAVITY_LEFT:
                editText.setGravity(Gravity.CENTER_VERTICAL | Gravity.START);
                break;
            case GRAVITY_RIGHT:
                editText.setGravity(Gravity.CENTER_VERTICAL | Gravity.END);
                break;
            case GRAVITY_TOP:
                editText.setGravity(Gravity.TOP | Gravity.START);
                break;
            case GRAVITY_BOTTOM:
                editText.setGravity(Gravity.BOTTOM | Gravity.START);
                break;
            case GRAVITY_CENTER:
                editText.setGravity(Gravity.CENTER);
                break;
            default:
                editText.setGravity(Gravity.CENTER_VERTICAL | Gravity.START);
                break;
        }
        return editText;
    }

    /**
     * 创建后缀文本
     *
     * @param context Context
     * @param suffix  String
     * @return TextView
     */
    private TextView createSuffixTextView(Context context, String suffix) {
        TextView textView = new TextView(context);
        textView.setTextSize(suffixSize);
        textView.setPadding(m8dp, m3dp, m8dp, m3dp);
        textView.setTextColor(suffixColor);
        textView.setText(suffix);
        int visibility = !TextUtils.isEmpty(suffix) ? VISIBLE : GONE;
        textView.setVisibility(visibility);
        return textView;
    }

    /**
     * 创建ImageView
     *
     * @param context  Context
     * @param imageId  int
     * @param position int
     * @return ImageView
     */
    private ImageView createImageView(Context context, int imageId, int position) {
        ImageView imageView = new ImageView(context);
        //获取图片的资源id
        imageView.setImageResource(imageId);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        LayoutParams params = new LayoutParams(imageSize, imageSize);
        imageView.setPadding(imagePadding, imagePadding, imagePadding, imagePadding);
        switch (position) {
            case 1:
                params.rightMargin = m8dp;
                break;
            case 2:
                break;
            case 3:
                params.leftMargin = m12dp;
                break;
            default:
                break;
        }
        imageView.setLayoutParams(params);
        return imageView;
    }

    /**
     * 设置CompoundDrawables
     *
     * @param textView   TextView
     * @param isRequired boolean
     */
    private void setCompoundDrawable(TextView textView, boolean isRequired) {
        if (isRequired) {
            Drawable drawable = getResources().getDrawable(R.drawable.ic_star);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            textView.setCompoundDrawablePadding(m3dp);
            textView.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
        } else {
            textView.setCompoundDrawables(null, null, null, null);
        }
    }

    @Override
    public void onClick(View v) {
        if (linearClickListener != null) {
            switch (v.getId()) {
                case SUFFIX_TEXT_ID:
                    linearClickListener.onSuffixTextClick(getId());
                    break;
                case FIRST_SUFFIX_IMAGE_ID:
                    linearClickListener.onFirstSuffixImageClick(getId());
                    break;
                case SECOND_SUFFIX_IMAGE_ID:
                    linearClickListener.onSecondSuffixImageClick(getId());
                    break;
                case LAYOUT_ADD_ID:
                    linearClickListener.onLinearItemClick(getId());
                    break;
                default:
                    linearClickListener.onLinearItemClick(getId());
                    break;
            }
        }
    }

    /**
     * 获取子表单项
     */
    public int formChildIndex(View view) {
        if (type == TYPE_OF_ADD_CHILD) {
            if (mLayoutChild != null) {
                return mLayoutChild.indexOfChild(view);
            }
        } else {
            throw new IllegalArgumentException("This view of type can not add child.");
        }
        return -1;
    }

    /**
     * 获取子表单项
     */
    public View getFormChild(int index) {
        if (type == TYPE_OF_ADD_CHILD) {
            if (mLayoutChild != null) {
                return mLayoutChild.getChildAt(index);
            }
        } else {
            throw new IllegalArgumentException("This view of type can not add child.");
        }
        return null;
    }

    /**
     * 获取子表单项列表
     */
    public List<View> getAllFormChild() {
        List<View> list = new ArrayList<>();
        if (type == TYPE_OF_ADD_CHILD) {
            if (mLayoutChild != null) {
                int start = 0;
                int end = 0;
                if (TextUtils.isEmpty(catalog)) {
                    start = 0;
                } else {
                    start = 1;
                }
                if (isDetail) {
                    end = mLayoutChild.getChildCount();
                } else {
                    end = mLayoutChild.getChildCount() - 1;
                }
                for (int i = start; i < end; i++) {
                    list.add(mLayoutChild.getChildAt(i));
                }
                return list;
            }
        } else {
            throw new IllegalArgumentException("This view of type can not add child.");
        }
        return list;
    }

    /**
     * 添加子表单项
     *
     * @param view View
     */
    public void addFormChild(View view) {
        if (type == TYPE_OF_ADD_CHILD) {
            if (mLayoutChild != null) {
                if (isDetail) {
                    mLayoutChild.addView(view);
                } else {
                    mLayoutChild.addView(view, mLayoutChild.getChildCount() - 1);
                }
            }
        } else {
            throw new IllegalArgumentException("This view of type can not add child.");
        }
    }

    /**
     * 删除所有子表单项
     */
    public void removeAllFormChild() {
        if (type == TYPE_OF_ADD_CHILD) {
            if (mLayoutChild != null) {
                int start = 0;
                int end = 0;
                if (TextUtils.isEmpty(catalog)) {
                    start = 0;
                } else {
                    start = 1;
                }
                if (isDetail) {
                    end = mLayoutChild.getChildCount();
                } else {
                    end = mLayoutChild.getChildCount() - 1;
                }
                //此处需注意，每次删除从第一个开始删除，循环end次
                for (int i = start; i < end; i++) {
                    removeFormChild(start);
                }
            }
        } else {
            throw new IllegalArgumentException("This view of type can not add child.");
        }
    }

    /**
     * 删除子表单项
     *
     * @param index int
     */
    public void removeFormChild(int index) {
        if (mLayoutChild != null) {
            removeFormChild(mLayoutChild.getChildAt(index));
        }
    }

    /**
     * 删除子表单项
     *
     * @param view View
     */
    public void removeFormChild(View view) {
        if (type == TYPE_OF_ADD_CHILD && mLayoutChild != null) {
            mLayoutChild.removeView(view);
        } else {
            throw new IllegalArgumentException("This view of type can not add child.");
        }
    }

    public LinearView setCatalog(String catalog) {
        this.catalog = catalog == null ? "" : catalog;
        return this;
    }

    /**
     * 设置title
     *
     * @param title String
     */
    public LinearView setTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            this.title = isDetail ? title + ":" : title;
        } else {
            this.title = "";
        }
        if (mTvTitle != null) {
            mTvTitle.setText(this.title);
            int visibility = TextUtils.isEmpty(title) ? GONE : VISIBLE;
            mTvTitle.setVisibility(visibility);
        }
        return this;
    }

    /**
     * 获取文本
     */
    public String getText() {
        if (mContentView != null) {
            if (mContentView instanceof EditText) {
                return ((EditText) mContentView).getText().toString().trim();
            } else if (mContentView instanceof TextView) {
                return ((TextView) mContentView).getText().toString();
            } else {
                return "";
            }
        } else {
            throw new IllegalArgumentException("This view of type can not get text.");
        }
    }

    /**
     * 设置文本
     */
    private LinearView setContent(String text) {
        this.content = text;
        if (mContentView != null) {
            if (mContentView instanceof EditText) {
                ((EditText) mContentView).setText(text);
            } else if (mContentView instanceof TextView) {
                ((TextView) mContentView).setText(text);
            }
        }
        return this;
    }

    /**
     * 设置文本
     */
    public LinearView setText(String text) {
        setContent(text == null ? "" : text);
        return this;
    }

    /**
     * 设置文本
     */
    public LinearView setText(String text, boolean isEmptyGone) {
        setContent(text == null ? "" : text);
        if (TextUtils.isEmpty(text)) {
            if (isEmptyGone) {
                setVisibility(View.GONE);
            } else {
                setVisibility(View.VISIBLE);
            }
        } else {
            setVisibility(View.VISIBLE);
        }
        return this;
    }

    /**
     * 设置 编辑器是否可编辑
     * @param isEnable
     * @return
     */
    public LinearView setContentViewEnable(boolean isEnable) {
        if (null != mContentView && mContentView instanceof EditText) {
            mContentView.setEnabled(isEnable);
            mContentView.setFocusable(isEnable);
            mContentView.setFocusableInTouchMode(isEnable);
        }
        return this;
    }

    /**
     * 是否可以编辑
     * @return
     */
    public boolean isContentViewEnable() {
        if (null != mContentView && mContentView instanceof EditText) {
            return mContentView.isEnabled();
        }
        return false;
    }

    /**
     * 设置 hint
     */
    public LinearView setHint(String hint) {
        this.hint = hint;
        if (mContentView != null && mContentView instanceof EditText) {
            ((EditText) mContentView).setHint(hint);
        }
        return this;
    }

    /**
     * 设置 suffix
     */
    public LinearView setSuffix(String suffix) {
        this.suffix = suffix;
        if (mTvSuffix != null) {
            mTvSuffix.setText(suffix);
            int visibility = !TextUtils.isEmpty(suffix) ? VISIBLE : GONE;
            mTvSuffix.setVisibility(visibility);
        }
        return this;
    }

    /**
     * 设置 titleColor
     */
    public LinearView setTitleColor(int titleColor) {
        this.titleColor = titleColor;
        if (mTvTitle != null) {
            mTvTitle.setTextColor(titleColor);
        }
        return this;
    }

    /**
     * 设置 contentColor
     */
    public LinearView setContentColor(int contentColor) {
        this.contentColor = contentColor;
        if (mContentView != null) {
            if (mContentView instanceof EditText) {
                ((EditText) mContentView).setTextColor(contentColor);
            } else if (mContentView instanceof TextView) {
                ((TextView) mContentView).setTextColor(contentColor);
            }
        }
        return this;
    }

    /**
     * 设置 suffixColor
     */
    public LinearView setSuffixColor(int suffixColor) {
        this.suffixColor = suffixColor;
        if (mTvSuffix != null) {
            mTvSuffix.setTextColor(suffixColor);
        }
        return this;
    }

    /**
     * 设置 titleSize
     */
    public LinearView setTitleSize(int titleSize) {
        this.titleSize = titleSize;
        if (mTvTitle != null) {
            mTvTitle.setTextSize(titleSize);
        }
        return this;
    }

    /**
     * 设置 contentSize
     */
    public LinearView setContentSize(int contentSize) {
        this.contentSize = contentSize;
        if (mContentView != null) {
            if (mContentView instanceof EditText) {
                ((EditText) mContentView).setTextSize(contentSize);
            } else if (mContentView instanceof TextView) {
                ((TextView) mContentView).setTextSize(contentSize);
            }
        }
        return this;
    }

    /**
     * 设置 suffixSize
     */
    public LinearView setSuffixSize(int suffixSize) {
        this.suffixSize = suffixSize;
        if (mTvSuffix != null) {
            mTvSuffix.setTextSize(suffixSize);
        }
        return this;
    }

    /**
     * 设置文本限制
     *
     * @param inputType 输入类型
     */
    public LinearView setInputType(int inputType) {
        this.inputType = inputType;
        if (isInputAble && inputType != 0) {
            if (mContentView != null && mContentView instanceof EditText) {
                ((EditText) mContentView).setInputType(inputType);
            }
        }
        return this;
    }

    /**
     * 设置光标的位置
     *
     * @param position 光标的位置
     */
    public LinearView setSelection(int position) {
        if (isInputAble) {
            if (mContentView != null && mContentView instanceof EditText) {
                ((EditText) mContentView).setSelection(position);
            }
        }
        return this;
    }

    /**
     * 设置最大文本长度
     */
    public LinearView setMaxLength(int maxLength) {
        this.maxLength = maxLength;
        if (mContentView != null) {
            if (maxLength != 0) {
                if (mContentView instanceof EditText) {
                    ((EditText) mContentView).setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
                } else if (mContentView instanceof TextView) {
                    ((TextView) mContentView).setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
                }
            }
        }
        return this;
    }

    /**
     * 设置文本输入监听
     */
    public LinearView addTextChangedListener(TextWatcher watcher) {
        this.watcher = watcher;
        if (mContentView != null && watcher != null && mContentView instanceof EditText) {
            ((EditText) mContentView).addTextChangedListener(watcher);
        }
        return this;
    }

    /**
     * 设置后缀文本显示
     *
     * @param visibility boolean
     */
    public LinearView setSuffixVisibility(boolean visibility) {
        if (mTvSuffix != null) {
            mTvSuffix.setVisibility(visibility ? VISIBLE : GONE);
        }
        return this;
    }

    /**
     * 判断是否必填项
     */
    public boolean isRequired() {
        return isRequired;
    }

    /**
     * 是否显示标记红点
     *
     * @param isShow boolean
     */
    public LinearView setRedPoint(boolean isShow) {
        if (mTvRedPoint != null) {
            mTvRedPoint.setVisibility(isShow ? VISIBLE : GONE);
        }
        return this;
    }

    /**
     * 设置 isRequired
     *
     * @param isRequired boolean
     */
    public LinearView setRequired(boolean isRequired) {
        this.isRequired = isRequired;
        if (mTvTitle != null) {
            setCompoundDrawable(mTvTitle, isRequired);
        }
        return this;
    }

    /**
     * 设置 isDetail
     *
     * @param detail boolean
     */
    public LinearView setDetail(boolean detail) {
        isDetail = detail;
        if (isDetail) {
            isInputAble = false;
            isRequired = false;
            firstImage = 0;
            secondImage = 0;
            contentGravity = GRAVITY_LEFT;
            if (type != TYPE_OF_ADD_CHILD) {
                title = title + ":";
            }
        }
        return this;
    }

    /**
     * 设置 isInputAble
     *
     * @param isInputAble boolean
     */
    public LinearView setInputAble(boolean isInputAble) {
        this.isInputAble = isInputAble;
        return this;
    }

    public LinearView setFirstImage(int firstImage) {
        this.firstImage = firstImage;
        return this;
    }

    public LinearView setSecondImage(int secondImage) {
        this.secondImage = secondImage;
        return this;
    }

    public LinearView setLinearBackground(int linearBackground) {
        this.linearBackground = linearBackground;
        return this;
    }

    /**
     * 更新UI
     */
    public void rebuildView() {
        removeAllViews();
        initView(getContext());
    }
}
