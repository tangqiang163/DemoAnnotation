package com.sixeco.order.base.utils;

import ch.qos.logback.classic.Logger;
import org.apache.commons.httpclient.util.HttpURLConnection;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@SuppressWarnings("deprecation")
public class HttpUtils {

	static Logger log = (Logger) LoggerFactory.getLogger(HttpUtils.class);

	private static PoolingHttpClientConnectionManager connMgr;
	private static RequestConfig requestConfig;
	private static final int MAX_TIMEOUT = 7000; // 链接超时判断
	private static final int MAX_LOAD_TIMEOUT = 15000; // 处理超时时间

	static {
		// 设置连接池
		connMgr = new PoolingHttpClientConnectionManager();
		// 设置连接池大小
		connMgr.setMaxTotal(100);
		connMgr.setDefaultMaxPerRoute(connMgr.getMaxTotal());

		RequestConfig.Builder configBuilder = RequestConfig.custom();
		// 设置连接超时
		configBuilder.setConnectTimeout(MAX_TIMEOUT);
		// 设置从连接池获取连接实例的超时
		configBuilder.setConnectionRequestTimeout(MAX_LOAD_TIMEOUT);
		requestConfig = configBuilder.build();
	}

	/**
	 * 发送 GET 请求（HTTP），不带输入数据
	 * 
	 * @param url
	 * @return
	 */
	public static String doGet(String url) {
		return doGet(url, new HashMap<String, String>());
	}

	/**
	 * 发送 GET 请求（HTTP），K-V形式
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	public static String doGet(String url, Map<String, String> params) {
		String apiUrl = url;
		StringBuffer param = new StringBuffer();
		int i = 0;
		for (String key : params.keySet()) {
			if (i == 0) {
				param.append("?");
			} else {
				param.append("&");
			}
			param.append(key).append("=").append(params.get(key));
			i++;
		}
		apiUrl += param;
		String result = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(apiUrl);
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(httpGet);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					result = EntityUtils.toString(entity, "UTF-8");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeByGet(response, httpGet, httpClient);
		}
		return result;
	}

//	public static String doGetForKP(String url, Map<String, String> params) {
//
//		String apiUrl = url;
//		StringBuffer param = new StringBuffer();
//		String source = "";
//		int i = 0;
//		for (String key : params.keySet()) {
//			if (i == 0) {
//				param.append("?");
//			} else {
//				param.append("&");
//			}
//			param.append(key).append("=").append(params.get(key));
//			i++;
//			source = MapUtils.getString(params, "source");
//		}
//		apiUrl += param;
//		String result = null;
//		CloseableHttpClient httpClient = HttpClients.createDefault();
//		HttpGet httpGet = new HttpGet(apiUrl);
//		CloseableHttpResponse response = null;
//		try {
//			// 酷屏appCode 测试服111，正式服333
//			String appCode = apiUrl.contains("test-periphery-shop.baojunev.com") ? "111" : "333";
//			httpGet.addHeader("appCode", appCode);
//			httpGet.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
//			String userId = params.get("userId");
//			httpGet.addHeader("userId", userId);
//			httpGet.addHeader("source", source);
//			response = httpClient.execute(httpGet);
//			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
//				HttpEntity entity = response.getEntity();
//				if (entity != null) {
//					result = EntityUtils.toString(entity, "UTF-8");
//				}
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			closeByGet(response, httpGet, httpClient);
//		}
//		return result;
//	}

	/**
	 * 发送 POST 请求（HTTP），不带输入数据
	 * 
	 * @param apiUrl
	 * @return
	 */
	public static String doPost(String apiUrl) {
		return doPost(apiUrl, new HashMap<String, Object>());
	}

//	/**
//	 * 发送POST请求 (HTTP),数据在URL后 apiUrl?K=V&K=V...
//	 *
//	 * @author tanxin
//	 * @param apiUrl
//	 * @param map
//	 * @return
//	 */
//	public static String doPostParam(String apiUrl, Map<String, String> params) {
//		CloseableHttpClient httpClient = HttpClients.createDefault();
//		String httpStr = null;
//		// params.
//		Set<Entry<String, String>> entrySet = params.entrySet();
//		String data = "";
//		for (Entry<String, String> entry : entrySet) {
//			String key = entry.getKey();
//			String value = entry.getValue();
//			data = data + key + "=" + value + "&";
//		}
//		HttpPost httpPost = null;
//		if (!"".equals(data)) {
//			data = data.substring(0, data.length() - 1);
//			try {
//				data = URLEncoder.encode(data, AppConfig.CHAR_SET);
//			} catch (UnsupportedEncodingException e) {
//				e.printStackTrace();
//			}
//			httpPost = new HttpPost(apiUrl + "?" + data);
//		} else {
//			httpPost = new HttpPost(apiUrl);
//		}
//		httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
//		CloseableHttpResponse response = null;
//		try {
//			httpPost.setConfig(requestConfig);
//			response = httpClient.execute(httpPost);
//			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
//				HttpEntity entity = response.getEntity();
//				httpStr = EntityUtils.toString(entity, "UTF-8");
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			closeByPost(response, httpPost, httpClient);
//		}
//		return httpStr;
//	}

	/**
	 * 发送 POST 请求（HTTP），K-V形式
	 * 
	 * @param apiUrl
	 *            API接口URL
	 * @param params
	 *            参数map
	 * @return
	 */
	public static String doPost(String apiUrl, Map<String, String> params) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		String httpStr = null;
		HttpPost httpPost = new HttpPost(apiUrl);
		httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		CloseableHttpResponse response = null;
		try {
			httpPost.setConfig(requestConfig);
			List<NameValuePair> pairList = new ArrayList<>(params.size());
			for (Entry<String, String> entry : params.entrySet()) {
				NameValuePair pair = new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue()));
				pairList.add(pair);
			}
			httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName("UTF-8")));
			response = httpClient.execute(httpPost);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = response.getEntity();
				httpStr = EntityUtils.toString(entity, "UTF-8");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeByPost(response, httpPost, httpClient);
		}
		return httpStr;
	}

    /**
     * 发送 POST 请求（HTTP），K-V形式
     *
     * @param apiUrl
     *            API接口URL
     * @param params
     *            参数map
     * @return
     */
    public static String doPostCopy(String apiUrl, Map<String, String> params) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String httpStr = null;
        HttpPost httpPost = new HttpPost(apiUrl);
        httpPost.addHeader("Content-Type", "application/json");
        httpPost.addHeader("Accept","application/json");
        CloseableHttpResponse response = null;
        try {
            httpPost.setConfig(requestConfig);
            List<NameValuePair> pairList = new ArrayList<>(params.size());
            for (Entry<String, String> entry : params.entrySet()) {
                NameValuePair pair = new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue()));
                pairList.add(pair);
            }
            httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName("UTF-8")));
            response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                httpStr = EntityUtils.toString(entity, "UTF-8");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeByPost(response, httpPost, httpClient);
        }
        return httpStr;
    }

    /**
     * 发送 POST 请求（HTTP），JSON形式
     *
     * @param apiUrl
     * @param json
     *            json对象
     * @return
     */
    public static String doPostCopy(String apiUrl, Object json) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String httpStr = null;
        HttpPost httpPost = new HttpPost(apiUrl);
        CloseableHttpResponse response = null;
        try {
            httpPost.setConfig(requestConfig);
            StringEntity stringEntity = new StringEntity(json.toString(), Charset.forName("UTF-8"));// 解决中文乱码问题
            stringEntity.setContentEncoding("UTF-8");
            httpPost.addHeader("Content-Type", "application/json;charset=utf-8");
            httpPost.addHeader("Accept","application/json");
            stringEntity.setContentType("application/json");
            httpPost.setEntity(stringEntity);
            response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                httpStr = EntityUtils.toString(entity, "UTF-8");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeByPost(response, httpPost, httpClient);
        }
        return httpStr;
    }

    /**
     * 绕过验证 
     *
     * @return
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    public static SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException,
                                                            KeyManagementException {
        SSLContext sc = SSLContext.getInstance("SSLv3");

        // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法  
        X509TrustManager trustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(
                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(
                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };

        sc.init(null, new TrustManager[] { trustManager }, null);
        return sc;
    }

//	/**
//	 * 发送 POST 请求（HTTP）
//	 *
//	 * @author YUFEI
//	 * @param authKey
//	 * @param apiUrl
//	 *            API接口URL
//	 * @param params
//	 *            参数map
//	 * @return
//	 * @throws JsonProcessingException
//	 * @throws UnsupportedEncodingException
//	 */
//	public static String doPostTDC(String apiUrl, String authKey, String authKeyPWD, Map<String, Object> paramMap)
//			throws JsonProcessingException, UnsupportedEncodingException {
//
//		Map<String, Object> reqHeadMapStr = new HashMap<String, Object>();
//		Map<String, Object> reqHeadMap = new HashMap<String, Object>();
//		if (Strings.isNotBlank(authKey)) {
//			reqHeadMap.put(Constants.AUTHKEY, authKey); // authKey: 接口授权码
//			reqHeadMap.put(Constants.ENCRYPTMODE, Constants.ENCRYPTMODE_VALUE); // 加密方式 AES
//			String uuidStr = UUID.randomUUID().toString();
//			reqHeadMap.put(Constants.REQGUID, uuidStr); // reqGUID
//			String reqSignStr = authKey + "|" + authKeyPWD + "|" + uuidStr;
//			reqHeadMap.put(Constants.REGSIGN, MD5Utils.encodeMD5(reqSignStr)); // reqSign
//			reqHeadMapStr.put("reqHead", reqHeadMap);
//			log.info(">>>>>>>>>>>>>>> reqHead={}", reqHeadMap);
//		}
//
//		ObjectMapper jsonObj = new ObjectMapper();
//		String jsonStr = jsonObj.writeValueAsString(paramMap);
//		String encryStr = EncryptUtils.encrypt(jsonStr, authKeyPWD);
//		Map<String, Object> reqContentMap = new HashMap<String, Object>();
//		reqContentMap.put("reqContent", encryStr);
//		log.info(">>>>>>>>>>>>>>> reqContent={}", encryStr);
//
//		reqHeadMapStr.put("reqBody", reqContentMap);
//		log.info(">>>>>>>>>>>>>>> reqBody={}", reqContentMap);
//		JSONObject jsonObject = JSONObject.fromObject(reqHeadMapStr);
//		CloseableHttpClient httpClient = HttpClients.createDefault();
//		String httpStr = null;
//		HttpPost httpPost = new HttpPost(apiUrl);
//		CloseableHttpResponse response = null;
//		try {
//			httpPost.setConfig(requestConfig);
//			StringEntity stringEntity = new StringEntity(jsonObject.toString(), "UTF-8");// 解决中文乱码问题
//			stringEntity.setContentEncoding("UTF-8");
//			stringEntity.setContentType("application/json");
//			httpPost.setEntity(stringEntity);
//			response = httpClient.execute(httpPost);
//			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
//				HttpEntity entity = response.getEntity();
//				httpStr = EntityUtils.toString(entity, "UTF-8");
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			closeByPost(response, httpPost, httpClient);
//		}
//		return httpStr;
//
//	}

	/**
	 * 发送 POST 请求（HTTP），JSON形式
	 * 
	 * @param apiUrl
	 * @param json
	 *            json对象
	 * @return
	 */
	public static String doPost(String apiUrl, Object json) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		String httpStr = null;
		HttpPost httpPost = new HttpPost(apiUrl);
		CloseableHttpResponse response = null;
		try {
			httpPost.setConfig(requestConfig);
			StringEntity stringEntity = new StringEntity(json.toString(), "UTF-8");// 解决中文乱码问题
			stringEntity.setContentEncoding("UTF-8");
			stringEntity.setContentType("application/json");
			httpPost.setEntity(stringEntity);
			response = httpClient.execute(httpPost);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = response.getEntity();
				httpStr = EntityUtils.toString(entity, "UTF-8");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeByPost(response, httpPost, httpClient);
		}
		return httpStr;
	}

	public static String doPostXML(String apiUrl, String xmlStr) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		String httpStr = null;
		HttpPost httpPost = new HttpPost(apiUrl);
		CloseableHttpResponse response = null;
		try {
			httpPost.setConfig(requestConfig);
			httpPost.setHeader("Content-Type", "text/xml; charset=UTF-8");
			StringEntity stringEntity = new StringEntity(xmlStr, "UTF-8");// 解决中文乱码问题
			httpPost.setEntity(stringEntity);
			response = httpClient.execute(httpPost);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = response.getEntity();
				httpStr = EntityUtils.toString(entity, "UTF-8");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeByPost(response, httpPost, httpClient);
		}
		return httpStr;
	}

	/**
	 * 下载文件
	 * 
	 * @author tanxin
	 * @param apiUrl
	 * @param saveUrl
	 * @param filename
	 */
	public static void downLoad(String apiUrl, String saveUrl, String filename) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(apiUrl);
		CloseableHttpResponse response = null;
		try {
			httpGet.setConfig(requestConfig);
			response = httpClient.execute(httpGet);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					InputStream is = entity.getContent();
					File file = new File(saveUrl + filename);
					FileOutputStream fos = new FileOutputStream(file);
					byte[] buffer = new byte[2048];
					int len = -1;
					while ((len = is.read(buffer)) != -1) {
						fos.write(buffer, 0, len);
					}
					fos.close();
					is.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	/**
//	 * 发送带证书post请求
//	 *
//	 * @author tanxin
//	 * @param apiUrl
//	 * @param keyPath
//	 * @param xmlStr
//	 * @return
//	 * @throws Exception
//	 */
//	public static String doPostXMLAndPKCS12(String apiUrl, String keyPath, String xmlStr) throws Exception {
//		KeyStore keyStore = KeyStore.getInstance("PKCS12");
//		FileInputStream instream = new FileInputStream(new File(keyPath));
//		try {
//			keyStore.load(instream, WeiXinConfig.mch_id.toCharArray());
//		} finally {
//			instream.close();
//		}
//		// Trust own CA and all self-signed certs
//		SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, WeiXinConfig.mch_id.toCharArray())
//				.build();
//		// Allow TLSv1 protocol only
//		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" }, null,
//				SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
//		CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
//		// 发送http请求
//		String httpStr = "";
//		HttpPost httpPost = new HttpPost(apiUrl);
//		CloseableHttpResponse response = null;
//		try {
//			httpPost.setHeader("Content-Type", "text/xml; charset=UTF-8");
//			StringEntity stringEntity = new StringEntity(xmlStr, "UTF-8");// 解决中文乱码问题
//			httpPost.setEntity(stringEntity);
//			response = httpClient.execute(httpPost);
//			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
//				HttpEntity entity = response.getEntity();
//				httpStr = EntityUtils.toString(entity, "UTF-8");
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			closeByPost(response, httpPost, httpClient);
//		}
//		return httpStr;
//	}

	/**
	 * 正常关闭post请求
	 * 
	 * @author tanxin
	 * @param response
	 * @param httpPost
	 * @param httpClient
	 */
	private static void closeByPost(CloseableHttpResponse response, HttpPost httpPost, CloseableHttpClient httpClient) {
		if (response != null) {
			try {
				EntityUtils.consume(response.getEntity());
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		if (httpPost != null) {
			try {
				httpPost.abort();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (httpClient != null) {
			try {
				httpClient.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

//	/**
//	 *
//	 * 拉取里程post请求
//	 */
//	public static String doTripPost(String url, String key, Map<String, Object> bodyMap)
//			throws JsonProcessingException, UnsupportedEncodingException {
//
//		Map<String, Object> reqHeadMapStr = new HashMap<String, Object>();
//		Map<String, Object> reqHeadMap = new HashMap<String, Object>();
//		reqHeadMap.put("authKey", key);
//		reqHeadMapStr.put("reqHead", reqHeadMap);
//		reqHeadMapStr.put("reqBody", bodyMap);
//		JSONObject jsonObject = JSONObject.fromObject(reqHeadMapStr);
//		CloseableHttpClient httpClient = HttpClients.createDefault();
//		String httpStr = null;
//		HttpPost httpPost = new HttpPost(url);
//		CloseableHttpResponse response = null;
//		try {
//			httpPost.setConfig(requestConfig);
//			StringEntity stringEntity = new StringEntity(jsonObject.toString(), "UTF-8");// 解决中文乱码问题
//			stringEntity.setContentEncoding("UTF-8");
//			stringEntity.setContentType("application/json");
//			httpPost.setEntity(stringEntity);
//			response = httpClient.execute(httpPost);
//			System.out.println(response.getStatusLine().getStatusCode());
//
//			if (200 == response.getStatusLine().getStatusCode()) {
//				HttpEntity entity = response.getEntity();
//				httpStr = EntityUtils.toString(entity, "UTF-8");
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//
//		}
//		return httpStr;
//
//	}

	/**
	 * 正常关闭post请求
	 * 
	 * @author tanxin
	 * @param response
	 * @param httpGet
	 * @param httpClient
	 */
	private static void closeByGet(CloseableHttpResponse response, HttpGet httpGet, CloseableHttpClient httpClient) {
		if (response != null) {
			try {
				EntityUtils.consume(response.getEntity());
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		if (httpGet != null) {
			try {
				httpGet.abort();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (httpClient != null) {
			try {
				httpClient.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 发送 GET 请求（HTTP），K-V形式
	 *
	 * @param url
	 * @param params
	 * @return
	 */
	public static String doGetWithSessionId(String url, String sessionId, Map<String, String> params) {
		String apiUrl = url;
		StringBuffer param = new StringBuffer();
		int i = 0;
		for (String key : params.keySet()) {
			if (i == 0) {
				param.append("?");
			} else {
				param.append("&");
			}
			param.append(key).append("=").append(params.get(key));
			i++;
		}
		apiUrl += param;
		String result = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(apiUrl);
		CloseableHttpResponse response = null;
		try {
			if (Strings.isNotBlank(sessionId)) {
				httpGet.addHeader("Authorization", sessionId);
			}
			httpGet.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			response = httpClient.execute(httpGet);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					result = EntityUtils.toString(entity, "UTF-8");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeByGet(response, httpGet, httpClient);
		}
		return result;
	}

//	/**
//	 * 发送POST请求 (HTTP),数据在URL后 apiUrl?K=V&K=V...
//	 *
//	 * @author tanxin
//	 * @param apiUrl
//	 * @param map
//	 * @return
//	 */
//	public static String doPostParamWithSessionId(String apiUrl, String sessionId, Map<String, String> params) {
//		JSONObject jsonObject = JSONObject.fromObject(params);
//		CloseableHttpClient httpClient = HttpClients.createDefault();
//		String httpStr = null;
//		HttpPost httpPost = new HttpPost(apiUrl);
//		CloseableHttpResponse response = null;
//		try {
//			httpPost.setConfig(requestConfig);
//			if (Strings.isNotBlank(sessionId)) {
//				httpPost.addHeader("Authorization", sessionId);
//			}
//			StringEntity stringEntity = new StringEntity(jsonObject.toString(), "UTF-8");// 解决中文乱码问题
//			stringEntity.setContentEncoding("UTF-8");
//			stringEntity.setContentType("application/json");
//			httpPost.setEntity(stringEntity);
//			response = httpClient.execute(httpPost);
//			log.info("远程调用url:{},参数{},响应code{}", apiUrl, jsonObject.toString(),
//					response.getStatusLine().getStatusCode());
//			if (200 == response.getStatusLine().getStatusCode()) {
//				HttpEntity entity = response.getEntity();
//				httpStr = EntityUtils.toString(entity, "UTF-8");
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//
//		}
//		return httpStr;
//	}

	public static String postReq(String url, String json, String type) {
		CloseableHttpClient client = null;
		CloseableHttpResponse httpResponse = null;
		try {
			client = HttpClients.createDefault();
			HttpPost post = new HttpPost(url);
			post.setConfig(requestConfig);
			post.addHeader("Content-type", type);
			StringEntity entity = new StringEntity(json, ContentType.create(type, "utf-8"));
			post.setEntity(entity);
			httpResponse = client.execute(post);
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			if (statusCode >= 200 && statusCode < 300) {
				String str = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
				return str;
			}
		} catch (UnsupportedCharsetException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (httpResponse != null) {
				try {
					httpResponse.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (client != null) {
				try {
					client.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public static String getReq(String url) {
		CloseableHttpClient client = null;
		CloseableHttpResponse httpResponse = null;
		try {
			client = HttpClients.createDefault();
			HttpGet get = new HttpGet(url);
			get.setConfig(requestConfig);
			httpResponse = client.execute(get);
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			if (statusCode >= 200 && statusCode < 300) {
				String str = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
				return str;
			}
		} catch (UnsupportedCharsetException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (httpResponse != null) {
				try {
					httpResponse.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (client != null) {
				try {
					client.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	/**
	 * 
	 * @param url
	 *            需要请求的网关路径
	 * @param sendData
	 *            请求时需要传入的参数
	 * @param urlencode
	 *            url的编码格式
	 * @param connTimeOut
	 *            链接超时时间
	 * @param readTimeOut
	 *            读取超时时间
	 * @param contentType
	 *            请求头部 固定输入"application/x-www-form-urlencoded;charset="+urlencode
	 * @param header
	 *            输入null
	 * @return
	 */
	public static String sendAndRcvHttpPostBase(String url, String sendData, String urlencode, int connTimeOut,
			int readTimeOut, String contentType, Map<String, String> header) {
		Long curTime = System.currentTimeMillis();
		String result = "";
		BufferedReader in = null;
		DataOutputStream out = null;
		int code = 999;
		HttpsURLConnection httpsConn = null;
		HttpURLConnection httpConn = null;
		try {
			URL myURL = new URL(url);
			log.info("请求地址：" + url);
			if (url.startsWith("https://")) {
				httpsConn = (HttpsURLConnection) myURL.openConnection();
				TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
					@Override
					public java.security.cert.X509Certificate[] getAcceptedIssuers() {
						return null;
					}

					@Override
					public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
					}

					@Override
					public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
					}
				} };
				SSLContext sc = SSLContext.getInstance("TLS");
				sc.init(null, trustAllCerts, new java.security.SecureRandom());
				httpsConn.setSSLSocketFactory(sc.getSocketFactory());
				HostnameVerifier hv = new HostnameVerifier() {
					@Override
					public boolean verify(String urlHostName, SSLSession session) {
						return true;
					}
				};
				httpsConn.setHostnameVerifier(hv);

				httpsConn.setRequestProperty("Accept-Charset", urlencode);
				httpsConn.setRequestProperty("User-Agent", "java HttpsURLConnection");
				if (header != null) {
					for (String key : header.keySet()) {
						httpsConn.setRequestProperty(key, (String) header.get(key));
					}
				}
				httpsConn.setRequestMethod("GET");
				httpsConn.setUseCaches(false);
				httpsConn.setRequestProperty("Content-Type", contentType);
				httpsConn.setConnectTimeout(connTimeOut);
				httpsConn.setReadTimeout(readTimeOut);
				httpsConn.setDoInput(true);
				httpsConn.setInstanceFollowRedirects(true);
				if (sendData != null) {
					httpsConn.setDoOutput(true);
					// 获取URLConnection对象对应的输出流
					out = new DataOutputStream(httpsConn.getOutputStream());
					// 发送请求参数
					out.write(sendData.getBytes(urlencode));
					// flush输出流的缓冲
					out.flush();
					out.close();
				}
				// 取得该连接的输入流，以读取响应内容
				in = new BufferedReader(new InputStreamReader(httpsConn.getInputStream(), urlencode));
				code = httpsConn.getResponseCode();
			} else {
				httpConn = (HttpURLConnection) myURL.openConnection();
				httpConn.setRequestProperty("Accept-Charset", urlencode);
				httpConn.setRequestProperty("user-agent", "java HttpURLConnection");
				if (header != null) {
					for (String key : header.keySet()) {
						httpConn.setRequestProperty(key, (String) header.get(key));
					}
				}
				httpConn.setRequestMethod("POST");
				httpConn.setUseCaches(false);
				httpConn.setRequestProperty("Content-Type", contentType);
				httpConn.setConnectTimeout(connTimeOut);
				httpConn.setReadTimeout(readTimeOut);
				httpConn.setDoInput(true);
				httpConn.setInstanceFollowRedirects(true);
				if (sendData != null) {
					httpConn.setDoOutput(true);
					// 获取URLConnection对象对应的输出流
					out = new DataOutputStream(httpConn.getOutputStream());
					// 发送请求参数
					out.write(sendData.getBytes(urlencode));
					// flush输出流的缓冲
					out.flush();
					out.close();
				}
				// 取得该连接的输入流，以读取响应内容
				in = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), urlencode));
				code = httpConn.getResponseCode();
			}
			if (HttpURLConnection.HTTP_OK == code) {
				String line;
				while ((line = in.readLine()) != null) {
					result += line;

					System.out.println("=====反回结果=====" + line);

				}
				if (result.length() > 2000) {
					log.info("http返回结果 !\n" + result.substring(0, 2000) + "...");
				} else {
					log.info("http返回结果 !\n" + result);
				}
			} else {
				result = null;
				throw new Exception("支付失败,服务端响应码：" + code);
			}
		} catch (IOException e) {
			log.info("http通讯失败 !", e);
			result = null;
		} catch (Exception e) {
			log.info("http通讯失败 !", e);
			result = null;
		} finally {
			log.info("对方地址：" + url);
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
			if (httpConn != null) {
				httpConn.disconnect();
			}
			if (httpsConn != null) {
				httpsConn.disconnect();
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
		}
		log.info("SimpleHttpConnUtil " + curTime + " end for " + (System.currentTimeMillis() - curTime) + "ms");
		return result;
	}

	public static void main(String[] args) {
//		System.out.print(FileUtil.getNewFileName(
//				"https://baojunev-mall.oss-cn-shenzhen.aliyuncs.com/20180203213043-6cd66542c7bc412cb1a40908459d5eca.png"));
		// HttpUtils.downLoad("https://baojunev-mall.oss-cn-shenzhen.aliyuncs.com/20180203213043-6cd66542c7bc412cb1a40908459d5eca.png",
		// "D:/test/", "img_1.png");
	}
}
