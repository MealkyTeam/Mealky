package com.kuba.mealky.presentation.meals

import com.kuba.mealky.domain.model.Meal
import com.kuba.mealky.presentation.commons.presenter.BasePresenter
import com.kuba.mealky.presentation.commons.presenter.BaseUI
import javax.inject.Inject


class MealListPresenter @Inject constructor(
//TODO add useCase
//        private val getMealsUseCase;

) : BasePresenter<MealListPresenter.UI>() {

    private var meals: MutableList<Meal> = mutableListOf()

    fun loadMeals() {
        //Mocked meals, remove
        meals = mutableListOf(
                Meal(1, "Pumpkin purée", 10, "To steam the pumpkin, peel and seed it, then cut into evenly sized cubes. Put the cubes in a steamer or colander set over a pan of simmering water and cook for 10 mins. Test with the point of a knife and cook for a further 5 mins if not cooked through. Mash and leave to cool. Alternatively, to microwave the pumpkin, cut it in half (no need to peel it or cut out the seeds) and sit cut-side up in the microwave. Cook for 20 mins, then check the flesh is soft by poking it with a fork. Keep cooking if you’d like it softer. Scoop the flesh into a bowl, then mash and leave to cool.\n" +
                        "\n", listOf("https://www.bbcgoodfood.com/sites/default/files/styles/recipe/public/recipe/recipe-image/2018/10/pumpkin-puree-main.jpg?itok=WCkCfUo4")),
                Meal(2, "Spiced lemon & ginger biscuits", 20, "Mix the butter and sugar with a wooden spoon. Stir in the spices, lemon zest and flour, then tip in the candied lemon peel and stem ginger – you might need to get your hands in to bring the mix together as a dough. Divide the dough in two and shape each half into a log about 5cm across. Wrap in cling film, then chill for 1 hr. You can freeze the unbaked dough for up to three months.\n" +
                        "\n" +
                        "Heat oven to 180C/160C fan/gas 4. Slice the logs into 1cm-thick rounds, place on two baking trays lined with baking parchment and bake for 12-15 mins. Leave to cool completely on the tray.\n" +
                        "\n" +
                        "Mix the lemon juice with the icing sugar to make a thin glaze. Brush over the biscuits and leave to set. Will keep for three days in an airtight container.", listOf("https://www.bbcgoodfood.com/sites/default/files/styles/recipe/public/recipe/recipe-image/2018/10/shortbread.jpg?itok=x6IFNG6J")),
                Meal(3, "Easy-peasy fruitcake", 30, "Put the rum (or brandy), orange zest and juice and mixed dried fruit in a bowl and stir. Leave to soak overnight.\n" +
                        "\n" +
                        "Heat oven to 170C/150C fan/gas 3½. Double line a 20cm tin with baking parchment. Beat the butter and sugar together until light and fluffy. Whisk in the eggs one by one, then fold in the almonds and flour. Add a pinch of salt and fold in the soaked fruit mixture (and any remaining liquid in the bowl), along with the nuts, candied peel and ginger. Spoon the mixture into the tin and level the surface.\n" +
                        "\n" +
                        "Bake for 1 hr, then turn the oven down to 150C/130C fan/gas 2 and bake for a further 2 hrs. Check the cake to see if it's pulling away from the sides of the tin and feels firm on top. If you need to, keep cooking for a further 15 mins. Cool in the tin. If storing in the tin, wrap the cake tightly first. Will freeze for up to two months.\n" +
                        "\n" +
                        "To decorate, brush the cake with the apricot jam (or glaze) and arrange your choice of candied fruit on top. Will keep in a sealed container for up to three weeks.\n" +
                        "\n", listOf("https://www.bbcgoodfood.com/sites/default/files/styles/recipe/public/recipe/recipe-image/2018/10/easy-fruit-cake.jpg?itok=fTTwVsAD"))
        )
        ui().perform { it.fillList(meals) }
    }

    fun onItemClicked(model:Meal) {
        ui().perform { it.openItem(model) }
    }

    interface UI : BaseUI {
        fun fillList(meals: MutableList<Meal>)
        fun removeFromList(meal: Meal)
        fun openItem(meal: Meal)
    }
}