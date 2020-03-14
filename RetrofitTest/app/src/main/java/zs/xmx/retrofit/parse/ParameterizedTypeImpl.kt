package zs.xmx.retrofit.parse

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/*
 * @创建者     默小铭
 * @博客       http://blog.csdn.net/u012792686
 * @本类描述	  生成解析泛型对象的Type实现类,目前仅用于Gson
 * @内容描述   用于解决泛型擦除问题
 * @使用说明   传泛型类的子类
 *            如: DataInfo dataInfo =new ParameterizedTypeImpl<DataInfo>(){}.deal(jsonStr)
 *            实例化时必须带上{},否则获取到的superclass为Object
 *
 *
 *   todo  研究下怎么把BaseResp拿进来T获取?还是构造方法传进来(待定)
 *   todo  FastJson不可用,需要看FastJson源码再处理(待定)
 *
 *
 */
open class ParameterizedTypeImpl<T> : ParameterizedType {

    /**
     * 返回BaseResp<T> T的类型
     */
    fun getType(): Type {
        return this
    }

    /**
     * 返回确切的泛型参数, 如Map<String></String>, Integer>返回[String, Integer]
     */
    override fun getActualTypeArguments(): Array<Type> {
        val genericSuperclass = javaClass.genericSuperclass //getGenericSuperclass()获得带有泛型的父类
        if (genericSuperclass is Class<*>) {
            throw RuntimeException("Missing type parameter.")
        }
        val parameterized = genericSuperclass as ParameterizedType
        return parameterized.actualTypeArguments
    }

    /**
     * 返回所属类型. 如,当前类型为O<T>.I<S>, 则返回O<T>. 顶级类型将返回null
    </T></S></T> */
    override fun getOwnerType(): Type? {
        return null
    }

    /**
     * 返回当前class或interface声明的类型, 如List返回List
     */
    override fun getRawType(): Type {
        return BaseResp::class.java
    }

}
