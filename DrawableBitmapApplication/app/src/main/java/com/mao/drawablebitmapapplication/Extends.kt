import android.content.res.Resources
import android.util.TypedValue

/**
 *  author : maoqitian
 *  date : 2021/3/7 22:45
 *  description : 扩展函数
 */

// dp 值转成 px 像素
val Float.dp
   get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,this,Resources.getSystem().displayMetrics)


// Int 值 dp 转 px
val Int.dp
get() = this.toFloat().dp