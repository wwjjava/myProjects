package ztzb.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import ztzb.ws.entity.RequestMessage;
import ztzb.ws.entity.ResponseMessage;


@WebService (targetNamespace="https://www.zhaotouzhaobiao.com/interface/all/wsdl")
public interface IZtzbWebService {
	/**
	 * @WebMethod 定义了wsdl里显示的webservice方法名
	 * @WebParam 定义了 @WebMethod 里传递的参数
	 * @WebResult 定义了wsdl里显示的webservice返回值的名字
	 */
	@WebMethod(operationName="save")
	@WebResult(name="response")
   public ResponseMessage saveZtzbService(@WebParam(name="request")RequestMessage request);
}