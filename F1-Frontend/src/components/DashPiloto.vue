<template>
    <div class="dashboard">
        <h2 class="homeAdminGerenciarLabel ajusteMargin">Selecione o relatório</h2>
        <div class="gridHome">
            <button class="btn btn-lg px-5 btn btn-success" @click="mudarParaORelatorio1()">Exibir resultados das corridas </button>
            <button class="btn btn-lg px-5 btn btn-success" @click="mudarParaORelatorio2()">to do</button>
        </div>
        <div class="relatorio" v-if="relatorio1">
            <h3 class="relatorio1Desc">Esse relatório tem o objetivo de consultar a quantidade de vitórias obtidas, apresentando o ano e a corrida onde cada vitlória foi alcançada.</h3>
            <div class="ordenadores">
                <h4>Ordenar por: </h4>
                <div class="ordenador" @click="mudarOrdenador(0)" :class="{ 'selecionado': selecionado == 0}">
                    <h4>Visão Geral</h4>
                </div>
                <div class="ordenador" @click="mudarOrdenador(1)" :class="{ 'selecionado': selecionado == 1}">
                    <h4>Por Corrida</h4>
                </div>
                <div class="ordenador" @click="mudarOrdenador(2)" :class="{ 'selecionado': selecionado == 2}">
                    <h4>Por Ano</h4>
                </div>
            </div>
            <div v-if="selecionado == 0" class="statusList">
                <h3 class="subtitulo">Total de vitórias: <span class="label"> {{ resultadoPiloto.geral.length }}</span></h3>
                <div v-for="resultado in resultadoPiloto.geral" :key="resultado.nomePremio" class="status">
                    <h4 class="statusStatus"><span class="label">Ano</span></h4>
                    <h4 class="statusQuantity">{{ resultado.ano }}</h4>
                    <h4 class="statusStatus"><span class="label">Prêmio</span></h4>
                    <h4 class="statusQuantity">{{ resultado.nomePremio }}</h4>
                </div>
            </div>
            <div v-if="selecionado == 1" class="vitoriasPorCorrida">
                <div v-for="resultado in resultadoPiloto.porCorrida" :key="resultado.nomeCorrida" class="statusPorCorrida">
                    <h3 class="subtitulo"><span class="label"> {{ resultado.anos.length }}</span> vitória(s) na corrida <span class="label"> {{ resultado.nomeCorrida }}</span></h3> 
                    <div class="anos">
                        <h4 class="subtitulo">No(s) ano(s) de:</h4> 
                        <div class="anos">
                            <h4 v-for="ano in resultado.anos" :key="ano" class="statusQuantity"><span class="label"> {{ ano }}</span></h4>
                        </div>
                    </div>
                </div>
            </div>
            <div v-if="selecionado == 2" class="vitoriasPorCorrida">
                <div v-for="resultado in resultadoPiloto.porAno" :key="resultado.ano" class="statusPorCorrida">
                    <h3 class="subtitulo"><span class="label"> {{ resultado.titulos.length }}</span> vitória(s) no ano de <span class="label"> {{ resultado.ano }}</span></h3> 
                    <div class="anos">
                        <div class="titulos">
                            <h4 v-for="titulo in resultado.titulos" :key="titulo" class="statusQuantity"><span class="label"> {{ titulo }}</span></h4>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="relatorio" v-if="relatorio2">
            <h3 class="relatorio1Desc">Esse relatório indica os aeroportos de médio e largo porte no Brasil que estejam no máximo a 100km de distância da cidade brasileira que for informada a baixo:</h3>
            <div class="busca">
                <h4 class="buscaTitle">Buscar por: </h4>
                <input class="buscaInput form-control form-control-lg" v-model="busca" type="text" placeholder="Digite o nome da cidade" @keypress="handleKeyPress">
                <button class="buscaButton btn btn-lg px-5 btn btn-success" type="submit" @click="buscar()">Buscar</button>
            </div>
            <div v-if="cidades.length > 0" class="ordenadores">
                <h4>Ordenar por: </h4>
                <div class="ordenador" @click="mudarOrdenador(0)" :class="{ 'selecionado': selecionado == 0}">
                    <h4>Código IATA</h4>
                </div>
                <div class="ordenador" @click="mudarOrdenador(1)" :class="{ 'selecionado': selecionado == 1}">
                    <h4>Aeroporto</h4>
                </div>
                <div class="ordenador" @click="mudarOrdenador(2)" :class="{ 'selecionado': selecionado == 2}">
                    <h4>Cidade do Aeroporto</h4>
                </div>
                <div class="ordenador" @click="mudarOrdenador(3)" :class="{ 'selecionado': selecionado == 3}">
                    <h4>Distância em Km</h4>
                </div>
                <div class="ordenador" @click="mudarOrdenador(4)" :class="{ 'selecionado': selecionado == 4}">
                    <h4>Tipo Aeroporto</h4>
                </div>
            </div>
            <div class="container mt-3" v-if="cidades.length > 0">
                <div class="row cidades">
                    <div class="col-md-6 statusList" v-for="cidade in cidades" :key="cidade.nome">
                        <h4 class="cidades">{{ cidade.nome }} - Latitude: {{ cidade.lat }} Longitude: {{ cidade.long }}</h4>
                            <div class="aeroporto" v-for="aeroporto in cidade.aeroportosProximos" :key="aeroporto.codigo_IATA">
                                <p><span class="label">Código IATA:</span> {{ aeroporto.codigo_IATA }}</p>
                                <p><span class="label">Aeroporto:</span> {{ aeroporto.aeroporto }}</p>
                                <p><span class="label">Cidade:</span> {{ aeroporto.cidade_aeroporto }}</p>
                                <p><span class="label">Distância:</span> {{ aeroporto.distancia_em_km.toFixed(2) }}km</p>
                                <p><span class="label">Tipo:</span> {{ aeroporto.tipo_aeroporto }}</p>
                            </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>

    export default {
        name: 'DashAdmin',
        components: { },
        data(){
            return {
                relatorio1: false,
                relatorio2: false,
                selecionado: 0,
                resultadoPiloto: {},
            }
        },
        methods:{
            mudarParaORelatorio1(){
                this.relatorio1 = true
                this.relatorio2 = false
                this.selecionado = 0
                this.resultadoPiloto = {
                    geral:[
                        {
                            nomePremio: 'grampix',
                            ano: 2022
                        },
                        {  
                            nomePremio: 'grampix',
                            ano: 2023
                        },
                        {
                            nomePremio: 'interlagos',
                            ano: 2022
                        }
                    ],
                    
                    porAno:[
                        {
                            ano: 2022,
                            titulos: [
                                'grampix','interlagos'
                            ]
                        },
                        {
                            ano: 2023,
                            titulos: [
                                'grampix'
                            ]
                        }
                    ],
                    porCorrida:[
                        {
                            nomeCorrida: 'grampix',
                            anos: [
                                2022, 2023
                            ]
                        },
                        {
                            nomeCorrida: 'interlagos',
                            anos: [
                                2022
                            ]
                        }
                    ]
                }
            },
            mudarParaORelatorio2(){
                this.relatorio1 = false
                this.relatorio2 = true
                this.cidades = []
                this.statusList = []
                this.selecionado = 3
            },
            mudarOrdenador(number){
                this.selecionado = number
                if(this.relatorio2){
                    const keys = Object.keys(this.cidades[1].aeroportosProximos[1]);
                    const ordenador = keys[number]
                    this.cidades.forEach(cidade => {
                        cidade.aeroportosProximos = cidade.aeroportosProximos.sort((a, b) => {
                                                                    const labelA = a[ordenador];
                                                                    const labelB = b[ordenador];
                                                                    if (labelA < labelB) {
                                                                        return -1;
                                                                    }
                                                                    if (labelA > labelB) {
                                                                        return 1;
                                                                    }
                                                                    return 0;}
                                                                );
                    

                        }
                    );


                }
            },
        }
    }
</script>

<style scoped>
    .dashboard{
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
    }
    .gridHome{
        display: grid;
        gap: 25px 25px;
        grid-template-columns: 1fr 1fr;
        margin-bottom: 40px;

    }
    .homeAdminGerenciarLabel{
        margin-bottom: 20px;
        margin-top: 40px;
    }

    .ajusteMargin{
        margin-top: -10px;
    }

    .relatorio{
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        width: 100%;
    }
    

    .relatorio1Desc{
        text-align: center;
        padding: 0 50px;
        margin: 40px 0;
    }

    .busca{
        display: flex;
        gap: 20px;
        margin-bottom: 40px;
        align-items: center;
        justify-content: center;
        max-width: 800px;
        padding: 0 20px;
    }

    .buscaTitle{
        width: 250px;
    }

    .ordenadores{
        display: flex;
        gap: 20px;
        margin-bottom: 20px;
        align-items: center;
        justify-content: center;
    }
    .ordenador{
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 10px;
        border-radius: 10px;
        border: 5px solid #198754;
        padding: 5px;
        cursor: pointer;

    }

    .selecionado{
        background-color: #198754;
        color: #FFF;
        transition: 0.2s;
    }
    /* Estilos para a lista de status */
    .statusList {
        display: flex;
        flex-direction: column;
        width: auto;
        border: 3px solid #888888;
        padding: 10px;
        margin-bottom: 40px;
        justify-content: center;
        align-items: center;
    }

    /* Estilos para cada item de status */
    .status {
        display: flex;
        justify-content: space-between;
        align-items: start;
        margin-bottom: 10px;
        padding: 5px;
    }

    .statusPorCorrida {
        display: flex;
        flex-direction: column;
        justify-content: space-between;
        align-items: start;
        margin-bottom: 10px;
        padding: 5px;
    }

    /* Estilos para o título do status */
    .statusStatus {
    font-size: 24px;
    margin-right: 10px;
    }

    /* Estilos para a quantidade do status */
    .statusQuantity {
    font-size: 24px;
    color: #222;
    margin: 0 10px;
    }

    .cidades{
        display: flex;
        flex-direction: column;
        padding: 10px;
    }

    .aeroporto{
        margin: 20px;
        background-color: #1987542f;
        color: #000;
        font-size: 24px;
        padding: 10px;
        border-radius: 10px;
    }

    .label{
        font-weight: bold;
    }

    .subtitulo{
        margin-bottom: 20px;
    }

    .anos{
        display: flex;
        flex-direction: row;
        margin-bottom: 20px;
    }

    .vitoriasPorCorrida{
        display: flex;
        flex-direction: column;
        width: auto;
        border: 3px solid #888888;
        padding: 10px;
        margin-bottom: 40px;
        justify-content: initial;
        align-items: initial;
    }
</style>