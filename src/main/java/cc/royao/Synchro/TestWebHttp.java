package cc.royao.Synchro;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.util.EntityUtils;

public class TestWebHttp {
	static final String host = "192.168.1.140";

	public void getUserByPhone() throws IOException,
			InterruptedException {
		String url = "/api.php/User/myinfoByPhone/phone/13873183787";
		CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault();
		try {
			// Start the client
			httpclient.start();
			final CountDownLatch latch1 = new CountDownLatch(1);
			final HttpGet request2 = new HttpGet();
			
			httpclient.execute(request2, new FutureCallback<HttpResponse>() {

				@Override
				public void completed(final HttpResponse response2) {
					latch1.countDown();
					System.out.println(request2.getRequestLine() + "->"
							+ response2.getStatusLine());
					String respHtml;
					try {
						respHtml = EntityUtils.toString(response2.getEntity());
						
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					return;

				}

				@Override
				public void failed(final Exception ex) {
					latch1.countDown();
					System.out.println(request2.getRequestLine() + "->" + ex);
				}

				@Override
				public void cancelled() {
					latch1.countDown();
					System.out.println(request2.getRequestLine() + " cancelled");
				}

			});
			latch1.await();
			
		} finally {
			httpclient.close();
		}
	}
	
	public static void main(String[] args) throws InterruptedException, IOException, ExecutionException {
		//String url="http://192.168.1.140/api.php/Comm/area";
		/*ParserImpl webHttpImpl = new ParserImpl();
		SoPage sop;
		try {
			sop = new SoPage(webHttpImpl.loadGet(url));
			String strcontent = sop.getWres().getContentAsString();
			System.out.println(strcontent);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault();
		
        try {
            // Start the client
            httpclient.start();

            
            // Execute request
            final HttpGet request1 = new HttpGet("http://www.oschina.net/");
            request1.setHeader("user-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1700.107 Safari/537.36");
            Future<HttpResponse> future = httpclient.execute(request1, null);
            // and wait until response is received
            HttpResponse response1 = future.get();
            System.out.println(request1.getRequestLine() + "->" + response1.getStatusLine());
     		
            // One most likely would want to use a callback for operation result
            
            final CountDownLatch latch1 = new CountDownLatch(1);
            final HttpGet request2 = new HttpGet("http://192.168.1.140/api.php/User/myinfoByPhone/phone/13873183787");
            httpclient.execute(request2, new FutureCallback<HttpResponse>() {

                @Override
                public void completed(final HttpResponse response2) {
                    latch1.countDown();
                    System.out.println(request2.getRequestLine() + "->" + response2.getStatusLine());
                    String respHtml;
					try {
						respHtml = EntityUtils.toString(response2.getEntity());
						System.out.println(respHtml);
	                } catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                    
                    return ;
                
                }

                @Override
                public void failed(final Exception ex) {
                    latch1.countDown();
                    System.out.println(request2.getRequestLine() + "->" + ex);
                }

                @Override
                public void cancelled() {
                    latch1.countDown();
                    System.out.println(request2.getRequestLine() + " cancelled");
                }

            });
            latch1.await();
            
            System.out.println("Done");
        } finally {
            httpclient.close();
        }
        
		
       
		
	}

}
