package br.com.qa.steps;

import br.com.qa.pages.TestApiHunterPage;
import io.cucumber.java.pt.Dado;

public class TestApiHunterSteps {
    
    private TestApiHunterPage api = TestApiHunterPage.getInstance(); 

    @Dado("que valido o resultado positivo esperado com comando GET")
    public void que_valido_o_resultado_positivo_esperado_com_comando_GET() {
        api.realizandoComandoGet();
    }

    @Dado("que envio uma requisicao GET para recuperar todos os leads")
    public void que_envio_uma_requisicao_GET_para_recuperar_todos_os_leads() {
        api.recuperarTodosOsLeadsComGet();
    }

    @Dado("que envio uma requisicao GET para recuperar lead")
    public void que_envio_uma_requisicao_GET_para_recuperar_lead() {
        api.recuperarLeadEspecifico();
    }

    @Dado("que envio uma requisicao POST para criacao de lead")
    public void que_envio_uma_requisicao_POST_para_criacao_de_lead() {
        api.deveCriarNovoLead();
    }

    @Dado("que envio uma requisicao PUT para editar o lead especifico")
    public void que_envio_uma_requisicao_PUT_para_editar_o_lead_especifico() {
        api.deveEditarRequisicaoDoLeadEspecifico();
    }

    @Dado("que envio uma requisicao DELETE para deletar o lead especifico")
    public void que_envio_uma_requisicao_DELETE_para_deletar_o_lead_especifico() {
        api.deveDeletarOLeadEspecifico();
    }

    @Dado("que envio uma requisicao GET para receber uma lista")
    public void que_envio_uma_requisicao_GET_para_receber_uma_lista() {
        api.deveEnviarRequestGetParaReceberLista();
    }

    @Dado("que envio uma requisicao GET para receber uma lista especifica")
    public void que_envio_uma_requisicao_GET_para_receber_uma_lista_especifica() {
        api.deveEnviarRequestGetParaReceberUmaListaEspecifica();
    }

    @Dado("que envio uma requisicao POST para criar nova lista")
    public void que_envio_uma_requisicao_POST_para_criar_nova_lista() {
        api.deveEnviarRequestPostParaCriarNovaLista();
    }

    @Dado("que envio uma requisicao PUT para editar lista de lead")
    public void que_envio_uma_requisicao_PUT_para_editar_lista_de_lead() {
        api.deveAlterarRequestComPutParaEditarListaDeLead();
    }

    @Dado("que envio uma requisicao DELETE para deletar uma lista de lead especifica")
    public void que_envio_uma_requisicao_DELETE_para_deletar_uma_lista_de_lead_especifica() {
        api.deveDeletarUmaListaDeLeadEspecifica();
    }
}
