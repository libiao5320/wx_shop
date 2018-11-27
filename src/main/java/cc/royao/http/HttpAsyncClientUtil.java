package cc.royao.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpTrace;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import cc.royao.cache.memcached.MemCachedClient;
import cc.royao.cache.memcached.SockIOPool;


public class HttpAsyncClientUtil {
	private static final Logger logger = Logger
			.getLogger(HttpAsyncClientUtil.class);

	/**
	 * 创建client对象 并设置全局的标准cookie策略
	 * 
	 * @Description
	 * @author jk
	 * @param url
	 * @return
	 * @throws HttpProcessException
	 * @return CloseableHttpAsyncClient
	 * @date 2016年1月30日
	 */
	public static CloseableHttpAsyncClient create(String url)
			throws HttpProcessException {
		if (url.toLowerCase().startsWith("https://")) {
			return HACB.custom().ssl().build();
		} else {
			return HACB.custom().build();
		}
	}

	public static void send(String url, IHandler handler) throws HttpProcessException {
		send(url, Charset.defaultCharset().name(), handler);
	}
	public static void send(String url, String encoding, IHandler handler) throws HttpProcessException {
		send(url, new Header[] {}, encoding, handler);
	}
	
	public static void send(String url, Header[] headers, String encoding, IHandler handler)
			throws HttpProcessException {
		send(url, new HashMap<String, Object>(), headers, encoding, handler);
	}
	
	public static void send(String url, Map<String, Object> parasMap, Header[] headers, String encoding,
			IHandler handler) throws HttpProcessException {
		send(create(url), url, HttpMethods.POST, parasMap, headers, encoding, handler);
	}
	
	public static void send(CloseableHttpAsyncClient client, String url, HttpMethods httpMethod, Header[] headers,
			String encoding, IHandler handler) throws HttpProcessException {
		send(client, url, httpMethod, new HashMap<String, Object>(), headers, encoding, handler);
	}
	/**
	 * post方式请求资源或服务，自定义client对象，传入请求参数，设置内容类型，并指定参数和返回数据的编码
	 * 
	 * @Description
	 * @author jk
	 * @param client client对象
	 * @param url 资源地址
	 * @param httpMethod 请求方法
	 * @param parasMap 请求参数
	 * @param headers 请求头信息
	 * @param encoding 编码
	 * @param handler 回调
	 * @throws HttpProcessException
	 * @return void
	 * @date 2016年1月30日
	 */
	public static void send(CloseableHttpAsyncClient client, String url,
			HttpMethods httpMethod, Map<String, Object> parasMap,
			Header[] headers, String encoding, IHandler handler)
			throws HttpProcessException {

		try {

			// 创建请求对象
			HttpRequestBase request = getRequest(url, httpMethod);

			// 设置header信息
			request.setHeaders(headers);

			// 判断是否支持设置entity(仅HttpPost、HttpPut、HttpPatch支持)
			if (HttpEntityEnclosingRequestBase.class.isAssignableFrom(request
					.getClass())) {
				List<NameValuePair> nvps = new ArrayList<NameValuePair>();

				// 检测url中是否存在参数
				url = Utils.checkHasParas(url, nvps, encoding);

				// 装填参数
				HttpEntity entity = Utils.map2List(nvps, parasMap, encoding);

				// 设置参数到请求对象中
				((HttpEntityEnclosingRequestBase) request).setEntity(entity);

				logger.debug("请求地址：" + url);
				logger.debug("请求参数：" + nvps.toString());
			} else {
				int idx = url.indexOf("?");
				logger.debug("请求地址："
						+ url.substring(0, (idx > 0 ? idx - 1
								: url.length() - 1)));
				if (idx > 0) {
					logger.debug("请求参数：" + url.substring(idx + 1));
				}
			}

			// 执行请求
			execute(client, request, encoding, handler);
		} catch (UnsupportedEncodingException e) {
			throw new HttpProcessException(e);
		}
	}

	/**
	 * 以Get方式，请求资源或服务，自定义client对象，传入请求参数，设置内容类型，并指定参数和返回数据的编码
	 * 
	 * @param url  资源地址
	 * @param headers  请求头信息
	 * @param encoding  编码
	 * @param handler   回调处理对象
	 * @return 返回处理结果
	 * @throws HttpProcessException
	 */
	public static void get(String url, Header[] headers, String encoding,
			IHandler handler) throws HttpProcessException {
		get(create(url), url, headers, encoding, handler);
	}

	/**
	 * 以Get方式，请求资源或服务，自定义client对象，传入请求参数，设置内容类型，并指定参数和返回数据的编码
	 * @param client client对象
	 * @param url 资源地址
	 * @param headers 请求头信息
	 * @param encoding 编码
	 * @param handler 回调处理对象
	 * @return 返回处理结果
	 * @throws HttpProcessException
	 */
	public static void get(CloseableHttpAsyncClient client, String url,
			Header[] headers, String encoding, IHandler handler)
			throws HttpProcessException {
		send(client, url, HttpMethods.GET, headers, encoding, handler);
	}

	/**
	 * 以Post方式，请求资源或服务，传入请求参数，设置内容类型，并指定参数和返回数据的编码
	 * 
	 * @param url  资源地址
	 * @param parasMap 请求参数
	 * @param headers 请求头信息
	 * @param encoding 编码
	 * @param handler 回调处理对象
	 * @return 返回处理结果
	 * @throws HttpProcessException
	 */
	public static void post(String url, Map<String, Object> parasMap,
			Header[] headers, String encoding, IHandler handler)
			throws HttpProcessException {
		post(create(url), url, parasMap, headers, encoding, handler);
	}

	/**
	 * 以Post方式，请求资源或服务，自定义client对象，传入请求参数，设置内容类型，并指定参数和返回数据的编码
	 * 
	 * @param client client对象
	 * @param url  资源地址
	 * @param parasMap  请求参数
	 * @param headers  请求头信息
	 * @param encoding  编码
	 * @param handler   回调处理对象
	 * @return 返回处理结果
	 * @throws HttpProcessException
	 */
	public static void post(CloseableHttpAsyncClient client, String url,
			Map<String, Object> parasMap, Header[] headers, String encoding,
			IHandler handler) throws HttpProcessException {
		send(client, url, HttpMethods.POST, parasMap, headers, encoding,
				handler);
	}

	/**
	 * 以Put方式，请求资源或服务，传入请求参数，设置内容类型，并指定参数和返回数据的编码
	 * @param url  资源地址
	 * @param parasMap  请求参数
	 * @param headers  请求头信息
	 * @param encoding  编码
	 * @param handler  回调处理对象
	 * @return 返回处理结果
	 * @throws HttpProcessException
	 */
	public static void put(String url, Map<String, Object> parasMap,
			Header[] headers, String encoding, IHandler handler)
			throws HttpProcessException {
		put(create(url), url, parasMap, headers, encoding, handler);
	}

	/**
	 * 以Put方式，请求资源或服务，自定义client对象，传入请求参数，设置内容类型，并指定参数和返回数据的编码
	 * 
	 * @param client  client对象
	 * @param url  资源地址
	 * @param parasMap 请求参数
	 * @param headers 请求头信息
	 * @param encoding 编码
	 * @param handler   回调处理对象
	 * @return 返回处理结果
	 * @throws HttpProcessException
	 */
	public static void put(CloseableHttpAsyncClient client, String url,
			Map<String, Object> parasMap, Header[] headers, String encoding,
			IHandler handler) throws HttpProcessException {
		send(client, url, HttpMethods.PUT, parasMap, headers, encoding, handler);
	}

	/**
	 * 以Delete方式，请求资源或服务，传入请求参数，设置内容类型，并指定参数和返回数据的编码
	 * 
	 * @param url  资源地址
	 * @param headers  请求头信息
	 * @param encoding  编码
	 * @param handler  回调处理对象
	 * @return 返回处理结果
	 * @throws HttpProcessException
	 */
	public static void delete(String url, Header[] headers, String encoding,
			IHandler handler) throws HttpProcessException {
		delete(create(url), url, headers, encoding, handler);
	}

	/**
	 * 以Get方式，请求资源或服务，自定义client对象，传入请求参数，设置内容类型，并指定参数和返回数据的编码
	 * 
	 * @param client    client对象
	 * @param url   资源地址
	 * @param headers  请求头信息
	 * @param encoding  编码
	 * @param handler  回调处理对象
	 * @return 返回处理结果
	 * @throws HttpProcessException
	 */
	public static void delete(CloseableHttpAsyncClient client, String url,
			Header[] headers, String encoding, IHandler handler)
			throws HttpProcessException {
		send(client, url, HttpMethods.DELETE, headers, encoding, handler);
	}




	/**
	 * 执行请求 
	 * @param client  client对象
	 * @param request  请求对象
	 * @param encoding  编码
	 * @param handler  回调处理对象
	 * @return 返回处理结果
	 */
	private static void execute(final CloseableHttpAsyncClient client,
			HttpRequestBase request, final String encoding,
			final IHandler handler) {
		// Start the client
		client.start();
		// 异步执行请求操作，通过回调，处理结果
		client.execute(request, new FutureCallback<HttpResponse>() {
			public void failed(Exception e) {
				handler.failed(e);
				close(client);
			}

			public void completed(HttpResponse resp) {
				String body = "";
				try {
					HttpEntity entity = resp.getEntity();
					if (entity != null) {
						final InputStream instream = entity.getContent();
						try {
							final StringBuilder sb = new StringBuilder();
							final char[] tmp = new char[1024];
							final Reader reader = new InputStreamReader(
									instream, encoding);
							int l;
							while ((l = reader.read(tmp)) != -1) {
								sb.append(tmp, 0, l);
							}
							body = sb.toString();
						} finally {
							instream.close();
							EntityUtils.consume(entity);
						}
					}
				} catch (Exception e) {
					logger.error(e);
				}
				handler.completed(body);
				close(client);
			}

			public void cancelled() {
				handler.cancelled();
				close(client);
			}
		});
	}

	/**
	 * 关闭client对象
	 * 
	 * @param client
	 */
	private static void close(final CloseableHttpAsyncClient client) {
		try {
			client.close();
		} catch (IOException e) {
			logger.error(e);
		}
	}

	public enum HttpMethods {

		/**
		 * 求获取Request-URI所标识的资源
		 */
		GET(0, "GET"),

		/**
		 * 向指定资源提交数据进行处理请求（例如提交表单或者上传文件）。数据被包含在请求体中。
		 * POST请求可能会导致新的资源的建立和/或已有资源的修改
		 */
		POST(1, "POST"),

		/**
		 * 向服务器索要与GET请求相一致的响应，只不过响应体将不会被返回。
		 * 这一方法可以在不必传输整个响应内容的情况下，就可以获取包含在响应消息头中的元信息 只获取响应信息报头
		 */
		HEAD(2, "HEAD"),

		/**
		 * 向指定资源位置上传其最新内容（全部更新，操作幂等）
		 */
		PUT(3, "PUT"),

		/**
		 * 请求服务器删除Request-URI所标识的资源
		 */
		DELETE(4, "DELETE"),

		/**
		 * 请求服务器回送收到的请求信息，主要用于测试或诊断
		 */
		TRACE(5, "TRACE"),

		/**
		 * 向指定资源位置上传其最新内容（部分更新，非幂等）
		 */
		PATCH(6, "PATCH"),

		/**
		 * 返回服务器针对特定资源所支持的HTTP请求方法。 也可以利用向Web服务器发送'*'的请求来测试服务器的功能性
		 */
		OPTIONS(7, "OPTIONS"),

		// /**
		// * HTTP/1.1协议中预留给能够将连接改为管道方式的代理服务器
		// */
		// CONNECT(99, "CONNECT"),
		;

		private int code;
		private String name;

		private HttpMethods(int code, String name) {
			this.code = code;
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public int getCode() {
			return code;
		}
	}

	/**
	 * 根据请求方法名，获取request对象
	 * 
	 * @param url
	 *            资源地址
	 * @param method
	 *            请求方式名称：get、post、head、put、delete、patch、trace、options
	 * @return
	 */
	private static HttpRequestBase getRequest(String url, HttpMethods method) {
		HttpRequestBase request = null;
		switch (method.getCode()) {
		case 0:// HttpGet
			request = new HttpGet(url);
			break;
		case 1:// HttpPost
			request = new HttpPost(url);
			break;
		case 2:// HttpHead
			request = new HttpHead(url);
			break;
		case 3:// HttpPut
			request = new HttpPut(url);
			break;
		case 4:// HttpDelete
			request = new HttpDelete(url);
			break;
		case 5:// HttpTrace
			request = new HttpTrace(url);
			break;
		case 6:// HttpPatch
			request = new HttpPatch(url);
			break;
		case 7:// HttpOptions
			request = new HttpOptions(url);
			break;
		default:
			request = new HttpPost(url);
			break;
		}
		return request;
	}

	/**
	 * 回调处理接口
	 * 
	 * @author jk
	 * @Description
	 * @date 2016年1月30日
	 */
	public interface IHandler {

		/**
		 * 处理异常时，执行该方法
		 * 
		 * @return
		 */
		Object failed(Exception e);

		/**
		 * 处理正常时，执行该方法
		 * 
		 * @return
		 */
		Object completed(String respBody);

		/**
		 * 处理取消时，执行该方法
		 * 
		 * @return
		 */
		Object cancelled();
	}

	/*public static void main(String[] args) throws HttpProcessException {
		
			String url = "http://192.168.1.177/api.php/User/myinfoByPhone";
			IHandler handler = new IHandler() {
				@Override
				public Object failed(Exception e) {
					System.out.println("失败了");
					return null;
				}
	
				@Override
				public Object completed(String respBody) {
					System.out.println("获取结果：" + respBody.length());
					System.out.println("获取结果：" + respBody);
					JSONObject result = JSONObject.parseObject(respBody);
					if(result.get("status") != null && Integer.valueOf(result.get("status").toString()) == 1){
						JSONArray jsonArray = (JSONArray) result.get("data");
						System.out.println(jsonArray.get(0));
						String[] servers = { "192.168.1.177:11211"};
						SockIOPool pool = SockIOPool.getInstance();
						pool.setServers( servers );
						pool.setFailover( true );
						pool.setInitConn( 100 ); 
						pool.setMinConn( 100 );
						pool.setMaxConn( 250 );
						pool.setMaintSleep( 30 );
						pool.setNagle( false );
						pool.setSocketTO( 3000 );
						pool.setAliveCheck( true );
						pool.initialize();

						MemCachedClient memCachedClient = new MemCachedClient();
						//String token = (String)memCachedClient.get("Thread-1_1");
						memCachedClient.set("java_token",jsonArray.get(0).toString());
						String token = (String)memCachedClient.get("java_token");
						System.out.println(token);
					}else{
						logger.error("json 解析出错");
						logger.info(respBody);
					}
					return null;
				}
	
				@Override
				public Object cancelled() {
					System.out.println("取消了");
					return null;
				}
			};
		    HttpHeader.custom().userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1700.107 Safari/537.36");
			
		    //HttpAsyncClientUtil.send(url, HttpHeader.custom().build(), Charset.defaultCharset().name() ,handler );
		
		    Map<String, Object> parasMap = new HashMap<String, Object>();
		    String phone = "13873183787";
		    String openid = "owTHvt4CEOAgQDfte3lHJwCSF-qg";
		    parasMap.put("phone", phone);
		    parasMap.put("openid", openid);
		    HttpAsyncClientUtil.send(url, parasMap, HttpHeader.custom().build(), Charset.defaultCharset().name() ,handler );
		
	}*/
}
