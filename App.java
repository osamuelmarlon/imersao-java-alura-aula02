package aluraStickers;

import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;
import java.io.File;

public class App {
	
	public static void main(String[] args) throws Exception {
		
		// fazer uma conexão HTTP e buscar os top 250 filmes
		String url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/MostPopularMovies.json"; // usando endereço alternativo
		URI endereco = URI.create(url);
		var client = HttpClient.newHttpClient();
		
		var request = HttpRequest.newBuilder(endereco).GET().build();
		    
		HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
		String body = response.body();
		//System.out.println(body);
		
		// "Parsear": pegar só os dados que interessa (título, poster, classificação)
		JsonParser parser = new JsonParser();
		List<Map<String, String>> listaDeFilmes = parser.parse(body);
		//System.out.println(listaDeFilmes.size());
		//System.out.println(listaDeFilmes.get(0));
		
		// exibir e manupular os dados
		File diretorio = new File("figurinhas/");
		diretorio.mkdir();
		
		for (Map<String, String> filme : listaDeFilmes) {
			
			String urlImagem = filme.get("image");
			String titulo = filme.get("title");
			
			InputStream inputStream = new URL(urlImagem).openStream();
			
			String nomeArquivo = "figurinhas/" + titulo + ".png";
				
			var geradora = new GeradoraDeFigurinhas();
			geradora.cria(inputStream, nomeArquivo);
			
			System.out.println(filme.get("title"));
			System.out.println();
			
			
			//System.out.println("\n\u001b[97m\u001b[104mTitulo:\u001b[m " );
			//System.out.println("\u001b[97m\u001b[42mPoster:\u001b[m ");
			//System.out.print("\u001b[30m\u001b[43mPontuacao:\u001b[m ");
			//double classificacao = Double.parseDouble(filme.get("imDbRating"));
			//int numeroEstrelas = (int) classificacao;
			//for(int n = 1; n <= numeroEstrelas; n++) {
			//	System.out.print("\u001b[1m\u001b[33m\u001b[40m*");
				
			//}
			//System.out.println("(" + classificacao + ")");
			//System.out.println();
								
		}
				
	}
}
