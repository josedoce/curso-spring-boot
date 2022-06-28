package github.josedoce.cursosb.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Url {
	private String param;
	
	public Url(String param) {
		this.param = param;
	}
	
	public static Url decode(String param) {
		return new Url(param);
	}
	
	public List<Integer> toIntegerList() {
		return Arrays.asList(this.param.split(","))
				.stream()
				.map(id-> Integer.parseInt(id))
				.collect(Collectors.toList());
	}
	
	public String toDecodedParam() {
		try {
			return URLDecoder.decode(this.param, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}
}
