/**
 * groovy小程序示例
 * @author liuxinyu
 */
class GroovyDemo{

    def static void main(args){
        sendSOAP()
    }

    /**
     * 读取网页内容保存到本地
     */
    def static saveUrl(){
        def url = "http://www.baidu.com"
        def writer = new BufferedWriter(new FileWriter("baidu.html"))

        url.toURL().eachLine{line->
            writer << line
        }

        writer.flush()
        writer.close()
    }

    /**
     * 查询手机归属地-SOAP
     */
    def static sendSOAP(){
        def mobileCode = '1364127'
        def soapRequest = """
			<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
			  <soap:Body>
			    <getMobileCodeInfo xmlns="http://WebXml.com.cn/">
			      <mobileCode>${mobileCode}</mobileCode>
			      <userID></userID>
			    </getMobileCodeInfo>
			  </soap:Body>
			</soap:Envelope>
		"""

        def soapUrl = new URL("http://webservice.webxml.com.cn/WebServices/MobileCodeWS.asmx")
        def conn = soapUrl.openConnection()
        conn.setRequestProperty('Content-Type','text/xml; charset=UTF-8')
        conn.doOutput = true

        def writer = new OutputStreamWriter(conn.outputStream)
        writer.write(soapRequest)
        writer.flush()
        writer.close()
        conn.connect()

        def soapResponse = ""
        def reader = new BufferedReader(new InputStreamReader(conn.content,"UTF-8"));
        reader.readLines().each{line->
            soapResponse += line
        }

        def Envelope = new XmlSlurper().parseText(soapResponse)
        println Envelope.Body.getMobileCodeInfoResponse.text()

    }

    /**
     * 查询手机归属地-POST
     */
    def static sendPOST(){
        def mobileCode = '1364127'
        def url = new URL("http://webservice.webxml.com.cn/WebServices/MobileCodeWS.asmx/getMobileCodeInfo" )
        def conn = url.openConnection()
        conn.setRequestMethod("POST" )
        def queryString = "userID=&mobileCode=${mobileCode}"
        conn.doOutput = true
        def writer = new OutputStreamWriter(conn.outputStream)
        writer.write(queryString)
        writer.flush()
        writer.close()
        conn.connect()

        def soapResponse = ""
        def reader = new BufferedReader(new InputStreamReader(conn.content,"UTF-8"));
        reader.readLines().each{line->
            soapResponse += line
        }

        def body = new XmlSlurper().parseText(soapResponse)
        println body.text()

    }

}
