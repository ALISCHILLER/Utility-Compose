package com.msa.utilitycompose.utils.currencyUtil



import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.ParseException

/**
 * کلاس Currency برای مدیریت مقادیر ارزی
 * این کلاس امکان انجام عملیات‌های مختلف مثل جمع، تفریق، ضرب، تقسیم و قالب‌بندی خروجی به فرمت ارزی را فراهم می‌آورد.
 * نویسنده: علی سلیمانی
 */
class Currency(private var value: BigDecimal) {

    // سازنده که مقدار را از نوع String می‌گیرد
    constructor(value: String) : this(parse(value))

    // سازنده که مقدار را از نوع Number می‌گیرد
    constructor(value: Number) : this(BigDecimal(value.toString()).setScale(2, RoundingMode.HALF_UP))

    /**
     * متد جمع دو ارز
     * این متد مقدار فعلی را با مقدار ارز دیگر جمع می‌کند
     */
    fun add(currency: Currency): Currency {
        return Currency(value.add(currency.value))
    }

    /**
     * متد جمع یک ارز با یک عدد
     * این متد مقدار فعلی را با یک عدد از نوع Number جمع می‌کند
     */
    fun add(number: Number): Currency {
        val currency = Currency(number)
        return Currency(value.add(currency.value))
    }

    /**
     * متد تفریق دو ارز
     * این متد مقدار فعلی را از مقدار ارز دیگر کم می‌کند
     */
    fun subtract(currency: Currency): Currency {
        return Currency(value.subtract(currency.value))
    }

    /**
     * متد ضرب دو ارز
     * این متد مقدار فعلی را در مقدار ارز دیگر ضرب می‌کند
     */
    fun multiply(currency: Currency): Currency {
        return Currency(value.multiply(currency.value))
    }

    /**
     * متد تقسیم دو ارز
     * این متد مقدار فعلی را بر مقدار ارز دیگر تقسیم می‌کند
     * می‌توان دقت و نوع رند کردن را نیز مشخص کرد
     */
    fun divide(currency: Currency, scale: Int = 2, roundingMode: RoundingMode = RoundingMode.HALF_UP): Currency {
        return Currency(value.divide(currency.value, scale, roundingMode))
    }

    /**
     * تبدیل مقدار به فرمت ارزی با جداکننده گروهی
     * این متد عدد را به رشته‌ای با قالب ارزی و جداکننده گروهی تبدیل می‌کند
     */
    fun toFormattedString(): String {
        val symbols = DecimalFormatSymbols.getInstance()
        symbols.groupingSeparator = ','  // جداکننده گروهی برای هزارگان
        val formatter = DecimalFormat("###,###.##", symbols)
        return formatter.format(value)
    }

    /**
     * تبدیل مقدار به رشته
     * این متد مقدار ارز را به شکل رشته‌ای ساده برمی‌گرداند
     */
    override fun toString(): String {
        return value.toString()
    }

    companion object {
        // مقادیر ثابت برای 0، 1، 10 و 100
        val ZERO = Currency(0)
        val ONE = Currency(1)
        val TEN = Currency(10)
        val HUNDRED = Currency(100)

        /**
         * متد تجزیه رشته به مقدار ارزی
         * این متد رشته را به عدد نوع BigDecimal تبدیل می‌کند
         * در صورتی که فرمت معتبر نباشد، خطا می‌دهد
         */
        private fun parse(value: String): BigDecimal {
            // حذف جداکننده‌های هزارگان
            val cleanedValue = value.replace(",", "")
            return try {
                // تبدیل به BigDecimal و ارجاع به مقدار پاک‌شده
                BigDecimal(cleanedValue)
            } catch (e: NumberFormatException) {
                // در صورت وجود خطا در فرمت، استثنا ایجاد می‌کند
                throw ParseException("Invalid currency format: $value", 0)
            }
        }
    }
}
