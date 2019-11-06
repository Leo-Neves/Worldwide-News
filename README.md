# Teste Android

Esse é um simples app que usa a NewsAPI (https://newsapi.org)

Ele usa a arquitetura MVVM, Retrofit, Koin (para injeção de dependência) e RxAndroid.

As seguintes tarefas foram relaizadas:

## Aplicativo não executando

Primeiramente o app não estava executando. Ao executar foi encontrado erro de NullPointerException no arquivo SourcesViewModel.kt:

    selectedCountry!!.name.toLowerCase(),
    selectedCategory!!.name.toLowerCase()
    
Neste casso o operador !! lança a exceção. Substituindo isto pelo operador ?. o código funcionou porque o método newsRepository.getSources(country, category) aceita parâmetros nulos.

    selectedCountry?.name?.toLowerCase(),
    selectedCategory?.name?.toLowerCase()  

A chave de API foi substiuída conforme informado nas instruções

## Desafios executados:

 - A listagem de notícias está paginada com scroll infinito. A API tem uma limitação de 100 notícias para uso gratuito, então as notícias são carregadas de 20 em 20 até o máximo de 100.
 - ListView de notícias foi substituído por RecyclerView para facilitar a verificação de chegar ao final do scroll e facilitar a atualização de novos ítens na lista. o Adapter também foi substituído por RecyclerView.Adapter.
