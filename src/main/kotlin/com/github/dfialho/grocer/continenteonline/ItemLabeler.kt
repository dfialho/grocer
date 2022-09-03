package com.github.dfialho.grocer.continenteonline

import com.github.dfialho.grocer.Item
import com.github.dfialho.grocer.continenteonline.rules.NameContainsRule
import com.github.dfialho.grocer.continenteonline.rules.Rule
import com.github.dfialho.grocer.continenteonline.rules.RuleResult
import java.util.UUID.randomUUID

class ItemLabeler {

    private val labelRules = listOf<Rule>(
        NameContainsRule(pattern = "Couscous", RuleResult("Cereais", "Couscous")),
        NameContainsRule(pattern = "Lenços", RuleResult("Higiene", "Lenços")),
        NameContainsRule(pattern = "EntregaZero", RuleResult("Serviços", "Entregas")),
        NameContainsRule(pattern = "Bolacha", RuleResult("Lanches", "Bolachas")),
        NameContainsRule(pattern = "Noodles", RuleResult("Variados", "Noodles")),
        NameContainsRule(pattern = "Areia para Gato", RuleResult("Animais", "Areia")),
        NameContainsRule(pattern = "Polpa e Tomate", RuleResult("Condimentos", "Polpa de Tomate")),
        NameContainsRule(pattern = "Couve Flor", RuleResult("Legumes", "Couve Flor")),
        NameContainsRule(pattern = "Espinafre", RuleResult("Legumes", "Espinafres")),
        NameContainsRule(pattern = "Flocos Aveia", RuleResult("Lanches", "Aveia")),
        NameContainsRule(pattern = "Massa Fresa para Pizza", RuleResult("Pizzas", "Massa")),
        NameContainsRule(pattern = "Pimento", RuleResult("Legumes", "Pimento")),
        NameContainsRule(pattern = "Tomate", RuleResult("Legumes", "Tomate")),
        NameContainsRule(pattern = "Banana", RuleResult("Frutas", "Banana")),
        NameContainsRule(pattern = "Arroz", RuleResult("Cereais", "Arroz")),
        NameContainsRule(pattern = "Azeite", RuleResult("Condimentos", "Azeite")),
        NameContainsRule(pattern = "Manjericão", RuleResult("Condimentos", "Manjericão")),
        NameContainsRule(pattern = "Molho e Soja", RuleResult("Condimentos", "Molho de Soja")),
        NameContainsRule(pattern = "Cebola", RuleResult("Legumes", "Cebola")),
        NameContainsRule(pattern = "Cogumelo", RuleResult("Legumes", "Cogumelo")),
        NameContainsRule(pattern = "Agrião", RuleResult("Legumes", "Agrião")),
        NameContainsRule(pattern = "Curgete", RuleResult("Legumes", "Curgete")),
        NameContainsRule(pattern = "Couve", RuleResult("Legumes", "Couve")),
        NameContainsRule(pattern = "Champô", RuleResult("Higiene", "Champô")),
        NameContainsRule(pattern = "Tortilha", RuleResult("Variados", "Tortilhas")),
        NameContainsRule(pattern = "Detergente Máquia Loiça", RuleResult("Detergentes", "Detergente Máquina Loiça")),
        NameContainsRule(pattern = "Detergente Máquia Roupa", RuleResult("Detergentes", "Detergente Máquina Roupa")),
        NameContainsRule(pattern = "Amaciador Roupa", RuleResult("Detergentes", "Amaciador Roupa")),
        NameContainsRule(pattern = "Molas", RuleResult("Detergentes", "Molas")),
        NameContainsRule(pattern = "Doce", RuleResult("Lanches", "Doce")),
        NameContainsRule(pattern = "Frango", RuleResult("Carnes", "Frango")),
        NameContainsRule(pattern = "Gel e Banho", RuleResult("Higiene", "Gel de Banho")),
        NameContainsRule(pattern = "Esparguete", RuleResult("Massas", "Esparguete")),
        NameContainsRule(pattern = "Massa", RuleResult("Massas", "Massa")),
        NameContainsRule(pattern = "Cacetinho", RuleResult("Pão", "Pão")),
        NameContainsRule(pattern = "Pão", RuleResult("Pão", "Pão")),
        NameContainsRule(pattern = "Pizza", RuleResult("Pizzas", "Pizza")),
        NameContainsRule(pattern = "Ameixa", RuleResult("Frutas", "Ameixa")),
        NameContainsRule(pattern = "Meloa", RuleResult("Frutas", "Meloa")),
        NameContainsRule(pattern = "Espargos", RuleResult("Legumes", "Espargos")),
        NameContainsRule(pattern = "Alho Francês", RuleResult("Legumes", "Alho Francês")),
        NameContainsRule(pattern = "Alho", RuleResult("Condimentos", "Alho")),
        NameContainsRule(pattern = "Cerveja", RuleResult("Bebidas", "Cerveja")),
        NameContainsRule(pattern = "Nectarina", RuleResult("Frutas", "Nectarina")),
        NameContainsRule(pattern = "Pêssego", RuleResult("Frutas", "Pêssego")),
        NameContainsRule(pattern = "Abacate", RuleResult("Legumes", "Abacate")),
        NameContainsRule(pattern = "Pellets", RuleResult("Animais", "Pellets")),
        NameContainsRule(pattern = "Tofu", RuleResult("Vegan", "Tofu")),
        NameContainsRule(pattern = "Fio Dentáro", RuleResult("Higiene", "Fio Dentário")),
        NameContainsRule(pattern = "Queijo", RuleResult("Lanches", "Queijo")),
        NameContainsRule(pattern = "Mirtilo", RuleResult("Frutas", "Mirtilo")),
        NameContainsRule(pattern = "Alface", RuleResult("Legumes", "Alface")),
        NameContainsRule(pattern = "Pepino", RuleResult("Legumes", "Pepino")),
        NameContainsRule(pattern = "Cenoura", RuleResult("Legumes", "Cenoura")),
        NameContainsRule(pattern = "Café", RuleResult("Bebidas", "Café")),
        NameContainsRule(pattern = "Gengibre", RuleResult("Condimentos", "Gengibre")),
        NameContainsRule(pattern = "Mostarda", RuleResult("Condimentos", "Mostarda")),
        NameContainsRule(pattern = "Ração", RuleResult("Animais", "Ração")),
        NameContainsRule(pattern = "Bacon", RuleResult("Lanches", "Bacon")),
        NameContainsRule(pattern = "Lima", RuleResult("Legumes", "Lima")),
        NameContainsRule(pattern = "Limão", RuleResult("Legumes", "Limão")),
        NameContainsRule(pattern = "Feijão", RuleResult("Legumes", "Feijão")),
        NameContainsRule(pattern = "Grão", RuleResult("Legumes", "Grão")),
        NameContainsRule(pattern = "Cereja", RuleResult("Frutas", "Cereja")),
        NameContainsRule(pattern = "Framboesa", RuleResult("Frutas", "Framboesa")),
        NameContainsRule(pattern = "Morango", RuleResult("Frutas", "Morango")),
        NameContainsRule(pattern = "Kiwi", RuleResult("Frutas", "Kiwi")),
        NameContainsRule(pattern = "Maçã", RuleResult("Frutas", "Maçã")),
        NameContainsRule(pattern = "Pera", RuleResult("Frutas", "Pera")),
        NameContainsRule(pattern = "Pensos Higiénicos", RuleResult("Higiene", "Pensos Higiénicos")),
        NameContainsRule(pattern = "Toalhitas", RuleResult("Higiene", "Toalhitas")),
        NameContainsRule(pattern = "Ambientador", RuleResult("Casa", "Ambientador")),
        NameContainsRule(pattern = "Sal ", RuleResult("Condimentos", "Sal")),
        NameContainsRule(pattern = "Nabo", RuleResult("Legumes", "Sal")),
        NameContainsRule(pattern = "Compressas", RuleResult("Saude", "Compressas")),
        NameContainsRule(pattern = "Cotonetes", RuleResult("Saude", "Cotonetes")),
        NameContainsRule(pattern = "Soro Fisiológio", RuleResult("Saude", "Soro")),
        NameContainsRule(pattern = "Pensos Rápidos", RuleResult("Saude", "Pensos")),
    )

    fun label(orderItem: OrderItem): Item {

        val itemId = randomUUID().toString()

        for (rule in labelRules) {
            if (rule.matches(orderItem)) {
                return Item(
                    id = itemId,
                    name = rule.result.name,
                    category = rule.result.category,
                    amount = orderItem.amount,
                    labeled = true
                )
            }
        }

        return Item(
            id = itemId,
            name = orderItem.name,
            category = orderItem.category,
            amount = orderItem.amount,
            labeled = false
        )
    }
}
