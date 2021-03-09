import android.content.res.Resources
import android.util.TypedValue

/**
 *  author : maoqitian
 *  date : 2021/3/7 22:45
 *  description : 扩展函数
 */

val Float.pxtodp
   get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,this,Resources.getSystem().displayMetrics)