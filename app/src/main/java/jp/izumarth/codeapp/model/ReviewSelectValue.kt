package jp.izumarth.codeapp.model

import androidx.compose.runtime.Immutable

@Immutable
enum class ReviewSelectValue(
    val id: String,
    val label: String,
    val values: List<String>,
    val description: String,
) {
    Color(
        id = "color",
        label = "色",
        values = listOf("Light", "Gold", "Brown", "Black"),
        "ピルスナーは淡色、スタウトは濃い茶色〜黒のようにスタイルによって色の傾向がある"
    ),

    Transparency(
        id = "transparency",
        label = "透明度",
        values = listOf("High", "Low"),
        "透明か、濁っているか"
    ),

    Carbonation(
        id = "carbonation",
        label = "炭酸",
        values = listOf("High", "Normal", "Low"),
        "目で見た際に炭酸ガスが見えるかどうか"
    ),

    HopsFlavor(
        id = "hopsFlavor",
        label = "ホップの香り",
        values = listOf("No", "Orange", "Rose", "Mint", "Earthy"),
        "ホップは花や柑橘、スパイスのような香りがします"
    ),

    MaltFlavor(
        id = "maltFlavor",
        label = "モルトの香り",
        values = listOf("No", "Toast", "Caramel", "Coffee", "Chocolate"),
        "モルトはコーヒー、チョコレート、カラメルのような香りがします"
    ),

    EsterFlavor(
        id = "esterFlavor",
        label = "エステルの香り",
        values = listOf("No", "Banana", "Apple", "Pear", "Strawberry"),
        "エステルはビール酵母由来で、果物のようなフルーティーな香りがします"
    ),

    Bitterness(
        id = "bitterness",
        label = "苦味",
        values = listOf("Weakness", "Balance", "Strong"),
        "モルトが弱い場合は苦く、強い場合はあまり感じません。モルトとホップのバランスが良いと甘味と苦味双方感じることができます。"
    ),

    Sweetness(
        id = "sweetness",
        label = "甘味",
        values = listOf("Weakness", "Balance", "Strong"),
        description = "フルーツビール等を除き、ビールの甘さは基本的に原料であるモルトから来ます。",
    ),

    Body(
        id = "body",
        label = "コク",
        values = listOf("FullBody", "Medium", "Light"),
        description = "フルボディなビールはアルコール度数が高かったり苦味が強い。反面ライトなビールは苦さも弱く風味も全体的に薄く飲みやすい",
    ),

    AfterTaste(
        id = "afterTaste",
        label = "キレ",
        values = listOf("Sharp", "No Sharpness"),
        description = "口に含んだ際の最初の味と後味の強弱とその後味の消えの良さをキレとする",
    ),

    TotalRate(
        id = "totalRate",
        label = "総合評価",
        values = listOf("1", "2", "3", "4", "5"),
        description = "総合的な味の評価",
    ),
    ;
}