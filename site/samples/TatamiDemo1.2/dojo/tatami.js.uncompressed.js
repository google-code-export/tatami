/*
	Copyright (c) 2004-2008, The Dojo Foundation
	All Rights Reserved.

	Licensed under the Academic Free License version 2.1 or above OR the
	modified BSD license. For more information on Dojo licensing, see:

		http://dojotoolkit.org/book/dojo-book-0-9/introduction/licensing
*/

/*
	This is a compiled version of Dojo, built for deployment and not for
	development. To get an editable version, please visit:

		http://dojotoolkit.org

	for documentation and information on getting the source.
*/

if(!dojo._hasResource["dojox.encoding.base64"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojox.encoding.base64"] = true;
dojo.provide("dojox.encoding.base64");

(function(){
	var p="=";
	var tab="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
	var dxe=dojox.encoding;

	dxe.base64.encode=function(/* byte[] */ba){
		//	summary
		//	Encode an array of bytes as a base64-encoded string
		var s=[], l=ba.length;
		var rm=l%3;
		var x=l-rm;
		for (var i=0; i<x;){
			var t=ba[i++]<<16|ba[i++]<<8|ba[i++];
			s.push(tab.charAt((t>>>18)&0x3f)); 
			s.push(tab.charAt((t>>>12)&0x3f));
			s.push(tab.charAt((t>>>6)&0x3f));
			s.push(tab.charAt(t&0x3f));
		}
		//	deal with trailers, based on patch from Peter Wood.
		switch(rm){
			case 2:{
				var t=ba[i++]<<16|ba[i++]<<8;
				s.push(tab.charAt((t>>>18)&0x3f));
				s.push(tab.charAt((t>>>12)&0x3f));
				s.push(tab.charAt((t>>>6)&0x3f));
				s.push(p);
				break;
			}
			case 1:{
				var t=ba[i++]<<16;
				s.push(tab.charAt((t>>>18)&0x3f));
				s.push(tab.charAt((t>>>12)&0x3f));
				s.push(p);
				s.push(p);
				break;
			}
		}
		return s.join("");	//	string
	};

	dxe.base64.decode=function(/* string */str){
		//	summary
		//	Convert a base64-encoded string to an array of bytes
		var s=str.split(""), out=[];
		var l=s.length;
		while(s[--l]==p){ }	//	strip off trailing padding
		for (var i=0; i<l;){
			var t=tab.indexOf(s[i++])<<18;
			if(i<=l){ t|=tab.indexOf(s[i++])<<12 };
			if(i<=l){ t|=tab.indexOf(s[i++])<<6 };
			if(i<=l){ t|=tab.indexOf(s[i++]) };
			out.push((t>>>16)&0xff);
			out.push((t>>>8)&0xff);
			out.push(t&0xff);
		}
		//	strip off any null bytes
		while(out[out.length-1]==0){ out.pop(); }
		return out;	//	byte[]
	};
})();

}

if(!dojo._hasResource["dojox.encoding.crypto._base"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojox.encoding.crypto._base"] = true;
dojo.provide("dojox.encoding.crypto._base");

(function(){
	var c=dojox.encoding.crypto;
	c.cipherModes={
		//	summary
		//	Enumeration for various cipher modes.
		ECB:0, CBC:1, PCBC:2, CFB:3, OFB:4, CTR:5 
	};
	c.outputTypes={ 
		//	summary
		//	Enumeration for input and output encodings.
		Base64:0, Hex:1, String:2, Raw:3 
	};
})();

}

if(!dojo._hasResource["dojox.encoding.crypto.Blowfish"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojox.encoding.crypto.Blowfish"] = true;
dojo.provide("dojox.encoding.crypto.Blowfish");




/*	Blowfish
 *	Created based on the C# implementation by Marcus Hahn (http://www.hotpixel.net/)
 *	Unsigned math based on Paul Johnstone and Peter Wood patches.
 *	2005-12-08
 */
dojox.encoding.crypto.Blowfish = new function(){
	//	summary
	//	Object for doing Blowfish encryption/decryption.
	var POW2=Math.pow(2,2);
	var POW3=Math.pow(2,3);
	var POW4=Math.pow(2,4);
	var POW8=Math.pow(2,8);
	var POW16=Math.pow(2,16);
	var POW24=Math.pow(2,24);
	var iv=null;	//	CBC mode initialization vector
	var boxes={
		p:[
			0x243f6a88, 0x85a308d3, 0x13198a2e, 0x03707344, 0xa4093822, 0x299f31d0, 0x082efa98, 0xec4e6c89, 
			0x452821e6, 0x38d01377, 0xbe5466cf, 0x34e90c6c, 0xc0ac29b7, 0xc97c50dd, 0x3f84d5b5, 0xb5470917, 
			0x9216d5d9, 0x8979fb1b
		],
		s0:[
			0xd1310ba6, 0x98dfb5ac, 0x2ffd72db, 0xd01adfb7, 0xb8e1afed, 0x6a267e96, 0xba7c9045, 0xf12c7f99,
			0x24a19947, 0xb3916cf7, 0x0801f2e2, 0x858efc16, 0x636920d8, 0x71574e69, 0xa458fea3, 0xf4933d7e,
			0x0d95748f, 0x728eb658, 0x718bcd58, 0x82154aee, 0x7b54a41d, 0xc25a59b5, 0x9c30d539, 0x2af26013,
			0xc5d1b023, 0x286085f0, 0xca417918, 0xb8db38ef, 0x8e79dcb0, 0x603a180e, 0x6c9e0e8b, 0xb01e8a3e,
			0xd71577c1, 0xbd314b27, 0x78af2fda, 0x55605c60, 0xe65525f3, 0xaa55ab94, 0x57489862, 0x63e81440,
			0x55ca396a, 0x2aab10b6, 0xb4cc5c34, 0x1141e8ce, 0xa15486af, 0x7c72e993, 0xb3ee1411, 0x636fbc2a,
			0x2ba9c55d, 0x741831f6, 0xce5c3e16, 0x9b87931e, 0xafd6ba33, 0x6c24cf5c, 0x7a325381, 0x28958677,
			0x3b8f4898, 0x6b4bb9af, 0xc4bfe81b, 0x66282193, 0x61d809cc, 0xfb21a991, 0x487cac60, 0x5dec8032,
			0xef845d5d, 0xe98575b1, 0xdc262302, 0xeb651b88, 0x23893e81, 0xd396acc5, 0x0f6d6ff3, 0x83f44239,
			0x2e0b4482, 0xa4842004, 0x69c8f04a, 0x9e1f9b5e, 0x21c66842, 0xf6e96c9a, 0x670c9c61, 0xabd388f0,
			0x6a51a0d2, 0xd8542f68, 0x960fa728, 0xab5133a3, 0x6eef0b6c, 0x137a3be4, 0xba3bf050, 0x7efb2a98, 
			0xa1f1651d, 0x39af0176, 0x66ca593e, 0x82430e88, 0x8cee8619, 0x456f9fb4, 0x7d84a5c3, 0x3b8b5ebe, 
			0xe06f75d8, 0x85c12073, 0x401a449f, 0x56c16aa6, 0x4ed3aa62, 0x363f7706, 0x1bfedf72, 0x429b023d, 
			0x37d0d724, 0xd00a1248, 0xdb0fead3, 0x49f1c09b, 0x075372c9, 0x80991b7b, 0x25d479d8, 0xf6e8def7,
			0xe3fe501a, 0xb6794c3b, 0x976ce0bd, 0x04c006ba, 0xc1a94fb6, 0x409f60c4, 0x5e5c9ec2, 0x196a2463,
			0x68fb6faf, 0x3e6c53b5, 0x1339b2eb, 0x3b52ec6f, 0x6dfc511f, 0x9b30952c, 0xcc814544, 0xaf5ebd09,
			0xbee3d004, 0xde334afd, 0x660f2807, 0x192e4bb3, 0xc0cba857, 0x45c8740f, 0xd20b5f39, 0xb9d3fbdb, 
			0x5579c0bd, 0x1a60320a, 0xd6a100c6, 0x402c7279, 0x679f25fe, 0xfb1fa3cc, 0x8ea5e9f8, 0xdb3222f8, 
			0x3c7516df, 0xfd616b15, 0x2f501ec8, 0xad0552ab, 0x323db5fa, 0xfd238760, 0x53317b48, 0x3e00df82, 
			0x9e5c57bb, 0xca6f8ca0, 0x1a87562e, 0xdf1769db, 0xd542a8f6, 0x287effc3, 0xac6732c6, 0x8c4f5573, 
			0x695b27b0, 0xbbca58c8, 0xe1ffa35d, 0xb8f011a0, 0x10fa3d98, 0xfd2183b8, 0x4afcb56c, 0x2dd1d35b,
			0x9a53e479, 0xb6f84565, 0xd28e49bc, 0x4bfb9790, 0xe1ddf2da, 0xa4cb7e33, 0x62fb1341, 0xcee4c6e8, 
			0xef20cada, 0x36774c01, 0xd07e9efe, 0x2bf11fb4, 0x95dbda4d, 0xae909198, 0xeaad8e71, 0x6b93d5a0,
			0xd08ed1d0, 0xafc725e0, 0x8e3c5b2f, 0x8e7594b7, 0x8ff6e2fb, 0xf2122b64, 0x8888b812, 0x900df01c, 
			0x4fad5ea0, 0x688fc31c, 0xd1cff191, 0xb3a8c1ad, 0x2f2f2218, 0xbe0e1777, 0xea752dfe, 0x8b021fa1,
			0xe5a0cc0f, 0xb56f74e8, 0x18acf3d6, 0xce89e299, 0xb4a84fe0, 0xfd13e0b7, 0x7cc43b81, 0xd2ada8d9,
			0x165fa266, 0x80957705, 0x93cc7314, 0x211a1477, 0xe6ad2065, 0x77b5fa86, 0xc75442f5, 0xfb9d35cf,
			0xebcdaf0c, 0x7b3e89a0, 0xd6411bd3, 0xae1e7e49, 0x00250e2d, 0x2071b35e, 0x226800bb, 0x57b8e0af,
			0x2464369b, 0xf009b91e, 0x5563911d, 0x59dfa6aa, 0x78c14389, 0xd95a537f, 0x207d5ba2, 0x02e5b9c5,
			0x83260376, 0x6295cfa9, 0x11c81968, 0x4e734a41, 0xb3472dca, 0x7b14a94a, 0x1b510052, 0x9a532915,
			0xd60f573f, 0xbc9bc6e4, 0x2b60a476, 0x81e67400, 0x08ba6fb5, 0x571be91f, 0xf296ec6b, 0x2a0dd915,
			0xb6636521, 0xe7b9f9b6, 0xff34052e, 0xc5855664, 0x53b02d5d, 0xa99f8fa1, 0x08ba4799, 0x6e85076a
		],
		s1:[
			0x4b7a70e9, 0xb5b32944, 0xdb75092e, 0xc4192623, 0xad6ea6b0, 0x49a7df7d, 0x9cee60b8, 0x8fedb266,
			0xecaa8c71, 0x699a17ff, 0x5664526c, 0xc2b19ee1, 0x193602a5, 0x75094c29, 0xa0591340, 0xe4183a3e,
			0x3f54989a, 0x5b429d65, 0x6b8fe4d6, 0x99f73fd6, 0xa1d29c07, 0xefe830f5, 0x4d2d38e6, 0xf0255dc1,
			0x4cdd2086, 0x8470eb26, 0x6382e9c6, 0x021ecc5e, 0x09686b3f, 0x3ebaefc9, 0x3c971814, 0x6b6a70a1,
			0x687f3584, 0x52a0e286, 0xb79c5305, 0xaa500737, 0x3e07841c, 0x7fdeae5c, 0x8e7d44ec, 0x5716f2b8,
			0xb03ada37, 0xf0500c0d, 0xf01c1f04, 0x0200b3ff, 0xae0cf51a, 0x3cb574b2, 0x25837a58, 0xdc0921bd,
			0xd19113f9, 0x7ca92ff6, 0x94324773, 0x22f54701, 0x3ae5e581, 0x37c2dadc, 0xc8b57634, 0x9af3dda7,
			0xa9446146, 0x0fd0030e, 0xecc8c73e, 0xa4751e41, 0xe238cd99, 0x3bea0e2f, 0x3280bba1, 0x183eb331, 
			0x4e548b38, 0x4f6db908, 0x6f420d03, 0xf60a04bf, 0x2cb81290, 0x24977c79, 0x5679b072, 0xbcaf89af, 
			0xde9a771f, 0xd9930810, 0xb38bae12, 0xdccf3f2e, 0x5512721f, 0x2e6b7124, 0x501adde6, 0x9f84cd87,
			0x7a584718, 0x7408da17, 0xbc9f9abc, 0xe94b7d8c, 0xec7aec3a, 0xdb851dfa, 0x63094366, 0xc464c3d2, 
			0xef1c1847, 0x3215d908, 0xdd433b37, 0x24c2ba16, 0x12a14d43, 0x2a65c451, 0x50940002, 0x133ae4dd,
			0x71dff89e, 0x10314e55, 0x81ac77d6, 0x5f11199b, 0x043556f1, 0xd7a3c76b, 0x3c11183b, 0x5924a509, 
			0xf28fe6ed, 0x97f1fbfa, 0x9ebabf2c, 0x1e153c6e, 0x86e34570, 0xeae96fb1, 0x860e5e0a, 0x5a3e2ab3, 
			0x771fe71c, 0x4e3d06fa, 0x2965dcb9, 0x99e71d0f, 0x803e89d6, 0x5266c825, 0x2e4cc978, 0x9c10b36a,
			0xc6150eba, 0x94e2ea78, 0xa5fc3c53, 0x1e0a2df4, 0xf2f74ea7, 0x361d2b3d, 0x1939260f, 0x19c27960, 
			0x5223a708, 0xf71312b6, 0xebadfe6e, 0xeac31f66, 0xe3bc4595, 0xa67bc883, 0xb17f37d1, 0x018cff28,
			0xc332ddef, 0xbe6c5aa5, 0x65582185, 0x68ab9802, 0xeecea50f, 0xdb2f953b, 0x2aef7dad, 0x5b6e2f84, 
			0x1521b628, 0x29076170, 0xecdd4775, 0x619f1510, 0x13cca830, 0xeb61bd96, 0x0334fe1e, 0xaa0363cf,
			0xb5735c90, 0x4c70a239, 0xd59e9e0b, 0xcbaade14, 0xeecc86bc, 0x60622ca7, 0x9cab5cab, 0xb2f3846e, 
			0x648b1eaf, 0x19bdf0ca, 0xa02369b9, 0x655abb50, 0x40685a32, 0x3c2ab4b3, 0x319ee9d5, 0xc021b8f7, 
			0x9b540b19, 0x875fa099, 0x95f7997e, 0x623d7da8, 0xf837889a, 0x97e32d77, 0x11ed935f, 0x16681281, 
			0x0e358829, 0xc7e61fd6, 0x96dedfa1, 0x7858ba99, 0x57f584a5, 0x1b227263, 0x9b83c3ff, 0x1ac24696,
			0xcdb30aeb, 0x532e3054, 0x8fd948e4, 0x6dbc3128, 0x58ebf2ef, 0x34c6ffea, 0xfe28ed61, 0xee7c3c73,
			0x5d4a14d9, 0xe864b7e3, 0x42105d14, 0x203e13e0, 0x45eee2b6, 0xa3aaabea, 0xdb6c4f15, 0xfacb4fd0, 
			0xc742f442, 0xef6abbb5, 0x654f3b1d, 0x41cd2105, 0xd81e799e, 0x86854dc7, 0xe44b476a, 0x3d816250,
			0xcf62a1f2, 0x5b8d2646, 0xfc8883a0, 0xc1c7b6a3, 0x7f1524c3, 0x69cb7492, 0x47848a0b, 0x5692b285,
			0x095bbf00, 0xad19489d, 0x1462b174, 0x23820e00, 0x58428d2a, 0x0c55f5ea, 0x1dadf43e, 0x233f7061,
			0x3372f092, 0x8d937e41, 0xd65fecf1, 0x6c223bdb, 0x7cde3759, 0xcbee7460, 0x4085f2a7, 0xce77326e,
			0xa6078084, 0x19f8509e, 0xe8efd855, 0x61d99735, 0xa969a7aa, 0xc50c06c2, 0x5a04abfc, 0x800bcadc,
			0x9e447a2e, 0xc3453484, 0xfdd56705, 0x0e1e9ec9, 0xdb73dbd3, 0x105588cd, 0x675fda79, 0xe3674340,
			0xc5c43465, 0x713e38d8, 0x3d28f89e, 0xf16dff20, 0x153e21e7, 0x8fb03d4a, 0xe6e39f2b, 0xdb83adf7
		],
		s2:[
			0xe93d5a68, 0x948140f7, 0xf64c261c, 0x94692934, 0x411520f7, 0x7602d4f7, 0xbcf46b2e, 0xd4a20068,
			0xd4082471, 0x3320f46a, 0x43b7d4b7, 0x500061af, 0x1e39f62e, 0x97244546, 0x14214f74, 0xbf8b8840,
			0x4d95fc1d, 0x96b591af, 0x70f4ddd3, 0x66a02f45, 0xbfbc09ec, 0x03bd9785, 0x7fac6dd0, 0x31cb8504,
			0x96eb27b3, 0x55fd3941, 0xda2547e6, 0xabca0a9a, 0x28507825, 0x530429f4, 0x0a2c86da, 0xe9b66dfb,
			0x68dc1462, 0xd7486900, 0x680ec0a4, 0x27a18dee, 0x4f3ffea2, 0xe887ad8c, 0xb58ce006, 0x7af4d6b6,
			0xaace1e7c, 0xd3375fec, 0xce78a399, 0x406b2a42, 0x20fe9e35, 0xd9f385b9, 0xee39d7ab, 0x3b124e8b,
			0x1dc9faf7, 0x4b6d1856, 0x26a36631, 0xeae397b2, 0x3a6efa74, 0xdd5b4332, 0x6841e7f7, 0xca7820fb,
			0xfb0af54e, 0xd8feb397, 0x454056ac, 0xba489527, 0x55533a3a, 0x20838d87, 0xfe6ba9b7, 0xd096954b,
			0x55a867bc, 0xa1159a58, 0xcca92963, 0x99e1db33, 0xa62a4a56, 0x3f3125f9, 0x5ef47e1c, 0x9029317c,
			0xfdf8e802, 0x04272f70, 0x80bb155c, 0x05282ce3, 0x95c11548, 0xe4c66d22, 0x48c1133f, 0xc70f86dc,
			0x07f9c9ee, 0x41041f0f, 0x404779a4, 0x5d886e17, 0x325f51eb, 0xd59bc0d1, 0xf2bcc18f, 0x41113564,
			0x257b7834, 0x602a9c60, 0xdff8e8a3, 0x1f636c1b, 0x0e12b4c2, 0x02e1329e, 0xaf664fd1, 0xcad18115,
			0x6b2395e0, 0x333e92e1, 0x3b240b62, 0xeebeb922, 0x85b2a20e, 0xe6ba0d99, 0xde720c8c, 0x2da2f728,
			0xd0127845, 0x95b794fd, 0x647d0862, 0xe7ccf5f0, 0x5449a36f, 0x877d48fa, 0xc39dfd27, 0xf33e8d1e,
			0x0a476341, 0x992eff74, 0x3a6f6eab, 0xf4f8fd37, 0xa812dc60, 0xa1ebddf8, 0x991be14c, 0xdb6e6b0d,
			0xc67b5510, 0x6d672c37, 0x2765d43b, 0xdcd0e804, 0xf1290dc7, 0xcc00ffa3, 0xb5390f92, 0x690fed0b,
			0x667b9ffb, 0xcedb7d9c, 0xa091cf0b, 0xd9155ea3, 0xbb132f88, 0x515bad24, 0x7b9479bf, 0x763bd6eb,
			0x37392eb3, 0xcc115979, 0x8026e297, 0xf42e312d, 0x6842ada7, 0xc66a2b3b, 0x12754ccc, 0x782ef11c,
			0x6a124237, 0xb79251e7, 0x06a1bbe6, 0x4bfb6350, 0x1a6b1018, 0x11caedfa, 0x3d25bdd8, 0xe2e1c3c9,
			0x44421659, 0x0a121386, 0xd90cec6e, 0xd5abea2a, 0x64af674e, 0xda86a85f, 0xbebfe988, 0x64e4c3fe,
			0x9dbc8057, 0xf0f7c086, 0x60787bf8, 0x6003604d, 0xd1fd8346, 0xf6381fb0, 0x7745ae04, 0xd736fccc, 
			0x83426b33, 0xf01eab71, 0xb0804187, 0x3c005e5f, 0x77a057be, 0xbde8ae24, 0x55464299, 0xbf582e61,
			0x4e58f48f, 0xf2ddfda2, 0xf474ef38, 0x8789bdc2, 0x5366f9c3, 0xc8b38e74, 0xb475f255, 0x46fcd9b9,
			0x7aeb2661, 0x8b1ddf84, 0x846a0e79, 0x915f95e2, 0x466e598e, 0x20b45770, 0x8cd55591, 0xc902de4c, 
			0xb90bace1, 0xbb8205d0, 0x11a86248, 0x7574a99e, 0xb77f19b6, 0xe0a9dc09, 0x662d09a1, 0xc4324633, 
			0xe85a1f02, 0x09f0be8c, 0x4a99a025, 0x1d6efe10, 0x1ab93d1d, 0x0ba5a4df, 0xa186f20f, 0x2868f169, 
			0xdcb7da83, 0x573906fe, 0xa1e2ce9b, 0x4fcd7f52, 0x50115e01, 0xa70683fa, 0xa002b5c4, 0x0de6d027, 
			0x9af88c27, 0x773f8641, 0xc3604c06, 0x61a806b5, 0xf0177a28, 0xc0f586e0, 0x006058aa, 0x30dc7d62,
			0x11e69ed7, 0x2338ea63, 0x53c2dd94, 0xc2c21634, 0xbbcbee56, 0x90bcb6de, 0xebfc7da1, 0xce591d76,
			0x6f05e409, 0x4b7c0188, 0x39720a3d, 0x7c927c24, 0x86e3725f, 0x724d9db9, 0x1ac15bb4, 0xd39eb8fc, 
			0xed545578, 0x08fca5b5, 0xd83d7cd3, 0x4dad0fc4, 0x1e50ef5e, 0xb161e6f8, 0xa28514d9, 0x6c51133c,
			0x6fd5c7e7, 0x56e14ec4, 0x362abfce, 0xddc6c837, 0xd79a3234, 0x92638212, 0x670efa8e, 0x406000e0
		],
		s3:[
			0x3a39ce37, 0xd3faf5cf, 0xabc27737, 0x5ac52d1b, 0x5cb0679e, 0x4fa33742, 0xd3822740, 0x99bc9bbe,
			0xd5118e9d, 0xbf0f7315, 0xd62d1c7e, 0xc700c47b, 0xb78c1b6b, 0x21a19045, 0xb26eb1be, 0x6a366eb4,
			0x5748ab2f, 0xbc946e79, 0xc6a376d2, 0x6549c2c8, 0x530ff8ee, 0x468dde7d, 0xd5730a1d, 0x4cd04dc6, 
			0x2939bbdb, 0xa9ba4650, 0xac9526e8, 0xbe5ee304, 0xa1fad5f0, 0x6a2d519a, 0x63ef8ce2, 0x9a86ee22,
			0xc089c2b8, 0x43242ef6, 0xa51e03aa, 0x9cf2d0a4, 0x83c061ba, 0x9be96a4d, 0x8fe51550, 0xba645bd6, 
			0x2826a2f9, 0xa73a3ae1, 0x4ba99586, 0xef5562e9, 0xc72fefd3, 0xf752f7da, 0x3f046f69, 0x77fa0a59, 
			0x80e4a915, 0x87b08601, 0x9b09e6ad, 0x3b3ee593, 0xe990fd5a, 0x9e34d797, 0x2cf0b7d9, 0x022b8b51,
			0x96d5ac3a, 0x017da67d, 0xd1cf3ed6, 0x7c7d2d28, 0x1f9f25cf, 0xadf2b89b, 0x5ad6b472, 0x5a88f54c, 
			0xe029ac71, 0xe019a5e6, 0x47b0acfd, 0xed93fa9b, 0xe8d3c48d, 0x283b57cc, 0xf8d56629, 0x79132e28, 
			0x785f0191, 0xed756055, 0xf7960e44, 0xe3d35e8c, 0x15056dd4, 0x88f46dba, 0x03a16125, 0x0564f0bd, 
			0xc3eb9e15, 0x3c9057a2, 0x97271aec, 0xa93a072a, 0x1b3f6d9b, 0x1e6321f5, 0xf59c66fb, 0x26dcf319,
			0x7533d928, 0xb155fdf5, 0x03563482, 0x8aba3cbb, 0x28517711, 0xc20ad9f8, 0xabcc5167, 0xccad925f, 
			0x4de81751, 0x3830dc8e, 0x379d5862, 0x9320f991, 0xea7a90c2, 0xfb3e7bce, 0x5121ce64, 0x774fbe32, 
			0xa8b6e37e, 0xc3293d46, 0x48de5369, 0x6413e680, 0xa2ae0810, 0xdd6db224, 0x69852dfd, 0x09072166, 
			0xb39a460a, 0x6445c0dd, 0x586cdecf, 0x1c20c8ae, 0x5bbef7dd, 0x1b588d40, 0xccd2017f, 0x6bb4e3bb,
			0xdda26a7e, 0x3a59ff45, 0x3e350a44, 0xbcb4cdd5, 0x72eacea8, 0xfa6484bb, 0x8d6612ae, 0xbf3c6f47,
			0xd29be463, 0x542f5d9e, 0xaec2771b, 0xf64e6370, 0x740e0d8d, 0xe75b1357, 0xf8721671, 0xaf537d5d, 
			0x4040cb08, 0x4eb4e2cc, 0x34d2466a, 0x0115af84, 0xe1b00428, 0x95983a1d, 0x06b89fb4, 0xce6ea048,
			0x6f3f3b82, 0x3520ab82, 0x011a1d4b, 0x277227f8, 0x611560b1, 0xe7933fdc, 0xbb3a792b, 0x344525bd, 
			0xa08839e1, 0x51ce794b, 0x2f32c9b7, 0xa01fbac9, 0xe01cc87e, 0xbcc7d1f6, 0xcf0111c3, 0xa1e8aac7, 
			0x1a908749, 0xd44fbd9a, 0xd0dadecb, 0xd50ada38, 0x0339c32a, 0xc6913667, 0x8df9317c, 0xe0b12b4f, 
			0xf79e59b7, 0x43f5bb3a, 0xf2d519ff, 0x27d9459c, 0xbf97222c, 0x15e6fc2a, 0x0f91fc71, 0x9b941525, 
			0xfae59361, 0xceb69ceb, 0xc2a86459, 0x12baa8d1, 0xb6c1075e, 0xe3056a0c, 0x10d25065, 0xcb03a442,
			0xe0ec6e0e, 0x1698db3b, 0x4c98a0be, 0x3278e964, 0x9f1f9532, 0xe0d392df, 0xd3a0342b, 0x8971f21e, 
			0x1b0a7441, 0x4ba3348c, 0xc5be7120, 0xc37632d8, 0xdf359f8d, 0x9b992f2e, 0xe60b6f47, 0x0fe3f11d,
			0xe54cda54, 0x1edad891, 0xce6279cf, 0xcd3e7e6f, 0x1618b166, 0xfd2c1d05, 0x848fd2c5, 0xf6fb2299, 
			0xf523f357, 0xa6327623, 0x93a83531, 0x56cccd02, 0xacf08162, 0x5a75ebb5, 0x6e163697, 0x88d273cc, 
			0xde966292, 0x81b949d0, 0x4c50901b, 0x71c65614, 0xe6c6c7bd, 0x327a140a, 0x45e1d006, 0xc3f27b9a, 
			0xc9aa53fd, 0x62a80f00, 0xbb25bfe2, 0x35bdd2f6, 0x71126905, 0xb2040222, 0xb6cbcf7c, 0xcd769c2b, 
			0x53113ec0, 0x1640e3d3, 0x38abbd60, 0x2547adf0, 0xba38209c, 0xf746ce76, 0x77afa1c5, 0x20756060,
			0x85cbfe4e, 0x8ae88dd8, 0x7aaaf9b0, 0x4cf9aa7e, 0x1948c25c, 0x02fb8a8c, 0x01c36ae4, 0xd6ebe1f9,
			0x90d4f869, 0xa65cdea0, 0x3f09252d, 0xc208e69f, 0xb74e6132, 0xce77e25b, 0x578fdfe3, 0x3ac372e6
		]
	}
////////////////////////////////////////////////////////////////////////////
//	fixes based on patch submitted by Peter Wood (#5791)
	function add(x,y){
		return (((x>>0x10)+(y>>0x10)+(((x&0xffff)+(y&0xffff))>>0x10))<<0x10)|(((x&0xffff)+(y&0xffff))&0xffff);
	}
	function xor(x,y){
		return (((x>>0x10)^(y>>0x10))<<0x10)|(((x&0xffff)^(y&0xffff))&0xffff);
	}

	function $(v, box){
		var d=box.s3[v&0xff]; v>>=8;
		var c=box.s2[v&0xff]; v>>=8;
		var b=box.s1[v&0xff]; v>>=8;
		var a=box.s0[v&0xff];

		var r = (((a>>0x10)+(b>>0x10)+(((a&0xffff)+(b&0xffff))>>0x10))<<0x10)|(((a&0xffff)+(b&0xffff))&0xffff);
		r = (((r>>0x10)^(c>>0x10))<<0x10)|(((r&0xffff)^(c&0xffff))&0xffff);
		return (((r>>0x10)+(d>>0x10)+(((r&0xffff)+(d&0xffff))>>0x10))<<0x10)|(((r&0xffff)+(d&0xffff))&0xffff);
	}
////////////////////////////////////////////////////////////////////////////
	function eb(o, box){
		//	TODO: see if this can't be made more efficient
		var l=o.left;
		var r=o.right;
		l=xor(l,box.p[0]);
		r=xor(r,xor($(l,box),box.p[1]));
		l=xor(l,xor($(r,box),box.p[2]));
		r=xor(r,xor($(l,box),box.p[3]));
		l=xor(l,xor($(r,box),box.p[4]));
		r=xor(r,xor($(l,box),box.p[5]));
		l=xor(l,xor($(r,box),box.p[6]));
		r=xor(r,xor($(l,box),box.p[7]));
		l=xor(l,xor($(r,box),box.p[8]));
		r=xor(r,xor($(l,box),box.p[9]));
		l=xor(l,xor($(r,box),box.p[10]));
		r=xor(r,xor($(l,box),box.p[11]));
		l=xor(l,xor($(r,box),box.p[12]));
		r=xor(r,xor($(l,box),box.p[13]));
		l=xor(l,xor($(r,box),box.p[14]));
		r=xor(r,xor($(l,box),box.p[15]));
		l=xor(l,xor($(r,box),box.p[16]));
		o.right=l;
		o.left=xor(r,box.p[17]);
	}

	function db(o, box){
		var l=o.left;
		var r=o.right;
		l=xor(l,box.p[17]);
		r=xor(r,xor($(l,box),box.p[16]));
		l=xor(l,xor($(r,box),box.p[15]));
		r=xor(r,xor($(l,box),box.p[14]));
		l=xor(l,xor($(r,box),box.p[13]));
		r=xor(r,xor($(l,box),box.p[12]));
		l=xor(l,xor($(r,box),box.p[11]));
		r=xor(r,xor($(l,box),box.p[10]));
		l=xor(l,xor($(r,box),box.p[9]));
		r=xor(r,xor($(l,box),box.p[8]));
		l=xor(l,xor($(r,box),box.p[7]));
		r=xor(r,xor($(l,box),box.p[6]));
		l=xor(l,xor($(r,box),box.p[5]));
		r=xor(r,xor($(l,box),box.p[4]));
		l=xor(l,xor($(r,box),box.p[3]));
		r=xor(r,xor($(l,box),box.p[2]));
		l=xor(l,xor($(r,box),box.p[1]));
		o.right=l;
		o.left=xor(r,box.p[0]);
	}

	//	Note that we aren't caching contexts here; it might take a little longer
	//	but we should be more secure this way.
	function init(key){
		var k=key;
		if(dojo.isString(k)){
			k = dojo.map(k.split(""), function(item){
				return item.charCodeAt(0) & 0xff;
			});
		}

		//	init the boxes
		var pos=0, data=0, res={ left:0, right:0 }, i, j;
		var box = { 
			p: dojo.map(boxes.p.slice(0), function(item){
				var l=k.length, j;
				for(j=0; j<4; j++){ data=(data*POW8)|k[pos++ % l]; }
				return (((item>>0x10)^(data>>0x10))<<0x10)|(((item&0xffff)^(data&0xffff))&0xffff);
			}),
			s0:boxes.s0.slice(0), 
			s1:boxes.s1.slice(0), 
			s2:boxes.s2.slice(0), 
			s3:boxes.s3.slice(0) 
		};

		//	encrypt p and the s boxes
		for(i=0, l=box.p.length; i<l;){
			eb(res, box);
			box.p[i++]=res.left, box.p[i++]=res.right;
		}
		for(i=0; i<4; i++){
			for(j=0, l=box["s"+i].length; j<l;){
				eb(res, box);
				box["s"+i][j++]=res.left, box["s"+i][j++]=res.right;
			}
		}
		return box;
	}

////////////////////////////////////////////////////////////////////////////
//	PUBLIC FUNCTIONS
////////////////////////////////////////////////////////////////////////////
	this.getIV=function(/* dojox.encoding.crypto.outputTypes? */ outputType){
		//	summary
		//	returns the initialization vector in the output format specified by outputType
		var out=outputType||dojox.encoding.crypto.outputTypes.Base64;
		switch(out){
			case dojox.encoding.crypto.outputTypes.Hex:{
				return dojo.map(iv, function(item){
					return item.toString(16);
				}).join("");			//	string
			}
			case dojox.encoding.crypto.outputTypes.String:{
				return iv.join("");		//	string
			}
			case dojox.encoding.crypto.outputTypes.Raw:{
				return iv;				//	array
			}
			default:{
				return dojox.encoding.base64.encode(iv); 	//	 string
			}
		}
	};

	this.setIV=function(/* string */data, /* dojox.encoding.crypto.outputTypes? */inputType){
		//	summary
		//	sets the initialization vector to data (as interpreted as inputType)
		var ip=inputType||dojox.encoding.crypto.outputTypes.Base64;
		var ba=null;
		switch(ip){
			case dojox.encoding.crypto.outputTypes.String:{
				ba = dojo.map(data.split(""), function(item){
					return item.charCodeAt(0);
				});
				break;
			}
			case dojox.encoding.crypto.outputTypes.Hex:{
				ba=[];
				for(var i=0, l=data.length-1; i<l; i+=2){
					ba.push(parseInt(data.substr(i,2), 16));
				}
				break;
			}
			case dojox.encoding.crypto.outputTypes.Raw:{
				ba=data;
				break;
			}
			default:{
				ba=dojox.encoding.base64.decode(data);
				break;
			}
		}
		//	make it a pair of words now
		iv={};
		iv.left=ba[0]*POW24|ba[1]*POW16|ba[2]*POW8|ba[3];
		iv.right=ba[4]*POW24|ba[5]*POW16|ba[6]*POW8|ba[7];
	};

	this.encrypt = function(/* string */plaintext, /* string */key, /* object? */ao){
		//	summary
		//	encrypts plaintext using key; allows user to specify output type and cipher mode via keyword object "ao"
		var out=dojox.encoding.crypto.outputTypes.Base64;
		var mode=dojox.encoding.crypto.cipherModes.EBC;
		if (ao){
			if (ao.outputType) out=ao.outputType;
			if (ao.cipherMode) mode=ao.cipherMode;
		}

		var bx = init(key), padding = 8-(plaintext.length&7);
		for (var i=0; i<padding; i++){ plaintext+=String.fromCharCode(padding); }

		var cipher=[], count=plaintext.length >> 3, pos=0, o={}, isCBC=(mode==dojox.encoding.crypto.cipherModes.CBC);
		var vector={left:iv.left||null, right:iv.right||null};
		for(var i=0; i<count; i++){
			o.left=plaintext.charCodeAt(pos)*POW24
				|plaintext.charCodeAt(pos+1)*POW16
				|plaintext.charCodeAt(pos+2)*POW8
				|plaintext.charCodeAt(pos+3);
			o.right=plaintext.charCodeAt(pos+4)*POW24
				|plaintext.charCodeAt(pos+5)*POW16
				|plaintext.charCodeAt(pos+6)*POW8
				|plaintext.charCodeAt(pos+7);

			if(isCBC){
				o.left=(((o.left>>0x10)^(vector.left>>0x10))<<0x10)|(((o.left&0xffff)^(vector.left&0xffff))&0xffff);
				o.right=(((o.right>>0x10)^(vector.right>>0x10))<<0x10)|(((o.right&0xffff)^(vector.right&0xffff))&0xffff);
			}

			eb(o, bx);	//	encrypt the block

			if(isCBC){
				vector.left=o.left;
				vector.right=o.right;
			}

			cipher.push((o.left>>24)&0xff); 
			cipher.push((o.left>>16)&0xff); 
			cipher.push((o.left>>8)&0xff);
			cipher.push(o.left&0xff);
			cipher.push((o.right>>24)&0xff); 
			cipher.push((o.right>>16)&0xff); 
			cipher.push((o.right>>8)&0xff);
			cipher.push(o.right&0xff);
			pos+=8;
		}

		switch(out){
			case dojox.encoding.crypto.outputTypes.Hex:{
				return dojo.map(cipher, function(item){
					return item.toString(16);
				}).join("");	//	string
			}
			case dojox.encoding.crypto.outputTypes.String:{
				return cipher.join("");	//	string
			}
			case dojox.encoding.crypto.outputTypes.Raw:{
				return cipher;	//	array
			}
			default:{
				return dojox.encoding.base64.encode(cipher);	//	string
			}
		}
	};

	this.decrypt = function(/* string */ciphertext, /* string */key, /* object? */ao){
		//	summary
		//	decrypts ciphertext using key; allows specification of how ciphertext is encoded via ao.
		var ip=dojox.encoding.crypto.outputTypes.Base64;
		var mode=dojox.encoding.crypto.cipherModes.EBC;
		if (ao){
			if (ao.outputType) ip=ao.outputType;
			if (ao.cipherMode) mode=ao.cipherMode;
		}
		var bx = init(key);
		var pt=[];
	
		var c=null;
		switch(ip){
			case dojox.encoding.crypto.outputTypes.Hex:{
				c = [];
				for(var i=0, l=ciphertext.length-1; i<l; i+=2){
					c.push(parseInt(ciphertext.substr(i,2), 16));
				}
				break;
			}
			case dojox.encoding.crypto.outputTypes.String:{
				c = dojo.map(ciphertext.split(""), function(item){
					return item.charCodeAt(0);
				});
				break;
			}
			case dojox.encoding.crypto.outputTypes.Raw:{
				c=ciphertext;	//	should be a byte array
				break;
			}
			default:{
				c=dojox.encoding.base64.decode(ciphertext);
				break;
			}
		}

		var count=c.length >> 3, pos=0, o={}, isCBC=(mode==dojox.encoding.crypto.cipherModes.CBC);
		var vector={left:iv.left||null, right:iv.right||null};
		for(var i=0; i<count; i++){
			o.left=c[pos]*POW24|c[pos+1]*POW16|c[pos+2]*POW8|c[pos+3];
			o.right=c[pos+4]*POW24|c[pos+5]*POW16|c[pos+6]*POW8|c[pos+7];

			if(isCBC){
				var left=o.left;
				var right=o.right;
			}

			db(o, bx);	//	decrypt the block

			if(isCBC){
				o.left=(((o.left>>0x10)^(vector.left>>0x10))<<0x10)|(((o.left&0xffff)^(vector.left&0xffff))&0xffff);
				o.right=(((o.right>>0x10)^(vector.right>>0x10))<<0x10)|(((o.right&0xffff)^(vector.right&0xffff))&0xffff);
				vector.left=left;
				vector.right=right;
			}

			pt.push((o.left>>24)&0xff);
			pt.push((o.left>>16)&0xff);
			pt.push((o.left>>8)&0xff);
			pt.push(o.left&0xff);
			pt.push((o.right>>24)&0xff);
			pt.push((o.right>>16)&0xff);
			pt.push((o.right>>8)&0xff);
			pt.push(o.right&0xff);
			pos+=8;
		}

		//	check for padding, and remove.
		if(pt[pt.length-1]==pt[pt.length-2]||pt[pt.length-1]==0x01){
			var n=pt[pt.length-1];
			pt.splice(pt.length-n, n);
		}

		//	convert to string
		return dojo.map(pt, function(item){
			return String.fromCharCode(item);
		}).join("");	//	string
	};

	this.setIV("0000000000000000", dojox.encoding.crypto.outputTypes.Hex);
}();

}

if(!dojo._hasResource["dojo.i18n"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojo.i18n"] = true;
dojo.provide("dojo.i18n");

/*=====
dojo.i18n = {
	// summary: Utility classes to enable loading of resources for internationalization (i18n)
};
=====*/

dojo.i18n.getLocalization = function(/*String*/packageName, /*String*/bundleName, /*String?*/locale){
	//	summary:
	//		Returns an Object containing the localization for a given resource
	//		bundle in a package, matching the specified locale.
	//	description:
	//		Returns a hash containing name/value pairs in its prototypesuch
	//		that values can be easily overridden.  Throws an exception if the
	//		bundle is not found.  Bundle must have already been loaded by
	//		`dojo.requireLocalization()` or by a build optimization step.  NOTE:
	//		try not to call this method as part of an object property
	//		definition (`var foo = { bar: dojo.i18n.getLocalization() }`).  In
	//		some loading situations, the bundle may not be available in time
	//		for the object definition.  Instead, call this method inside a
	//		function that is run after all modules load or the page loads (like
	//		in `dojo.addOnLoad()`), or in a widget lifecycle method.
	//	packageName:
	//		package which is associated with this resource
	//	bundleName:
	//		the base filename of the resource bundle (without the ".js" suffix)
	//	locale:
	//		the variant to load (optional).  By default, the locale defined by
	//		the host environment: dojo.locale

	locale = dojo.i18n.normalizeLocale(locale);

	// look for nearest locale match
	var elements = locale.split('-');
	var module = [packageName,"nls",bundleName].join('.');
	var bundle = dojo._loadedModules[module];
	if(bundle){
		var localization;
		for(var i = elements.length; i > 0; i--){
			var loc = elements.slice(0, i).join('_');
			if(bundle[loc]){
				localization = bundle[loc];
				break;
			}
		}
		if(!localization){
			localization = bundle.ROOT;
		}

		// make a singleton prototype so that the caller won't accidentally change the values globally
		if(localization){
			var clazz = function(){};
			clazz.prototype = localization;
			return new clazz(); // Object
		}
	}

	throw new Error("Bundle not found: " + bundleName + " in " + packageName+" , locale=" + locale);
};

dojo.i18n.normalizeLocale = function(/*String?*/locale){
	//	summary:
	//		Returns canonical form of locale, as used by Dojo.
	//
	//  description:
	//		All variants are case-insensitive and are separated by '-' as specified in [RFC 3066](http://www.ietf.org/rfc/rfc3066.txt).
	//		If no locale is specified, the dojo.locale is returned.  dojo.locale is defined by
	//		the user agent's locale unless overridden by djConfig.

	var result = locale ? locale.toLowerCase() : dojo.locale;
	if(result == "root"){
		result = "ROOT";
	}
	return result; // String
};

dojo.i18n._requireLocalization = function(/*String*/moduleName, /*String*/bundleName, /*String?*/locale, /*String?*/availableFlatLocales){
	//	summary:
	//		See dojo.requireLocalization()
	//	description:
	// 		Called by the bootstrap, but factored out so that it is only
	// 		included in the build when needed.

	var targetLocale = dojo.i18n.normalizeLocale(locale);
 	var bundlePackage = [moduleName, "nls", bundleName].join(".");
	// NOTE: 
	//		When loading these resources, the packaging does not match what is
	//		on disk.  This is an implementation detail, as this is just a
	//		private data structure to hold the loaded resources.  e.g.
	//		`tests/hello/nls/en-us/salutations.js` is loaded as the object
	//		`tests.hello.nls.salutations.en_us={...}` The structure on disk is
	//		intended to be most convenient for developers and translators, but
	//		in memory it is more logical and efficient to store in a different
	//		order.  Locales cannot use dashes, since the resulting path will
	//		not evaluate as valid JS, so we translate them to underscores.
	
	//Find the best-match locale to load if we have available flat locales.
	var bestLocale = "";
	if(availableFlatLocales){
		var flatLocales = availableFlatLocales.split(",");
		for(var i = 0; i < flatLocales.length; i++){
			//Locale must match from start of string.
			if(targetLocale.indexOf(flatLocales[i]) == 0){
				if(flatLocales[i].length > bestLocale.length){
					bestLocale = flatLocales[i];
				}
			}
		}
		if(!bestLocale){
			bestLocale = "ROOT";
		}		
	}

	//See if the desired locale is already loaded.
	var tempLocale = availableFlatLocales ? bestLocale : targetLocale;
	var bundle = dojo._loadedModules[bundlePackage];
	var localizedBundle = null;
	if(bundle){
		if(dojo.config.localizationComplete && bundle._built){return;}
		var jsLoc = tempLocale.replace(/-/g, '_');
		var translationPackage = bundlePackage+"."+jsLoc;
		localizedBundle = dojo._loadedModules[translationPackage];
	}

	if(!localizedBundle){
		bundle = dojo["provide"](bundlePackage);
		var syms = dojo._getModuleSymbols(moduleName);
		var modpath = syms.concat("nls").join("/");
		var parent;

		dojo.i18n._searchLocalePath(tempLocale, availableFlatLocales, function(loc){
			var jsLoc = loc.replace(/-/g, '_');
			var translationPackage = bundlePackage + "." + jsLoc;
			var loaded = false;
			if(!dojo._loadedModules[translationPackage]){
				// Mark loaded whether it's found or not, so that further load attempts will not be made
				dojo["provide"](translationPackage);
				var module = [modpath];
				if(loc != "ROOT"){module.push(loc);}
				module.push(bundleName);
				var filespec = module.join("/") + '.js';
				loaded = dojo._loadPath(filespec, null, function(hash){
					// Use singleton with prototype to point to parent bundle, then mix-in result from loadPath
					var clazz = function(){};
					clazz.prototype = parent;
					bundle[jsLoc] = new clazz();
					for(var j in hash){ bundle[jsLoc][j] = hash[j]; }
				});
			}else{
				loaded = true;
			}
			if(loaded && bundle[jsLoc]){
				parent = bundle[jsLoc];
			}else{
				bundle[jsLoc] = parent;
			}
			
			if(availableFlatLocales){
				//Stop the locale path searching if we know the availableFlatLocales, since
				//the first call to this function will load the only bundle that is needed.
				return true;
			}
		});
	}

	//Save the best locale bundle as the target locale bundle when we know the
	//the available bundles.
	if(availableFlatLocales && targetLocale != bestLocale){
		bundle[targetLocale.replace(/-/g, '_')] = bundle[bestLocale.replace(/-/g, '_')];
	}
};

(function(){
	// If other locales are used, dojo.requireLocalization should load them as
	// well, by default. 
	// 
	// Override dojo.requireLocalization to do load the default bundle, then
	// iterate through the extraLocale list and load those translations as
	// well, unless a particular locale was requested.

	var extra = dojo.config.extraLocale;
	if(extra){
		if(!extra instanceof Array){
			extra = [extra];
		}

		var req = dojo.i18n._requireLocalization;
		dojo.i18n._requireLocalization = function(m, b, locale, availableFlatLocales){
			req(m,b,locale, availableFlatLocales);
			if(locale){return;}
			for(var i=0; i<extra.length; i++){
				req(m,b,extra[i], availableFlatLocales);
			}
		};
	}
})();

dojo.i18n._searchLocalePath = function(/*String*/locale, /*Boolean*/down, /*Function*/searchFunc){
	//	summary:
	//		A helper method to assist in searching for locale-based resources.
	//		Will iterate through the variants of a particular locale, either up
	//		or down, executing a callback function.  For example, "en-us" and
	//		true will try "en-us" followed by "en" and finally "ROOT".

	locale = dojo.i18n.normalizeLocale(locale);

	var elements = locale.split('-');
	var searchlist = [];
	for(var i = elements.length; i > 0; i--){
		searchlist.push(elements.slice(0, i).join('-'));
	}
	searchlist.push(false);
	if(down){searchlist.reverse();}

	for(var j = searchlist.length - 1; j >= 0; j--){
		var loc = searchlist[j] || "ROOT";
		var stop = searchFunc(loc);
		if(stop){ break; }
	}
};

dojo.i18n._preloadLocalizations = function(/*String*/bundlePrefix, /*Array*/localesGenerated){
	//	summary:
	//		Load built, flattened resource bundles, if available for all
	//		locales used in the page. Only called by built layer files.

	function preload(locale){
		locale = dojo.i18n.normalizeLocale(locale);
		dojo.i18n._searchLocalePath(locale, true, function(loc){
			for(var i=0; i<localesGenerated.length;i++){
				if(localesGenerated[i] == loc){
					dojo["require"](bundlePrefix+"_"+loc);
					return true; // Boolean
				}
			}
			return false; // Boolean
		});
	}
	preload();
	var extra = dojo.config.extraLocale||[];
	for(var i=0; i<extra.length; i++){
		preload(extra[i]);
	}
};

}

if(!dojo._hasResource["dojo.string"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojo.string"] = true;
dojo.provide("dojo.string");

/*=====
dojo.string = { 
	// summary: String utilities for Dojo
};
=====*/

dojo.string.pad = function(/*String*/text, /*int*/size, /*String?*/ch, /*boolean?*/end){
	// summary:
	//		Pad a string to guarantee that it is at least `size` length by
	//		filling with the character `ch` at either the start or end of the
	//		string. Pads at the start, by default.
	// text: the string to pad
	// size: length to provide padding
	// ch: character to pad, defaults to '0'
	// end: adds padding at the end if true, otherwise pads at start

	var out = String(text);
	if(!ch){
		ch = '0';
	}
	while(out.length < size){
		if(end){
			out += ch;
		}else{
			out = ch + out;
		}
	}
	return out;	// String
};

dojo.string.substitute = function(	/*String*/template, 
									/*Object|Array*/map, 
									/*Function?*/transform, 
									/*Object?*/thisObject){
	// summary:
	//		Performs parameterized substitutions on a string. Throws an
	//		exception if any parameter is unmatched.
	// description:
	//		For example,
	//		|	dojo.string.substitute("File '${0}' is not found in directory '${1}'.",["foo.html","/temp"]);
	//		|	dojo.string.substitute("File '${name}' is not found in directory '${info.dir}'.",
	//		|		{name: "foo.html", info: {dir: "/temp"}});
	//		both return
	//		|	"File 'foo.html' is not found in directory '/temp'."
	// template: 
	//		a string with expressions in the form `${key}` to be replaced or
	//		`${key:format}` which specifies a format function.
	// map: hash to search for substitutions
	// transform: 
	//		a function to process all parameters before substitution takes
	//		place, e.g. dojo.string.encodeXML
	// thisObject: 
	//		where to look for optional format function; default to the global
	//		namespace

	return template.replace(/\$\{([^\s\:\}]+)(?:\:([^\s\:\}]+))?\}/g, function(match, key, format){
		var value = dojo.getObject(key,false,map);
		if(format){ value = dojo.getObject(format,false,thisObject)(value);}
		if(transform){ value = transform(value, key); }
		return value.toString();
	}); // string
};

dojo.string.trim = function(/*String*/ str){
	// summary: trims whitespaces from both sides of the string
	// description:
	//	This version of trim() was taken from [Steven Levithan's blog](http://blog.stevenlevithan.com/archives/faster-trim-javascript).
	//	The short yet performant version of this function is 
	//	dojo.trim(), which is part of Dojo base.
	str = str.replace(/^\s+/, '');
	for(var i = str.length - 1; i > 0; i--){
		if(/\S/.test(str.charAt(i))){
			str = str.substring(0, i + 1);
			break;
		}
	}
	return str;	// String
};

}

if(!dojo._hasResource["dojo.regexp"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojo.regexp"] = true;
dojo.provide("dojo.regexp");

/*=====
dojo.regexp = {
	// summary: Regular expressions and Builder resources
};
=====*/

dojo.regexp.escapeString = function(/*String*/str, /*String?*/except){
	//	summary:
	//		Adds escape sequences for special characters in regular expressions
	// except:
	//		a String with special characters to be left unescaped

//	return str.replace(/([\f\b\n\t\r[\^$|?*+(){}])/gm, "\\$1"); // string
	return str.replace(/([\.$?*!=:|{}\(\)\[\]\\\/^])/g, function(ch){
		if(except && except.indexOf(ch) != -1){
			return ch;
		}
		return "\\" + ch;
	}); // String
}

dojo.regexp.buildGroupRE = function(/*Object|Array*/arr, /*Function*/re, /*Boolean?*/nonCapture){
	//	summary:
	//		Builds a regular expression that groups subexpressions
	//	description:
	//		A utility function used by some of the RE generators. The
	//		subexpressions are constructed by the function, re, in the second
	//		parameter.  re builds one subexpression for each elem in the array
	//		a, in the first parameter. Returns a string for a regular
	//		expression that groups all the subexpressions.
	// arr:
	//		A single value or an array of values.
	// re:
	//		A function. Takes one parameter and converts it to a regular
	//		expression. 
	// nonCapture:
	//		If true, uses non-capturing match, otherwise matches are retained
	//		by regular expression. Defaults to false

	// case 1: a is a single value.
	if(!(arr instanceof Array)){
		return re(arr); // String
	}

	// case 2: a is an array
	var b = [];
	for(var i = 0; i < arr.length; i++){
		// convert each elem to a RE
		b.push(re(arr[i]));
	}

	 // join the REs as alternatives in a RE group.
	return dojo.regexp.group(b.join("|"), nonCapture); // String
}

dojo.regexp.group = function(/*String*/expression, /*Boolean?*/nonCapture){
	// summary:
	//		adds group match to expression
	// nonCapture:
	//		If true, uses non-capturing match, otherwise matches are retained
	//		by regular expression. 
	return "(" + (nonCapture ? "?:":"") + expression + ")"; // String
}

}

if(!dojo._hasResource["dojo.number"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojo.number"] = true;
dojo.provide("dojo.number");







/*=====
dojo.number = {
	// summary: localized formatting and parsing routines for Number
}

dojo.number.__FormatOptions = function(){
	//	pattern: String?
	//		override [formatting pattern](http://www.unicode.org/reports/tr35/#Number_Format_Patterns)
	//		with this string
	//	type: String?
	//		choose a format type based on the locale from the following:
	//		decimal, scientific, percent, currency. decimal by default.
	//	places: Number?
	//		fixed number of decimal places to show.  This overrides any
	//		information in the provided pattern.
	//	round: Number?
	//		5 rounds to nearest .5; 0 rounds to nearest whole (default). -1
	//		means don't round.
	//	currency: String?
	//		an [ISO4217](http://en.wikipedia.org/wiki/ISO_4217) currency code, a three letter sequence like "USD"
	//	symbol: String?
	//		localized currency symbol
	//	locale: String?
	//		override the locale used to determine formatting rules
	this.pattern = pattern;
	this.type = type;
	this.places = places;
	this.round = round;
	this.currency = currency;
	this.symbol = symbol;
	this.locale = locale;
}
=====*/

dojo.number.format = function(/*Number*/value, /*dojo.number.__FormatOptions?*/options){
	// summary:
	//		Format a Number as a String, using locale-specific settings
	// description:
	//		Create a string from a Number using a known localized pattern.
	//		Formatting patterns appropriate to the locale are chosen from the
	//		[CLDR](http://unicode.org/cldr) as well as the appropriate symbols and
	//		delimiters.  See <http://www.unicode.org/reports/tr35/#Number_Elements>
	// value:
	//		the number to be formatted.  If not a valid JavaScript number,
	//		return null.

	options = dojo.mixin({}, options || {});
	var locale = dojo.i18n.normalizeLocale(options.locale);
	var bundle = dojo.i18n.getLocalization("dojo.cldr", "number", locale);
	options.customs = bundle;
	var pattern = options.pattern || bundle[(options.type || "decimal") + "Format"];
	if(isNaN(value)){ return null; } // null
	return dojo.number._applyPattern(value, pattern, options); // String
};

//dojo.number._numberPatternRE = /(?:[#0]*,?)*[#0](?:\.0*#*)?/; // not precise, but good enough
dojo.number._numberPatternRE = /[#0,]*[#0](?:\.0*#*)?/; // not precise, but good enough

dojo.number._applyPattern = function(/*Number*/value, /*String*/pattern, /*dojo.number.__FormatOptions?*/options){
	// summary:
	//		Apply pattern to format value as a string using options. Gives no
	//		consideration to local customs.
	// value:
	//		the number to be formatted.
	// pattern:
	//		a pattern string as described by
	//		[unicode.org TR35](http://www.unicode.org/reports/tr35/#Number_Format_Patterns)
	// options: dojo.number.__FormatOptions?
	//		_applyPattern is usually called via `dojo.number.format()` which
	//		populates an extra property in the options parameter, "customs".
	//		The customs object specifies group and decimal parameters if set.

	//TODO: support escapes
	options = options || {};
	var group = options.customs.group;
	var decimal = options.customs.decimal;

	var patternList = pattern.split(';');
	var positivePattern = patternList[0];
	pattern = patternList[(value < 0) ? 1 : 0] || ("-" + positivePattern);

	//TODO: only test against unescaped
	if(pattern.indexOf('%') != -1){
		value *= 100;
	}else if(pattern.indexOf('\u2030') != -1){
		value *= 1000; // per mille
	}else if(pattern.indexOf('\u00a4') != -1){
		group = options.customs.currencyGroup || group;//mixins instead?
		decimal = options.customs.currencyDecimal || decimal;// Should these be mixins instead?
		pattern = pattern.replace(/\u00a4{1,3}/, function(match){
			var prop = ["symbol", "currency", "displayName"][match.length-1];
			return options[prop] || options.currency || "";
		});
	}else if(pattern.indexOf('E') != -1){
		throw new Error("exponential notation not supported");
	}
	
	//TODO: support @ sig figs?
	var numberPatternRE = dojo.number._numberPatternRE;
	var numberPattern = positivePattern.match(numberPatternRE);
	if(!numberPattern){
		throw new Error("unable to find a number expression in pattern: "+pattern);
	}
	return pattern.replace(numberPatternRE,
		dojo.number._formatAbsolute(value, numberPattern[0], {decimal: decimal, group: group, places: options.places}));
}

dojo.number.round = function(/*Number*/value, /*Number*/places, /*Number?*/multiple){
	//	summary:
	//		Rounds the number at the given number of places
	//	value:
	//		the number to round
	//	places:
	//		the number of decimal places where rounding takes place
	//	multiple:
	//		rounds next place to nearest multiple

	var pieces = String(value).split(".");
	var length = (pieces[1] && pieces[1].length) || 0;
	if(length > places){
		var factor = Math.pow(10, places);
		if(multiple > 0){factor *= 10/multiple;places++;} //FIXME
		value = Math.round(value * factor)/factor;

		// truncate to remove any residual floating point values
		pieces = String(value).split(".");
		length = (pieces[1] && pieces[1].length) || 0;
		if(length > places){
			pieces[1] = pieces[1].substr(0, places);
			value = Number(pieces.join("."));
		}
	}
	return value; //Number
}

/*=====
dojo.number.__FormatAbsoluteOptions = function(){
	//	decimal: String?
	//		the decimal separator
	//	group: String?
	//		the group separator
	//	places: Integer?
	//		number of decimal places
	//	round: Number?
	//		5 rounds to nearest .5; 0 rounds to nearest whole (default). -1
	//		means don't round.
	this.decimal = decimal;
	this.group = group;
	this.places = places;
	this.round = round;
}
=====*/

dojo.number._formatAbsolute = function(/*Number*/value, /*String*/pattern, /*dojo.number.__FormatAbsoluteOptions?*/options){
	// summary: 
	//		Apply numeric pattern to absolute value using options. Gives no
	//		consideration to local customs.
	// value:
	//		the number to be formatted, ignores sign
	// pattern:
	//		the number portion of a pattern (e.g. `#,##0.00`)
	options = options || {};
	if(options.places === true){options.places=0;}
	if(options.places === Infinity){options.places=6;} // avoid a loop; pick a limit

	var patternParts = pattern.split(".");
	var maxPlaces = (options.places >= 0) ? options.places : (patternParts[1] && patternParts[1].length) || 0;
	if(!(options.round < 0)){
		value = dojo.number.round(value, maxPlaces, options.round);
	}

	var valueParts = String(Math.abs(value)).split(".");
	var fractional = valueParts[1] || "";
	if(options.places){
		valueParts[1] = dojo.string.pad(fractional.substr(0, options.places), options.places, '0', true);
	}else if(patternParts[1] && options.places !== 0){
		// Pad fractional with trailing zeros
		var pad = patternParts[1].lastIndexOf("0") + 1;
		if(pad > fractional.length){
			valueParts[1] = dojo.string.pad(fractional, pad, '0', true);
		}

		// Truncate fractional
		var places = patternParts[1].length;
		if(places < fractional.length){
			valueParts[1] = fractional.substr(0, places);
		}
	}else{
		if(valueParts[1]){ valueParts.pop(); }
	}

	// Pad whole with leading zeros
	var patternDigits = patternParts[0].replace(',', '');
	pad = patternDigits.indexOf("0");
	if(pad != -1){
		pad = patternDigits.length - pad;
		if(pad > valueParts[0].length){
			valueParts[0] = dojo.string.pad(valueParts[0], pad);
		}

		// Truncate whole
		if(patternDigits.indexOf("#") == -1){
			valueParts[0] = valueParts[0].substr(valueParts[0].length - pad);
		}
	}

	// Add group separators
	var index = patternParts[0].lastIndexOf(',');
	var groupSize, groupSize2;
	if(index != -1){
		groupSize = patternParts[0].length - index - 1;
		var remainder = patternParts[0].substr(0, index);
		index = remainder.lastIndexOf(',');
		if(index != -1){
			groupSize2 = remainder.length - index - 1;
		}
	}
	var pieces = [];
	for(var whole = valueParts[0]; whole;){
		var off = whole.length - groupSize;
		pieces.push((off > 0) ? whole.substr(off) : whole);
		whole = (off > 0) ? whole.slice(0, off) : "";
		if(groupSize2){
			groupSize = groupSize2;
			delete groupSize2;
		}
	}
	valueParts[0] = pieces.reverse().join(options.group || ",");

	return valueParts.join(options.decimal || ".");
};

/*=====
dojo.number.__RegexpOptions = function(){
	//	pattern: String?
	//		override pattern with this string.  Default is provided based on
	//		locale.
	//	type: String?
	//		choose a format type based on the locale from the following:
	//		decimal, scientific, percent, currency. decimal by default.
	//	locale: String?
	//		override the locale used to determine formatting rules
	//	strict: Boolean?
	//		strict parsing, false by default
	//	places: Number|String?
	//		number of decimal places to accept: Infinity, a positive number, or
	//		a range "n,m".  By default, defined by pattern.
	this.pattern = pattern;
	this.type = type;
	this.locale = locale;
	this.strict = strict;
	this.places = places;
}
=====*/
dojo.number.regexp = function(/*dojo.number.__RegexpOptions?*/options){
	//	summary:
	//		Builds the regular needed to parse a number
	//	description:
	//		Returns regular expression with positive and negative match, group
	//		and decimal separators
	return dojo.number._parseInfo(options).regexp; // String
}

dojo.number._parseInfo = function(/*Object?*/options){
	options = options || {};
	var locale = dojo.i18n.normalizeLocale(options.locale);
	var bundle = dojo.i18n.getLocalization("dojo.cldr", "number", locale);
	var pattern = options.pattern || bundle[(options.type || "decimal") + "Format"];
//TODO: memoize?
	var group = bundle.group;
	var decimal = bundle.decimal;
	var factor = 1;

	if(pattern.indexOf('%') != -1){
		factor /= 100;
	}else if(pattern.indexOf('\u2030') != -1){
		factor /= 1000; // per mille
	}else{
		var isCurrency = pattern.indexOf('\u00a4') != -1;
		if(isCurrency){
			group = bundle.currencyGroup || group;
			decimal = bundle.currencyDecimal || decimal;
		}
	}

	//TODO: handle quoted escapes
	var patternList = pattern.split(';');
	if(patternList.length == 1){
		patternList.push("-" + patternList[0]);
	}

	var re = dojo.regexp.buildGroupRE(patternList, function(pattern){
		pattern = "(?:"+dojo.regexp.escapeString(pattern, '.')+")";
		return pattern.replace(dojo.number._numberPatternRE, function(format){
			var flags = {
				signed: false,
				separator: options.strict ? group : [group,""],
				fractional: options.fractional,
				decimal: decimal,
				exponent: false};
			var parts = format.split('.');
			var places = options.places;
			if(parts.length == 1 || places === 0){flags.fractional = false;}
			else{
				if(places === undefined){ places = parts[1].lastIndexOf('0')+1; }
				if(places && options.fractional == undefined){flags.fractional = true;} // required fractional, unless otherwise specified
				if(!options.places && (places < parts[1].length)){ places += "," + parts[1].length; }
				flags.places = places;
			}
			var groups = parts[0].split(',');
			if(groups.length>1){
				flags.groupSize = groups.pop().length;
				if(groups.length>1){
					flags.groupSize2 = groups.pop().length;
				}
			}
			return "("+dojo.number._realNumberRegexp(flags)+")";
		});
	}, true);

	if(isCurrency){
		// substitute the currency symbol for the placeholder in the pattern
		re = re.replace(/(\s*)(\u00a4{1,3})(\s*)/g, function(match, before, target, after){
			var prop = ["symbol", "currency", "displayName"][target.length-1];
			var symbol = dojo.regexp.escapeString(options[prop] || options.currency || "");
			before = before ? "\\s" : "";
			after = after ? "\\s" : "";
			if(!options.strict){
				if(before){before += "*";}
				if(after){after += "*";}
				return "(?:"+before+symbol+after+")?";
			}
			return before+symbol+after;
		});
	}

//TODO: substitute localized sign/percent/permille/etc.?

	// normalize whitespace and return
	return {regexp: re.replace(/[\xa0 ]/g, "[\\s\\xa0]"), group: group, decimal: decimal, factor: factor}; // Object
}

/*=====
dojo.number.__ParseOptions = function(){
	//	pattern: String
	//		override pattern with this string.  Default is provided based on
	//		locale.
	//	type: String?
	//		choose a format type based on the locale from the following:
	//		decimal, scientific, percent, currency. decimal by default.
	//	locale: String
	//		override the locale used to determine formatting rules
	//	strict: Boolean?
	//		strict parsing, false by default
	//	currency: Object
	//		object with currency information
	this.pattern = pattern;
	this.type = type;
	this.locale = locale;
	this.strict = strict;
	this.currency = currency;
}
=====*/
dojo.number.parse = function(/*String*/expression, /*dojo.number.__ParseOptions?*/options){
	// summary:
	//		Convert a properly formatted string to a primitive Number, using
	//		locale-specific settings.
	// description:
	//		Create a Number from a string using a known localized pattern.
	//		Formatting patterns are chosen appropriate to the locale
	//		and follow the syntax described by
	//		[unicode.org TR35](http://www.unicode.org/reports/tr35/#Number_Format_Patterns)
	// expression:
	//		A string representation of a Number
	var info = dojo.number._parseInfo(options);
	var results = (new RegExp("^"+info.regexp+"$")).exec(expression);
	if(!results){
		return NaN; //NaN
	}
	var absoluteMatch = results[1]; // match for the positive expression
	if(!results[1]){
		if(!results[2]){
			return NaN; //NaN
		}
		// matched the negative pattern
		absoluteMatch =results[2];
		info.factor *= -1;
	}

	// Transform it to something Javascript can parse as a number.  Normalize
	// decimal point and strip out group separators or alternate forms of whitespace
	absoluteMatch = absoluteMatch.
		replace(new RegExp("["+info.group + "\\s\\xa0"+"]", "g"), "").
		replace(info.decimal, ".");
	// Adjust for negative sign, percent, etc. as necessary
	return Number(absoluteMatch) * info.factor; //Number
};

/*=====
dojo.number.__RealNumberRegexpFlags = function(){
	//	places: Number?
	//		The integer number of decimal places or a range given as "n,m".  If
	//		not given, the decimal part is optional and the number of places is
	//		unlimited.
	//	decimal: String?
	//		A string for the character used as the decimal point.  Default
	//		is ".".
	//	fractional: Boolean|Array?
	//		Whether decimal places are allowed.  Can be true, false, or [true,
	//		false].  Default is [true, false]
	//	exponent: Boolean|Array?
	//		Express in exponential notation.  Can be true, false, or [true,
	//		false]. Default is [true, false], (i.e. will match if the
	//		exponential part is present are not).
	//	eSigned: Boolean|Array?
	//		The leading plus-or-minus sign on the exponent.  Can be true,
	//		false, or [true, false].  Default is [true, false], (i.e. will
	//		match if it is signed or unsigned).  flags in regexp.integer can be
	//		applied.
	this.places = places;
	this.decimal = decimal;
	this.fractional = fractional;
	this.exponent = exponent;
	this.eSigned = eSigned;
}
=====*/

dojo.number._realNumberRegexp = function(/*dojo.number.__RealNumberRegexpFlags?*/flags){
	// summary:
	//		Builds a regular expression to match a real number in exponential
	//		notation

	// assign default values to missing paramters
	flags = flags || {};
	//TODO: use mixin instead?
	if(!("places" in flags)){ flags.places = Infinity; }
	if(typeof flags.decimal != "string"){ flags.decimal = "."; }
	if(!("fractional" in flags) || /^0/.test(flags.places)){ flags.fractional = [true, false]; }
	if(!("exponent" in flags)){ flags.exponent = [true, false]; }
	if(!("eSigned" in flags)){ flags.eSigned = [true, false]; }

	// integer RE
	var integerRE = dojo.number._integerRegexp(flags);

	// decimal RE
	var decimalRE = dojo.regexp.buildGroupRE(flags.fractional,
		function(q){
			var re = "";
			if(q && (flags.places!==0)){
				re = "\\" + flags.decimal;
				if(flags.places == Infinity){ 
					re = "(?:" + re + "\\d+)?"; 
				}else{
					re += "\\d{" + flags.places + "}"; 
				}
			}
			return re;
		},
		true
	);

	// exponent RE
	var exponentRE = dojo.regexp.buildGroupRE(flags.exponent,
		function(q){ 
			if(q){ return "([eE]" + dojo.number._integerRegexp({ signed: flags.eSigned}) + ")"; }
			return ""; 
		}
	);

	// real number RE
	var realRE = integerRE + decimalRE;
	// allow for decimals without integers, e.g. .25
	if(decimalRE){realRE = "(?:(?:"+ realRE + ")|(?:" + decimalRE + "))";}
	return realRE + exponentRE; // String
};

/*=====
dojo.number.__IntegerRegexpFlags = function(){
	//	signed: Boolean?
	//		The leading plus-or-minus sign. Can be true, false, or `[true,false]`.
	//		Default is `[true, false]`, (i.e. will match if it is signed
	//		or unsigned).
	//	separator: String?
	//		The character used as the thousands separator. Default is no
	//		separator. For more than one symbol use an array, e.g. `[",", ""]`,
	//		makes ',' optional.
	//	groupSize: Number?
	//		group size between separators
	//	groupSize2: Number?
	//		second grouping, where separators 2..n have a different interval than the first separator (for India)
	this.signed = signed;
	this.separator = separator;
	this.groupSize = groupSize;
	this.groupSize2 = groupSize2;
}
=====*/

dojo.number._integerRegexp = function(/*dojo.number.__IntegerRegexpFlags?*/flags){
	// summary: 
	//		Builds a regular expression that matches an integer

	// assign default values to missing paramters
	flags = flags || {};
	if(!("signed" in flags)){ flags.signed = [true, false]; }
	if(!("separator" in flags)){
		flags.separator = "";
	}else if(!("groupSize" in flags)){
		flags.groupSize = 3;
	}
	// build sign RE
	var signRE = dojo.regexp.buildGroupRE(flags.signed,
		function(q) { return q ? "[-+]" : ""; },
		true
	);

	// number RE
	var numberRE = dojo.regexp.buildGroupRE(flags.separator,
		function(sep){
			if(!sep){
				return "(?:0|[1-9]\\d*)";
			}

			sep = dojo.regexp.escapeString(sep);
			if(sep == " "){ sep = "\\s"; }
			else if(sep == "\xa0"){ sep = "\\s\\xa0"; }

			var grp = flags.groupSize, grp2 = flags.groupSize2;
			if(grp2){
				var grp2RE = "(?:0|[1-9]\\d{0," + (grp2-1) + "}(?:[" + sep + "]\\d{" + grp2 + "})*[" + sep + "]\\d{" + grp + "})";
				return ((grp-grp2) > 0) ? "(?:" + grp2RE + "|(?:0|[1-9]\\d{0," + (grp-1) + "}))" : grp2RE;
			}
			return "(?:0|[1-9]\\d{0," + (grp-1) + "}(?:[" + sep + "]\\d{" + grp + "})*)";
		},
		true
	);

	// integer RE
	return signRE + numberRE; // String
}

}

if(!dojo._hasResource["dojo.cldr.monetary"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojo.cldr.monetary"] = true;
dojo.provide("dojo.cldr.monetary");

dojo.cldr.monetary.getData = function(/*String*/code){
// summary: A mapping of currency code to currency-specific formatting information. Returns a unique object with properties: places, round.
// code: an [ISO 4217](http://en.wikipedia.org/wiki/ISO_4217) currency code

// from http://www.unicode.org/cldr/data/common/supplemental/supplementalData.xml:supplementalData/currencyData/fractions

	var placesData = {
		ADP:0,BHD:3,BIF:0,BYR:0,CLF:0,CLP:0,DJF:0,ESP:0,GNF:0,
		IQD:3,ITL:0,JOD:3,JPY:0,KMF:0,KRW:0,KWD:3,LUF:0,LYD:3,
		MGA:0,MGF:0,OMR:3,PYG:0,RWF:0,TND:3,TRL:0,VUV:0,XAF:0,
		XOF:0,XPF:0
	};

	var roundingData = {CHF:5};

	var places = placesData[code], round = roundingData[code];
	if(typeof places == "undefined"){ places = 2; }
	if(typeof round == "undefined"){ round = 0; }

	return {places: places, round: round}; // Object
};

}

if(!dojo._hasResource["dojo.currency"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojo.currency"] = true;
dojo.provide("dojo.currency");






/*=====
dojo.currency = {
	// summary: localized formatting and parsing routines for currencies
}
=====*/

dojo.currency._mixInDefaults = function(options){
	options = options || {};
	options.type = "currency";

	// Get locale-depenent currency data, like the symbol
	var bundle = dojo.i18n.getLocalization("dojo.cldr", "currency", options.locale) || {};

	// Mixin locale-independent currency data, like # of places
	var iso = options.currency;
	var data = dojo.cldr.monetary.getData(iso);

	dojo.forEach(["displayName","symbol","group","decimal"], function(prop){
		data[prop] = bundle[iso+"_"+prop];
	});

	data.fractional = [true, false];

	// Mixin with provided options
	return dojo.mixin(data, options);
}

dojo.currency.format = function(/*Number*/value, /*dojo.number.__FormatOptions?*/options){
// summary:
//		Format a Number as a currency, using locale-specific settings
//
// description:
//		Create a string from a Number using a known, localized pattern.
//		[Formatting patterns](http://www.unicode.org/reports/tr35/#Number_Elements) appropriate to the locale are chosen from the [CLDR](http://unicode.org/cldr)
//		as well as the appropriate symbols and delimiters.
//
// value:
//		the number to be formatted.

	return dojo.number.format(value, dojo.currency._mixInDefaults(options));
}

dojo.currency.regexp = function(/*dojo.number.__RegexpOptions?*/options){
//
// summary:
//		Builds the regular needed to parse a currency value
//
// description:
//		Returns regular expression with positive and negative match, group and decimal separators
//		Note: the options.places default, the number of decimal places to accept, is defined by the currency type.
	return dojo.number.regexp(dojo.currency._mixInDefaults(options)); // String
}

/*=====
dojo.declare("dojo.currency.__ParseOptions", [dojo.number.__ParseOptions], {
	//	type: String?
	//		currency, set by default.
	//	symbol: String?
	//		override currency symbol. Normally, will be looked up in table of supported currencies,
	//		and ISO currency code will be used if not found.  See dojo.i18n.cldr.nls->currency.js
	//	places: Number?
	//		number of decimal places to accept.  Default is defined by currency.
	//	fractional: Boolean?|Array?
	//		where places are implied by pattern or explicit 'places' parameter, whether to include the fractional portion.
	//		By default for currencies, it the fractional portion is optional.
	type: "",
	symbol: "",
	places: "",
	fractional: ""
});
=====*/

dojo.currency.parse = function(/*String*/expression, /*dojo.currency.__ParseOptions?*/options){
	//
	// summary:
	//		Convert a properly formatted currency string to a primitive Number,
	//		using locale-specific settings.
	//
	// description:
	//		Create a Number from a string using a known, localized pattern.
	//		[Formatting patterns](http://www.unicode.org/reports/tr35/#Number_Format_Patterns) are chosen appropriate to the locale.
	//
	// expression: A string representation of a Number

	return dojo.number.parse(expression, dojo.currency._mixInDefaults(options));
}

}

if(!dojo._hasResource["dojo.fx"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojo.fx"] = true;
dojo.provide("dojo.fx");
dojo.provide("dojo.fx.Toggler");

/*=====
dojo.fx = {
	// summary: Effects library on top of Base animations
};
=====*/

(function(){
	var _baseObj = {
			_fire: function(evt, args){
				if(this[evt]){
					this[evt].apply(this, args||[]);
				}
				return this;
			}
		};

	var _chain = function(animations){
		this._index = -1;
		this._animations = animations||[];
		this._current = this._onAnimateCtx = this._onEndCtx = null;

		this.duration = 0;
		dojo.forEach(this._animations, function(a){
			this.duration += a.duration;
			if(a.delay){ this.duration += a.delay; }
		}, this);
	};
	dojo.extend(_chain, {
		_onAnimate: function(){
			this._fire("onAnimate", arguments);
		},
		_onEnd: function(){
			dojo.disconnect(this._onAnimateCtx);
			dojo.disconnect(this._onEndCtx);
			this._onAnimateCtx = this._onEndCtx = null;
			if(this._index + 1 == this._animations.length){
				this._fire("onEnd");
			}else{
				// switch animations
				this._current = this._animations[++this._index];
				this._onAnimateCtx = dojo.connect(this._current, "onAnimate", this, "_onAnimate");
				this._onEndCtx = dojo.connect(this._current, "onEnd", this, "_onEnd");
				this._current.play(0, true);
			}
		},
		play: function(/*int?*/ delay, /*Boolean?*/ gotoStart){
			if(!this._current){ this._current = this._animations[this._index = 0]; }
			if(!gotoStart && this._current.status() == "playing"){ return this; }
			var beforeBegin = dojo.connect(this._current, "beforeBegin", this, function(){
					this._fire("beforeBegin");
				}),
				onBegin = dojo.connect(this._current, "onBegin", this, function(arg){
					this._fire("onBegin", arguments);
				}),
				onPlay = dojo.connect(this._current, "onPlay", this, function(arg){
					this._fire("onPlay", arguments);
					dojo.disconnect(beforeBegin);
					dojo.disconnect(onBegin);
					dojo.disconnect(onPlay);
				});
			if(this._onAnimateCtx){
				dojo.disconnect(this._onAnimateCtx);
			}
			this._onAnimateCtx = dojo.connect(this._current, "onAnimate", this, "_onAnimate");
			if(this._onEndCtx){
				dojo.disconnect(this._onEndCtx);
			}
			this._onEndCtx = dojo.connect(this._current, "onEnd", this, "_onEnd");
			this._current.play.apply(this._current, arguments);
			return this;
		},
		pause: function(){
			if(this._current){
				var e = dojo.connect(this._current, "onPause", this, function(arg){
						this._fire("onPause", arguments);
						dojo.disconnect(e);
					});
				this._current.pause();
			}
			return this;
		},
		gotoPercent: function(/*Decimal*/percent, /*Boolean?*/ andPlay){
			this.pause();
			var offset = this.duration * percent;
			this._current = null;
			dojo.some(this._animations, function(a){
				if(a.duration <= offset){
					this._current = a;
					return true;
				}
				offset -= a.duration;
				return false;
			});
			if(this._current){
				this._current.gotoPercent(offset / _current.duration, andPlay);
			}
			return this;
		},
		stop: function(/*boolean?*/ gotoEnd){
			if(this._current){
				if(gotoEnd){
					for(; this._index + 1 < this._animations.length; ++this._index){
						this._animations[this._index].stop(true);
					}
					this._current = this._animations[this._index];
				}
				var e = dojo.connect(this._current, "onStop", this, function(arg){
						this._fire("onStop", arguments);
						dojo.disconnect(e);
					});
				this._current.stop();
			}
			return this;
		},
		status: function(){
			return this._current ? this._current.status() : "stopped";
		},
		destroy: function(){
			if(this._onAnimateCtx){ dojo.disconnect(this._onAnimateCtx); }
			if(this._onEndCtx){ dojo.disconnect(this._onEndCtx); }
		}
	});
	dojo.extend(_chain, _baseObj);

	dojo.fx.chain = function(/*dojo._Animation[]*/ animations){
		// summary: Chain a list of dojo._Animation s to run in sequence
		// example:
		//	|	dojo.fx.chain([
		//	|		dojo.fadeIn({ node:node }),
		//	|		dojo.fadeOut({ node:otherNode })
		//	|	]).play();
		//
		return new _chain(animations) // dojo._Animation
	};

	var _combine = function(animations){
		this._animations = animations||[];
		this._connects = [];
		this._finished = 0;

		this.duration = 0;
		dojo.forEach(animations, function(a){
			var duration = a.duration;
			if(a.delay){ duration += a.delay; }
			if(this.duration < duration){ this.duration = duration; }
			this._connects.push(dojo.connect(a, "onEnd", this, "_onEnd"));
		}, this);
		
		this._pseudoAnimation = new dojo._Animation({curve: [0, 1], duration: this.duration});
		dojo.forEach(["beforeBegin", "onBegin", "onPlay", "onAnimate", "onPause", "onStop"], 
			function(evt){
				this._connects.push(dojo.connect(this._pseudoAnimation, evt, dojo.hitch(this, "_fire", evt)));
			},
			this
		);
	};
	dojo.extend(_combine, {
		_doAction: function(action, args){
			dojo.forEach(this._animations, function(a){
				a[action].apply(a, args);
			});
			return this;
		},
		_onEnd: function(){
			if(++this._finished == this._animations.length){
				this._fire("onEnd");
			}
		},
		_call: function(action, args){
			var t = this._pseudoAnimation;
			t[action].apply(t, args);
		},
		play: function(/*int?*/ delay, /*Boolean?*/ gotoStart){
			this._finished = 0;
			this._doAction("play", arguments);
			this._call("play", arguments);
			return this;
		},
		pause: function(){
			this._doAction("pause", arguments);
			this._call("pause", arguments);
			return this;
		},
		gotoPercent: function(/*Decimal*/percent, /*Boolean?*/ andPlay){
			var ms = this.duration * percent;
			dojo.forEach(this._animations, function(a){
				a.gotoPercent(a.duration < ms ? 1 : (ms / a.duration), andPlay);
			});
			this._call("gotoProcent", arguments);
			return this;
		},
		stop: function(/*boolean?*/ gotoEnd){
			this._doAction("stop", arguments);
			this._call("stop", arguments);
			return this;
		},
		status: function(){
			return this._pseudoAnimation.status();
		},
		destroy: function(){
			dojo.forEach(this._connects, dojo.disconnect);
		}
	});
	dojo.extend(_combine, _baseObj);

	dojo.fx.combine = function(/*dojo._Animation[]*/ animations){
		// summary: Combine a list of dojo._Animation s to run in parallel
		// example:
		//	|	dojo.fx.combine([
		//	|		dojo.fadeIn({ node:node }),
		//	|		dojo.fadeOut({ node:otherNode })
		//	|	]).play();
		return new _combine(animations); // dojo._Animation
	};
})();

dojo.declare("dojo.fx.Toggler", null, {
	// summary:
	//		class constructor for an animation toggler. It accepts a packed
	//		set of arguments about what type of animation to use in each
	//		direction, duration, etc.
	//
	// example:
	//	|	var t = new dojo.fx.Toggler({
	//	|		node: "nodeId",
	//	|		showDuration: 500,
	//	|		// hideDuration will default to "200"
	//	|		showFunc: dojo.wipeIn, 
	//	|		// hideFunc will default to "fadeOut"
	//	|	});
	//	|	t.show(100); // delay showing for 100ms
	//	|	// ...time passes...
	//	|	t.hide();

	// FIXME: need a policy for where the toggler should "be" the next
	// time show/hide are called if we're stopped somewhere in the
	// middle.

	constructor: function(args){
		var _t = this;

		dojo.mixin(_t, args);
		_t.node = args.node;
		_t._showArgs = dojo.mixin({}, args);
		_t._showArgs.node = _t.node;
		_t._showArgs.duration = _t.showDuration;
		_t.showAnim = _t.showFunc(_t._showArgs);

		_t._hideArgs = dojo.mixin({}, args);
		_t._hideArgs.node = _t.node;
		_t._hideArgs.duration = _t.hideDuration;
		_t.hideAnim = _t.hideFunc(_t._hideArgs);

		dojo.connect(_t.showAnim, "beforeBegin", dojo.hitch(_t.hideAnim, "stop", true));
		dojo.connect(_t.hideAnim, "beforeBegin", dojo.hitch(_t.showAnim, "stop", true));
	},

	// node: DomNode
	//	the node to toggle
	node: null,

	// showFunc: Function
	//	The function that returns the dojo._Animation to show the node
	showFunc: dojo.fadeIn,

	// hideFunc: Function	
	//	The function that returns the dojo._Animation to hide the node
	hideFunc: dojo.fadeOut,

	// showDuration:
	//	Time in milliseconds to run the show Animation
	showDuration: 200,

	// hideDuration:
	//	Time in milliseconds to run the hide Animation
	hideDuration: 200,

	/*=====
	_showArgs: null,
	_showAnim: null,

	_hideArgs: null,
	_hideAnim: null,

	_isShowing: false,
	_isHiding: false,
	=====*/

	show: function(delay){
		// summary: Toggle the node to showing
		return this.showAnim.play(delay || 0);
	},

	hide: function(delay){
		// summary: Toggle the node to hidden
		return this.hideAnim.play(delay || 0);
	}
});

dojo.fx.wipeIn = function(/*Object*/ args){
	// summary
	//		Returns an animation that will expand the
	//		node defined in 'args' object from it's current height to
	//		it's natural height (with no scrollbar).
	//		Node must have no margin/border/padding.
	args.node = dojo.byId(args.node);
	var node = args.node, s = node.style;

	var anim = dojo.animateProperty(dojo.mixin({
		properties: {
			height: {
				// wrapped in functions so we wait till the last second to query (in case value has changed)
				start: function(){
					// start at current [computed] height, but use 1px rather than 0
					// because 0 causes IE to display the whole panel
					s.overflow="hidden";
					if(s.visibility=="hidden"||s.display=="none"){
						s.height="1px";
						s.display="";
						s.visibility="";
						return 1;
					}else{
						var height = dojo.style(node, "height");
						return Math.max(height, 1);
					}
				},
				end: function(){
					return node.scrollHeight;
				}
			}
		}
	}, args));

	dojo.connect(anim, "onEnd", function(){ 
		s.height = "auto";
	});

	return anim; // dojo._Animation
}

dojo.fx.wipeOut = function(/*Object*/ args){
	// summary
	//		Returns an animation that will shrink node defined in "args"
	//		from it's current height to 1px, and then hide it.
	var node = args.node = dojo.byId(args.node);
	var s = node.style;

	var anim = dojo.animateProperty(dojo.mixin({
		properties: {
			height: {
				end: 1 // 0 causes IE to display the whole panel
			}
		}
	}, args));

	dojo.connect(anim, "beforeBegin", function(){
		s.overflow = "hidden";
		s.display = "";
	});
	dojo.connect(anim, "onEnd", function(){
		s.height = "auto";
		s.display = "none";
	});

	return anim; // dojo._Animation
}

dojo.fx.slideTo = function(/*Object?*/ args){
	// summary
	//		Returns an animation that will slide "node" 
	//		defined in args Object from its current position to
	//		the position defined by (args.left, args.top).
	// example:
	//	|	dojo.fx.slideTo({ node: node, left:"40", top:"50", unit:"px" }).play()

	var node = (args.node = dojo.byId(args.node));
	
	var top = null;
	var left = null;
	
	var init = (function(n){
		return function(){
			var cs = dojo.getComputedStyle(n);
			var pos = cs.position;
			top = (pos == 'absolute' ? n.offsetTop : parseInt(cs.top) || 0);
			left = (pos == 'absolute' ? n.offsetLeft : parseInt(cs.left) || 0);
			if(pos != 'absolute' && pos != 'relative'){
				var ret = dojo.coords(n, true);
				top = ret.y;
				left = ret.x;
				n.style.position="absolute";
				n.style.top=top+"px";
				n.style.left=left+"px";
			}
		};
	})(node);
	init();

	var anim = dojo.animateProperty(dojo.mixin({
		properties: {
			top: { end: args.top||0 },
			left: { end: args.left||0 }
		}
	}, args));
	dojo.connect(anim, "beforeBegin", anim, init);

	return anim; // dojo._Animation
}

}

if(!dojo._hasResource["dijit._base.focus"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dijit._base.focus"] = true;
dojo.provide("dijit._base.focus");

// summary:
//		These functions are used to query or set the focus and selection.
//
//		Also, they trace when widgets become actived/deactivated,
//		so that the widget can fire _onFocus/_onBlur events.
//		"Active" here means something similar to "focused", but
//		"focus" isn't quite the right word because we keep track of
//		a whole stack of "active" widgets.  Example:  Combobutton --> Menu -->
//		MenuItem.   The onBlur event for Combobutton doesn't fire due to focusing
//		on the Menu or a MenuItem, since they are considered part of the
//		Combobutton widget.  It only happens when focus is shifted
//		somewhere completely different.

dojo.mixin(dijit,
{
	// _curFocus: DomNode
	//		Currently focused item on screen
	_curFocus: null,

	// _prevFocus: DomNode
	//		Previously focused item on screen
	_prevFocus: null,

	isCollapsed: function(){
		// summary: tests whether the current selection is empty
		var _window = dojo.global;
		var _document = dojo.doc;
		if(_document.selection){ // IE
			return !_document.selection.createRange().text; // Boolean
		}else{
			var selection = _window.getSelection();
			if(dojo.isString(selection)){ // Safari
				return !selection; // Boolean
			}else{ // Mozilla/W3
				return selection.isCollapsed || !selection.toString(); // Boolean
			}
		}
	},

	getBookmark: function(){
		// summary: Retrieves a bookmark that can be used with moveToBookmark to return to the same range
		var bookmark, selection = dojo.doc.selection;
		if(selection){ // IE
			var range = selection.createRange();
			if(selection.type.toUpperCase()=='CONTROL'){
				if(range.length){
					bookmark=[];
					var i=0,len=range.length;
					while(i<len){
						bookmark.push(range.item(i++));
					}
				}else{
					bookmark=null;
				}
			}else{
				bookmark = range.getBookmark();
			}
		}else{
			if(window.getSelection){
				selection = dojo.global.getSelection();
				if(selection){
					range = selection.getRangeAt(0);
					bookmark = range.cloneRange();
				}
			}else{
				console.warn("No idea how to store the current selection for this browser!");
			}
		}
		return bookmark; // Array
	},

	moveToBookmark: function(/*Object*/bookmark){
		// summary: Moves current selection to a bookmark
		// bookmark: This should be a returned object from dojo.html.selection.getBookmark()
		var _document = dojo.doc;
		if(_document.selection){ // IE
			var range;
			if(dojo.isArray(bookmark)){
				range = _document.body.createControlRange();
				dojo.forEach(bookmark, "range.addElement(item)"); //range.addElement does not have call/apply method, so can not call it directly
			}else{
				range = _document.selection.createRange();
				range.moveToBookmark(bookmark);
			}
			range.select();
		}else{ //Moz/W3C
			var selection = dojo.global.getSelection && dojo.global.getSelection();
			if(selection && selection.removeAllRanges){
				selection.removeAllRanges();
				selection.addRange(bookmark);
			}else{
				console.warn("No idea how to restore selection for this browser!");
			}
		}
	},

	getFocus: function(/*Widget?*/menu, /*Window?*/openedForWindow){
		// summary:
		//	Returns the current focus and selection.
		//	Called when a popup appears (either a top level menu or a dialog),
		//	or when a toolbar/menubar receives focus
		//
		// menu:
		//	The menu that's being opened
		//
		// openedForWindow:
		//	iframe in which menu was opened
		//
		// returns:
		//	A handle to restore focus/selection

		return {
			// Node to return focus to
			node: menu && dojo.isDescendant(dijit._curFocus, menu.domNode) ? dijit._prevFocus : dijit._curFocus,

			// Previously selected text
			bookmark:
				!dojo.withGlobal(openedForWindow||dojo.global, dijit.isCollapsed) ?
				dojo.withGlobal(openedForWindow||dojo.global, dijit.getBookmark) :
				null,

			openedForWindow: openedForWindow
		}; // Object
	},

	focus: function(/*Object || DomNode */ handle){
		// summary:
		//		Sets the focused node and the selection according to argument.
		//		To set focus to an iframe's content, pass in the iframe itself.
		// handle:
		//		object returned by get(), or a DomNode

		if(!handle){ return; }

		var node = "node" in handle ? handle.node : handle,		// because handle is either DomNode or a composite object
			bookmark = handle.bookmark,
			openedForWindow = handle.openedForWindow;

		// Set the focus
		// Note that for iframe's we need to use the <iframe> to follow the parentNode chain,
		// but we need to set focus to iframe.contentWindow
		if(node){
			var focusNode = (node.tagName.toLowerCase()=="iframe") ? node.contentWindow : node;
			if(focusNode && focusNode.focus){
				try{
					// Gecko throws sometimes if setting focus is impossible,
					// node not displayed or something like that
					focusNode.focus();
				}catch(e){/*quiet*/}
			}			
			dijit._onFocusNode(node);
		}

		// set the selection
		// do not need to restore if current selection is not empty
		// (use keyboard to select a menu item)
		if(bookmark && dojo.withGlobal(openedForWindow||dojo.global, dijit.isCollapsed)){
			if(openedForWindow){
				openedForWindow.focus();
			}
			try{
				dojo.withGlobal(openedForWindow||dojo.global, dijit.moveToBookmark, null, [bookmark]);
			}catch(e){
				/*squelch IE internal error, see http://trac.dojotoolkit.org/ticket/1984 */
			}
		}
	},

	// _activeStack: Array
	//		List of currently active widgets (focused widget and it's ancestors)
	_activeStack: [],

	registerWin: function(/*Window?*/targetWindow){
		// summary:
		//		Registers listeners on the specified window (either the main
		//		window or an iframe) to detect when the user has clicked somewhere.
		//		Anyone that creates an iframe should call this function.

		if(!targetWindow){
			targetWindow = window;
		}

		dojo.connect(targetWindow.document, "onmousedown", function(evt){
			dijit._justMouseDowned = true;
			setTimeout(function(){ dijit._justMouseDowned = false; }, 0);
			dijit._onTouchNode(evt.target||evt.srcElement);
		});
		//dojo.connect(targetWindow, "onscroll", ???);

		// Listen for blur and focus events on targetWindow's body
		var body = targetWindow.document.body || targetWindow.document.getElementsByTagName("body")[0];
		if(body){
			if(dojo.isIE){
				body.attachEvent('onactivate', function(evt){
					if(evt.srcElement.tagName.toLowerCase() != "body"){
						dijit._onFocusNode(evt.srcElement);
					}
				});
				body.attachEvent('ondeactivate', function(evt){ dijit._onBlurNode(evt.srcElement); });
			}else{
				body.addEventListener('focus', function(evt){ dijit._onFocusNode(evt.target); }, true);
				body.addEventListener('blur', function(evt){ dijit._onBlurNode(evt.target); }, true);
			}
		}
		body = null;	// prevent memory leak (apparent circular reference via closure)
	},

	_onBlurNode: function(/*DomNode*/ node){
		// summary:
		// 		Called when focus leaves a node.
		//		Usually ignored, _unless_ it *isn't* follwed by touching another node,
		//		which indicates that we tabbed off the last field on the page,
		//		in which case every widget is marked inactive
		dijit._prevFocus = dijit._curFocus;
		dijit._curFocus = null;

		if(dijit._justMouseDowned){
			// the mouse down caused a new widget to be marked as active; this blur event
			// is coming late, so ignore it.
			return;
		}

		// if the blur event isn't followed by a focus event then mark all widgets as inactive.
		if(dijit._clearActiveWidgetsTimer){
			clearTimeout(dijit._clearActiveWidgetsTimer);
		}
		dijit._clearActiveWidgetsTimer = setTimeout(function(){
			delete dijit._clearActiveWidgetsTimer;
			dijit._setStack([]);
			dijit._prevFocus = null;
		}, 100);
	},

	_onTouchNode: function(/*DomNode*/ node){
		// summary:
		//		Callback when node is focused or mouse-downed

		// ignore the recent blurNode event
		if(dijit._clearActiveWidgetsTimer){
			clearTimeout(dijit._clearActiveWidgetsTimer);
			delete dijit._clearActiveWidgetsTimer;
		}

		// compute stack of active widgets (ex: ComboButton --> Menu --> MenuItem)
		var newStack=[];
		try{
			while(node){
				if(node.dijitPopupParent){
					node=dijit.byId(node.dijitPopupParent).domNode;
				}else if(node.tagName && node.tagName.toLowerCase()=="body"){
					// is this the root of the document or just the root of an iframe?
					if(node===dojo.body()){
						// node is the root of the main document
						break;
					}
					// otherwise, find the iframe this node refers to (can't access it via parentNode,
					// need to do this trick instead). window.frameElement is supported in IE/FF/Webkit
					node=dijit.getDocumentWindow(node.ownerDocument).frameElement;
				}else{
					var id = node.getAttribute && node.getAttribute("widgetId");
					if(id){
						newStack.unshift(id);
					}
					node=node.parentNode;
				}
			}
		}catch(e){ /* squelch */ }

		dijit._setStack(newStack);
	},

	_onFocusNode: function(/*DomNode*/ node){
		// summary
		//		Callback when node is focused
		if(node && node.tagName && node.tagName.toLowerCase() == "body"){
			return;
		}
		dijit._onTouchNode(node);

		if(node==dijit._curFocus){ return; }
		if(dijit._curFocus){
			dijit._prevFocus = dijit._curFocus;
		}
		dijit._curFocus = node;
		dojo.publish("focusNode", [node]);
	},

	_setStack: function(newStack){
		// summary
		//	The stack of active widgets has changed.  Send out appropriate events and record new stack

		var oldStack = dijit._activeStack;		
		dijit._activeStack = newStack;

		// compare old stack to new stack to see how many elements they have in common
		for(var nCommon=0; nCommon<Math.min(oldStack.length, newStack.length); nCommon++){
			if(oldStack[nCommon] != newStack[nCommon]){
				break;
			}
		}

		// for all elements that have gone out of focus, send blur event
		for(var i=oldStack.length-1; i>=nCommon; i--){
			var widget = dijit.byId(oldStack[i]);
			if(widget){
				widget._focused = false;
				widget._hasBeenBlurred = true;
				if(widget._onBlur){
					widget._onBlur();
				}
				if (widget._setStateClass){
					widget._setStateClass();
				}
				dojo.publish("widgetBlur", [widget]);
			}
		}

		// for all element that have come into focus, send focus event
		for(i=nCommon; i<newStack.length; i++){
			widget = dijit.byId(newStack[i]);
			if(widget){
				widget._focused = true;
				if(widget._onFocus){
					widget._onFocus();
				}
				if (widget._setStateClass){
					widget._setStateClass();
				}
				dojo.publish("widgetFocus", [widget]);
			}
		}
	}
});

// register top window and all the iframes it contains
dojo.addOnLoad(dijit.registerWin);

}

if(!dojo._hasResource["dijit._base.manager"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dijit._base.manager"] = true;
dojo.provide("dijit._base.manager");

dojo.declare("dijit.WidgetSet", null, {
	// summary:
	//	A set of widgets indexed by id

	constructor: function(){
		this._hash={};
	},

	add: function(/*Widget*/ widget){
		if(this._hash[widget.id]){
			throw new Error("Tried to register widget with id==" + widget.id + " but that id is already registered");
		}
		this._hash[widget.id]=widget;
	},

	remove: function(/*String*/ id){
		delete this._hash[id];
	},

	forEach: function(/*Function*/ func){
		for(var id in this._hash){
			func(this._hash[id]);
		}
	},

	filter: function(/*Function*/ filter){
		var res = new dijit.WidgetSet();
		this.forEach(function(widget){
			if(filter(widget)){ res.add(widget); }
		});
		return res;		// dijit.WidgetSet
	},

	byId: function(/*String*/ id){
		return this._hash[id];
	},

	byClass: function(/*String*/ cls){
		return this.filter(function(widget){ return widget.declaredClass==cls; });	// dijit.WidgetSet
	}
	});

/*=====
dijit.registry = {
	// summary: A list of widgets on a page.
	// description: Is an instance of dijit.WidgetSet
};
=====*/
dijit.registry = new dijit.WidgetSet();

dijit._widgetTypeCtr = {};

dijit.getUniqueId = function(/*String*/widgetType){
	// summary
	//	Generates a unique id for a given widgetType

	var id;
	do{
		id = widgetType + "_" +
			(widgetType in dijit._widgetTypeCtr ?
				++dijit._widgetTypeCtr[widgetType] : dijit._widgetTypeCtr[widgetType] = 0);
	}while(dijit.byId(id));
	return id; // String
};


if(dojo.isIE){
	// Only run this for IE because we think it's only necessary in that case,
	// and because it causes problems on FF.  See bug #3531 for details.
	dojo.addOnUnload(function(){
		dijit.registry.forEach(function(widget){ widget.destroy(); });
	});
}

dijit.byId = function(/*String|Widget*/id){
	// summary:
	//		Returns a widget by its id, or if passed a widget, no-op (like dojo.byId())
	return (dojo.isString(id)) ? dijit.registry.byId(id) : id; // Widget
};

dijit.byNode = function(/* DOMNode */ node){
	// summary:
	//		Returns the widget as referenced by node
	return dijit.registry.byId(node.getAttribute("widgetId")); // Widget
};

dijit.getEnclosingWidget = function(/* DOMNode */ node){
	// summary:
	//		Returns the widget whose dom tree contains node or null if
	//		the node is not contained within the dom tree of any widget
	while(node){
		if(node.getAttribute && node.getAttribute("widgetId")){
			return dijit.registry.byId(node.getAttribute("widgetId"));
		}
		node = node.parentNode;
	}
	return null;
};

// elements that are tab-navigable if they have no tabindex value set
// (except for "a", which must have an href attribute)
dijit._tabElements = {
	area: true,
	button: true,
	input: true,
	object: true,
	select: true,
	textarea: true
};

dijit._isElementShown = function(/*Element*/elem){
	var style = dojo.style(elem);
	return (style.visibility != "hidden")
		&& (style.visibility != "collapsed")
		&& (style.display != "none");
}

dijit.isTabNavigable = function(/*Element*/elem){
	// summary:
	//		Tests if an element is tab-navigable
	if(dojo.hasAttr(elem, "disabled")){ return false; }
	var hasTabindex = dojo.hasAttr(elem, "tabindex");
	var tabindex = dojo.attr(elem, "tabindex");
	if(hasTabindex && tabindex >= 0) {
		return true; // boolean
	}
	var name = elem.nodeName.toLowerCase();
	if(((name == "a" && dojo.hasAttr(elem, "href"))
			|| dijit._tabElements[name])
		&& (!hasTabindex || tabindex >= 0)){
		return true; // boolean
	}
	return false; // boolean
};

dijit._getTabNavigable = function(/*DOMNode*/root){
	// summary:
	//		Finds the following descendants of the specified root node:
	//		* the first tab-navigable element in document order
	//		  without a tabindex or with tabindex="0"
	//		* the last tab-navigable element in document order
	//		  without a tabindex or with tabindex="0"
	//		* the first element in document order with the lowest
	//		  positive tabindex value
	//		* the last element in document order with the highest
	//		  positive tabindex value
	var first, last, lowest, lowestTabindex, highest, highestTabindex;
	var walkTree = function(/*DOMNode*/parent){
		dojo.query("> *", parent).forEach(function(child){
			var isShown = dijit._isElementShown(child);
			if(isShown && dijit.isTabNavigable(child)){
				var tabindex = dojo.attr(child, "tabindex");
				if(!dojo.hasAttr(child, "tabindex") || tabindex == 0){
					if(!first){ first = child; }
					last = child;
				}else if(tabindex > 0){
					if(!lowest || tabindex < lowestTabindex){
						lowestTabindex = tabindex;
						lowest = child;
					}
					if(!highest || tabindex >= highestTabindex){
						highestTabindex = tabindex;
						highest = child;
					}
				}
			}
			if(isShown){ walkTree(child) }
		});
	};
	if(dijit._isElementShown(root)){ walkTree(root) }
	return { first: first, last: last, lowest: lowest, highest: highest };
}

dijit.getFirstInTabbingOrder = function(/*String|DOMNode*/root){
	// summary:
	//		Finds the descendant of the specified root node
	//		that is first in the tabbing order
	var elems = dijit._getTabNavigable(dojo.byId(root));
	return elems.lowest ? elems.lowest : elems.first; // Element
};

dijit.getLastInTabbingOrder = function(/*String|DOMNode*/root){
	// summary:
	//		Finds the descendant of the specified root node
	//		that is last in the tabbing order
	var elems = dijit._getTabNavigable(dojo.byId(root));
	return elems.last ? elems.last : elems.highest; // Element
};

}

if(!dojo._hasResource["dijit._base.place"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dijit._base.place"] = true;
dojo.provide("dijit._base.place");

// ported from dojo.html.util

dijit.getViewport = function(){
	//	summary
	//	Returns the dimensions and scroll position of the viewable area of a browser window

	var _window = dojo.global;
	var _document = dojo.doc;

	// get viewport size
	var w = 0, h = 0;
	var de = _document.documentElement;
	var dew = de.clientWidth, deh = de.clientHeight;
	if(dojo.isMozilla){
		// mozilla
		// _window.innerHeight includes the height taken by the scroll bar
		// clientHeight is ideal but has DTD issues:
		// #4539: FF reverses the roles of body.clientHeight/Width and documentElement.clientHeight/Width based on the DTD!
		// check DTD to see whether body or documentElement returns the viewport dimensions using this algorithm:
		var minw, minh, maxw, maxh;
		var dbw = _document.body.clientWidth;
		if(dbw > dew){
			minw = dew;
			maxw = dbw;
		}else{
			maxw = dew;
			minw = dbw;
		}
		var dbh = _document.body.clientHeight;
		if(dbh > deh){
			minh = deh;
			maxh = dbh;
		}else{
			maxh = deh;
			minh = dbh;
		}
		w = (maxw > _window.innerWidth) ? minw : maxw;
		h = (maxh > _window.innerHeight) ? minh : maxh;
	}else if(!dojo.isOpera && _window.innerWidth){
		//in opera9, dojo.body().clientWidth should be used, instead
		//of window.innerWidth/document.documentElement.clientWidth
		//so we have to check whether it is opera
		w = _window.innerWidth;
		h = _window.innerHeight;
	}else if(dojo.isIE && de && deh){
		w = dew;
		h = deh;
	}else if(dojo.body().clientWidth){
		// IE5, Opera
		w = dojo.body().clientWidth;
		h = dojo.body().clientHeight;
	}

	// get scroll position
	var scroll = dojo._docScroll();

	return { w: w, h: h, l: scroll.x, t: scroll.y };	//	object
};

dijit.placeOnScreen = function(
	/* DomNode */	node,
	/* Object */		pos,
	/* Object */		corners,
	/* boolean? */		tryOnly){
	//	summary:
	//		Keeps 'node' in the visible area of the screen while trying to
	//		place closest to pos.x, pos.y. The input coordinates are
	//		expected to be the desired document position.
	//
	//		Set which corner(s) you want to bind to, such as
	//		
	//			placeOnScreen(node, {x: 10, y: 20}, ["TR", "BL"])
	//		
	//		The desired x/y will be treated as the topleft(TL)/topright(TR) or
	//		BottomLeft(BL)/BottomRight(BR) corner of the node. Each corner is tested
	//		and if a perfect match is found, it will be used. Otherwise, it goes through
	//		all of the specified corners, and choose the most appropriate one.
	//		
	//		NOTE: node is assumed to be absolutely or relatively positioned.

	var choices = dojo.map(corners, function(corner){ return { corner: corner, pos: pos }; });

	return dijit._place(node, choices);
}

dijit._place = function(/*DomNode*/ node, /* Array */ choices, /* Function */ layoutNode){
	// summary:
	//		Given a list of spots to put node, put it at the first spot where it fits,
	//		of if it doesn't fit anywhere then the place with the least overflow
	// choices: Array
	//		Array of elements like: {corner: 'TL', pos: {x: 10, y: 20} }
	//		Above example says to put the top-left corner of the node at (10,20)
	//	layoutNode: Function(node, aroundNodeCorner, nodeCorner)
	//		for things like tooltip, they are displayed differently (and have different dimensions)
	//		based on their orientation relative to the parent.   This adjusts the popup based on orientation.

	// get {x: 10, y: 10, w: 100, h:100} type obj representing position of
	// viewport over document
	var view = dijit.getViewport();

	// This won't work if the node is inside a <div style="position: relative">,
	// so reattach it to dojo.doc.body.   (Otherwise, the positioning will be wrong
	// and also it might get cutoff)
	if(!node.parentNode || String(node.parentNode.tagName).toLowerCase() != "body"){
		dojo.body().appendChild(node);
	}

	var best = null;
	dojo.some(choices, function(choice){
		var corner = choice.corner;
		var pos = choice.pos;

		// configure node to be displayed in given position relative to button
		// (need to do this in order to get an accurate size for the node, because
		// a tooltips size changes based on position, due to triangle)
		if(layoutNode){
			layoutNode(node, choice.aroundCorner, corner);
		}

		// get node's size
		var style = node.style;
		var oldDisplay = style.display;
		var oldVis = style.visibility;
		style.visibility = "hidden";
		style.display = "";
		var mb = dojo.marginBox(node);
		style.display = oldDisplay;
		style.visibility = oldVis;

		// coordinates and size of node with specified corner placed at pos,
		// and clipped by viewport
		var startX = (corner.charAt(1) == 'L' ? pos.x : Math.max(view.l, pos.x - mb.w)),
			startY = (corner.charAt(0) == 'T' ? pos.y : Math.max(view.t, pos.y -  mb.h)),
			endX = (corner.charAt(1) == 'L' ? Math.min(view.l + view.w, startX + mb.w) : pos.x),
			endY = (corner.charAt(0) == 'T' ? Math.min(view.t + view.h, startY + mb.h) : pos.y),
			width = endX - startX,
			height = endY - startY,
			overflow = (mb.w - width) + (mb.h - height);

		if(best == null || overflow < best.overflow){
			best = {
				corner: corner,
				aroundCorner: choice.aroundCorner,
				x: startX,
				y: startY,
				w: width,
				h: height,
				overflow: overflow
			};
		}
		return !overflow;
	});

	node.style.left = best.x + "px";
	node.style.top = best.y + "px";
	if(best.overflow && layoutNode){
		layoutNode(node, best.aroundCorner, best.corner);
	}
	return best;
}

dijit.placeOnScreenAroundElement = function(
	/* DomNode */		node,
	/* DomNode */		aroundNode,
	/* Object */		aroundCorners,
	/* Function */		layoutNode){

	//	summary
	//	Like placeOnScreen, except it accepts aroundNode instead of x,y
	//	and attempts to place node around it.  Uses margin box dimensions.
	//
	//	aroundCorners
	//		specify Which corner of aroundNode should be
	//		used to place the node => which corner(s) of node to use (see the
	//		corners parameter in dijit.placeOnScreen)
	//		e.g. {'TL': 'BL', 'BL': 'TL'}
	//
	//	layoutNode: Function(node, aroundNodeCorner, nodeCorner)
	//		for things like tooltip, they are displayed differently (and have different dimensions)
	//		based on their orientation relative to the parent.   This adjusts the popup based on orientation.


	// get coordinates of aroundNode
	aroundNode = dojo.byId(aroundNode);
	var oldDisplay = aroundNode.style.display;
	aroundNode.style.display="";
	// #3172: use the slightly tighter border box instead of marginBox
	var aroundNodeW = aroundNode.offsetWidth; //mb.w;
	var aroundNodeH = aroundNode.offsetHeight; //mb.h;
	var aroundNodePos = dojo.coords(aroundNode, true);
	aroundNode.style.display=oldDisplay;

	// Generate list of possible positions for node
	var choices = [];
	for(var nodeCorner in aroundCorners){
		choices.push( {
			aroundCorner: nodeCorner,
			corner: aroundCorners[nodeCorner],
			pos: {
				x: aroundNodePos.x + (nodeCorner.charAt(1) == 'L' ? 0 : aroundNodeW),
				y: aroundNodePos.y + (nodeCorner.charAt(0) == 'T' ? 0 : aroundNodeH)
			}
		});
	}

	return dijit._place(node, choices, layoutNode);
}

}

if(!dojo._hasResource["dijit._base.window"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dijit._base.window"] = true;
dojo.provide("dijit._base.window");

dijit.getDocumentWindow = function(doc){
	//	summary
	// 	Get window object associated with document doc

	// With Safari, there is not way to retrieve the window from the document, so we must fix it.
	if(dojo.isSafari && !doc._parentWindow){
		/*
			This is a Safari specific function that fix the reference to the parent
			window from the document object.
			TODO: #5711: should the use of document below reference dojo.doc instead
			in case they're not the same?
		*/
		var fix=function(win){
			win.document._parentWindow=win;
			for(var i=0; i<win.frames.length; i++){
				fix(win.frames[i]);
			}
		}
		fix(window.top);
	}

	//In some IE versions (at least 6.0), document.parentWindow does not return a
	//reference to the real window object (maybe a copy), so we must fix it as well
	//We use IE specific execScript to attach the real window reference to
	//document._parentWindow for later use
	//TODO: #5711: should the use of document below reference dojo.doc instead in case they're not the same?
	if(dojo.isIE && window !== document.parentWindow && !doc._parentWindow){
		/*
		In IE 6, only the variable "window" can be used to connect events (others
		may be only copies).
		*/
		doc.parentWindow.execScript("document._parentWindow = window;", "Javascript");
		//to prevent memory leak, unset it after use
		//another possibility is to add an onUnload handler which seems overkill to me (liucougar)
		var win = doc._parentWindow;
		doc._parentWindow = null;
		return win;	//	Window
	}

	return doc._parentWindow || doc.parentWindow || doc.defaultView;	//	Window
}

}

if(!dojo._hasResource["dijit._base.popup"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dijit._base.popup"] = true;
dojo.provide("dijit._base.popup");





dijit.popup = new function(){
	// summary:
	//		This class is used to show/hide widgets as popups.
	//

	var stack = [],
		beginZIndex=1000,
		idGen = 1;

	this.prepare = function(/*DomNode*/ node){
		// summary:
		//		Prepares a node to be used as a popup
		//
		// description:
		//		Attaches node to dojo.doc.body, and
		//		positions it off screen, but not display:none, so that
		//		the widget doesn't appear in the page flow and/or cause a blank
		//		area at the bottom of the viewport (making scrollbar longer), but
		//		initialization of contained widgets works correctly
	
		dojo.body().appendChild(node);
		var s = node.style;
		if(s.display == "none"){
			s.display="";
		}
		s.visibility = "hidden";	// not needed for hiding, but used as flag that node is off-screen
		s.position = "absolute";
		s.top = "-9999px";
	};

	this.open = function(/*Object*/ args){
		// summary:
		//		Popup the widget at the specified position
		//
		// args: Object
		//		popup: Widget
		//			widget to display,
		//		parent: Widget
		//			the button etc. that is displaying this popup
		//		around: DomNode
		//			DOM node (typically a button); place popup relative to this node
		//		orient: Object
		//			structure specifying possible positions of popup relative to "around" node
		//		onCancel: Function
		//			callback when user has canceled the popup by
		//				1. hitting ESC or
		//				2. by using the popup widget's proprietary cancel mechanism (like a cancel button in a dialog);
		//				   ie: whenever popupWidget.onCancel() is called, args.onCancel is called
		//		onClose: Function
		//			callback whenever this popup is closed
		//		onExecute: Function
		//			callback when user "executed" on the popup/sub-popup by selecting a menu choice, etc. (top menu only)
		//
		// examples:
		//		1. opening at the mouse position
		//			dijit.popup.open({popup: menuWidget, x: evt.pageX, y: evt.pageY});
		//		2. opening the widget as a dropdown
		//			dijit.popup.open({parent: this, popup: menuWidget, around: this.domNode, onClose: function(){...}  });
		//
		//	Note that whatever widget called dijit.popup.open() should also listen to it's own _onBlur callback
		//	(fired from _base/focus.js) to know that focus has moved somewhere else and thus the popup should be closed.

		var widget = args.popup,
			orient = args.orient || {'BL':'TL', 'TL':'BL'},
			around = args.around,
			id = (args.around && args.around.id) ? (args.around.id+"_dropdown") : ("popup_"+idGen++);

		// make wrapper div to hold widget and possibly hold iframe behind it.
		// we can't attach the iframe as a child of the widget.domNode because
		// widget.domNode might be a <table>, <ul>, etc.
		var wrapper = dojo.doc.createElement("div");
		dijit.setWaiRole(wrapper, "presentation");
		wrapper.id = id;
		wrapper.className="dijitPopup";
		wrapper.style.zIndex = beginZIndex + stack.length;
		wrapper.style.visibility = "hidden";
		if(args.parent){
			wrapper.dijitPopupParent=args.parent.id;
		}
		dojo.body().appendChild(wrapper);

		var s = widget.domNode.style;
		s.display = "";
		s.visibility = "";
		s.position = "";
		wrapper.appendChild(widget.domNode);

		var iframe = new dijit.BackgroundIframe(wrapper);

		// position the wrapper node
		var best = around ?
			dijit.placeOnScreenAroundElement(wrapper, around, orient, widget.orient ? dojo.hitch(widget, "orient") : null) :
			dijit.placeOnScreen(wrapper, args, orient == 'R' ? ['TR','BR','TL','BL'] : ['TL','BL','TR','BR']);

		wrapper.style.visibility = "visible";
		// TODO: use effects to fade in wrapper

		var handlers = [];

		// Compute the closest ancestor popup that's *not* a child of another popup.
		// Ex: For a TooltipDialog with a button that spawns a tree of menus, find the popup of the button.
		var getTopPopup = function(){
			for(var pi=stack.length-1; pi > 0 && stack[pi].parent === stack[pi-1].widget; pi--){
				/* do nothing, just trying to get right value for pi */
			}
			return stack[pi];
		}

		// provide default escape and tab key handling
		// (this will work for any widget, not just menu)
		handlers.push(dojo.connect(wrapper, "onkeypress", this, function(evt){
			if(evt.keyCode == dojo.keys.ESCAPE && args.onCancel){
				dojo.stopEvent(evt);
				args.onCancel();
			}else if(evt.keyCode == dojo.keys.TAB){
				dojo.stopEvent(evt);
				var topPopup = getTopPopup();
				if(topPopup && topPopup.onCancel){
					topPopup.onCancel();
				}
			}
		}));

		// watch for cancel/execute events on the popup and notify the caller
		// (for a menu, "execute" means clicking an item)
		if(widget.onCancel){
			handlers.push(dojo.connect(widget, "onCancel", null, args.onCancel));
		}

		handlers.push(dojo.connect(widget, widget.onExecute ? "onExecute" : "onChange", null, function(){
			var topPopup = getTopPopup();
			if(topPopup && topPopup.onExecute){
				topPopup.onExecute();
			}
		}));

		stack.push({
			wrapper: wrapper,
			iframe: iframe,
			widget: widget,
			parent: args.parent,
			onExecute: args.onExecute,
			onCancel: args.onCancel,
 			onClose: args.onClose,
			handlers: handlers
		});

		if(widget.onOpen){
			widget.onOpen(best);
		}

		return best;
	};

	this.close = function(/*Widget*/ popup){
		// summary:
		//		Close specified popup and any popups that it parented
		while(dojo.some(stack, function(elem){return elem.widget == popup;})){
			var top = stack.pop(),
				wrapper = top.wrapper,
				iframe = top.iframe,
				widget = top.widget,
				onClose = top.onClose;
	
			if(widget.onClose){
				widget.onClose();
			}
			dojo.forEach(top.handlers, dojo.disconnect);
	
			// #2685: check if the widget still has a domNode so ContentPane can change its URL without getting an error
			if(!widget||!widget.domNode){ return; }
			
			this.prepare(widget.domNode);

			iframe.destroy();
			dojo._destroyElement(wrapper);
	
			if(onClose){
				onClose();
			}
		}
	};
}();

dijit._frames = new function(){
	// summary: cache of iframes
	var queue = [];

	this.pop = function(){
		var iframe;
		if(queue.length){
			iframe = queue.pop();
			iframe.style.display="";
		}else{
			if(dojo.isIE){
				var html="<iframe src='javascript:\"\"'"
					+ " style='position: absolute; left: 0px; top: 0px;"
					+ "z-index: -1; filter:Alpha(Opacity=\"0\");'>";
				iframe = dojo.doc.createElement(html);
			}else{
			 	iframe = dojo.doc.createElement("iframe");
				iframe.src = 'javascript:""';
				iframe.className = "dijitBackgroundIframe";
			}
			iframe.tabIndex = -1; // Magic to prevent iframe from getting focus on tab keypress - as style didnt work.
			dojo.body().appendChild(iframe);
		}
		return iframe;
	};

	this.push = function(iframe){
		iframe.style.display="";
		if(dojo.isIE){
			iframe.style.removeExpression("width");
			iframe.style.removeExpression("height");
		}
		queue.push(iframe);
	}
}();

// fill the queue
if(dojo.isIE && dojo.isIE < 7){
	dojo.addOnLoad(function(){
		var f = dijit._frames;
		dojo.forEach([f.pop()], f.push);
	});
}


dijit.BackgroundIframe = function(/* DomNode */node){
	//	summary:
	//		For IE z-index schenanigans. id attribute is required.
	//
	//	description:
	//		new dijit.BackgroundIframe(node)
	//			Makes a background iframe as a child of node, that fills
	//			area (and position) of node

	if(!node.id){ throw new Error("no id"); }
	if((dojo.isIE && dojo.isIE < 7) || (dojo.isFF && dojo.isFF < 3 && dojo.hasClass(dojo.body(), "dijit_a11y"))){
		var iframe = dijit._frames.pop();
		node.appendChild(iframe);
		if(dojo.isIE){
			iframe.style.setExpression("width", dojo._scopeName + ".doc.getElementById('" + node.id + "').offsetWidth");
			iframe.style.setExpression("height", dojo._scopeName + ".doc.getElementById('" + node.id + "').offsetHeight");
		}
		this.iframe = iframe;
	}
};

dojo.extend(dijit.BackgroundIframe, {
	destroy: function(){
		//	summary: destroy the iframe
		if(this.iframe){
			dijit._frames.push(this.iframe);
			delete this.iframe;
		}
	}
});

}

if(!dojo._hasResource["dijit._base.scroll"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dijit._base.scroll"] = true;
dojo.provide("dijit._base.scroll");

dijit.scrollIntoView = function(/* DomNode */node){
	//	summary
	//	Scroll the passed node into view, if it is not.

	// don't rely on that node.scrollIntoView works just because the function is there
	// it doesnt work in Konqueror or Opera even though the function is there and probably
	//	not safari either
	// native scrollIntoView() causes FF3's whole window to scroll if there is no scroll bar 
	//	on the immediate parent
	// dont like browser sniffs implementations but sometimes you have to use it
	// #6146: IE scrollIntoView is broken
	// It's not enough just to scroll the menu node into view if
	// node.scrollIntoView hides part of the parent's scrollbar,
	// so just manage the parent scrollbar ourselves
	var parent = node.parentNode;
	var parentBottom = parent.scrollTop + dojo.marginBox(parent).h; //PORT was getBorderBox
	var nodeBottom = node.offsetTop + dojo.marginBox(node).h;
	if(parentBottom < nodeBottom){
		parent.scrollTop += (nodeBottom - parentBottom);
	}else if(parent.scrollTop > node.offsetTop){
		parent.scrollTop -= (parent.scrollTop - node.offsetTop);
	}
};

}

if(!dojo._hasResource["dijit._base.sniff"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dijit._base.sniff"] = true;
dojo.provide("dijit._base.sniff");

// ported from dojo.html.applyBrowserClass (style.js)

//	summary:
//		Applies pre-set class names based on browser & version to the
//		top-level HTML node.  Simply doing a require on this module will
//		establish this CSS.  Modified version of Morris' CSS hack.
(function(){
	var d = dojo;
	var ie = d.isIE;
	var opera = d.isOpera;
	var maj = Math.floor;
	var ff = d.isFF;
	var classes = {
		dj_ie: ie,
//		dj_ie55: ie == 5.5,
		dj_ie6: maj(ie) == 6,
		dj_ie7: maj(ie) == 7,
		dj_iequirks: ie && d.isQuirks,
// NOTE: Opera not supported by dijit
		dj_opera: opera,
		dj_opera8: maj(opera) == 8,
		dj_opera9: maj(opera) == 9,
		dj_khtml: d.isKhtml,
		dj_safari: d.isSafari,
		dj_gecko: d.isMozilla,
		dj_ff2: maj(ff) == 2
	}; // no dojo unsupported browsers

	for(var p in classes){
		if(classes[p]){
			var html = dojo.doc.documentElement; //TODO browser-specific DOM magic needed?
			if(html.className){
				html.className += " " + p;
			}else{
				html.className = p;
			}
		}
	}
})();

}

if(!dojo._hasResource["dijit._base.bidi"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dijit._base.bidi"] = true;
dojo.provide("dijit._base.bidi");

// summary: applies a class to the top of the document for right-to-left stylesheet rules

dojo.addOnLoad(function(){
	if(!dojo._isBodyLtr()){
		dojo.addClass(dojo.body(), "dijitRtl");
	}
});

}

if(!dojo._hasResource["dijit._base.typematic"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dijit._base.typematic"] = true;
dojo.provide("dijit._base.typematic");

dijit.typematic = {
	// summary:
	//	These functions are used to repetitively call a user specified callback
	//	method when a specific key or mouse click over a specific DOM node is
	//	held down for a specific amount of time.
	//	Only 1 such event is allowed to occur on the browser page at 1 time.

	_fireEventAndReload: function(){
		this._timer = null;
		this._callback(++this._count, this._node, this._evt);
		this._currentTimeout = (this._currentTimeout < 0) ? this._initialDelay : ((this._subsequentDelay > 1) ? this._subsequentDelay : Math.round(this._currentTimeout * this._subsequentDelay));
		this._timer = setTimeout(dojo.hitch(this, "_fireEventAndReload"), this._currentTimeout);
	},

	trigger: function(/*Event*/ evt, /* Object */ _this, /*DOMNode*/ node, /* Function */ callback, /* Object */ obj, /* Number */ subsequentDelay, /* Number */ initialDelay){
		// summary:
		//      Start a timed, repeating callback sequence.
		//      If already started, the function call is ignored.
		//      This method is not normally called by the user but can be
		//      when the normal listener code is insufficient.
		//	Parameters:
		//	evt: key or mouse event object to pass to the user callback
		//	_this: pointer to the user's widget space.
		//	node: the DOM node object to pass the the callback function
		//	callback: function to call until the sequence is stopped called with 3 parameters:
		//		count: integer representing number of repeated calls (0..n) with -1 indicating the iteration has stopped
		//		node: the DOM node object passed in
		//		evt: key or mouse event object
		//	obj: user space object used to uniquely identify each typematic sequence
		//	subsequentDelay: if > 1, the number of milliseconds until the 3->n events occur
		//		or else the fractional time multiplier for the next event's delay, default=0.9
		//	initialDelay: the number of milliseconds until the 2nd event occurs, default=500ms
		if(obj != this._obj){
			this.stop();
			this._initialDelay = initialDelay || 500;
			this._subsequentDelay = subsequentDelay || 0.90;
			this._obj = obj;
			this._evt = evt;
			this._node = node;
			this._currentTimeout = -1;
			this._count = -1;
			this._callback = dojo.hitch(_this, callback);
			this._fireEventAndReload();
		}
	},

	stop: function(){
		// summary:
		//	  Stop an ongoing timed, repeating callback sequence.
		if(this._timer){
			clearTimeout(this._timer);
			this._timer = null;
		}
		if(this._obj){
			this._callback(-1, this._node, this._evt);
			this._obj = null;
		}
	},

	addKeyListener: function(/*DOMNode*/ node, /*Object*/ keyObject, /*Object*/ _this, /*Function*/ callback, /*Number*/ subsequentDelay, /*Number*/ initialDelay){
		// summary: Start listening for a specific typematic key.
		//	keyObject: an object defining the key to listen for.
		//		key: (mandatory) the keyCode (number) or character (string) to listen for.
		//		ctrlKey: desired ctrl key state to initiate the calback sequence:
		//			pressed (true)
		//			released (false)
		//			either (unspecified)
		//		altKey: same as ctrlKey but for the alt key
		//		shiftKey: same as ctrlKey but for the shift key
		//	See the trigger method for other parameters.
		//	Returns an array of dojo.connect handles
		return [
			dojo.connect(node, "onkeypress", this, function(evt){
				if(evt.keyCode == keyObject.keyCode && (!keyObject.charCode || keyObject.charCode == evt.charCode) &&
				(keyObject.ctrlKey === undefined || keyObject.ctrlKey == evt.ctrlKey) &&
				(keyObject.altKey === undefined || keyObject.altKey == evt.ctrlKey) &&
				(keyObject.shiftKey === undefined || keyObject.shiftKey == evt.ctrlKey)){
					dojo.stopEvent(evt);
					dijit.typematic.trigger(keyObject, _this, node, callback, keyObject, subsequentDelay, initialDelay);
				}else if(dijit.typematic._obj == keyObject){
					dijit.typematic.stop();
				}
			}),
			dojo.connect(node, "onkeyup", this, function(evt){
				if(dijit.typematic._obj == keyObject){
					dijit.typematic.stop();
				}
			})
		];
	},

	addMouseListener: function(/*DOMNode*/ node, /*Object*/ _this, /*Function*/ callback, /*Number*/ subsequentDelay, /*Number*/ initialDelay){
		// summary: Start listening for a typematic mouse click.
		//	See the trigger method for other parameters.
		//	Returns an array of dojo.connect handles
		var dc = dojo.connect;
		return [
			dc(node, "mousedown", this, function(evt){
				dojo.stopEvent(evt);
				dijit.typematic.trigger(evt, _this, node, callback, node, subsequentDelay, initialDelay);
			}),
			dc(node, "mouseup", this, function(evt){
				dojo.stopEvent(evt);
				dijit.typematic.stop();
			}),
			dc(node, "mouseout", this, function(evt){
				dojo.stopEvent(evt);
				dijit.typematic.stop();
			}),
			dc(node, "mousemove", this, function(evt){
				dojo.stopEvent(evt);
			}),
			dc(node, "dblclick", this, function(evt){
				dojo.stopEvent(evt);
				if(dojo.isIE){
					dijit.typematic.trigger(evt, _this, node, callback, node, subsequentDelay, initialDelay);
					setTimeout(dojo.hitch(this, dijit.typematic.stop), 50);
				}
			})
		];
	},

	addListener: function(/*Node*/ mouseNode, /*Node*/ keyNode, /*Object*/ keyObject, /*Object*/ _this, /*Function*/ callback, /*Number*/ subsequentDelay, /*Number*/ initialDelay){
		// summary: Start listening for a specific typematic key and mouseclick.
		//	This is a thin wrapper to addKeyListener and addMouseListener.
		//	mouseNode: the DOM node object to listen on for mouse events.
		//	keyNode: the DOM node object to listen on for key events.
		//	See the addMouseListener and addKeyListener methods for other parameters.
		//	Returns an array of dojo.connect handles
		return this.addKeyListener(keyNode, keyObject, _this, callback, subsequentDelay, initialDelay).concat(
			this.addMouseListener(mouseNode, _this, callback, subsequentDelay, initialDelay));
	}
};

}

if(!dojo._hasResource["dijit._base.wai"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dijit._base.wai"] = true;
dojo.provide("dijit._base.wai");

dijit.wai = {
	onload: function(){
		// summary:
		//		Detects if we are in high-contrast mode or not

		// This must be a named function and not an anonymous
		// function, so that the widget parsing code can make sure it
		// registers its onload function after this function.
		// DO NOT USE "this" within this function.

		// create div for testing if high contrast mode is on or images are turned off
		var div = dojo.doc.createElement("div");
		div.id = "a11yTestNode";
		div.style.cssText = 'border: 1px solid;'
			+ 'border-color:red green;'
			+ 'position: absolute;'
			+ 'height: 5px;'
			+ 'top: -999px;'
			+ 'background-image: url("' + dojo.moduleUrl("dojo", "resources/blank.gif") + '");';
		dojo.body().appendChild(div);

		// test it
		var cs = dojo.getComputedStyle(div);
		if(cs){
			var bkImg = cs.backgroundImage;
			var needsA11y = (cs.borderTopColor==cs.borderRightColor) || (bkImg != null && (bkImg == "none" || bkImg == "url(invalid-url:)" ));
			dojo[needsA11y ? "addClass" : "removeClass"](dojo.body(), "dijit_a11y");
			dojo.body().removeChild(div);
		}
	}
};

// Test if computer is in high contrast mode.
// Make sure the a11y test runs first, before widgets are instantiated.
if(dojo.isIE || dojo.isMoz){	// NOTE: checking in Safari messes things up
	dojo._loaders.unshift(dijit.wai.onload);
}

dojo.mixin(dijit,
{
	hasWaiRole: function(/*Element*/ elem){
		// summary: Determines if an element has a role.
		// returns: true if elem has a role attribute and false if not.
		return elem.hasAttribute ? elem.hasAttribute("role") : !!elem.getAttribute("role");
	},

	getWaiRole: function(/*Element*/ elem){
		// summary: Gets the role for an element.
		// returns:
		//		The role of elem or an empty string if elem
		//		does not have a role.
		var value = elem.getAttribute("role");
		if(value){
			var prefixEnd = value.indexOf(":");
			return prefixEnd == -1 ? value : value.substring(prefixEnd+1);
		}else{
			return "";
		}
	},

	setWaiRole: function(/*Element*/ elem, /*String*/ role){
		// summary: Sets the role on an element.
		// description:
		//		On Firefox 2 and below, "wairole:" is
		//		prepended to the provided role value.
		elem.setAttribute("role", (dojo.isFF && dojo.isFF < 3) ? "wairole:" + role : role);
	},

	removeWaiRole: function(/*Element*/ elem){
		// summary: Removes the role from an element.
		elem.removeAttribute("role");
	},

	hasWaiState: function(/*Element*/ elem, /*String*/ state){
		// summary: Determines if an element has a given state.
		// description:
		//		On Firefox 2 and below, we check for an attribute in namespace
		//		"http://www.w3.org/2005/07/aaa" with a name of the given state.
		//		On all other browsers, we check for an attribute
		//		called "aria-"+state.
		// returns:
		//		true if elem has a value for the given state and
		//		false if it does not.
		if(dojo.isFF && dojo.isFF < 3){
			return elem.hasAttributeNS("http://www.w3.org/2005/07/aaa", state);
		}else{
			return elem.hasAttribute ? elem.hasAttribute("aria-"+state) : !!elem.getAttribute("aria-"+state);
		}
	},

	getWaiState: function(/*Element*/ elem, /*String*/ state){
		// summary: Gets the value of a state on an element.
		// description:
		//		On Firefox 2 and below, we check for an attribute in namespace
		//		"http://www.w3.org/2005/07/aaa" with a name of the given state.
		//		On all other browsers, we check for an attribute called
		//		"aria-"+state.
		// returns:
		//		The value of the requested state on elem
		//		or an empty string if elem has no value for state.
		if(dojo.isFF && dojo.isFF < 3){
			return elem.getAttributeNS("http://www.w3.org/2005/07/aaa", state);
		}else{
			var value = elem.getAttribute("aria-"+state);
			return value ? value : "";
		}
	},

	setWaiState: function(/*Element*/ elem, /*String*/ state, /*String*/ value){
		// summary: Sets a state on an element.
		// description:
		//		On Firefox 2 and below, we set an attribute in namespace
		//		"http://www.w3.org/2005/07/aaa" with a name of the given state.
		//		On all other browsers, we set an attribute called
		//		"aria-"+state.
		if(dojo.isFF && dojo.isFF < 3){
			elem.setAttributeNS("http://www.w3.org/2005/07/aaa",
				"aaa:"+state, value);
		}else{
			elem.setAttribute("aria-"+state, value);
		}
	},

	removeWaiState: function(/*Element*/ elem, /*String*/ state){
		// summary: Removes a state from an element.
		// description:
		//		On Firefox 2 and below, we remove the attribute in namespace
		//		"http://www.w3.org/2005/07/aaa" with a name of the given state.
		//		On all other browsers, we remove the attribute called
		//		"aria-"+state.
		if(dojo.isFF && dojo.isFF < 3){
			elem.removeAttributeNS("http://www.w3.org/2005/07/aaa", state);
		}else{
			elem.removeAttribute("aria-"+state);
		}
	}
});

}

if(!dojo._hasResource["dijit._base"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dijit._base"] = true;
dojo.provide("dijit._base");












//	FIXME: Find a better way of solving this bug!
if(dojo.isSafari){
	//	Ugly-ass hack to solve bug #5626 for 1.1; basically force Safari to re-layout.
	//	Note that we can't reliably use dojo.addOnLoad here because this bug is basically
	//		a timing / race condition; so instead we use window.onload.
	dojo.connect(window, "load", function(){
		window.resizeBy(1,0);
		setTimeout(function(){ window.resizeBy(-1,0); }, 10);
	});
}

}

if(!dojo._hasResource["dijit._Widget"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dijit._Widget"] = true;
dojo.provide("dijit._Widget");

dojo.require( "dijit._base" );

dojo.declare("dijit._Widget", null, {
	//	summary:
	//		The foundation of dijit widgets. 	
	//
	//	id: String
	//		a unique, opaque ID string that can be assigned by users or by the
	//		system. If the developer passes an ID which is known not to be
	//		unique, the specified ID is ignored and the system-generated ID is
	//		used instead.
	id: "",

	//	lang: String
	//		Rarely used.  Overrides the default Dojo locale used to render this widget,
	//		as defined by the [HTML LANG](http://www.w3.org/TR/html401/struct/dirlang.html#adef-lang) attribute.
	//		Value must be among the list of locales specified during by the Dojo bootstrap,
	//		formatted according to [RFC 3066](http://www.ietf.org/rfc/rfc3066.txt) (like en-us).
	lang: "",

	//	dir: String
	//		Unsupported by Dijit, but here for completeness.  Dijit only supports setting text direction on the
	//		entire document.
	//		Bi-directional support, as defined by the [HTML DIR](http://www.w3.org/TR/html401/struct/dirlang.html#adef-dir)
	//		attribute. Either left-to-right "ltr" or right-to-left "rtl".
	dir: "",

	// class: String
	//		HTML class attribute
	"class": "",

	// style: String
	//		HTML style attribute
	style: "",

	// title: String
	//		HTML title attribute
	title: "",

	// srcNodeRef: DomNode
	//		pointer to original dom node
	srcNodeRef: null,

	// domNode: DomNode
	//		this is our visible representation of the widget! Other DOM
	//		Nodes may by assigned to other properties, usually through the
	//		template system's dojoAttachPonit syntax, but the domNode
	//		property is the canonical "top level" node in widget UI.
	domNode: null,

	// attributeMap: Object
	//		A map of attributes and attachpoints -- typically standard HTML attributes -- to set
	//		on the widget's dom, at the "domNode" attach point, by default.
	//		Other node references can be specified as properties of 'this'
	attributeMap: {id:"", dir:"", lang:"", "class":"", style:"", title:""},  // TODO: add on* handlers?

	//////////// INITIALIZATION METHODS ///////////////////////////////////////
//TODOC: params and srcNodeRef need docs.  Is srcNodeRef optional?
//TODOC: summary needed for postscript
	postscript: function(/*Object?*/params, /*DomNode|String*/srcNodeRef){
		this.create(params, srcNodeRef);
	},

	create: function(/*Object?*/params, /*DomNode|String*/srcNodeRef){
		//	summary:
		//		Kick off the life-cycle of a widget
		//	description:
		//		To understand the process by which widgets are instantiated, it
		//		is critical to understand what other methods create calls and
		//		which of them you'll want to override. Of course, adventurous
		//		developers could override create entirely, but this should
		//		only be done as a last resort.
		//
		//		Below is a list of the methods that are called, in the order
		//		they are fired, along with notes about what they do and if/when
		//		you should over-ride them in your widget:
		//
		// * postMixInProperties:
		//	|	* a stub function that you can over-ride to modify
		//		variables that may have been naively assigned by
		//		mixInProperties
		// * widget is added to manager object here
		// * buildRendering:
		//	|	* Subclasses use this method to handle all UI initialization
		//		Sets this.domNode.  Templated widgets do this automatically
		//		and otherwise it just uses the source dom node.
		// * postCreate:
		//	|	* a stub function that you can over-ride to modify take
		//		actions once the widget has been placed in the UI

		// store pointer to original dom tree
		this.srcNodeRef = dojo.byId(srcNodeRef);

		// For garbage collection.  An array of handles returned by Widget.connect()
		// Each handle returned from Widget.connect() is an array of handles from dojo.connect()
		this._connects=[];

		// _attaches: String[]
		// 		names of all our dojoAttachPoint variables
		this._attaches=[];

		//mixin our passed parameters
		if(this.srcNodeRef && (typeof this.srcNodeRef.id == "string")){ this.id = this.srcNodeRef.id; }
		if(params){
			this.params = params;
			dojo.mixin(this,params);
		}
		this.postMixInProperties();

		// generate an id for the widget if one wasn't specified
		// (be sure to do this before buildRendering() because that function might
		// expect the id to be there.
		if(!this.id){
			this.id=dijit.getUniqueId(this.declaredClass.replace(/\./g,"_"));
		}
		dijit.registry.add(this);

		this.buildRendering();

		// Copy attributes listed in attributeMap into the [newly created] DOM for the widget.
		// The placement of these attributes is according to the property mapping in attributeMap.
		// Note special handling for 'style' and 'class' attributes which are lists and can
		// have elements from both old and new structures, and some attributes like "type"
		// cannot be processed this way as they are not mutable.
		if(this.domNode){
			for(var attr in this.attributeMap){
				var value = this[attr];
				if(typeof value != "object" && ((value !== "" && value !== false) || (params && params[attr]))){
					this.setAttribute(attr, value);
				}
			}
		}

		if(this.domNode){
			this.domNode.setAttribute("widgetId", this.id);
		}
		this.postCreate();

		// If srcNodeRef has been processed and removed from the DOM (e.g. TemplatedWidget) then delete it to allow GC.
		if(this.srcNodeRef && !this.srcNodeRef.parentNode){
			delete this.srcNodeRef;
		}	
	},

	postMixInProperties: function(){
		// summary
		//	Called after the parameters to the widget have been read-in,
		//	but before the widget template is instantiated.
		//	Especially useful to set properties that are referenced in the widget template.
	},

	buildRendering: function(){
		// summary:
		//		Construct the UI for this widget, setting this.domNode.
		//		Most widgets will mixin TemplatedWidget, which overrides this method.
		this.domNode = this.srcNodeRef || dojo.doc.createElement('div');
	},

	postCreate: function(){
		// summary:
		//		Called after a widget's dom has been setup
	},

	startup: function(){
		// summary:
		//		Called after a widget's children, and other widgets on the page, have been created.
		//		Provides an opportunity to manipulate any children before they are displayed.
		//		This is useful for composite widgets that need to control or layout sub-widgets.
		//		Many layout widgets can use this as a wiring phase.
		this._started = true;
	},

	//////////// DESTROY FUNCTIONS ////////////////////////////////

	destroyRecursive: function(/*Boolean*/ finalize){
		// summary:
		// 		Destroy this widget and it's descendants. This is the generic
		// 		"destructor" function that all widget users should call to
		// 		cleanly discard with a widget. Once a widget is destroyed, it's
		// 		removed from the manager object.
		// finalize: Boolean
		//		is this function being called part of global environment
		//		tear-down?

		this.destroyDescendants();
		this.destroy();
	},

	destroy: function(/*Boolean*/ finalize){
		// summary:
		// 		Destroy this widget, but not its descendants
		// finalize: Boolean
		//		is this function being called part of global environment
		//		tear-down?

		this.uninitialize();
		dojo.forEach(this._connects, function(array){
			dojo.forEach(array, dojo.disconnect);
		});

		// destroy widgets created as part of template, etc.
		dojo.forEach(this._supportingWidgets || [], function(w){ w.destroy(); });
		
		this.destroyRendering(finalize);
		dijit.registry.remove(this.id);
	},

	destroyRendering: function(/*Boolean*/ finalize){
		// summary:
		//		Destroys the DOM nodes associated with this widget
		// finalize: Boolean
		//		is this function being called part of global environment
		//		tear-down?

		if(this.bgIframe){
			this.bgIframe.destroy();
			delete this.bgIframe;
		}

		if(this.domNode){
			dojo._destroyElement(this.domNode);
			delete this.domNode;
		}

		if(this.srcNodeRef){
			dojo._destroyElement(this.srcNodeRef);
			delete this.srcNodeRef;
		}
	},

	destroyDescendants: function(){
		// summary:
		//		Recursively destroy the children of this widget and their
		//		descendants.

		// TODO: should I destroy in the reverse order, to go bottom up?
		dojo.forEach(this.getDescendants(), function(widget){ widget.destroy(); });
	},

	uninitialize: function(){
		// summary:
		//		stub function. Override to implement custom widget tear-down
		//		behavior.
		return false;
	},

	////////////////// MISCELLANEOUS METHODS ///////////////////

	onFocus: function(){
		// summary:
		//		stub function. Override or connect to this method to receive
		//		notifications for when the widget moves into focus.
	},

	onBlur: function(){
		// summary:
		//		stub function. Override or connect to this method to receive
		//		notifications for when the widget moves out of focus.
	},

	_onFocus: function(e){
		this.onFocus();
	},

	_onBlur: function(){
		this.onBlur();
	},

	setAttribute: function(/*String*/ attr, /*anything*/ value){
		// summary
		//		Set native HTML attributes reflected in the widget,
		//		such as readOnly, disabled, and maxLength in TextBox widgets.
		// description
		//		In general, a widget's "value" is controlled via setValue()/getValue(), 
		//		rather than this method.  The exception is for widgets where the
		//		end user can't adjust the value, such as Button and CheckBox;
		//		in the unusual case that you want to change the value attribute of
		//		those widgets, use setAttribute().
		var mapNode = this[this.attributeMap[attr]||'domNode'];
		this[attr] = value;
		switch(attr){
			case "class":
				dojo.addClass(mapNode, value);
				break;
			case "style":
				if(mapNode.style.cssText){
					mapNode.style.cssText += "; " + value;// FIXME: Opera
				}else{
					mapNode.style.cssText = value;
				}
				break;
			default:
				if(/^on[A-Z]/.test(attr)){ // eg. onSubmit needs to be onsubmit
					attr = attr.toLowerCase();
				}
				if(typeof value == "function"){ // functions execute in the context of the widget
					value = dojo.hitch(this, value);
				}
				dojo.attr(mapNode, attr, value);
		}
	},

	toString: function(){
		// summary:
		//		returns a string that represents the widget. When a widget is
		//		cast to a string, this method will be used to generate the
		//		output. Currently, it does not implement any sort of reversable
		//		serialization.
		return '[Widget ' + this.declaredClass + ', ' + (this.id || 'NO ID') + ']'; // String
	},

	getDescendants: function(){
		// summary:
		//	Returns all the widgets that contained by this, i.e., all widgets underneath this.containerNode.
		if(this.containerNode){
			var list= dojo.query('[widgetId]', this.containerNode);
			return list.map(dijit.byNode);		// Array
		}else{
			return [];
		}
	},

//TODOC
	nodesWithKeyClick: ["input", "button"],

	connect: function(
			/*Object|null*/ obj,
			/*String*/ event,
			/*String|Function*/ method){
		//	summary:
		//		Connects specified obj/event to specified method of this object
		//		and registers for disconnect() on widget destroy.
		//		Special event: "ondijitclick" triggers on a click or enter-down or space-up
		//		Similar to dojo.connect() but takes three arguments rather than four.
		var handles =[];
		if(event == "ondijitclick"){
			// add key based click activation for unsupported nodes.
			if(!this.nodesWithKeyClick[obj.nodeName]){
				handles.push(dojo.connect(obj, "onkeydown", this,
					function(e){
						if(e.keyCode == dojo.keys.ENTER){
							return (dojo.isString(method))?
								this[method](e) : method.call(this, e);
						}else if(e.keyCode == dojo.keys.SPACE){
							// stop space down as it causes IE to scroll
							// the browser window
							dojo.stopEvent(e);
						}
			 		}));
				handles.push(dojo.connect(obj, "onkeyup", this,
					function(e){
						if(e.keyCode == dojo.keys.SPACE){
							return dojo.isString(method) ?
								this[method](e) : method.call(this, e);
						}
			 		}));
			}
			event = "onclick";
		}
		handles.push(dojo.connect(obj, event, this, method));

		// return handles for FormElement and ComboBox
		this._connects.push(handles);
		return handles;
	},

	disconnect: function(/*Object*/ handles){
		// summary:
		//		Disconnects handle created by this.connect.
		//		Also removes handle from this widget's list of connects
		for(var i=0; i<this._connects.length; i++){
			if(this._connects[i]==handles){
				dojo.forEach(handles, dojo.disconnect);
				this._connects.splice(i, 1);
				return;
			}
		}
	},

	isLeftToRight: function(){
		// summary:
		//		Checks the DOM to for the text direction for bi-directional support
		// description:
		//		This method cannot be used during widget construction because the widget
		//		must first be connected to the DOM tree.  Parent nodes are searched for the
		//		'dir' attribute until one is found, otherwise left to right mode is assumed.
		//		See HTML spec, DIR attribute for more information.

		if(!("_ltr" in this)){
			this._ltr = dojo.getComputedStyle(this.domNode).direction != "rtl";
		}
		return this._ltr; //Boolean
	},

	isFocusable: function(){
		// summary:
		//		Return true if this widget can currently be focused
		//		and false if not
		return this.focus && (dojo.style(this.domNode, "display") != "none");
	}
});

}

if(!dojo._hasResource["dojox.gfx.matrix"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojox.gfx.matrix"] = true;
dojo.provide("dojox.gfx.matrix");

(function(){
	var m = dojox.gfx.matrix;
	
	// candidates for dojox.math:
	m._degToRad = function(degree){ return Math.PI * degree / 180; };
	m._radToDeg = function(radian){ return radian / Math.PI * 180; };

	m.Matrix2D = function(arg){
		// summary: a 2D matrix object
		// description: Normalizes a 2D matrix-like object. If arrays is passed, 
		//		all objects of the array are normalized and multiplied sequentially.
		// arg: Object
		//		a 2D matrix-like object, a number, or an array of such objects
		if(arg){
			if(typeof arg == "number"){
				this.xx = this.yy = arg;
			}else if(arg instanceof Array){
				if(arg.length > 0){
					var matrix = m.normalize(arg[0]);
					// combine matrices
					for(var i = 1; i < arg.length; ++i){
						var l = matrix, r = dojox.gfx.matrix.normalize(arg[i]);
						matrix = new m.Matrix2D();
						matrix.xx = l.xx * r.xx + l.xy * r.yx;
						matrix.xy = l.xx * r.xy + l.xy * r.yy;
						matrix.yx = l.yx * r.xx + l.yy * r.yx;
						matrix.yy = l.yx * r.xy + l.yy * r.yy;
						matrix.dx = l.xx * r.dx + l.xy * r.dy + l.dx;
						matrix.dy = l.yx * r.dx + l.yy * r.dy + l.dy;
					}
					dojo.mixin(this, matrix);
				}
			}else{
				dojo.mixin(this, arg);
			}
		}
	};

	// the default (identity) matrix, which is used to fill in missing values
	dojo.extend(m.Matrix2D, {xx: 1, xy: 0, yx: 0, yy: 1, dx: 0, dy: 0});

	dojo.mixin(m, {
		// summary: class constants, and methods of dojox.gfx.matrix
		
		// matrix constants
		
		// identity: dojox.gfx.matrix.Matrix2D
		//		an identity matrix constant: identity * (x, y) == (x, y)
		identity: new m.Matrix2D(),
		
		// flipX: dojox.gfx.matrix.Matrix2D
		//		a matrix, which reflects points at x = 0 line: flipX * (x, y) == (-x, y)
		flipX:    new m.Matrix2D({xx: -1}),
		
		// flipY: dojox.gfx.matrix.Matrix2D
		//		a matrix, which reflects points at y = 0 line: flipY * (x, y) == (x, -y)
		flipY:    new m.Matrix2D({yy: -1}),
		
		// flipXY: dojox.gfx.matrix.Matrix2D
		//		a matrix, which reflects points at the origin of coordinates: flipXY * (x, y) == (-x, -y)
		flipXY:   new m.Matrix2D({xx: -1, yy: -1}),
		
		// matrix creators
		
		translate: function(a, b){
			// summary: forms a translation matrix
			// description: The resulting matrix is used to translate (move) points by specified offsets.
			// a: Number: an x coordinate value
			// b: Number: a y coordinate value
			if(arguments.length > 1){
				return new m.Matrix2D({dx: a, dy: b}); // dojox.gfx.matrix.Matrix2D
			}
			// branch
			// a: dojox.gfx.Point: a point-like object, which specifies offsets for both dimensions
			// b: null
			return new m.Matrix2D({dx: a.x, dy: a.y}); // dojox.gfx.matrix.Matrix2D
		},
		scale: function(a, b){
			// summary: forms a scaling matrix
			// description: The resulting matrix is used to scale (magnify) points by specified offsets.
			// a: Number: a scaling factor used for the x coordinate
			// b: Number: a scaling factor used for the y coordinate
			if(arguments.length > 1){
				return new m.Matrix2D({xx: a, yy: b}); // dojox.gfx.matrix.Matrix2D
			}
			if(typeof a == "number"){
				// branch
				// a: Number: a uniform scaling factor used for the both coordinates
				// b: null
				return new m.Matrix2D({xx: a, yy: a}); // dojox.gfx.matrix.Matrix2D
			}
			// branch
			// a: dojox.gfx.Point: a point-like object, which specifies scale factors for both dimensions
			// b: null
			return new m.Matrix2D({xx: a.x, yy: a.y}); // dojox.gfx.matrix.Matrix2D
		},
		rotate: function(angle){
			// summary: forms a rotating matrix
			// description: The resulting matrix is used to rotate points 
			//		around the origin of coordinates (0, 0) by specified angle.
			// angle: Number: an angle of rotation in radians (>0 for CW)
			var c = Math.cos(angle);
			var s = Math.sin(angle);
			return new m.Matrix2D({xx: c, xy: -s, yx: s, yy: c}); // dojox.gfx.matrix.Matrix2D
		},
		rotateg: function(degree){
			// summary: forms a rotating matrix
			// description: The resulting matrix is used to rotate points
			//		around the origin of coordinates (0, 0) by specified degree.
			//		See dojox.gfx.matrix.rotate() for comparison.
			// degree: Number: an angle of rotation in degrees (>0 for CW)
			return m.rotate(m._degToRad(degree)); // dojox.gfx.matrix.Matrix2D
		},
		skewX: function(angle) {
			// summary: forms an x skewing matrix
			// description: The resulting matrix is used to skew points in the x dimension
			//		around the origin of coordinates (0, 0) by specified angle.
			// angle: Number: an skewing angle in radians
			return new m.Matrix2D({xy: -Math.tan(angle)}); // dojox.gfx.matrix.Matrix2D
		},
		skewXg: function(degree){
			// summary: forms an x skewing matrix
			// description: The resulting matrix is used to skew points in the x dimension
			//		around the origin of coordinates (0, 0) by specified degree.
			//		See dojox.gfx.matrix.skewX() for comparison.
			// degree: Number: an skewing angle in degrees
			return m.skewX(m._degToRad(degree)); // dojox.gfx.matrix.Matrix2D
		},
		skewY: function(angle){
			// summary: forms a y skewing matrix
			// description: The resulting matrix is used to skew points in the y dimension
			//		around the origin of coordinates (0, 0) by specified angle.
			// angle: Number: an skewing angle in radians
			return new m.Matrix2D({yx: Math.tan(angle)}); // dojox.gfx.matrix.Matrix2D
		},
		skewYg: function(degree){
			// summary: forms a y skewing matrix
			// description: The resulting matrix is used to skew points in the y dimension
			//		around the origin of coordinates (0, 0) by specified degree.
			//		See dojox.gfx.matrix.skewY() for comparison.
			// degree: Number: an skewing angle in degrees
			return m.skewY(m._degToRad(degree)); // dojox.gfx.matrix.Matrix2D
		},
		reflect: function(a, b){
			// summary: forms a reflection matrix
			// description: The resulting matrix is used to reflect points around a vector, 
			//		which goes through the origin.
			// a: dojox.gfx.Point: a point-like object, which specifies a vector of reflection
			// b: null
			if(arguments.length == 1){
				b = a.y;
				a = a.x;
			}
			// branch
			// a: Number: an x coordinate value
			// b: Number: a y coordinate value
			
			// make a unit vector
			var a2 = a * a, b2 = b * b, n2 = a2 + b2, xy = 2 * a * b / n2;
			return new m.Matrix2D({xx: 2 * a2 / n2 - 1, xy: xy, yx: xy, yy: 2 * b2 / n2 - 1}); // dojox.gfx.matrix.Matrix2D
		},
		project: function(a, b){
			// summary: forms an orthogonal projection matrix
			// description: The resulting matrix is used to project points orthogonally on a vector, 
			//		which goes through the origin.
			// a: dojox.gfx.Point: a point-like object, which specifies a vector of projection
			// b: null
			if(arguments.length == 1){
				b = a.y;
				a = a.x;
			}
			// branch
			// a: Number: an x coordinate value
			// b: Number: a y coordinate value
			
			// make a unit vector
			var a2 = a * a, b2 = b * b, n2 = a2 + b2, xy = a * b / n2;
			return new m.Matrix2D({xx: a2 / n2, xy: xy, yx: xy, yy: b2 / n2}); // dojox.gfx.matrix.Matrix2D
		},
		
		// ensure matrix 2D conformance
		normalize: function(matrix){
			// summary: converts an object to a matrix, if necessary
			// description: Converts any 2D matrix-like object or an array of
			//		such objects to a valid dojox.gfx.matrix.Matrix2D object.
			// matrix: Object: an object, which is converted to a matrix, if necessary
			return (matrix instanceof m.Matrix2D) ? matrix : new m.Matrix2D(matrix); // dojox.gfx.matrix.Matrix2D
		},
		
		// common operations
		
		clone: function(matrix){
			// summary: creates a copy of a 2D matrix
			// matrix: dojox.gfx.matrix.Matrix2D: a 2D matrix-like object to be cloned
			var obj = new m.Matrix2D();
			for(var i in matrix){
				if(typeof(matrix[i]) == "number" && typeof(obj[i]) == "number" && obj[i] != matrix[i]) obj[i] = matrix[i];
			}
			return obj; // dojox.gfx.matrix.Matrix2D
		},
		invert: function(matrix){
			// summary: inverts a 2D matrix
			// matrix: dojox.gfx.matrix.Matrix2D: a 2D matrix-like object to be inverted
			var M = m.normalize(matrix),
				D = M.xx * M.yy - M.xy * M.yx,
				M = new m.Matrix2D({
					xx: M.yy/D, xy: -M.xy/D, 
					yx: -M.yx/D, yy: M.xx/D, 
					dx: (M.xy * M.dy - M.yy * M.dx) / D, 
					dy: (M.yx * M.dx - M.xx * M.dy) / D
				});
			return M; // dojox.gfx.matrix.Matrix2D
		},
		_multiplyPoint: function(matrix, x, y){
			// summary: applies a matrix to a point
			// matrix: dojox.gfx.matrix.Matrix2D: a 2D matrix object to be applied
			// x: Number: an x coordinate of a point
			// y: Number: a y coordinate of a point
			return {x: matrix.xx * x + matrix.xy * y + matrix.dx, y: matrix.yx * x + matrix.yy * y + matrix.dy}; // dojox.gfx.Point
		},
		multiplyPoint: function(matrix, /* Number||Point */ a, /* Number, optional */ b){
			// summary: applies a matrix to a point
			// matrix: dojox.gfx.matrix.Matrix2D: a 2D matrix object to be applied
			// a: Number: an x coordinate of a point
			// b: Number: a y coordinate of a point
			var M = m.normalize(matrix);
			if(typeof a == "number" && typeof b == "number"){
				return m._multiplyPoint(M, a, b); // dojox.gfx.Point
			}
			// branch
			// matrix: dojox.gfx.matrix.Matrix2D: a 2D matrix object to be applied
			// a: dojox.gfx.Point: a point
			// b: null
			return m._multiplyPoint(M, a.x, a.y); // dojox.gfx.Point
		},
		multiply: function(matrix){
			// summary: combines matrices by multiplying them sequentially in the given order
			// matrix: dojox.gfx.matrix.Matrix2D...: a 2D matrix-like object, 
			//		all subsequent arguments are matrix-like objects too
			var M = m.normalize(matrix);
			// combine matrices
			for(var i = 1; i < arguments.length; ++i){
				var l = M, r = m.normalize(arguments[i]);
				M = new m.Matrix2D();
				M.xx = l.xx * r.xx + l.xy * r.yx;
				M.xy = l.xx * r.xy + l.xy * r.yy;
				M.yx = l.yx * r.xx + l.yy * r.yx;
				M.yy = l.yx * r.xy + l.yy * r.yy;
				M.dx = l.xx * r.dx + l.xy * r.dy + l.dx;
				M.dy = l.yx * r.dx + l.yy * r.dy + l.dy;
			}
			return M; // dojox.gfx.matrix.Matrix2D
		},
		
		// high level operations
		
		_sandwich: function(matrix, x, y){
			// summary: applies a matrix at a centrtal point
			// matrix: dojox.gfx.matrix.Matrix2D: a 2D matrix-like object, which is applied at a central point
			// x: Number: an x component of the central point
			// y: Number: a y component of the central point
			return m.multiply(m.translate(x, y), matrix, m.translate(-x, -y)); // dojox.gfx.matrix.Matrix2D
		},
		scaleAt: function(a, b, c, d){
			// summary: scales a picture using a specified point as a center of scaling
			// description: Compare with dojox.gfx.matrix.scale().
			// a: Number: a scaling factor used for the x coordinate
			// b: Number: a scaling factor used for the y coordinate
			// c: Number: an x component of a central point
			// d: Number: a y component of a central point
			
			// accepts several signatures:
			//	1) uniform scale factor, Point
			//	2) uniform scale factor, x, y
			//	3) x scale, y scale, Point
			//	4) x scale, y scale, x, y
			
			switch(arguments.length){
				case 4:
					// a and b are scale factor components, c and d are components of a point
					return m._sandwich(m.scale(a, b), c, d); // dojox.gfx.matrix.Matrix2D
				case 3:
					if(typeof c == "number"){
						// branch
						// a: Number: a uniform scaling factor used for both coordinates
						// b: Number: an x component of a central point
						// c: Number: a y component of a central point
						// d: null
						return m._sandwich(m.scale(a), b, c); // dojox.gfx.matrix.Matrix2D
					}
					// branch
					// a: Number: a scaling factor used for the x coordinate
					// b: Number: a scaling factor used for the y coordinate
					// c: dojox.gfx.Point: a central point
					// d: null
					return m._sandwich(m.scale(a, b), c.x, c.y); // dojox.gfx.matrix.Matrix2D
			}
			// branch
			// a: Number: a uniform scaling factor used for both coordinates
			// b: dojox.gfx.Point: a central point
			// c: null
			// d: null
			return m._sandwich(m.scale(a), b.x, b.y); // dojox.gfx.matrix.Matrix2D
		},
		rotateAt: function(angle, a, b){
			// summary: rotates a picture using a specified point as a center of rotation
			// description: Compare with dojox.gfx.matrix.rotate().
			// angle: Number: an angle of rotation in radians (>0 for CW)
			// a: Number: an x component of a central point
			// b: Number: a y component of a central point
			
			// accepts several signatures:
			//	1) rotation angle in radians, Point
			//	2) rotation angle in radians, x, y
			
			if(arguments.length > 2){
				return m._sandwich(m.rotate(angle), a, b); // dojox.gfx.matrix.Matrix2D
			}
			
			// branch
			// angle: Number: an angle of rotation in radians (>0 for CCW)
			// a: dojox.gfx.Point: a central point
			// b: null
			return m._sandwich(m.rotate(angle), a.x, a.y); // dojox.gfx.matrix.Matrix2D
		},
		rotategAt: function(degree, a, b){
			// summary: rotates a picture using a specified point as a center of rotation
			// description: Compare with dojox.gfx.matrix.rotateg().
			// degree: Number: an angle of rotation in degrees (>0 for CW)
			// a: Number: an x component of a central point
			// b: Number: a y component of a central point
			
			// accepts several signatures:
			//	1) rotation angle in degrees, Point
			//	2) rotation angle in degrees, x, y
			
			if(arguments.length > 2){
				return m._sandwich(m.rotateg(degree), a, b); // dojox.gfx.matrix.Matrix2D
			}

			// branch
			// degree: Number: an angle of rotation in degrees (>0 for CCW)
			// a: dojox.gfx.Point: a central point
			// b: null
			return m._sandwich(m.rotateg(degree), a.x, a.y); // dojox.gfx.matrix.Matrix2D
		},
		skewXAt: function(angle, a, b){
			// summary: skews a picture along the x axis using a specified point as a center of skewing
			// description: Compare with dojox.gfx.matrix.skewX().
			// angle: Number: an skewing angle in radians
			// a: Number: an x component of a central point
			// b: Number: a y component of a central point
			
			// accepts several signatures:
			//	1) skew angle in radians, Point
			//	2) skew angle in radians, x, y
			
			if(arguments.length > 2){
				return m._sandwich(m.skewX(angle), a, b); // dojox.gfx.matrix.Matrix2D
			}

			// branch
			// angle: Number: an skewing angle in radians
			// a: dojox.gfx.Point: a central point
			// b: null
			return m._sandwich(m.skewX(angle), a.x, a.y); // dojox.gfx.matrix.Matrix2D
		},
		skewXgAt: function(degree, a, b){
			// summary: skews a picture along the x axis using a specified point as a center of skewing
			// description: Compare with dojox.gfx.matrix.skewXg().
			// degree: Number: an skewing angle in degrees
			// a: Number: an x component of a central point
			// b: Number: a y component of a central point
			
			// accepts several signatures:
			//	1) skew angle in degrees, Point
			//	2) skew angle in degrees, x, y

			if(arguments.length > 2){
				return m._sandwich(m.skewXg(degree), a, b); // dojox.gfx.matrix.Matrix2D
			}

			// branch
			// degree: Number: an skewing angle in degrees
			// a: dojox.gfx.Point: a central point
			// b: null
			return m._sandwich(m.skewXg(degree), a.x, a.y); // dojox.gfx.matrix.Matrix2D
		},
		skewYAt: function(angle, a, b){
			// summary: skews a picture along the y axis using a specified point as a center of skewing
			// description: Compare with dojox.gfx.matrix.skewY().
			// angle: Number: an skewing angle in radians
			// a: Number: an x component of a central point
			// b: Number: a y component of a central point
			
			// accepts several signatures:
			//	1) skew angle in radians, Point
			//	2) skew angle in radians, x, y
			
			if(arguments.length > 2){
				return m._sandwich(m.skewY(angle), a, b); // dojox.gfx.matrix.Matrix2D
			}

			// branch
			// angle: Number: an skewing angle in radians
			// a: dojox.gfx.Point: a central point
			// b: null
			return m._sandwich(m.skewY(angle), a.x, a.y); // dojox.gfx.matrix.Matrix2D
		},
		skewYgAt: function(/* Number */ degree, /* Number||Point */ a, /* Number, optional */ b){
			// summary: skews a picture along the y axis using a specified point as a center of skewing
			// description: Compare with dojox.gfx.matrix.skewYg().
			// degree: Number: an skewing angle in degrees
			// a: Number: an x component of a central point
			// b: Number: a y component of a central point
			
			// accepts several signatures:
			//	1) skew angle in degrees, Point
			//	2) skew angle in degrees, x, y

			if(arguments.length > 2){
				return m._sandwich(m.skewYg(degree), a, b); // dojox.gfx.matrix.Matrix2D
			}

			// branch
			// degree: Number: an skewing angle in degrees
			// a: dojox.gfx.Point: a central point
			// b: null
			return m._sandwich(m.skewYg(degree), a.x, a.y); // dojox.gfx.matrix.Matrix2D
		}
		
		//TODO: rect-to-rect mapping, scale-to-fit (isotropic and anisotropic versions)
		
	});
})();

// propagate Matrix2D up
dojox.gfx.Matrix2D = dojox.gfx.matrix.Matrix2D;

}

if(!dojo._hasResource["dojox.gfx._base"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojox.gfx._base"] = true;
dojo.provide("dojox.gfx._base");

(function(){
	var g = dojox.gfx, b = g._base;
	
	// candidates for dojox.style (work on VML and SVG nodes)
	g._hasClass = function(/*DomNode*/node, /*String*/classStr){
		//	summary:
		//		Returns whether or not the specified classes are a portion of the
		//		class list currently applied to the node. 
		// return (new RegExp('(^|\\s+)'+classStr+'(\\s+|$)')).test(node.className)	// Boolean
		return ((" "+node.getAttribute("className")+" ").indexOf(" "+classStr+" ") >= 0);  // Boolean
	}
	g._addClass = function(/*DomNode*/node, /*String*/classStr){
		//	summary:
		//		Adds the specified classes to the end of the class list on the
		//		passed node.
		var cls = node.getAttribute("className");
		if((" "+cls+" ").indexOf(" "+classStr+" ") < 0){
			node.setAttribute("className", cls + (cls ? ' ' : '') + classStr);
		}
	}
	g._removeClass = function(/*DomNode*/node, /*String*/classStr){
		//	summary: Removes classes from node.
		node.setAttribute("className", node.getAttribute("className").replace(new RegExp('(^|\\s+)'+classStr+'(\\s+|$)'), "$1$2"));
	}
	
	
	// candidate for dojox.html.metrics (dynamic font resize handler is not implemented here)
	
	//	derived from Morris John's emResized measurer
	b._getFontMeasurements = function(){
		//	summary
		//	Returns an object that has pixel equivilents of standard font size values.
		var heights = {
			'1em':0, '1ex':0, '100%':0, '12pt':0, '16px':0, 'xx-small':0, 'x-small':0,
			'small':0, 'medium':0, 'large':0, 'x-large':0, 'xx-large':0
		};
	
		if(dojo.isIE){
			//	we do a font-size fix if and only if one isn't applied already.
			//	NOTE: If someone set the fontSize on the HTML Element, this will kill it.
			dojo.doc.documentElement.style.fontSize="100%";
		}
	
		//	set up the measuring node.
		var div=dojo.doc.createElement("div");
		div.style.position="absolute";
		div.style.left="-100px";
		div.style.top="0";
		div.style.width="30px";
		div.style.height="1000em";
		div.style.border="0";
		div.style.margin="0";
		div.style.padding="0";
		div.style.outline="0";
		div.style.lineHeight="1";
		div.style.overflow="hidden";
		dojo.body().appendChild(div);
	
		//	do the measurements.
		for(var p in heights){
			div.style.fontSize = p;
			heights[p] = Math.round(div.offsetHeight * 12/16) * 16/12 / 1000;
		}
		
		dojo.body().removeChild(div);
		div = null;
		return heights; 	//	object
	};
	
	var fontMeasurements = null;
	
	b._getCachedFontMeasurements = function(recalculate){
		if(recalculate || !fontMeasurements){
			fontMeasurements = b._getFontMeasurements();
		}
		return fontMeasurements;
	};
	
	// candidate for dojox.html.metrics
	
	var measuringNode = null, empty = {};
	b._getTextBox = function(/* String */ text, /* Object */ style, /* String? */ className){
		var m;
		if(!measuringNode){
			m = measuringNode = dojo.doc.createElement("div");
			m.style.position = "absolute";
			m.style.left = "-10000px";
			m.style.top = "0";
			dojo.body().appendChild(m);
		}else{
			m = measuringNode;
		}
		// reset styles
		m.className = "";
		m.style.border = "0";
		m.style.margin = "0";
		m.style.padding = "0";
		m.style.outline = "0";
		// set new style
		if(arguments.length > 1 && style){
			for(var i in style){
				if(i in empty){ continue; }
				m.style[i] = style[i];
			}
		}
		// set classes
		if(arguments.length > 2 && className){
			m.className = className;
		}
		// take a measure
		m.innerHTML = text;
		return dojo.marginBox(m);
	};
	
	// candidate for dojo.dom
	
	var uniqueId = 0;
	b._getUniqueId = function(){
		// summary: returns a unique string for use with any DOM element
		var id;
		do{
			id = dojo._scopeName + "Unique" + (++uniqueId);
		}while(dojo.byId(id));
		return id;
	};
})();

dojo.mixin(dojox.gfx, {
	// summary: defines constants, prototypes, and utility functions
	
	// default shapes, which are used to fill in missing parameters
	defaultPath:     {type: "path",     path: ""},
	defaultPolyline: {type: "polyline", points: []},
	defaultRect:     {type: "rect",     x: 0, y: 0, width: 100, height: 100, r: 0},
	defaultEllipse:  {type: "ellipse",  cx: 0, cy: 0, rx: 200, ry: 100},
	defaultCircle:   {type: "circle",   cx: 0, cy: 0, r: 100},
	defaultLine:     {type: "line",     x1: 0, y1: 0, x2: 100, y2: 100},
	defaultImage:    {type: "image",    x: 0, y: 0, width: 0, height: 0, src: ""},
	defaultText:     {type: "text",     x: 0, y: 0, text: "",
		align: "start", decoration: "none", rotated: false, kerning: true },
	defaultTextPath: {type: "textpath", text: "",
		align: "start", decoration: "none", rotated: false, kerning: true },

	// default geometric attributes
	defaultStroke: {type: "stroke", color: "black", style: "solid", width: 1, cap: "butt", join: 4},
	defaultLinearGradient: {type: "linear", x1: 0, y1: 0, x2: 100, y2: 100, 
		colors: [{offset: 0, color: "black"}, {offset: 1, color: "white"}]},
	defaultRadialGradient: {type: "radial", cx: 0, cy: 0, r: 100, 
		colors: [{offset: 0, color: "black"}, {offset: 1, color: "white"}]},
	defaultPattern: {type: "pattern", x: 0, y: 0, width: 0, height: 0, src: ""},
	defaultFont: {type: "font", style: "normal", variant: "normal", weight: "normal", 
		size: "10pt", family: "serif"},

	normalizeColor: function(/*Color*/ color){
		// summary: converts any legal color representation to normalized dojo.Color object
		return (color instanceof dojo.Color) ? color : new dojo.Color(color); // dojo.Color
	},
	normalizeParameters: function(existed, update){
		// summary: updates an existing object with properties from an "update" object
		// existed: Object: the "target" object to be updated
		// update:  Object: the "update" object, whose properties will be used to update the existed object
		if(update){
			var empty = {};
			for(var x in existed){
				if(x in update && !(x in empty)){
					existed[x] = update[x];
				}
			}
		}
		return existed;	// Object
	},
	makeParameters: function(defaults, update){
		// summary: copies the original object, and all copied properties from the "update" object
		// defaults: Object: the object to be cloned before updating
		// update:   Object: the object, which properties are to be cloned during updating
		if(!update) return dojo.clone(defaults);
		var result = {};
		for(var i in defaults){
			if(!(i in result)){
				result[i] = dojo.clone((i in update) ? update[i] : defaults[i]);
			}
		}
		return result; // Object
	},
	formatNumber: function(x, addSpace){
		// summary: converts a number to a string using a fixed notation
		// x:			Number:		number to be converted
		// addSpace:	Boolean?:	if it is true, add a space before a positive number
		var val = x.toString();
		if(val.indexOf("e") >= 0){
			val = x.toFixed(4);
		}else{
			var point = val.indexOf(".");
			if(point >= 0 && val.length - point > 5){
				val = x.toFixed(4);
			}
		}
		if(x < 0){
			return val; // String
		}
		return addSpace ? " " + val : val; // String
	},
	// font operations
	makeFontString: function(font){
		// summary: converts a font object to a CSS font string
		// font:	Object:	font object (see dojox.gfx.defaultFont)
		return font.style + " " + font.variant + " " + font.weight + " " + font.size + " " + font.family; // Object
	},
	splitFontString: function(str){
		// summary: converts a CSS font string to a font object
		// str:		String:	a CSS font string
		var font = dojo.clone(dojox.gfx.defaultFont);
		var t = str.split(/\s+/);
		do{
			if(t.length < 5){ break; }
			font.style  = t[0];
			font.varian = t[1];
			font.weight = t[2];
			var i = t[3].indexOf("/");
			font.size = i < 0 ? t[3] : t[3].substring(0, i);
			var j = 4;
			if(i < 0){
				if(t[4] == "/"){
					j = 6;
					break;
				}
				if(t[4].substr(0, 1) == "/"){
					j = 5;
					break;
				}
			}
			if(j + 3 > t.length){ break; }
			font.size = t[j];
			font.family = t[j + 1];
		}while(false);
		return font;	// Object
	},
	// length operations
	cm_in_pt: 72 / 2.54,	// Number: centimeters per inch
	mm_in_pt: 7.2 / 2.54,	// Number: millimeters per inch
	px_in_pt: function(){
		// summary: returns a number of pixels per point
		return dojox.gfx._base._getCachedFontMeasurements()["12pt"] / 12;	// Number
	},
	pt2px: function(len){
		// summary: converts points to pixels
		// len: Number: a value in points
		return len * dojox.gfx.px_in_pt();	// Number
	},
	px2pt: function(len){
		// summary: converts pixels to points
		// len: Number: a value in pixels
		return len / dojox.gfx.px_in_pt();	// Number
	},
	normalizedLength: function(len) {
		// summary: converts any length value to pixels
		// len: String: a length, e.g., "12pc"
		if(len.length == 0) return 0;
		if(len.length > 2){
			var px_in_pt = dojox.gfx.px_in_pt();
			var val = parseFloat(len);
			switch(len.slice(-2)){
				case "px": return val;
				case "pt": return val * px_in_pt;
				case "in": return val * 72 * px_in_pt;
				case "pc": return val * 12 * px_in_pt;
				case "mm": return val / dojox.gfx.mm_in_pt * px_in_pt;
				case "cm": return val / dojox.gfx.cm_in_pt * px_in_pt;
			}
		}
		return parseFloat(len);	// Number
	},
	
	// a constant used to split a SVG/VML path into primitive components
	pathVmlRegExp: /([A-Za-z]+)|(\d+(\.\d+)?)|(\.\d+)|(-\d+(\.\d+)?)|(-\.\d+)/g,
	pathSvgRegExp: /([A-Za-z])|(\d+(\.\d+)?)|(\.\d+)|(-\d+(\.\d+)?)|(-\.\d+)/g,
	
	equalSources: function(a, b){
		// summary: compares event sources, returns true if they are equal
		return a && b && a == b;
	}
});

}

if(!dojo._hasResource["dojox.gfx"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojox.gfx"] = true;
dojo.provide("dojox.gfx");




(function(){
	var renderers = (typeof dojo.config["gfxRenderer"] == "string" ?
		dojo.config["gfxRenderer"] : "svg,vml,silverlight,canvas").split(",");
	for(var i = 0; i < renderers.length; ++i){
		switch(renderers[i]){
			case "svg":
				//TODO: need more comprehensive test for SVG
				if(!dojo.isIE && (navigator.userAgent.indexOf("iPhone") < 0) && (navigator.userAgent.indexOf("iPod") < 0)){ dojox.gfx.renderer = "svg"; }
				break;
			case "vml":
				if(dojo.isIE != 0){ dojox.gfx.renderer = "vml"; }
				break;
			case "silverlight":
				//TODO: need more comprehensive test for Silverlight
				if(window.Silverlight){ dojox.gfx.renderer = "silverlight"; }
				break;
			case "canvas":
				//TODO: need more comprehensive test for Canvas
				if(dojo.isIE == 0){ dojox.gfx.renderer = "canvas"; }
				break;
		}
		if(dojox.gfx.renderer){ break; }
	}
	console.log("gfx renderer = " + dojox.gfx.renderer);
})();

// include a renderer conditionally
dojo.requireIf(dojox.gfx.renderer == "svg", "dojox.gfx.svg");
dojo.requireIf(dojox.gfx.renderer == "vml", "dojox.gfx.vml");
dojo.requireIf(dojox.gfx.renderer == "silverlight", "dojox.gfx.silverlight");
dojo.requireIf(dojox.gfx.renderer == "canvas", "dojox.gfx.canvas");

}

if(!dojo._hasResource["dojo.date"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojo.date"] = true;
dojo.provide("dojo.date");

/*=====
dojo.date = {
	// summary: Date manipulation utilities
}
=====*/

dojo.date.getDaysInMonth = function(/*Date*/dateObject){
	//	summary:
	//		Returns the number of days in the month used by dateObject
	var month = dateObject.getMonth();
	var days = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
	if(month == 1 && dojo.date.isLeapYear(dateObject)){ return 29; } // Number
	return days[month]; // Number
}

dojo.date.isLeapYear = function(/*Date*/dateObject){
	//	summary:
	//		Determines if the year of the dateObject is a leap year
	//	description:
	//		Leap years are years with an additional day YYYY-02-29, where the
	//		year number is a multiple of four with the following exception: If
	//		a year is a multiple of 100, then it is only a leap year if it is
	//		also a multiple of 400. For example, 1900 was not a leap year, but
	//		2000 is one.

	var year = dateObject.getFullYear();
	return !(year%400) || (!(year%4) && !!(year%100)); // Boolean
}

// FIXME: This is not localized
dojo.date.getTimezoneName = function(/*Date*/dateObject){
	//	summary:
	//		Get the user's time zone as provided by the browser
	// dateObject:
	//		Needed because the timezone may vary with time (daylight savings)
	//	description:
	//		Try to get time zone info from toString or toLocaleString method of
	//		the Date object -- UTC offset is not a time zone.  See
	//		http://www.twinsun.com/tz/tz-link.htm Note: results may be
	//		inconsistent across browsers.

	var str = dateObject.toString(); // Start looking in toString
	var tz = ''; // The result -- return empty string if nothing found
	var match;

	// First look for something in parentheses -- fast lookup, no regex
	var pos = str.indexOf('(');
	if(pos > -1){
		tz = str.substring(++pos, str.indexOf(')'));
	}else{
		// If at first you don't succeed ...
		// If IE knows about the TZ, it appears before the year
		// Capital letters or slash before a 4-digit year 
		// at the end of string
		var pat = /([A-Z\/]+) \d{4}$/;
		if((match = str.match(pat))){
			tz = match[1];
		}else{
		// Some browsers (e.g. Safari) glue the TZ on the end
		// of toLocaleString instead of putting it in toString
			str = dateObject.toLocaleString();
			// Capital letters or slash -- end of string, 
			// after space
			pat = / ([A-Z\/]+)$/;
			if((match = str.match(pat))){
				tz = match[1];
			}
		}
	}

	// Make sure it doesn't somehow end up return AM or PM
	return (tz == 'AM' || tz == 'PM') ? '' : tz; // String
}

// Utility methods to do arithmetic calculations with Dates

dojo.date.compare = function(/*Date*/date1, /*Date?*/date2, /*String?*/portion){
	//	summary:
	//		Compare two date objects by date, time, or both.
	//	description:
	//  	Returns 0 if equal, positive if a > b, else negative.
	//	date1:
	//		Date object
	//	date2:
	//		Date object.  If not specified, the current Date is used.
	//	portion:
	//		A string indicating the "date" or "time" portion of a Date object.
	//		Compares both "date" and "time" by default.  One of the following:
	//		"date", "time", "datetime"

	// Extra step required in copy for IE - see #3112
	date1 = new Date(Number(date1));
	date2 = new Date(Number(date2 || new Date()));

	if(portion !== "undefined"){
		if(portion == "date"){
			// Ignore times and compare dates.
			date1.setHours(0, 0, 0, 0);
			date2.setHours(0, 0, 0, 0);
		}else if(portion == "time"){
			// Ignore dates and compare times.
			date1.setFullYear(0, 0, 0);
			date2.setFullYear(0, 0, 0);
		}
	}
	
	if(date1 > date2){ return 1; } // int
	if(date1 < date2){ return -1; } // int
	return 0; // int
};

dojo.date.add = function(/*Date*/date, /*String*/interval, /*int*/amount){
	//	summary:
	//		Add to a Date in intervals of different size, from milliseconds to years
	//	date: Date
	//		Date object to start with
	//	interval:
	//		A string representing the interval.  One of the following:
	//			"year", "month", "day", "hour", "minute", "second",
	//			"millisecond", "quarter", "week", "weekday"
	//	amount:
	//		How much to add to the date.

	var sum = new Date(Number(date)); // convert to Number before copying to accomodate IE (#3112)
	var fixOvershoot = false;
	var property = "Date";

	switch(interval){
		case "day":
			break;
		case "weekday":
			//i18n FIXME: assumes Saturday/Sunday weekend, but this is not always true.  see dojo.cldr.supplemental

			// Divide the increment time span into weekspans plus leftover days
			// e.g., 8 days is one 5-day weekspan / and two leftover days
			// Can't have zero leftover days, so numbers divisible by 5 get
			// a days value of 5, and the remaining days make up the number of weeks
			var days, weeks;
			var mod = amount % 5;
			if(!mod){
				days = (amount > 0) ? 5 : -5;
				weeks = (amount > 0) ? ((amount-5)/5) : ((amount+5)/5);
			}else{
				days = mod;
				weeks = parseInt(amount/5);
			}
			// Get weekday value for orig date param
			var strt = date.getDay();
			// Orig date is Sat / positive incrementer
			// Jump over Sun
			var adj = 0;
			if(strt == 6 && amount > 0){
				adj = 1;
			}else if(strt == 0 && amount < 0){
			// Orig date is Sun / negative incrementer
			// Jump back over Sat
				adj = -1;
			}
			// Get weekday val for the new date
			var trgt = strt + days;
			// New date is on Sat or Sun
			if(trgt == 0 || trgt == 6){
				adj = (amount > 0) ? 2 : -2;
			}
			// Increment by number of weeks plus leftover days plus
			// weekend adjustments
			amount = (7 * weeks) + days + adj;
			break;
		case "year":
			property = "FullYear";
			// Keep increment/decrement from 2/29 out of March
			fixOvershoot = true;
			break;
		case "week":
			amount *= 7;
			break;
		case "quarter":
			// Naive quarter is just three months
			amount *= 3;
			// fallthrough...
		case "month":
			// Reset to last day of month if you overshoot
			fixOvershoot = true;
			property = "Month";
			break;
		case "hour":
		case "minute":
		case "second":
		case "millisecond":
			property = "UTC"+interval.charAt(0).toUpperCase() + interval.substring(1) + "s";
	}

	if(property){
		sum["set"+property](sum["get"+property]()+amount);
	}

	if(fixOvershoot && (sum.getDate() < date.getDate())){
		sum.setDate(0);
	}

	return sum; // Date
};

dojo.date.difference = function(/*Date*/date1, /*Date?*/date2, /*String?*/interval){
	//	summary:
	//		Get the difference in a specific unit of time (e.g., number of
	//		months, weeks, days, etc.) between two dates, rounded to the
	//		nearest integer.
	//	date1:
	//		Date object
	//	date2:
	//		Date object.  If not specified, the current Date is used.
	//	interval:
	//		A string representing the interval.  One of the following:
	//			"year", "month", "day", "hour", "minute", "second",
	//			"millisecond", "quarter", "week", "weekday"
	//		Defaults to "day".

	date2 = date2 || new Date();
	interval = interval || "day";
	var yearDiff = date2.getFullYear() - date1.getFullYear();
	var delta = 1; // Integer return value

	switch(interval){
		case "quarter":
			var m1 = date1.getMonth();
			var m2 = date2.getMonth();
			// Figure out which quarter the months are in
			var q1 = Math.floor(m1/3) + 1;
			var q2 = Math.floor(m2/3) + 1;
			// Add quarters for any year difference between the dates
			q2 += (yearDiff * 4);
			delta = q2 - q1;
			break;
		case "weekday":
			var days = Math.round(dojo.date.difference(date1, date2, "day"));
			var weeks = parseInt(dojo.date.difference(date1, date2, "week"));
			var mod = days % 7;

			// Even number of weeks
			if(mod == 0){
				days = weeks*5;
			}else{
				// Weeks plus spare change (< 7 days)
				var adj = 0;
				var aDay = date1.getDay();
				var bDay = date2.getDay();

				weeks = parseInt(days/7);
				mod = days % 7;
				// Mark the date advanced by the number of
				// round weeks (may be zero)
				var dtMark = new Date(date1);
				dtMark.setDate(dtMark.getDate()+(weeks*7));
				var dayMark = dtMark.getDay();

				// Spare change days -- 6 or less
				if(days > 0){
					switch(true){
						// Range starts on Sat
						case aDay == 6:
							adj = -1;
							break;
						// Range starts on Sun
						case aDay == 0:
							adj = 0;
							break;
						// Range ends on Sat
						case bDay == 6:
							adj = -1;
							break;
						// Range ends on Sun
						case bDay == 0:
							adj = -2;
							break;
						// Range contains weekend
						case (dayMark + mod) > 5:
							adj = -2;
					}
				}else if(days < 0){
					switch(true){
						// Range starts on Sat
						case aDay == 6:
							adj = 0;
							break;
						// Range starts on Sun
						case aDay == 0:
							adj = 1;
							break;
						// Range ends on Sat
						case bDay == 6:
							adj = 2;
							break;
						// Range ends on Sun
						case bDay == 0:
							adj = 1;
							break;
						// Range contains weekend
						case (dayMark + mod) < 0:
							adj = 2;
					}
				}
				days += adj;
				days -= (weeks*2);
			}
			delta = days;
			break;
		case "year":
			delta = yearDiff;
			break;
		case "month":
			delta = (date2.getMonth() - date1.getMonth()) + (yearDiff * 12);
			break;
		case "week":
			// Truncate instead of rounding
			// Don't use Math.floor -- value may be negative
			delta = parseInt(dojo.date.difference(date1, date2, "day")/7);
			break;
		case "day":
			delta /= 24;
			// fallthrough
		case "hour":
			delta /= 60;
			// fallthrough
		case "minute":
			delta /= 60;
			// fallthrough
		case "second":
			delta /= 1000;
			// fallthrough
		case "millisecond":
			delta *= date2.getTime() - date1.getTime();
	}

	// Round for fractional values and DST leaps
	return Math.round(delta); // Number (integer)
};

}

if(!dojo._hasResource["dojo.cldr.supplemental"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojo.cldr.supplemental"] = true;
dojo.provide("dojo.cldr.supplemental");



dojo.cldr.supplemental.getFirstDayOfWeek = function(/*String?*/locale){
// summary: Returns a zero-based index for first day of the week
// description:
//		Returns a zero-based index for first day of the week, as used by the local (Gregorian) calendar.
//		e.g. Sunday (returns 0), or Monday (returns 1)

	// from http://www.unicode.org/cldr/data/common/supplemental/supplementalData.xml:supplementalData/weekData/firstDay
	var firstDay = {/*default is 1=Monday*/
		mv:5,
		ae:6,af:6,bh:6,dj:6,dz:6,eg:6,er:6,et:6,iq:6,ir:6,jo:6,ke:6,kw:6,lb:6,ly:6,ma:6,om:6,qa:6,sa:6,
		sd:6,so:6,tn:6,ye:6,
		as:0,au:0,az:0,bw:0,ca:0,cn:0,fo:0,ge:0,gl:0,gu:0,hk:0,ie:0,il:0,is:0,jm:0,jp:0,kg:0,kr:0,la:0,
		mh:0,mo:0,mp:0,mt:0,nz:0,ph:0,pk:0,sg:0,th:0,tt:0,tw:0,um:0,us:0,uz:0,vi:0,za:0,zw:0,
		et:0,mw:0,ng:0,tj:0,
// variant. do not use?		gb:0,
		sy:4
	};

	var country = dojo.cldr.supplemental._region(locale);
	var dow = firstDay[country];
	return (dow === undefined) ? 1 : dow; /*Number*/
};

dojo.cldr.supplemental._region = function(/*String?*/locale){
	locale = dojo.i18n.normalizeLocale(locale);
	var tags = locale.split('-');
	var region = tags[1];
	if(!region){
		// IE often gives language only (#2269)
		// Arbitrary mappings of language-only locales to a country:
        region = {de:"de", en:"us", es:"es", fi:"fi", fr:"fr", hu:"hu", it:"it",
        ja:"jp", ko:"kr", nl:"nl", pt:"br", sv:"se", zh:"cn"}[tags[0]];
	}else if(region.length == 4){
		// The ISO 3166 country code is usually in the second position, unless a
		// 4-letter script is given. See http://www.ietf.org/rfc/rfc4646.txt
		region = tags[2];
	}
	return region;
}

dojo.cldr.supplemental.getWeekend = function(/*String?*/locale){
// summary: Returns a hash containing the start and end days of the weekend
// description:
//		Returns a hash containing the start and end days of the weekend according to local custom using locale,
//		or by default in the user's locale.
//		e.g. {start:6, end:0}

	// from http://www.unicode.org/cldr/data/common/supplemental/supplementalData.xml:supplementalData/weekData/weekend{Start,End}
	var weekendStart = {/*default is 6=Saturday*/
		eg:5,il:5,sy:5,
		'in':0,
		ae:4,bh:4,dz:4,iq:4,jo:4,kw:4,lb:4,ly:4,ma:4,om:4,qa:4,sa:4,sd:4,tn:4,ye:4		
	};

	var weekendEnd = {/*default is 0=Sunday*/
		ae:5,bh:5,dz:5,iq:5,jo:5,kw:5,lb:5,ly:5,ma:5,om:5,qa:5,sa:5,sd:5,tn:5,ye:5,af:5,ir:5,
		eg:6,il:6,sy:6
	};

	var country = dojo.cldr.supplemental._region(locale);
	var start = weekendStart[country];
	var end = weekendEnd[country];
	if(start === undefined){start=6;}
	if(end === undefined){end=0;}
	return {start:start, end:end}; /*Object {start,end}*/
};

}

if(!dojo._hasResource["dojo.date.locale"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojo.date.locale"] = true;
dojo.provide("dojo.date.locale");

// Localization methods for Date.   Honor local customs using locale-dependent dojo.cldr data.







// Load the bundles containing localization information for
// names and formats


//NOTE: Everything in this module assumes Gregorian calendars.
// Other calendars will be implemented in separate modules.

(function(){
	// Format a pattern without literals
	function formatPattern(dateObject, bundle, fullYear, pattern){
		return pattern.replace(/([a-z])\1*/ig, function(match){
			var s, pad;
			var c = match.charAt(0);
			var l = match.length;
			var widthList = ["abbr", "wide", "narrow"];
			switch(c){
				case 'G':
					s = bundle[(l < 4) ? "eraAbbr" : "eraNames"][dateObject.getFullYear() < 0 ? 0 : 1];
					break;
				case 'y':
					s = dateObject.getFullYear();
					switch(l){
						case 1:
							break;
						case 2:
							if(!fullYear){
								s = String(s); s = s.substr(s.length - 2);
								break;
							}
							// fallthrough
						default:
							pad = true;
					}
					break;
				case 'Q':
				case 'q':
					s = Math.ceil((dateObject.getMonth()+1)/3);
//					switch(l){
//						case 1: case 2:
							pad = true;
//							break;
//						case 3: case 4: // unimplemented
//					}
					break;
				case 'M':
				case 'L':
					var m = dateObject.getMonth();
					var widthM;
					switch(l){
						case 1: case 2:
							s = m+1; pad = true;
							break;
						case 3: case 4: case 5:
							widthM = widthList[l-3];
							break;
					}
					if(widthM){
						var typeM = (c == "L") ? "standalone" : "format";
						var propM = ["months", typeM, widthM].join("-");
						s = bundle[propM][m];
					}
					break;
				case 'w':
					var firstDay = 0;
					s = dojo.date.locale._getWeekOfYear(dateObject, firstDay); pad = true;
					break;
				case 'd':
					s = dateObject.getDate(); pad = true;
					break;
				case 'D':
					s = dojo.date.locale._getDayOfYear(dateObject); pad = true;
					break;
				case 'E':
				case 'e':
				case 'c': // REVIEW: don't see this in the spec?
					var d = dateObject.getDay();
					var widthD;
					switch(l){
						case 1: case 2:
							if(c == 'e'){
								var first = dojo.cldr.supplemental.getFirstDayOfWeek(options.locale);
								d = (d-first+7)%7;
							}
							if(c != 'c'){
								s = d+1; pad = true;
								break;
							}
							// else fallthrough...
						case 3: case 4: case 5:
							widthD = widthList[l-3];
							break;
					}
					if(widthD){
						var typeD = (c == "c") ? "standalone" : "format";
						var propD = ["days", typeD, widthD].join("-");
						s = bundle[propD][d];
					}
					break;
				case 'a':
					var timePeriod = (dateObject.getHours() < 12) ? 'am' : 'pm';
					s = bundle[timePeriod];
					break;
				case 'h':
				case 'H':
				case 'K':
				case 'k':
					var h = dateObject.getHours();
					// strange choices in the date format make it impossible to write this succinctly
					switch (c){
						case 'h': // 1-12
							s = (h % 12) || 12;
							break;
						case 'H': // 0-23
							s = h;
							break;
						case 'K': // 0-11
							s = (h % 12);
							break;
						case 'k': // 1-24
							s = h || 24;
							break;
					}
					pad = true;
					break;
				case 'm':
					s = dateObject.getMinutes(); pad = true;
					break;
				case 's':
					s = dateObject.getSeconds(); pad = true;
					break;
				case 'S':
					s = Math.round(dateObject.getMilliseconds() * Math.pow(10, l-3)); pad = true;
					break;
				case 'v': // FIXME: don't know what this is. seems to be same as z?
				case 'z':
					// We only have one timezone to offer; the one from the browser
					s = dojo.date.getTimezoneName(dateObject);
					if(s){break;}
					l=4;
					// fallthrough... use GMT if tz not available
				case 'Z':
					var offset = dateObject.getTimezoneOffset();
					var tz = [
						(offset<=0 ? "+" : "-"),
						dojo.string.pad(Math.floor(Math.abs(offset)/60), 2),
						dojo.string.pad(Math.abs(offset)% 60, 2)
					];
					if(l==4){
						tz.splice(0, 0, "GMT");
						tz.splice(3, 0, ":");
					}
					s = tz.join("");
					break;
//				case 'Y': case 'u': case 'W': case 'F': case 'g': case 'A':
//					console.debug(match+" modifier unimplemented");
				default:
					throw new Error("dojo.date.locale.format: invalid pattern char: "+pattern);
			}
			if(pad){ s = dojo.string.pad(s, l); }
			return s;
		});
	}

/*=====
	dojo.date.locale.__FormatOptions = function(){
	//	selector: String
	//		choice of 'time','date' (default: date and time)
	//	formatLength: String
	//		choice of long, short, medium or full (plus any custom additions).  Defaults to 'short'
	//	datePattern:String
	//		override pattern with this string
	//	timePattern:String
	//		override pattern with this string
	//	am: String
	//		override strings for am in times
	//	pm: String
	//		override strings for pm in times
	//	locale: String
	//		override the locale used to determine formatting rules
	//	fullYear: Boolean
	//		(format only) use 4 digit years whenever 2 digit years are called for
	//	strict: Boolean
	//		(parse only) strict parsing, off by default
		this.selector = selector;
		this.formatLength = formatLength;
		this.datePattern = datePattern;
		this.timePattern = timePattern;
		this.am = am;
		this.pm = pm;
		this.locale = locale;
		this.fullYear = fullYear;
		this.strict = strict;
	}
=====*/

dojo.date.locale.format = function(/*Date*/dateObject, /*dojo.date.locale.__FormatOptions?*/options){
	// summary:
	//		Format a Date object as a String, using locale-specific settings.
	//
	// description:
	//		Create a string from a Date object using a known localized pattern.
	//		By default, this method formats both date and time from dateObject.
	//		Formatting patterns are chosen appropriate to the locale.  Different
	//		formatting lengths may be chosen, with "full" used by default.
	//		Custom patterns may be used or registered with translations using
	//		the dojo.date.locale.addCustomFormats method.
	//		Formatting patterns are implemented using [the syntax described at
	//		unicode.org](http://www.unicode.org/reports/tr35/tr35-4.html#Date_Format_Patterns)
	//
	// dateObject:
	//		the date and/or time to be formatted.  If a time only is formatted,
	//		the values in the year, month, and day fields are irrelevant.  The
	//		opposite is true when formatting only dates.

	options = options || {};

	var locale = dojo.i18n.normalizeLocale(options.locale);
	var formatLength = options.formatLength || 'short';
	var bundle = dojo.date.locale._getGregorianBundle(locale);
	var str = [];
	var sauce = dojo.hitch(this, formatPattern, dateObject, bundle, options.fullYear);
	if(options.selector == "year"){
		// Special case as this is not yet driven by CLDR data
		var year = dateObject.getFullYear();
		if(locale.match(/^zh|^ja/)){
			year += "\u5E74";
		}
		return year;
	}
	if(options.selector != "time"){
		var datePattern = options.datePattern || bundle["dateFormat-"+formatLength];
		if(datePattern){str.push(_processPattern(datePattern, sauce));}
	}
	if(options.selector != "date"){
		var timePattern = options.timePattern || bundle["timeFormat-"+formatLength];
		if(timePattern){str.push(_processPattern(timePattern, sauce));}
	}
	var result = str.join(" "); //TODO: use locale-specific pattern to assemble date + time
	return result; // String
};

dojo.date.locale.regexp = function(/*dojo.date.locale.__FormatOptions?*/options){
	// summary:
	//		Builds the regular needed to parse a localized date

	return dojo.date.locale._parseInfo(options).regexp; // String
};

dojo.date.locale._parseInfo = function(/*dojo.date.locale.__FormatOptions?*/options){
	options = options || {};
	var locale = dojo.i18n.normalizeLocale(options.locale);
	var bundle = dojo.date.locale._getGregorianBundle(locale);
	var formatLength = options.formatLength || 'short';
	var datePattern = options.datePattern || bundle["dateFormat-" + formatLength];
	var timePattern = options.timePattern || bundle["timeFormat-" + formatLength];
	var pattern;
	if(options.selector == 'date'){
		pattern = datePattern;
	}else if(options.selector == 'time'){
		pattern = timePattern;
	}else{
		pattern = datePattern + ' ' + timePattern; //TODO: use locale-specific pattern to assemble date + time
	}

	var tokens = [];
	var re = _processPattern(pattern, dojo.hitch(this, _buildDateTimeRE, tokens, bundle, options));
	return {regexp: re, tokens: tokens, bundle: bundle};
};

dojo.date.locale.parse = function(/*String*/value, /*dojo.date.locale.__FormatOptions?*/options){
	// summary:
	//		Convert a properly formatted string to a primitive Date object,
	//		using locale-specific settings.
	//
	// description:
	//		Create a Date object from a string using a known localized pattern.
	//		By default, this method parses looking for both date and time in the string.
	//		Formatting patterns are chosen appropriate to the locale.  Different
	//		formatting lengths may be chosen, with "full" used by default.
	//		Custom patterns may be used or registered with translations using
	//		the dojo.date.locale.addCustomFormats method.
	//	
	//		Formatting patterns are implemented using [the syntax described at
	//		unicode.org](http://www.unicode.org/reports/tr35/tr35-4.html#Date_Format_Patterns)
	//		When two digit years are used, a century is chosen according to a sliding 
	//		window of 80 years before and 20 years after present year, for both `yy` and `yyyy` patterns.
	//		year < 100CE requires strict mode.
	//
	// value:
	//		A string representation of a date

	var info = dojo.date.locale._parseInfo(options);
	var tokens = info.tokens, bundle = info.bundle;
	var re = new RegExp("^" + info.regexp + "$");
	var match = re.exec(value);
	if(!match){ return null; } // null

	var widthList = ['abbr', 'wide', 'narrow'];
	var result = [1970,0,1,0,0,0,0]; // will get converted to a Date at the end
	var amPm = "";
	var valid = dojo.every(match, function(v, i){
		if(!i){return true;}
		var token=tokens[i-1];
		var l=token.length;
		switch(token.charAt(0)){
			case 'y':
				if(l != 2 && options.strict){
					//interpret year literally, so '5' would be 5 A.D.
					result[0] = v;
				}else{
					if(v<100){
						v = Number(v);
						//choose century to apply, according to a sliding window
						//of 80 years before and 20 years after present year
						var year = '' + new Date().getFullYear();
						var century = year.substring(0, 2) * 100;
						var cutoff = Math.min(Number(year.substring(2, 4)) + 20, 99);
						var num = (v < cutoff) ? century + v : century - 100 + v;
						result[0] = num;
					}else{
						//we expected 2 digits and got more...
						if(options.strict){
							return false;
						}
						//interpret literally, so '150' would be 150 A.D.
						//also tolerate '1950', if 'yyyy' input passed to 'yy' format
						result[0] = v;
					}
				}
				break;
			case 'M':
				if(l>2){
					var months = bundle['months-format-' + widthList[l-3]].concat();
					if(!options.strict){
						//Tolerate abbreviating period in month part
						//Case-insensitive comparison
						v = v.replace(".","").toLowerCase();
						months = dojo.map(months, function(s){ return s.replace(".","").toLowerCase(); } );
					}
					v = dojo.indexOf(months, v);
					if(v == -1){
//						console.debug("dojo.date.locale.parse: Could not parse month name: '" + v + "'.");
						return false;
					}
				}else{
					v--;
				}
				result[1] = v;
				break;
			case 'E':
			case 'e':
				var days = bundle['days-format-' + widthList[l-3]].concat();
				if(!options.strict){
					//Case-insensitive comparison
					v = v.toLowerCase();
					days = dojo.map(days, function(d){return d.toLowerCase();});
				}
				v = dojo.indexOf(days, v);
				if(v == -1){
//					console.debug("dojo.date.locale.parse: Could not parse weekday name: '" + v + "'.");
					return false;
				}

				//TODO: not sure what to actually do with this input,
				//in terms of setting something on the Date obj...?
				//without more context, can't affect the actual date
				//TODO: just validate?
				break;
			case 'D':
				result[1] = 0;
				// fallthrough...
			case 'd':
				result[2] = v;
				break;
			case 'a': //am/pm
				var am = options.am || bundle.am;
				var pm = options.pm || bundle.pm;
				if(!options.strict){
					var period = /\./g;
					v = v.replace(period,'').toLowerCase();
					am = am.replace(period,'').toLowerCase();
					pm = pm.replace(period,'').toLowerCase();
				}
				if(options.strict && v != am && v != pm){
//					console.debug("dojo.date.locale.parse: Could not parse am/pm part.");
					return false;
				}

				// we might not have seen the hours field yet, so store the state and apply hour change later
				amPm = (v == pm) ? 'p' : (v == am) ? 'a' : '';
				break;
			case 'K': //hour (1-24)
				if(v == 24){ v = 0; }
				// fallthrough...
			case 'h': //hour (1-12)
			case 'H': //hour (0-23)
			case 'k': //hour (0-11)
				//TODO: strict bounds checking, padding
				if(v > 23){
//					console.debug("dojo.date.locale.parse: Illegal hours value");
					return false;
				}

				//in the 12-hour case, adjusting for am/pm requires the 'a' part
				//which could come before or after the hour, so we will adjust later
				result[3] = v;
				break;
			case 'm': //minutes
				result[4] = v;
				break;
			case 's': //seconds
				result[5] = v;
				break;
			case 'S': //milliseconds
				result[6] = v;
//				break;
//			case 'w':
//TODO				var firstDay = 0;
//			default:
//TODO: throw?
//				console.debug("dojo.date.locale.parse: unsupported pattern char=" + token.charAt(0));
		}
		return true;
	});

	var hours = +result[3];
	if(amPm === 'p' && hours < 12){
		result[3] = hours + 12; //e.g., 3pm -> 15
	}else if(amPm === 'a' && hours == 12){
		result[3] = 0; //12am -> 0
	}

	//TODO: implement a getWeekday() method in order to test 
	//validity of input strings containing 'EEE' or 'EEEE'...

	var dateObject = new Date(result[0], result[1], result[2], result[3], result[4], result[5], result[6]); // Date
	if(options.strict){
		dateObject.setFullYear(result[0]);
	}

	// Check for overflow.  The Date() constructor normalizes things like April 32nd...
	//TODO: why isn't this done for times as well?
	var allTokens = tokens.join("");
	if(!valid ||
		(allTokens.indexOf('M') != -1 && dateObject.getMonth() != result[1]) ||
		(allTokens.indexOf('d') != -1 && dateObject.getDate() != result[2])){
		return null;
	}

	return dateObject; // Date
};

function _processPattern(pattern, applyPattern, applyLiteral, applyAll){
	//summary: Process a pattern with literals in it

	// Break up on single quotes, treat every other one as a literal, except '' which becomes '
	var identity = function(x){return x;};
	applyPattern = applyPattern || identity;
	applyLiteral = applyLiteral || identity;
	applyAll = applyAll || identity;

	//split on single quotes (which escape literals in date format strings) 
	//but preserve escaped single quotes (e.g., o''clock)
	var chunks = pattern.match(/(''|[^'])+/g); 
	var literal = false;

	dojo.forEach(chunks, function(chunk, i){
		if(!chunk){
			chunks[i]='';
		}else{
			chunks[i]=(literal ? applyLiteral : applyPattern)(chunk);
			literal = !literal;
		}
	});
	return applyAll(chunks.join(''));
}

function _buildDateTimeRE(tokens, bundle, options, pattern){
	pattern = dojo.regexp.escapeString(pattern);
	if(!options.strict){ pattern = pattern.replace(" a", " ?a"); } // kludge to tolerate no space before am/pm
	return pattern.replace(/([a-z])\1*/ig, function(match){
		// Build a simple regexp.  Avoid captures, which would ruin the tokens list
		var s;
		var c = match.charAt(0);
		var l = match.length;
		var p2 = '', p3 = '';
		if(options.strict){
			if(l > 1){ p2 = '0' + '{'+(l-1)+'}'; }
			if(l > 2){ p3 = '0' + '{'+(l-2)+'}'; }
		}else{
			p2 = '0?'; p3 = '0{0,2}';
		}
		switch(c){
			case 'y':
				s = '\\d{2,4}';
				break;
			case 'M':
				s = (l>2) ? '\\S+' : p2+'[1-9]|1[0-2]';
				break;
			case 'D':
				s = p2+'[1-9]|'+p3+'[1-9][0-9]|[12][0-9][0-9]|3[0-5][0-9]|36[0-6]';
				break;
			case 'd':
				s = p2+'[1-9]|[12]\\d|3[01]';
				break;
			case 'w':
				s = p2+'[1-9]|[1-4][0-9]|5[0-3]';
				break;
		    case 'E':
				s = '\\S+';
				break;
			case 'h': //hour (1-12)
				s = p2+'[1-9]|1[0-2]';
				break;
			case 'k': //hour (0-11)
				s = p2+'\\d|1[01]';
				break;
			case 'H': //hour (0-23)
				s = p2+'\\d|1\\d|2[0-3]';
				break;
			case 'K': //hour (1-24)
				s = p2+'[1-9]|1\\d|2[0-4]';
				break;
			case 'm':
			case 's':
				s = '[0-5]\\d';
				break;
			case 'S':
				s = '\\d{'+l+'}';
				break;
			case 'a':
				var am = options.am || bundle.am || 'AM';
				var pm = options.pm || bundle.pm || 'PM';
				if(options.strict){
					s = am + '|' + pm;
				}else{
					s = am + '|' + pm;
					if(am != am.toLowerCase()){ s += '|' + am.toLowerCase(); }
					if(pm != pm.toLowerCase()){ s += '|' + pm.toLowerCase(); }
				}
				break;
			default:
			// case 'v':
			// case 'z':
			// case 'Z':
				s = ".*";
//				console.debug("parse of date format, pattern=" + pattern);
		}

		if(tokens){ tokens.push(match); }

		return "(" + s + ")"; // add capture
	}).replace(/[\xa0 ]/g, "[\\s\\xa0]"); // normalize whitespace.  Need explicit handling of \xa0 for IE.
}
})();

(function(){
var _customFormats = [];
dojo.date.locale.addCustomFormats = function(/*String*/packageName, /*String*/bundleName){
	// summary:
	//		Add a reference to a bundle containing localized custom formats to be
	//		used by date/time formatting and parsing routines.
	//
	// description:
	//		The user may add custom localized formats where the bundle has properties following the
	//		same naming convention used by dojo.cldr: `dateFormat-xxxx` / `timeFormat-xxxx`
	//		The pattern string should match the format used by the CLDR.
	//		See dojo.date.locale.format() for details.
	//		The resources must be loaded by dojo.requireLocalization() prior to use

	_customFormats.push({pkg:packageName,name:bundleName});
};

dojo.date.locale._getGregorianBundle = function(/*String*/locale){
	var gregorian = {};
	dojo.forEach(_customFormats, function(desc){
		var bundle = dojo.i18n.getLocalization(desc.pkg, desc.name, locale);
		gregorian = dojo.mixin(gregorian, bundle);
	}, this);
	return gregorian; /*Object*/
};
})();

dojo.date.locale.addCustomFormats("dojo.cldr","gregorian");

dojo.date.locale.getNames = function(/*String*/item, /*String*/type, /*String?*/use, /*String?*/locale){
	// summary:
	//		Used to get localized strings from dojo.cldr for day or month names.
	//
	// item:
	//	'months' || 'days'
	// type:
	//	'wide' || 'narrow' || 'abbr' (e.g. "Monday", "Mon", or "M" respectively, in English)
	// use:
	//	'standAlone' || 'format' (default)
	// locale:
	//	override locale used to find the names

	var label;
	var lookup = dojo.date.locale._getGregorianBundle(locale);
	var props = [item, use, type];
	if(use == 'standAlone'){
		label = lookup[props.join('-')];
	}
	props[1] = 'format';

	// return by copy so changes won't be made accidentally to the in-memory model
	return (label || lookup[props.join('-')]).concat(); /*Array*/
};

dojo.date.locale.isWeekend = function(/*Date?*/dateObject, /*String?*/locale){
	// summary:
	//	Determines if the date falls on a weekend, according to local custom.

	var weekend = dojo.cldr.supplemental.getWeekend(locale);
	var day = (dateObject || new Date()).getDay();
	if(weekend.end < weekend.start){
		weekend.end += 7;
		if(day < weekend.start){ day += 7; }
	}
	return day >= weekend.start && day <= weekend.end; // Boolean
};

// These are used only by format and strftime.  Do they need to be public?  Which module should they go in?

dojo.date.locale._getDayOfYear = function(/*Date*/dateObject){
	// summary: gets the day of the year as represented by dateObject
	return dojo.date.difference(new Date(dateObject.getFullYear(), 0, 1), dateObject) + 1; // Number
};

dojo.date.locale._getWeekOfYear = function(/*Date*/dateObject, /*Number*/firstDayOfWeek){
	if(arguments.length == 1){ firstDayOfWeek = 0; } // Sunday

	var firstDayOfYear = new Date(dateObject.getFullYear(), 0, 1).getDay();
	var adj = (firstDayOfYear - firstDayOfWeek + 7) % 7;
	var week = Math.floor((dojo.date.locale._getDayOfYear(dateObject) + adj - 1) / 7);

	// if year starts on the specified day, start counting weeks at 1
	if(firstDayOfYear == firstDayOfWeek){ week++; }

	return week; // Number
};

}

if(!dojo._hasResource["dojox.grid._data.editors"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojox.grid._data.editors"] = true;
dojo.provide("dojox.grid._data.editors");
dojo.provide("dojox.grid.editors");

dojo.declare("dojox.grid.editors.Base", null, {
	// summary:
	//	base grid editor class. Other grid editors should inherited from this class.
	constructor: function(inCell){
		this.cell = inCell;
	},
	//private
	_valueProp: "value",
	_formatPending: false,
	format: function(inDatum, inRowIndex){
		// summary:
		//	formats the cell for editing
		// inDatum: anything
		//	cell data to edit
		// inRowIndex: int
		//	grid row index
		// returns: string of html to place in grid cell
	},
	//protected
	needFormatNode: function(inDatum, inRowIndex){
		this._formatPending = true;
		dojox.grid.whenIdle(this, "_formatNode", inDatum, inRowIndex);
	},
	cancelFormatNode: function(){
		this._formatPending = false;
	},
	//private
	_formatNode: function(inDatum, inRowIndex){
		if(this._formatPending){
			this._formatPending = false;
			// make cell selectable
			dojo.setSelectable(this.cell.grid.domNode, true);
			this.formatNode(this.getNode(inRowIndex), inDatum, inRowIndex);
		}
	},
	//protected
	getNode: function(inRowIndex){
		return (this.cell.getNode(inRowIndex) || 0).firstChild || 0;
	},
	formatNode: function(inNode, inDatum, inRowIndex){
		// summary:
		//	format the editing dom node. Use when editor is a widget.
		// inNode: dom node
		// dom node for the editor
		// inDatum: anything
		//	cell data to edit
		// inRowIndex: int
		//	grid row index
		if(dojo.isIE){
			// IE sux bad
			dojox.grid.whenIdle(this, "focus", inRowIndex, inNode);
		}else{
			this.focus(inRowIndex, inNode);
		}
	},
	dispatchEvent: function(m, e){
		if(m in this){
			return this[m](e);
		}
	},
	//public
	getValue: function(inRowIndex){
		// summary:
		//	returns value entered into editor
		// inRowIndex: int
		// grid row index
		// returns:
		//	value of editor
		return this.getNode(inRowIndex)[this._valueProp];
	},
	setValue: function(inRowIndex, inValue){
		// summary:
		//	set the value of the grid editor
		// inRowIndex: int
		// grid row index
		// inValue: anything
		//	value of editor
		var n = this.getNode(inRowIndex);
		if(n){
			n[this._valueProp] = inValue
		};
	},
	focus: function(inRowIndex, inNode){
		// summary:
		//	focus the grid editor
		// inRowIndex: int
		// grid row index
		// inNode: dom node
		//	editor node
		dojox.grid.focusSelectNode(inNode || this.getNode(inRowIndex));
	},
	save: function(inRowIndex){
		// summary:
		//	save editor state
		// inRowIndex: int
		// grid row index
		this.value = this.value || this.getValue(inRowIndex);
		//console.log("save", this.value, inCell.index, inRowIndex);
	},
	restore: function(inRowIndex){
		// summary:
		//	restore editor state
		// inRowIndex: int
		// grid row index
		this.setValue(inRowIndex, this.value);
		//console.log("restore", this.value, inCell.index, inRowIndex);
	},
	//protected
	_finish: function(inRowIndex){
		// summary:
		//	called when editing is completed to clean up editor
		// inRowIndex: int
		// grid row index
		dojo.setSelectable(this.cell.grid.domNode, false);
		this.cancelFormatNode(this.cell);
	},
	//public
	apply: function(inRowIndex){
		// summary:
		//	apply edit from cell editor
		// inRowIndex: int
		// grid row index
		this.cell.applyEdit(this.getValue(inRowIndex), inRowIndex);
		this._finish(inRowIndex);
	},
	cancel: function(inRowIndex){
		// summary:
		//	cancel cell edit
		// inRowIndex: int
		// grid row index
		this.cell.cancelEdit(inRowIndex);
		this._finish(inRowIndex);
	}
});
dojox.grid.editors.base = dojox.grid.editors.Base; // back-compat

dojo.declare("dojox.grid.editors.Input", dojox.grid.editors.Base, {
	// summary
	// grid cell editor that provides a standard text input box
	constructor: function(inCell){
		this.keyFilter = this.keyFilter || this.cell.keyFilter;
	},
	// keyFilter: object
	// optional regex for disallowing keypresses
	keyFilter: null,
	format: function(inDatum, inRowIndex){
		this.needFormatNode(inDatum, inRowIndex);
		return '<input class="dojoxGrid-input" type="text" value="' + inDatum + '">';
	},
	formatNode: function(inNode, inDatum, inRowIndex){
		this.inherited(arguments);
		// FIXME: feels too specific for this interface
		this.cell.registerOnBlur(inNode, inRowIndex);
	},
	doKey: function(e){
		if(this.keyFilter){
			var key = String.fromCharCode(e.charCode);
			if(key.search(this.keyFilter) == -1){
				dojo.stopEvent(e);
			}
		}
	},
	_finish: function(inRowIndex){
		this.inherited(arguments);
		var n = this.getNode(inRowIndex);
		try{
			dojox.grid.fire(n, "blur");
		}catch(e){}
	}
});
dojox.grid.editors.input = dojox.grid.editors.Input; // back compat

dojo.declare("dojox.grid.editors.Select", dojox.grid.editors.Input, {
	// summary:
	// grid cell editor that provides a standard select
	// options: text of each item
	// values: value for each item
	// returnIndex: editor returns only the index of the selected option and not the value
	constructor: function(inCell){
		this.options = this.options || this.cell.options;
		this.values = this.values || this.cell.values || this.options;
	},
	format: function(inDatum, inRowIndex){
		this.needFormatNode(inDatum, inRowIndex);
		var h = [ '<select class="dojoxGrid-select">' ];
		for (var i=0, o, v; ((o=this.options[i]) !== undefined)&&((v=this.values[i]) !== undefined); i++){
			h.push("<option", (inDatum==v ? ' selected' : ''), ' value="' + v + '"', ">", o, "</option>");
		}
		h.push('</select>');
		return h.join('');
	},
	getValue: function(inRowIndex){
		var n = this.getNode(inRowIndex);
		if(n){
			var i = n.selectedIndex, o = n.options[i];
			return this.cell.returnIndex ? i : o.value || o.innerHTML;
		}
	}
});
dojox.grid.editors.select = dojox.grid.editors.Select; // back compat

dojo.declare("dojox.grid.editors.AlwaysOn", dojox.grid.editors.Input, {
	// summary:
	// grid cell editor that is always on, regardless of grid editing state
	// alwaysOn: boolean
	// flag to use editor to format grid cell regardless of editing state.
	alwaysOn: true,
	_formatNode: function(inDatum, inRowIndex){
		this.formatNode(this.getNode(inRowIndex), inDatum, inRowIndex);
	},
	applyStaticValue: function(inRowIndex){
		var e = this.cell.grid.edit;
		e.applyCellEdit(this.getValue(inRowIndex), this.cell, inRowIndex);
		e.start(this.cell, inRowIndex, true);
	}
});
dojox.grid.editors.alwaysOn = dojox.grid.editors.AlwaysOn; // back-compat

dojo.declare("dojox.grid.editors.Bool", dojox.grid.editors.AlwaysOn, {
	// summary:
	// grid cell editor that provides a standard checkbox that is always on
	_valueProp: "checked",
	format: function(inDatum, inRowIndex){
		return '<input class="dojoxGrid-input" type="checkbox"' + (inDatum ? ' checked="checked"' : '') + ' style="width: auto" />';
	},
	doclick: function(e){
		if(e.target.tagName == 'INPUT'){
			this.applyStaticValue(e.rowIndex);
		}
	}
});
dojox.grid.editors.bool = dojox.grid.editors.Bool; // back-compat

}

if(!dojo._hasResource["dojo.date.stamp"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojo.date.stamp"] = true;
dojo.provide("dojo.date.stamp");

// Methods to convert dates to or from a wire (string) format using well-known conventions

dojo.date.stamp.fromISOString = function(/*String*/formattedString, /*Number?*/defaultTime){
	//	summary:
	//		Returns a Date object given a string formatted according to a subset of the ISO-8601 standard.
	//
	//	description:
	//		Accepts a string formatted according to a profile of ISO8601 as defined by
	//		[RFC3339](http://www.ietf.org/rfc/rfc3339.txt), except that partial input is allowed.
	//		Can also process dates as specified [by the W3C](http://www.w3.org/TR/NOTE-datetime)
	//		The following combinations are valid:
	//
	//			* dates only
	//			|	* yyyy
	//			|	* yyyy-MM
	//			|	* yyyy-MM-dd
	// 			* times only, with an optional time zone appended
	//			|	* THH:mm
	//			|	* THH:mm:ss
	//			|	* THH:mm:ss.SSS
	// 			* and "datetimes" which could be any combination of the above
	//
	//		timezones may be specified as Z (for UTC) or +/- followed by a time expression HH:mm
	//		Assumes the local time zone if not specified.  Does not validate.  Improperly formatted
	//		input may return null.  Arguments which are out of bounds will be handled
	// 		by the Date constructor (e.g. January 32nd typically gets resolved to February 1st)
	//		Only years between 100 and 9999 are supported.
	//
  	//	formattedString:
	//		A string such as 2005-06-30T08:05:00-07:00 or 2005-06-30 or T08:05:00
	//
	//	defaultTime:
	//		Used for defaults for fields omitted in the formattedString.
	//		Uses 1970-01-01T00:00:00.0Z by default.

	if(!dojo.date.stamp._isoRegExp){
		dojo.date.stamp._isoRegExp =
//TODO: could be more restrictive and check for 00-59, etc.
			/^(?:(\d{4})(?:-(\d{2})(?:-(\d{2}))?)?)?(?:T(\d{2}):(\d{2})(?::(\d{2})(.\d+)?)?((?:[+-](\d{2}):(\d{2}))|Z)?)?$/;
	}

	var match = dojo.date.stamp._isoRegExp.exec(formattedString);
	var result = null;

	if(match){
		match.shift();
		if(match[1]){match[1]--;} // Javascript Date months are 0-based
		if(match[6]){match[6] *= 1000;} // Javascript Date expects fractional seconds as milliseconds

		if(defaultTime){
			// mix in defaultTime.  Relatively expensive, so use || operators for the fast path of defaultTime === 0
			defaultTime = new Date(defaultTime);
			dojo.map(["FullYear", "Month", "Date", "Hours", "Minutes", "Seconds", "Milliseconds"], function(prop){
				return defaultTime["get" + prop]();
			}).forEach(function(value, index){
				if(match[index] === undefined){
					match[index] = value;
				}
			});
		}
		result = new Date(match[0]||1970, match[1]||0, match[2]||1, match[3]||0, match[4]||0, match[5]||0, match[6]||0);
//		result.setFullYear(match[0]||1970); // for year < 100

		var offset = 0;
		var zoneSign = match[7] && match[7].charAt(0);
		if(zoneSign != 'Z'){
			offset = ((match[8] || 0) * 60) + (Number(match[9]) || 0);
			if(zoneSign != '-'){ offset *= -1; }
		}
		if(zoneSign){
			offset -= result.getTimezoneOffset();
		}
		if(offset){
			result.setTime(result.getTime() + offset * 60000);
		}
	}

	return result; // Date or null
}

/*=====
	dojo.date.stamp.__Options = function(){
		//	selector: String
		//		"date" or "time" for partial formatting of the Date object.
		//		Both date and time will be formatted by default.
		//	zulu: Boolean
		//		if true, UTC/GMT is used for a timezone
		//	milliseconds: Boolean
		//		if true, output milliseconds
		this.selector = selector;
		this.zulu = zulu;
		this.milliseconds = milliseconds;
	}
=====*/

dojo.date.stamp.toISOString = function(/*Date*/dateObject, /*dojo.date.stamp.__Options?*/options){
	//	summary:
	//		Format a Date object as a string according a subset of the ISO-8601 standard
	//
	//	description:
	//		When options.selector is omitted, output follows [RFC3339](http://www.ietf.org/rfc/rfc3339.txt)
	//		The local time zone is included as an offset from GMT, except when selector=='time' (time without a date)
	//		Does not check bounds.  Only years between 100 and 9999 are supported.
	//
	//	dateObject:
	//		A Date object

	var _ = function(n){ return (n < 10) ? "0" + n : n; };
	options = options || {};
	var formattedDate = [];
	var getter = options.zulu ? "getUTC" : "get";
	var date = "";
	if(options.selector != "time"){
		var year = dateObject[getter+"FullYear"]();
		date = ["0000".substr((year+"").length)+year, _(dateObject[getter+"Month"]()+1), _(dateObject[getter+"Date"]())].join('-');
	}
	formattedDate.push(date);
	if(options.selector != "date"){
		var time = [_(dateObject[getter+"Hours"]()), _(dateObject[getter+"Minutes"]()), _(dateObject[getter+"Seconds"]())].join(':');
		var millis = dateObject[getter+"Milliseconds"]();
		if(options.milliseconds){
			time += "."+ (millis < 100 ? "0" : "") + _(millis);
		}
		if(options.zulu){
			time += "Z";
		}else if(options.selector != "time"){
			var timezoneOffset = dateObject.getTimezoneOffset();
			var absOffset = Math.abs(timezoneOffset);
			time += (timezoneOffset > 0 ? "-" : "+") + 
				_(Math.floor(absOffset/60)) + ":" + _(absOffset%60);
		}
		formattedDate.push(time);
	}
	return formattedDate.join('T'); // String
}

}

if(!dojo._hasResource["dojo.parser"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojo.parser"] = true;
dojo.provide("dojo.parser");


dojo.parser = new function(){
	// summary: The Dom/Widget parsing package

	var d = dojo;
	var dtName = d._scopeName + "Type";
	var qry = "[" + dtName + "]";

	function val2type(/*Object*/ value){
		// summary:
		//		Returns name of type of given value.

		if(d.isString(value)){ return "string"; }
		if(typeof value == "number"){ return "number"; }
		if(typeof value == "boolean"){ return "boolean"; }
		if(d.isFunction(value)){ return "function"; }
		if(d.isArray(value)){ return "array"; } // typeof [] == "object"
		if(value instanceof Date) { return "date"; } // assume timestamp
		if(value instanceof d._Url){ return "url"; }
		return "object";
	}

	function str2obj(/*String*/ value, /*String*/ type){
		// summary:
		//		Convert given string value to given type
		switch(type){
			case "string":
				return value;
			case "number":
				return value.length ? Number(value) : NaN;
			case "boolean":
				// for checked/disabled value might be "" or "checked".  interpret as true.
				return typeof value == "boolean" ? value : !(value.toLowerCase()=="false");
			case "function":
				if(d.isFunction(value)){
					// IE gives us a function, even when we say something like onClick="foo"
					// (in which case it gives us an invalid function "function(){ foo }"). 
					//  Therefore, convert to string
					value=value.toString();
					value=d.trim(value.substring(value.indexOf('{')+1, value.length-1));
				}
				try{
					if(value.search(/[^\w\.]+/i) != -1){
						// TODO: "this" here won't work
						value = d.parser._nameAnonFunc(new Function(value), this);
					}
					return d.getObject(value, false);
				}catch(e){ return new Function(); }
			case "array":
				return value.split(/\s*,\s*/);
			case "date":
				switch(value){
					case "": return new Date("");	// the NaN of dates
					case "now": return new Date();	// current date
					default: return d.date.stamp.fromISOString(value);
				}
			case "url":
				return d.baseUrl + value;
			default:
				return d.fromJson(value);
		}
	}

	var instanceClasses = {
		// map from fully qualified name (like "dijit.Button") to structure like
		// { cls: dijit.Button, params: {label: "string", disabled: "boolean"} }
	};
	
	function getClassInfo(/*String*/ className){
		// className:
		//		fully qualified name (like "dijit.Button")
		// returns:
		//		structure like
		//			{ 
		//				cls: dijit.Button, 
		//				params: { label: "string", disabled: "boolean"}
		//			}

		if(!instanceClasses[className]){
			// get pointer to widget class
			var cls = d.getObject(className);
			if(!d.isFunction(cls)){
				throw new Error("Could not load class '" + className +
					"'. Did you spell the name correctly and use a full path, like 'dijit.form.Button'?");
			}
			var proto = cls.prototype;
	
			// get table of parameter names & types
			var params={};
			for(var name in proto){
				if(name.charAt(0)=="_"){ continue; } 	// skip internal properties
				var defVal = proto[name];
				params[name]=val2type(defVal);
			}

			instanceClasses[className] = { cls: cls, params: params };
		}
		return instanceClasses[className];
	}

	this._functionFromScript = function(script){
		var preamble = "";
		var suffix = "";
		var argsStr = script.getAttribute("args");
		if(argsStr){
			d.forEach(argsStr.split(/\s*,\s*/), function(part, idx){
				preamble += "var "+part+" = arguments["+idx+"]; ";
			});
		}
		var withStr = script.getAttribute("with");
		if(withStr && withStr.length){
			d.forEach(withStr.split(/\s*,\s*/), function(part){
				preamble += "with("+part+"){";
				suffix += "}";
			});
		}
		return new Function(preamble+script.innerHTML+suffix);
	}

	this.instantiate = function(/* Array */nodes){
		// summary:
		//		Takes array of nodes, and turns them into class instances and
		//		potentially calls a layout method to allow them to connect with
		//		any children		
		var thelist = [];
		d.forEach(nodes, function(node){
			if(!node){ return; }
			var type = node.getAttribute(dtName);
			if((!type)||(!type.length)){ return; }
			var clsInfo = getClassInfo(type);
			var clazz = clsInfo.cls;
			var ps = clazz._noScript||clazz.prototype._noScript;

			// read parameters (ie, attributes).
			// clsInfo.params lists expected params like {"checked": "boolean", "n": "number"}
			var params = {};
			var attributes = node.attributes;
			for(var name in clsInfo.params){
				var item = attributes.getNamedItem(name);
				if(!item || (!item.specified && (!dojo.isIE || name.toLowerCase()!="value"))){ continue; }
				var value = item.value;
				// Deal with IE quirks for 'class' and 'style'
				switch(name){
				case "class":
					value = node.className;
					break;
				case "style":
					value = node.style && node.style.cssText; // FIXME: Opera?
				}
				var _type = clsInfo.params[name];
				params[name] = str2obj(value, _type);
			}

			// Process <script type="dojo/*"> script tags
			// <script type="dojo/method" event="foo"> tags are added to params, and passed to
			// the widget on instantiation.
			// <script type="dojo/method"> tags (with no event) are executed after instantiation
			// <script type="dojo/connect" event="foo"> tags are dojo.connected after instantiation
			// note: dojo/* script tags cannot exist in self closing widgets, like <input />
			if(!ps){
				var connects = [],	// functions to connect after instantiation
					calls = [];		// functions to call after instantiation

				d.query("> script[type^='dojo/']", node).orphan().forEach(function(script){
					var event = script.getAttribute("event"),
						type = script.getAttribute("type"),
						nf = d.parser._functionFromScript(script);
					if(event){
						if(type == "dojo/connect"){
							connects.push({event: event, func: nf});
						}else{
							params[event] = nf;
						}
					}else{
						calls.push(nf);
					}
				});
			}

			var markupFactory = clazz["markupFactory"];
			if(!markupFactory && clazz["prototype"]){
				markupFactory = clazz.prototype["markupFactory"];
			}
			// create the instance
			var instance = markupFactory ? markupFactory(params, node, clazz) : new clazz(params, node);
			thelist.push(instance);

			// map it to the JS namespace if that makes sense
			var jsname = node.getAttribute("jsId");
			if(jsname){
				d.setObject(jsname, instance);
			}

			// process connections and startup functions
			if(!ps){
				d.forEach(connects, function(connect){
					d.connect(instance, connect.event, null, connect.func);
				});
				d.forEach(calls, function(func){
					func.call(instance);
				});
			}
		});

		// Call startup on each top level instance if it makes sense (as for
		// widgets).  Parent widgets will recursively call startup on their
		// (non-top level) children
		d.forEach(thelist, function(instance){
			if(	instance  && 
				instance.startup &&
				!instance._started && 
				(!instance.getParent || !instance.getParent())
			){
				instance.startup();
			}
		});
		return thelist;
	};

	this.parse = function(/*DomNode?*/ rootNode){
		// summary:
		//		Search specified node (or root node) recursively for class instances,
		//		and instantiate them Searches for
		//		dojoType="qualified.class.name"
		var list = d.query(qry, rootNode);
		// go build the object instances
		var instances = this.instantiate(list);
		return instances;
	};
}();

//Register the parser callback. It should be the first callback
//after the a11y test.

(function(){
	var parseRunner = function(){ 
		if(dojo.config["parseOnLoad"] == true){
			dojo.parser.parse(); 
		}
	};

	// FIXME: need to clobber cross-dependency!!
	if(dojo.exists("dijit.wai.onload") && (dijit.wai.onload === dojo._loaders[0])){
		dojo._loaders.splice(1, 0, parseRunner);
	}else{
		dojo._loaders.unshift(parseRunner);
	}
})();

//TODO: ported from 0.4.x Dojo.  Can we reduce this?
dojo.parser._anonCtr = 0;
dojo.parser._anon = {}; // why is this property required?
dojo.parser._nameAnonFunc = function(/*Function*/anonFuncPtr, /*Object*/thisObj){
	// summary:
	//		Creates a reference to anonFuncPtr in thisObj with a completely
	//		unique name. The new name is returned as a String. 
	var jpn = "$joinpoint";
	var nso = (thisObj|| dojo.parser._anon);
	if(dojo.isIE){
		var cn = anonFuncPtr["__dojoNameCache"];
		if(cn && nso[cn] === anonFuncPtr){
			return anonFuncPtr["__dojoNameCache"];
		}
	}
	var ret = "__"+dojo.parser._anonCtr++;
	while(typeof nso[ret] != "undefined"){
		ret = "__"+dojo.parser._anonCtr++;
	}
	nso[ret] = anonFuncPtr;
	return ret; // String
}

}

if(!dojo._hasResource["dijit._Templated"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dijit._Templated"] = true;
dojo.provide("dijit._Templated");





dojo.declare("dijit._Templated",
	null,
	{
		//	summary:
		//		Mixin for widgets that are instantiated from a template
		// 
		// templateNode: DomNode
		//		a node that represents the widget template. Pre-empts both templateString and templatePath.
		templateNode: null,

		// templateString: String
		//		a string that represents the widget template. Pre-empts the
		//		templatePath. In builds that have their strings "interned", the
		//		templatePath is converted to an inline templateString, thereby
		//		preventing a synchronous network call.
		templateString: null,

		// templatePath: String
		//	Path to template (HTML file) for this widget relative to dojo.baseUrl
		templatePath: null,

		// widgetsInTemplate: Boolean
		//		should we parse the template to find widgets that might be
		//		declared in markup inside it? false by default.
		widgetsInTemplate: false,

		// containerNode: DomNode
		//		holds child elements. "containerNode" is generally set via a
		//		dojoAttachPoint assignment and it designates where children of
		//		the src dom node will be placed
		containerNode: null,

		// skipNodeCache: Boolean
		//		if using a cached widget template node poses issues for a
		//		particular widget class, it can set this property to ensure
		//		that its template is always re-built from a string
		_skipNodeCache: false,

		_stringRepl: function(tmpl){
			var className = this.declaredClass, _this = this;
			// Cache contains a string because we need to do property replacement
			// do the property replacement
			return dojo.string.substitute(tmpl, this, function(value, key){
				if(key.charAt(0) == '!'){ value = _this[key.substr(1)]; }
				if(typeof value == "undefined"){ throw new Error(className+" template:"+key); } // a debugging aide
				if(!value){ return ""; }

				// Substitution keys beginning with ! will skip the transform step,
				// in case a user wishes to insert unescaped markup, e.g. ${!foo}
				return key.charAt(0) == "!" ? value :
					// Safer substitution, see heading "Attribute values" in
					// http://www.w3.org/TR/REC-html40/appendix/notes.html#h-B.3.2
					value.toString().replace(/"/g,"&quot;"); //TODO: add &amp? use encodeXML method?
			}, this);
		},

		// method over-ride
		buildRendering: function(){
			// summary:
			//		Construct the UI for this widget from a template, setting this.domNode.

			// Lookup cached version of template, and download to cache if it
			// isn't there already.  Returns either a DomNode or a string, depending on
			// whether or not the template contains ${foo} replacement parameters.
			var cached = dijit._Templated.getCachedTemplate(this.templatePath, this.templateString, this._skipNodeCache);

			var node;
			if(dojo.isString(cached)){
				node = dijit._Templated._createNodesFromText(this._stringRepl(cached))[0];
			}else{
				// if it's a node, all we have to do is clone it
				node = cached.cloneNode(true);
			}

			// recurse through the node, looking for, and attaching to, our
			// attachment points which should be defined on the template node.
			this._attachTemplateNodes(node);

			var source = this.srcNodeRef;
			if(source && source.parentNode){
				source.parentNode.replaceChild(node, source);
			}

			this.domNode = node;
			if(this.widgetsInTemplate){
				var cw = this._supportingWidgets  = dojo.parser.parse(node);
				this._attachTemplateNodes(cw, function(n,p){
					return n[p];
				});
			}

			this._fillContent(source);
		},

		_fillContent: function(/*DomNode*/ source){
			// summary:
			//		relocate source contents to templated container node
			//		this.containerNode must be able to receive children, or exceptions will be thrown
			var dest = this.containerNode;
			if(source && dest){
				while(source.hasChildNodes()){
					dest.appendChild(source.firstChild);
				}
			}
		},

		_attachTemplateNodes: function(rootNode, getAttrFunc){
			// summary: Iterate through the template and attach functions and nodes accordingly.	
			// description:		
			//		Map widget properties and functions to the handlers specified in
			//		the dom node and it's descendants. This function iterates over all
			//		nodes and looks for these properties:
			//			* dojoAttachPoint
			//			* dojoAttachEvent	
			//			* waiRole
			//			* waiState
			// rootNode: DomNode|Array[Widgets]
			//		the node to search for properties. All children will be searched.
			// getAttrFunc: function?
			//		a function which will be used to obtain property for a given
			//		DomNode/Widget

			getAttrFunc = getAttrFunc || function(n,p){ return n.getAttribute(p); };

			var nodes = dojo.isArray(rootNode) ? rootNode : (rootNode.all || rootNode.getElementsByTagName("*"));
			var x=dojo.isArray(rootNode)?0:-1;
			for(; x<nodes.length; x++){
				var baseNode = (x == -1) ? rootNode : nodes[x];
				if(this.widgetsInTemplate && getAttrFunc(baseNode,'dojoType')){
					continue;
				}
				// Process dojoAttachPoint
				var attachPoint = getAttrFunc(baseNode, "dojoAttachPoint");
				if(attachPoint){
					var point, points = attachPoint.split(/\s*,\s*/);
					while((point = points.shift())){
						if(dojo.isArray(this[point])){
							this[point].push(baseNode);
						}else{
							this[point]=baseNode;
						}
					}
				}

				// Process dojoAttachEvent
				var attachEvent = getAttrFunc(baseNode, "dojoAttachEvent");
				if(attachEvent){
					// NOTE: we want to support attributes that have the form
					// "domEvent: nativeEvent; ..."
					var event, events = attachEvent.split(/\s*,\s*/);
					var trim = dojo.trim;
					while((event = events.shift())){
						if(event){
							var thisFunc = null;
							if(event.indexOf(":") != -1){
								// oh, if only JS had tuple assignment
								var funcNameArr = event.split(":");
								event = trim(funcNameArr[0]);
								thisFunc = trim(funcNameArr[1]);
							}else{
								event = trim(event);
							}
							if(!thisFunc){
								thisFunc = event;
							}
							this.connect(baseNode, event, thisFunc);
						}
					}
				}

				// waiRole, waiState
				var role = getAttrFunc(baseNode, "waiRole");
				if(role){
					dijit.setWaiRole(baseNode, role);
				}
				var values = getAttrFunc(baseNode, "waiState");
				if(values){
					dojo.forEach(values.split(/\s*,\s*/), function(stateValue){
						if(stateValue.indexOf('-') != -1){
							var pair = stateValue.split('-');
							dijit.setWaiState(baseNode, pair[0], pair[1]);
						}
					});
				}

			}
		}
	}
);

// key is either templatePath or templateString; object is either string or DOM tree
dijit._Templated._templateCache = {};

dijit._Templated.getCachedTemplate = function(templatePath, templateString, alwaysUseString){
	// summary:
	//		Static method to get a template based on the templatePath or
	//		templateString key
	// templatePath: String
	//		The URL to get the template from. dojo.uri.Uri is often passed as well.
	// templateString: String?
	//		a string to use in lieu of fetching the template from a URL. Takes precedence
	//		over templatePath
	// Returns: Mixed
	//	Either string (if there are ${} variables that need to be replaced) or just
	//	a DOM tree (if the node can be cloned directly)

	// is it already cached?
	var tmplts = dijit._Templated._templateCache;
	var key = templateString || templatePath;
	var cached = tmplts[key];
	if(cached){
		return cached;
	}

	// If necessary, load template string from template path
	if(!templateString){
		templateString = dijit._Templated._sanitizeTemplateString(dojo._getText(templatePath));
	}

	templateString = dojo.string.trim(templateString);

	if(alwaysUseString || templateString.match(/\$\{([^\}]+)\}/g)){
		// there are variables in the template so all we can do is cache the string
		return (tmplts[key] = templateString); //String
	}else{
		// there are no variables in the template so we can cache the DOM tree
		return (tmplts[key] = dijit._Templated._createNodesFromText(templateString)[0]); //Node
	}
};

dijit._Templated._sanitizeTemplateString = function(/*String*/tString){
	// summary: 
	//		Strips <?xml ...?> declarations so that external SVG and XML
	// 		documents can be added to a document without worry. Also, if the string
	//		is an HTML document, only the part inside the body tag is returned.
	if(tString){
		tString = tString.replace(/^\s*<\?xml(\s)+version=[\'\"](\d)*.(\d)*[\'\"](\s)*\?>/im, "");
		var matches = tString.match(/<body[^>]*>\s*([\s\S]+)\s*<\/body>/im);
		if(matches){
			tString = matches[1];
		}
	}else{
		tString = "";
	}
	return tString; //String
};


if(dojo.isIE){
	dojo.addOnUnload(function(){
		var cache = dijit._Templated._templateCache;
		for(var key in cache){
			var value = cache[key];
			if(!isNaN(value.nodeType)){ // isNode equivalent
				dojo._destroyElement(value);
			}
			delete cache[key];
		}
	});
}

(function(){
	var tagMap = {
		cell: {re: /^<t[dh][\s\r\n>]/i, pre: "<table><tbody><tr>", post: "</tr></tbody></table>"},
		row: {re: /^<tr[\s\r\n>]/i, pre: "<table><tbody>", post: "</tbody></table>"},
		section: {re: /^<(thead|tbody|tfoot)[\s\r\n>]/i, pre: "<table>", post: "</table>"}
	};

	// dummy container node used temporarily to hold nodes being created
	var tn;

	dijit._Templated._createNodesFromText = function(/*String*/text){
		// summary:
		//	Attempts to create a set of nodes based on the structure of the passed text.

		if(!tn){
			tn = dojo.doc.createElement("div");
			tn.style.display="none";
			dojo.body().appendChild(tn);
		}
		var tableType = "none";
		var rtext = text.replace(/^\s+/, "");
		for(var type in tagMap){
			var map = tagMap[type];
			if(map.re.test(rtext)){
				tableType = type;
				text = map.pre + text + map.post;
				break;
			}
		}

		tn.innerHTML = text;
		if(tn.normalize){
			tn.normalize();
		}

		var tag = { cell: "tr", row: "tbody", section: "table" }[tableType];
		var _parent = (typeof tag != "undefined") ?
						tn.getElementsByTagName(tag)[0] :
						tn;

		var nodes = [];
		while(_parent.firstChild){
			nodes.push(_parent.removeChild(_parent.firstChild));
		}
		tn.innerHTML="";
		return nodes;	//	Array
	}
})();

// These arguments can be specified for widgets which are used in templates.
// Since any widget can be specified as sub widgets in template, mix it
// into the base widget class.  (This is a hack, but it's effective.)
dojo.extend(dijit._Widget,{
	dojoAttachEvent: "",
	dojoAttachPoint: "",
	waiRole: "",
	waiState:""
})

}

if(!dojo._hasResource["dijit._Calendar"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dijit._Calendar"] = true;
dojo.provide("dijit._Calendar");








dojo.declare(
	"dijit._Calendar",
	[dijit._Widget, dijit._Templated],
	{
	//	
	//	summary:
	//		A simple GUI for choosing a date in the context of a monthly calendar.
	//
	//	description:
	//		A simple GUI for choosing a date in the context of a monthly calendar.
	//		This widget is used internally by other widgets and is not accessible
	//		as a standalone widget.
	//		This widget can't be used in a form because it doesn't serialize the date to an
	//		`<input>` field.  For a form element, use dijit.form.DateTextBox instead.
	//
	//		Note that the parser takes all dates attributes passed in the
	//		[RFC 3339 format](http://www.faqs.org/rfcs/rfc3339.html), e.g. `2005-06-30T08:05:00-07:00`
	//		so that they are serializable and locale-independent.
	//
	//	example:
	//	|	var calendar = new dijit._Calendar({}, dojo.byId("calendarNode"));
	//
	//	example:
	//	|	<div dojoType="dijit._Calendar"></div>
	//	
		templateString:"<table cellspacing=\"0\" cellpadding=\"0\" class=\"dijitCalendarContainer\">\r\n\t<thead>\r\n\t\t<tr class=\"dijitReset dijitCalendarMonthContainer\" valign=\"top\">\r\n\t\t\t<th class='dijitReset' dojoAttachPoint=\"decrementMonth\">\r\n\t\t\t\t<div class=\"dijitInline dijitCalendarIncrementControl dijitCalendarDecrease\"><span dojoAttachPoint=\"decreaseArrowNode\" class=\"dijitA11ySideArrow dijitCalendarIncrementControl dijitCalendarDecreaseInner\">-</span></div>\r\n\t\t\t</th>\r\n\t\t\t<th class='dijitReset' colspan=\"5\">\r\n\t\t\t\t<div dojoAttachPoint=\"monthLabelSpacer\" class=\"dijitCalendarMonthLabelSpacer\"></div>\r\n\t\t\t\t<div dojoAttachPoint=\"monthLabelNode\" class=\"dijitCalendarMonthLabel\"></div>\r\n\t\t\t</th>\r\n\t\t\t<th class='dijitReset' dojoAttachPoint=\"incrementMonth\">\r\n\t\t\t\t<div class=\"dijitInline dijitCalendarIncrementControl dijitCalendarIncrease\"><span dojoAttachPoint=\"increaseArrowNode\" class=\"dijitA11ySideArrow dijitCalendarIncrementControl dijitCalendarIncreaseInner\">+</span></div>\r\n\t\t\t</th>\r\n\t\t</tr>\r\n\t\t<tr>\r\n\t\t\t<th class=\"dijitReset dijitCalendarDayLabelTemplate\"><span class=\"dijitCalendarDayLabel\"></span></th>\r\n\t\t</tr>\r\n\t</thead>\r\n\t<tbody dojoAttachEvent=\"onclick: _onDayClick\" class=\"dijitReset dijitCalendarBodyContainer\">\r\n\t\t<tr class=\"dijitReset dijitCalendarWeekTemplate\">\r\n\t\t\t<td class=\"dijitReset dijitCalendarDateTemplate\"><span class=\"dijitCalendarDateLabel\"></span></td>\r\n\t\t</tr>\r\n\t</tbody>\r\n\t<tfoot class=\"dijitReset dijitCalendarYearContainer\">\r\n\t\t<tr>\r\n\t\t\t<td class='dijitReset' valign=\"top\" colspan=\"7\">\r\n\t\t\t\t<h3 class=\"dijitCalendarYearLabel\">\r\n\t\t\t\t\t<span dojoAttachPoint=\"previousYearLabelNode\" class=\"dijitInline dijitCalendarPreviousYear\"></span>\r\n\t\t\t\t\t<span dojoAttachPoint=\"currentYearLabelNode\" class=\"dijitInline dijitCalendarSelectedYear\"></span>\r\n\t\t\t\t\t<span dojoAttachPoint=\"nextYearLabelNode\" class=\"dijitInline dijitCalendarNextYear\"></span>\r\n\t\t\t\t</h3>\r\n\t\t\t</td>\r\n\t\t</tr>\r\n\t</tfoot>\r\n</table>\t\r\n",

		// value: Date
		// 	the currently selected Date
		value: new Date(),

		// dayWidth: String
		// 	How to represent the days of the week in the calendar header. See dojo.date.locale
		dayWidth: "narrow",

		setValue: function(/*Date*/ value){
			// summary: set the current date and update the UI.  If the date is disabled, the selection will
			//	not change, but the display will change to the corresponding month.
			if(!this.value || dojo.date.compare(value, this.value)){
				value = new Date(value);
				this.displayMonth = new Date(value);
				if(!this.isDisabledDate(value, this.lang)){
					this.value = value;
					this.value.setHours(0,0,0,0);
					this.onChange(this.value);
				}
				this._populateGrid();
			}
		},

		_setText: function(node, text){
			while(node.firstChild){
				node.removeChild(node.firstChild);
			}
			node.appendChild(dojo.doc.createTextNode(text));
		},

		_populateGrid: function(){
			var month = this.displayMonth;
			month.setDate(1);
			var firstDay = month.getDay();
			var daysInMonth = dojo.date.getDaysInMonth(month);
			var daysInPreviousMonth = dojo.date.getDaysInMonth(dojo.date.add(month, "month", -1));
			var today = new Date();
			var selected = this.value;

			var dayOffset = dojo.cldr.supplemental.getFirstDayOfWeek(this.lang);
			if(dayOffset > firstDay){ dayOffset -= 7; }

			// Iterate through dates in the calendar and fill in date numbers and style info
			dojo.query(".dijitCalendarDateTemplate", this.domNode).forEach(function(template, i){
				i += dayOffset;
				var date = new Date(month);
				var number, clazz = "dijitCalendar", adj = 0;

				if(i < firstDay){
					number = daysInPreviousMonth - firstDay + i + 1;
					adj = -1;
					clazz += "Previous";
				}else if(i >= (firstDay + daysInMonth)){
					number = i - firstDay - daysInMonth + 1;
					adj = 1;
					clazz += "Next";
				}else{
					number = i - firstDay + 1;
					clazz += "Current";
				}

				if(adj){
					date = dojo.date.add(date, "month", adj);
				}
				date.setDate(number);

				if(!dojo.date.compare(date, today, "date")){
					clazz = "dijitCalendarCurrentDate " + clazz;
				}

				if(!dojo.date.compare(date, selected, "date")){
					clazz = "dijitCalendarSelectedDate " + clazz;
				}

				if(this.isDisabledDate(date, this.lang)){
					clazz = "dijitCalendarDisabledDate " + clazz;
				}

				var clazz2 = this.getClassForDate(date, this.lang);
				if(clazz2){
					clazz += clazz2 + " " + clazz;
				}

				template.className =  clazz + "Month dijitCalendarDateTemplate";
				template.dijitDateValue = date.valueOf();
				var label = dojo.query(".dijitCalendarDateLabel", template)[0];
				this._setText(label, date.getDate());
			}, this);

			// Fill in localized month name
			var monthNames = dojo.date.locale.getNames('months', 'wide', 'standAlone', this.lang);
			this._setText(this.monthLabelNode, monthNames[month.getMonth()]);

			// Fill in localized prev/current/next years
			var y = month.getFullYear() - 1;
			var d = new Date();
			dojo.forEach(["previous", "current", "next"], function(name){
				d.setFullYear(y++);
				this._setText(this[name+"YearLabelNode"],
					dojo.date.locale.format(d, {selector:'year', locale:this.lang}));
			}, this);

			// Set up repeating mouse behavior
			var _this = this;
			var typematic = function(nodeProp, dateProp, adj){
				dijit.typematic.addMouseListener(_this[nodeProp], _this, function(count){
					if(count >= 0){ _this._adjustDisplay(dateProp, adj); }
				}, 0.8, 500);
			};
			typematic("incrementMonth", "month", 1);
			typematic("decrementMonth", "month", -1);
			typematic("nextYearLabelNode", "year", 1);
			typematic("previousYearLabelNode", "year", -1);
		},

		goToToday: function(){
			this.setValue(new Date());
		},

		postCreate: function(){
			this.inherited(arguments);
			
			var cloneClass = dojo.hitch(this, function(clazz, n){
				var template = dojo.query(clazz, this.domNode)[0];
	 			for(var i=0; i<n; i++){
					template.parentNode.appendChild(template.cloneNode(true));
				}
			});

			// clone the day label and calendar day templates 6 times to make 7 columns
			cloneClass(".dijitCalendarDayLabelTemplate", 6);
			cloneClass(".dijitCalendarDateTemplate", 6);

			// now make 6 week rows
			cloneClass(".dijitCalendarWeekTemplate", 5);

			// insert localized day names in the header
			var dayNames = dojo.date.locale.getNames('days', this.dayWidth, 'standAlone', this.lang);
			var dayOffset = dojo.cldr.supplemental.getFirstDayOfWeek(this.lang);
			dojo.query(".dijitCalendarDayLabel", this.domNode).forEach(function(label, i){
				this._setText(label, dayNames[(i + dayOffset) % 7]);
			}, this);

			// Fill in spacer element with all the month names (invisible) so that the maximum width will affect layout
			var monthNames = dojo.date.locale.getNames('months', 'wide', 'standAlone', this.lang);
			dojo.forEach(monthNames, function(name){
				var monthSpacer = dojo.doc.createElement("div");
				this._setText(monthSpacer, name);
				this.monthLabelSpacer.appendChild(monthSpacer);
			}, this);

			this.value = null;
			this.setValue(new Date());
		},

		_adjustDisplay: function(/*String*/part, /*int*/amount){
			this.displayMonth = dojo.date.add(this.displayMonth, part, amount);
			this._populateGrid();
		},

		_onDayClick: function(/*Event*/evt){
			var node = evt.target;
			dojo.stopEvent(evt);
			while(!node.dijitDateValue){
				node = node.parentNode;
			}
			if(!dojo.hasClass(node, "dijitCalendarDisabledDate")){
				this.setValue(node.dijitDateValue);
				this.onValueSelected(this.value);
			}
		},

		onValueSelected: function(/*Date*/date){
			// summary: a date cell was selected.  It may be the same as the previous value.
		},

		onChange: function(/*Date*/date){
			// summary: called only when the selected date has changed
		},

		isDisabledDate: function(/*Date*/dateObject, /*String?*/locale){
			// summary:
			//	May be overridden to disable certain dates in the calendar e.g. `isDisabledDate=dojo.date.locale.isWeekend`
/*=====
			return false; // Boolean
=====*/
		},

		getClassForDate: function(/*Date*/dateObject, /*String?*/locale){
			// summary:
			//  May be overridden to return CSS classes to associate with the date entry for the given dateObject,
			//  for example to indicate a holiday in specified locale.

/*=====
			return ""; // String
=====*/
		}
	}
);

}

if(!dojo._hasResource["dijit.form._FormWidget"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dijit.form._FormWidget"] = true;
dojo.provide("dijit.form._FormWidget");




dojo.declare("dijit.form._FormWidget", [dijit._Widget, dijit._Templated],
{
	/*
	Summary:
		_FormWidget's correspond to native HTML elements such as <checkbox> or <button>.
		Each _FormWidget represents a single HTML element.

		All these widgets should have these attributes just like native HTML input elements.
		You can set them during widget construction.

		They also share some common methods.
	*/

	// baseClass: String
	//		Root CSS class of the widget (ex: dijitTextBox), used to add CSS classes of widget
	//		(ex: "dijitTextBox dijitTextBoxInvalid dijitTextBoxFocused dijitTextBoxInvalidFocused")
	//		See _setStateClass().
	baseClass: "",

	// name: String
	//		Name used when submitting form; same as "name" attribute or plain HTML elements
	name: "",

	// alt: String
	//		Corresponds to the native HTML <input> element's attribute.
	alt: "",

	// value: String
	//		Corresponds to the native HTML <input> element's attribute.
	value: "",

	// type: String
	//		Corresponds to the native HTML <input> element's attribute.
	type: "text",

	// tabIndex: Integer
	//		Order fields are traversed when user hits the tab key
	tabIndex: "0",

	// disabled: Boolean
	//		Should this widget respond to user input?
	//		In markup, this is specified as "disabled='disabled'", or just "disabled".
	disabled: false,

	// readOnly: Boolean
	//		Should this widget respond to user input?
	//		In markup, this is specified as "readOnly".
	//		Similar to disabled except readOnly form values are submitted
	readOnly: false,

	// intermediateChanges: Boolean
	//		Fires onChange for each value change or only on demand
	intermediateChanges: false,

	// These mixins assume that the focus node is an INPUT, as many but not all _FormWidgets are.
	// Don't attempt to mixin the 'type', 'name' attributes here programatically -- they must be declared
	// directly in the template as read by the parser in order to function. IE is known to specifically 
	// require the 'name' attribute at element creation time.
	attributeMap: dojo.mixin(dojo.clone(dijit._Widget.prototype.attributeMap),
		{value:"focusNode", disabled:"focusNode", readOnly:"focusNode", id:"focusNode", tabIndex:"focusNode", alt:"focusNode"}),

	setAttribute: function(/*String*/ attr, /*anything*/ value){
		this.inherited(arguments);
		switch(attr){
			case "disabled":
				var tabIndexNode = this[this.attributeMap['tabIndex']||'domNode'];
				if(value){
					//reset those, because after the domNode is disabled, we can no longer receive
					//mouse related events, see #4200
					this._hovering = false;
					this._active = false;
					// remove the tabIndex, especially for FF
					tabIndexNode.removeAttribute('tabIndex');
				}else{
					tabIndexNode.setAttribute('tabIndex', this.tabIndex);
				}
				dijit.setWaiState(this[this.attributeMap['disabled']||'domNode'], "disabled", value);
				this._setStateClass();
		}
	},

	setDisabled: function(/*Boolean*/ disabled){
		// summary:
		//		Set disabled state of widget (Deprecated).
		dojo.deprecated("setDisabled("+disabled+") is deprecated. Use setAttribute('disabled',"+disabled+") instead.", "", "2.0");
		this.setAttribute('disabled', disabled);
	},


	_onMouse : function(/*Event*/ event){
		// summary:
		//	Sets _hovering, _active, and stateModifier properties depending on mouse state,
		//	then calls setStateClass() to set appropriate CSS classes for this.domNode.
		//
		//	To get a different CSS class for hover, send onmouseover and onmouseout events to this method.
		//	To get a different CSS class while mouse button is depressed, send onmousedown to this method.

		var mouseNode = event.currentTarget;
		if(mouseNode && mouseNode.getAttribute){
			this.stateModifier = mouseNode.getAttribute("stateModifier") || "";
		}

		if(!this.disabled){
			switch(event.type){
				case "mouseenter":	
				case "mouseover":
					this._hovering = true;
					this._active = this._mouseDown;
					break;

				case "mouseout":
				case "mouseleave":
					this._hovering = false;
					this._active = false;
					break;

				case "mousedown" :
					this._active = true;
					this._mouseDown = true;
					// set a global event to handle mouseup, so it fires properly
					//	even if the cursor leaves the button
					var mouseUpConnector = this.connect(dojo.body(), "onmouseup", function(){
						this._active = false;
						this._mouseDown = false;
						this._setStateClass();
						this.disconnect(mouseUpConnector);
					});
					if(this.isFocusable()){ this.focus(); }
					break;
			}
			this._setStateClass();
		}
	},

	isFocusable: function(){
		return !this.disabled && !this.readOnly && this.focusNode && (dojo.style(this.domNode, "display") != "none");
	},

	focus: function(){
		setTimeout(dojo.hitch(this, dijit.focus, this.focusNode), 0); // cannot call focus() from an event handler directly
	},

	_setStateClass: function(){
		// summary
		//	Update the visual state of the widget by setting the css classes on this.domNode
		//  (or this.stateNode if defined) by combining this.baseClass with
		//	various suffixes that represent the current widget state(s).
		//
		//	In the case where a widget has multiple
		//	states, it sets the class based on all possible
		//  combinations.  For example, an invalid form widget that is being hovered
		//	will be "dijitInput dijitInputInvalid dijitInputHover dijitInputInvalidHover".
		//
		//	For complex widgets with multiple regions, there can be various hover/active states,
		//	such as "Hover" or "CloseButtonHover" (for tab buttons).
		//	This is controlled by a stateModifier="CloseButton" attribute on the close button node.
		//
		//	The widget may have one or more of the following states, determined
		//	by this.state, this.checked, this.valid, and this.selected:
		//		Error - ValidationTextBox sets this.state to "Error" if the current input value is invalid
		//		Checked - ex: a checkmark or a ToggleButton in a checked state, will have this.checked==true
		//		Selected - ex: currently selected tab will have this.selected==true
		//
		//	In addition, it may have one or more of the following states,
		//	based on this.disabled and flags set in _onMouse (this._active, this._hovering, this._focused):
		//		Disabled	- if the widget is disabled
		//		Active		- if the mouse (or space/enter key?) is being pressed down
		//		Focused		- if the widget has focus
		//		Hover		- if the mouse is over the widget

		// Get original (non state related, non baseClass related) class specified in template
		if(!("staticClass" in this)){
			this.staticClass = (this.stateNode||this.domNode).className;
		}

		// Compute new set of classes
		var classes = [ this.baseClass ];

		function multiply(modifier){
			classes=classes.concat(dojo.map(classes, function(c){ return c+modifier; }), "dijit"+modifier);
		}

		if(this.checked){
			multiply("Checked");
		}
		if(this.state){
			multiply(this.state);
		}
		if(this.selected){
			multiply("Selected");
		}

		if(this.disabled){
			multiply("Disabled");
		}else if(this.readOnly){
			multiply("ReadOnly");
		}else if(this._active){
			multiply(this.stateModifier+"Active");
		}else{
			if(this._focused){
				multiply("Focused");
			}
			if(this._hovering){
				multiply(this.stateModifier+"Hover");
			}
		}

		(this.stateNode || this.domNode).className = this.staticClass + " " + classes.join(" ");
	},

	onChange: function(newValue){
		// summary: callback when value is changed
	},

	_onChangeMonitor: 'value',
	_onChangeActive: false,

	_handleOnChange: function(/*anything*/ newValue, /*Boolean, optional*/ priorityChange){
		// summary: set the value of the widget.
		this._lastValue = newValue;
		if(this._lastValueReported == undefined && (priorityChange === null || !this._onChangeActive)){
			this._resetValue = this._lastValueReported = newValue;
		}
		if((this.intermediateChanges || priorityChange || priorityChange === undefined) && 
			((newValue && newValue.toString)?newValue.toString():newValue) !== ((this._lastValueReported && this._lastValueReported.toString)?this._lastValueReported.toString():this._lastValueReported)){
			this._lastValueReported = newValue;
			if(this._onChangeActive){ this.onChange(newValue); }
		}
	},

	reset: function(){
		this._hasBeenBlurred = false;
		if(this.setValue && !this._getValueDeprecated){
			this.setValue(this._resetValue, true);
		}else if(this._onChangeMonitor){
			this.setAttribute(this._onChangeMonitor, (this._resetValue !== undefined && this._resetValue !== null)? this._resetValue : '');
		}
	},

	create: function(){
		this.inherited(arguments);
		this._onChangeActive = true;
		this._setStateClass();
	},
	
	destroy: function(){
		if(this._layoutHackHandle){
			clearTimeout(this._layoutHackHandle);
		}
		this.inherited(arguments);
	},

	setValue: function(/*String*/ value){
		dojo.deprecated("dijit.form._FormWidget:setValue("+value+") is deprecated.  Use setAttribute('value',"+value+") instead.", "", "2.0");
		this.setAttribute('value', value);
	},

	_getValueDeprecated: true, // Form uses this, remove when getValue is removed
	getValue: function(){
		dojo.deprecated("dijit.form._FormWidget:getValue() is deprecated.  Use widget.value instead.", "", "2.0");
		return this.value;
	},

	_layoutHack: function(){
		// summary: work around table sizing bugs on FF2 by forcing redraw
		if(dojo.isFF == 2){
			var node=this.domNode;
			var old = node.style.opacity;
			node.style.opacity = "0.999";
			this._layoutHackHandle = setTimeout(dojo.hitch(this, function(){
				this._layoutHackHandle = null;
				node.style.opacity = old;
			}), 0);
		}
	}
});

dojo.declare("dijit.form._FormValueWidget", dijit.form._FormWidget,
{
	/*
	Summary:
		_FormValueWidget's correspond to native HTML elements such as <input> or <select> that have user changeable values.
		Each _ValueWidget represents a single input value, and has a (possibly hidden) <input> element,
		to which it serializes its input value, so that form submission (either normal submission or via FormBind?)
		works as expected.
	*/

	attributeMap: dojo.mixin(dojo.clone(dijit.form._FormWidget.prototype.attributeMap),
		{value:""}),

	postCreate: function(){
		this.setValue(this.value, null);
	},

	setValue: function(/*anything*/ newValue, /*Boolean, optional*/ priorityChange){
		// summary: set the value of the widget.
		this.value = newValue;
		this._handleOnChange(newValue, priorityChange);
	},

	_getValueDeprecated: false, // remove when _FormWidget:getValue is removed
	getValue: function(){
		// summary: get the value of the widget.
		return this._lastValue;
	},

	undo: function(){
		// summary: restore the value to the last value passed to onChange
		this.setValue(this._lastValueReported, false);
	},

	_valueChanged: function(){
		var v = this.getValue();
		var lv = this._lastValueReported;
		// Equality comparison of objects such as dates are done by reference so
		// two distinct objects are != even if they have the same data. So use
		// toStrings in case the values are objects.
		return ((v !== null && (v !== undefined) && v.toString)?v.toString():'') !== ((lv !== null && (lv !== undefined) && lv.toString)?lv.toString():'');
	},

	_onKeyPress: function(e){
		if(e.keyCode == dojo.keys.ESCAPE && !e.shiftKey && !e.ctrlKey && !e.altKey){
			if(this._valueChanged()){
				this.undo();
				dojo.stopEvent(e);
				return false;
			}
		}
		return true;
	}
});

}

if(!dojo._hasResource["dijit.form.TextBox"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dijit.form.TextBox"] = true;
dojo.provide("dijit.form.TextBox");



dojo.declare(
	"dijit.form.TextBox",
	dijit.form._FormValueWidget,
	{
		//	summary:
		//		A base class for textbox form inputs
		//
		//	trim: Boolean
		//		Removes leading and trailing whitespace if true.  Default is false.
		trim: false,

		//	uppercase: Boolean
		//		Converts all characters to uppercase if true.  Default is false.
		uppercase: false,

		//	lowercase: Boolean
		//		Converts all characters to lowercase if true.  Default is false.
		lowercase: false,

		//	propercase: Boolean
		//		Converts the first character of each word to uppercase if true.
		propercase: false,

		//	maxLength: String
		//		HTML INPUT tag maxLength declaration.
		maxLength: "",

		templateString:"<input class=\"dijit dijitReset dijitLeft\" dojoAttachPoint='textbox,focusNode' name=\"${name}\"\r\n\tdojoAttachEvent='onmouseenter:_onMouse,onmouseleave:_onMouse,onfocus:_onMouse,onblur:_onMouse,onkeypress:_onKeyPress,onkeyup'\r\n\tautocomplete=\"off\" type=\"${type}\"\r\n\t/>\r\n",
		baseClass: "dijitTextBox",

		attributeMap: dojo.mixin(dojo.clone(dijit.form._FormValueWidget.prototype.attributeMap),
			{maxLength:"focusNode"}),

		getDisplayedValue: function(){
			//	summary:
			//		Returns the formatted value that the user sees in the textbox, which may be different
			//		from the serialized value that's actually sent to the server (see dijit.form.ValidationTextBox.serialize)
			return this.filter(this.textbox.value);
		},

		getValue: function(){
			return this.parse(this.getDisplayedValue(), this.constraints);
		},

		setValue: function(value, /*Boolean?*/ priorityChange, /*String?*/ formattedValue){
			//	summary: 
			//		Sets the value of the widget to "value" which can be of
			//		any type as determined by the widget.
			//
			//	value:
			//		The visual element value is also set to a corresponding,
			//		but not necessarily the same, value.
			//
			//	formattedValue:
			//		If specified, used to set the visual element value,
			//		otherwise a computed visual value is used.
			//
			//	priorityChange:
			//		If true, an onChange event is fired immediately instead of 
			//		waiting for the next blur event.

			var filteredValue = this.filter(value);
			if((((typeof filteredValue == typeof value) && (value !== undefined/*#5317*/)) || (value === null/*#5329*/)) && (formattedValue == null || formattedValue == undefined)){
				formattedValue = this.format(filteredValue, this.constraints);
			}
			if(formattedValue != null && formattedValue != undefined){
				this.textbox.value = formattedValue;
			}
			dijit.form.TextBox.superclass.setValue.call(this, filteredValue, priorityChange);
		},

		setDisplayedValue: function(/*String*/value, /*Boolean?*/ priorityChange){
			//	summary: 
			//		Sets the value of the visual element to the string "value".
			//		The widget value is also set to a corresponding,
			//		but not necessarily the same, value.
			//
			//	priorityChange:
			//		If true, an onChange event is fired immediately instead of 
			//		waiting for the next blur event.

			this.textbox.value = value;
			this.setValue(this.getValue(), priorityChange);
		},

		format: function(/* String */ value, /* Object */ constraints){
			//	summary:
			//		Replacable function to convert a value to a properly formatted string
			return ((value == null || value == undefined) ? "" : (value.toString ? value.toString() : value));
		},

		parse: function(/* String */ value, /* Object */ constraints){
			//	summary:
			//		Replacable function to convert a formatted string to a value
			return value;
		},

		postCreate: function(){
			// setting the value here is needed since value="" in the template causes "undefined"
			// and setting in the DOM (instead of the JS object) helps with form reset actions
			this.textbox.setAttribute("value", this.getDisplayedValue());
			this.inherited(arguments);

			/*#5297:if(this.srcNodeRef){
				dojo.style(this.textbox, "cssText", this.style);
				this.textbox.className += " " + this["class"];
			}*/
			this._layoutHack();
		},

		filter: function(val){
			//	summary:
			//		Apply specified filters to textbox value
			if(val === null || val === undefined){ return ""; }
			else if(typeof val != "string"){ return val; }
			if(this.trim){
				val = dojo.trim(val);
			}
			if(this.uppercase){
				val = val.toUpperCase();
			}
			if(this.lowercase){
				val = val.toLowerCase();
			}
			if(this.propercase){
				val = val.replace(/[^\s]+/g, function(word){
					return word.substring(0,1).toUpperCase() + word.substring(1);
				});
			}
			return val;
		},

		_setBlurValue: function(){
			this.setValue(this.getValue(), (this.isValid ? this.isValid() : true));
		},

		_onBlur: function(){
			this._setBlurValue();
			this.inherited(arguments);
		},

		onkeyup: function(){
			//	summary:
			//		User replaceable keyup event handler
		}
	}
);

dijit.selectInputText = function(/*DomNode*/element, /*Number?*/ start, /*Number?*/ stop){
	//	summary:
	//		Select text in the input element argument, from start (default 0), to stop (default end).

	// TODO: use functions in _editor/selection.js?
	var _window = dojo.global;
	var _document = dojo.doc;
	element = dojo.byId(element);
	if(isNaN(start)){ start = 0; }
	if(isNaN(stop)){ stop = element.value ? element.value.length : 0; }
	element.focus();
	if(_document["selection"] && dojo.body()["createTextRange"]){ // IE
		if(element.createTextRange){
			var range = element.createTextRange();
			with(range){
				collapse(true);
				moveStart("character", start);
				moveEnd("character", stop);
				select();
			}
		}
	}else if(_window["getSelection"]){
		var selection = _window.getSelection();
		// FIXME: does this work on Safari?
		if(element.setSelectionRange){
			element.setSelectionRange(start, stop);
		}
	}
}

}

if(!dojo._hasResource["dijit.Tooltip"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dijit.Tooltip"] = true;
dojo.provide("dijit.Tooltip");




dojo.declare(
	"dijit._MasterTooltip",
	[dijit._Widget, dijit._Templated],
	{
		// summary
		//		Internal widget that holds the actual tooltip markup,
		//		which occurs once per page.
		//		Called by Tooltip widgets which are just containers to hold
		//		the markup

		// duration: Integer
		//		Milliseconds to fade in/fade out
		duration: 200,

		templateString:"<div class=\"dijitTooltip dijitTooltipLeft\" id=\"dojoTooltip\">\r\n\t<div class=\"dijitTooltipContainer dijitTooltipContents\" dojoAttachPoint=\"containerNode\" waiRole='alert'></div>\r\n\t<div class=\"dijitTooltipConnector\"></div>\r\n</div>\r\n",

		postCreate: function(){
			dojo.body().appendChild(this.domNode);

			this.bgIframe = new dijit.BackgroundIframe(this.domNode);

			// Setup fade-in and fade-out functions.
			this.fadeIn = dojo.fadeIn({ node: this.domNode, duration: this.duration, onEnd: dojo.hitch(this, "_onShow") });
			this.fadeOut = dojo.fadeOut({ node: this.domNode, duration: this.duration, onEnd: dojo.hitch(this, "_onHide") });

		},

		show: function(/*String*/ innerHTML, /*DomNode*/ aroundNode, /*String[]?*/ position){
			// summary:
			//	Display tooltip w/specified contents to right specified node
			//	(To left if there's no space on the right, or if LTR==right)

			if(this.aroundNode && this.aroundNode === aroundNode){
				return;
			}

			if(this.fadeOut.status() == "playing"){
				// previous tooltip is being hidden; wait until the hide completes then show new one
				this._onDeck=arguments;
				return;
			}
			this.containerNode.innerHTML=innerHTML;

			// Firefox bug. when innerHTML changes to be shorter than previous
			// one, the node size will not be updated until it moves.
			this.domNode.style.top = (this.domNode.offsetTop + 1) + "px";

			// position the element and change CSS according to position[] (a list of positions to try)
			var align = {};
			var ltr = this.isLeftToRight();
			dojo.forEach( (position && position.length) ? position : dijit.Tooltip.defaultPosition, function(pos){
				switch(pos){
					case "after":				
						align[ltr ? "BR" : "BL"] = ltr ? "BL" : "BR";
						break;
					case "before":
						align[ltr ? "BL" : "BR"] = ltr ? "BR" : "BL";
						break;
					case "below":
						// first try to align left borders, next try to align right borders (or reverse for RTL mode)
						align[ltr ? "BL" : "BR"] = ltr ? "TL" : "TR";
						align[ltr ? "BR" : "BL"] = ltr ? "TR" : "TL";
						break;
					case "above":
					default:
						// first try to align left borders, next try to align right borders (or reverse for RTL mode)
						align[ltr ? "TL" : "TR"] = ltr ? "BL" : "BR";
						align[ltr ? "TR" : "TL"] = ltr ? "BR" : "BL";
						break;
				}
			});
			var pos = dijit.placeOnScreenAroundElement(this.domNode, aroundNode, align, dojo.hitch(this, "orient"));

			// show it
			dojo.style(this.domNode, "opacity", 0);
			this.fadeIn.play();
			this.isShowingNow = true;
			this.aroundNode = aroundNode;
		},

		orient: function(/* DomNode */ node, /* String */ aroundCorner, /* String */ tooltipCorner){
			// summary: private function to set CSS for tooltip node based on which position it's in
			node.className = "dijitTooltip " +
				{
					"BL-TL": "dijitTooltipBelow dijitTooltipABLeft",
					"TL-BL": "dijitTooltipAbove dijitTooltipABLeft",
					"BR-TR": "dijitTooltipBelow dijitTooltipABRight",
					"TR-BR": "dijitTooltipAbove dijitTooltipABRight",
					"BR-BL": "dijitTooltipRight",
					"BL-BR": "dijitTooltipLeft"
				}[aroundCorner + "-" + tooltipCorner];
		},

		_onShow: function(){
			if(dojo.isIE){
				// the arrow won't show up on a node w/an opacity filter
				this.domNode.style.filter="";
			}
		},

		hide: function(aroundNode){
			// summary: hide the tooltip
			if(!this.aroundNode || this.aroundNode !== aroundNode){
				return;
			}
			if(this._onDeck){
				// this hide request is for a show() that hasn't even started yet;
				// just cancel the pending show()
				this._onDeck=null;
				return;
			}
			this.fadeIn.stop();
			this.isShowingNow = false;
			this.aroundNode = null;
			this.fadeOut.play();
		},

		_onHide: function(){
			this.domNode.style.cssText="";	// to position offscreen again
			if(this._onDeck){
				// a show request has been queued up; do it now
				this.show.apply(this, this._onDeck);
				this._onDeck=null;
			}
		}

	}
);

dijit.showTooltip = function(/*String*/ innerHTML, /*DomNode*/ aroundNode, /*String[]?*/ position){
	// summary:
	//	Display tooltip w/specified contents in specified position.
	//	See description of dijit.Tooltip.defaultPosition for details on position parameter.
	//	If position is not specified then dijit.Tooltip.defaultPosition is used.
	if(!dijit._masterTT){ dijit._masterTT = new dijit._MasterTooltip(); }
	return dijit._masterTT.show(innerHTML, aroundNode, position);
};

dijit.hideTooltip = function(aroundNode){
	// summary: hide the tooltip
	if(!dijit._masterTT){ dijit._masterTT = new dijit._MasterTooltip(); }
	return dijit._masterTT.hide(aroundNode);
};

dojo.declare(
	"dijit.Tooltip",
	dijit._Widget,
	{
		// summary
		//		Pops up a tooltip (a help message) when you hover over a node.

		// label: String
		//		Text to display in the tooltip.
		//		Specified as innerHTML when creating the widget from markup.
		label: "",

		// showDelay: Integer
		//		Number of milliseconds to wait after hovering over/focusing on the object, before
		//		the tooltip is displayed.
		showDelay: 400,

		// connectId: String[]
		//		Id(s) of domNodes to attach the tooltip to.
		//		When user hovers over any of the specified dom nodes, the tooltip will appear.
		connectId: [],

		//	position: String[]
		//		See description of dijit.Tooltip.defaultPosition for details on position parameter.
		position: [],

		postCreate: function(){
			if(this.srcNodeRef){
				this.srcNodeRef.style.display = "none";
			}

			this._connectNodes = [];
			
			dojo.forEach(this.connectId, function(id) {
				var node = dojo.byId(id);
				if (node) {
					this._connectNodes.push(node);
					dojo.forEach(["onMouseOver", "onMouseOut", "onFocus", "onBlur", "onHover", "onUnHover"], function(event){
						this.connect(node, event.toLowerCase(), "_"+event);
					}, this);
					if(dojo.isIE){
						// BiDi workaround
						node.style.zoom = 1;
					}
				}
			}, this);
		},

		_onMouseOver: function(/*Event*/ e){
			this._onHover(e);
		},

		_onMouseOut: function(/*Event*/ e){
			if(dojo.isDescendant(e.relatedTarget, e.target)){
				// false event; just moved from target to target child; ignore.
				return;
			}
			this._onUnHover(e);
		},

		_onFocus: function(/*Event*/ e){
			this._focus = true;
			this._onHover(e);
			this.inherited(arguments);
		},
		
		_onBlur: function(/*Event*/ e){
			this._focus = false;
			this._onUnHover(e);
			this.inherited(arguments);
		},

		_onHover: function(/*Event*/ e){
			if(!this._showTimer){
				var target = e.target;
				this._showTimer = setTimeout(dojo.hitch(this, function(){this.open(target)}), this.showDelay);
			}
		},

		_onUnHover: function(/*Event*/ e){
			// keep a tooltip open if the associated element has focus
			if(this._focus){ return; }
			if(this._showTimer){
				clearTimeout(this._showTimer);
				delete this._showTimer;
			}
			this.close();
		},

		open: function(/*DomNode*/ target){
 			// summary: display the tooltip; usually not called directly.
			target = target || this._connectNodes[0];
			if(!target){ return; }

			if(this._showTimer){
				clearTimeout(this._showTimer);
				delete this._showTimer;
			}
			dijit.showTooltip(this.label || this.domNode.innerHTML, target, this.position);
			
			this._connectNode = target;
		},

		close: function(){
			// summary: hide the tooltip; usually not called directly.
			dijit.hideTooltip(this._connectNode);
			delete this._connectNode;
			if(this._showTimer){
				clearTimeout(this._showTimer);
				delete this._showTimer;
			}
		},

		uninitialize: function(){
			this.close();
		}
	}
);

// dijit.Tooltip.defaultPosition: String[]
//		This variable controls the position of tooltips, if the position is not specified to
//		the Tooltip widget or *TextBox widget itself.  It's an array of strings with the following values:
//
//			* before: places tooltip to the left of the target node/widget, or to the right in
//			  the case of RTL scripts like Hebrew and Arabic
//			* after: places tooltip to the right of the target node/widget, or to the left in
//			  the case of RTL scripts like Hebrew and Arabic
//			* above: tooltip goes above target node
//			* below: tooltip goes below target node
//
//		The list is positions is tried, in order, until a position is found where the tooltip fits
//		within the viewport.
//
//		Be careful setting this parameter.  A value of "above" may work fine until the user scrolls
//		the screen so that there's no room above the target node.   Nodes with drop downs, like
//		DropDownButton or FilteringSelect, are especially problematic, in that you need to be sure
//		that the drop down and tooltip don't overlap, even when the viewport is scrolled so that there
//		is only room below (or above) the target node, but not both.
dijit.Tooltip.defaultPosition = ["after", "before"];

}

if(!dojo._hasResource["dijit.form.ValidationTextBox"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dijit.form.ValidationTextBox"] = true;
dojo.provide("dijit.form.ValidationTextBox");








/*=====
	dijit.form.ValidationTextBox.__Constraints = function(){
		// locale: String
		//		locale used for validation, picks up value from this widget's lang attribute
		// _flags_: anything
		//		various flags passed to regExpGen function
		this.locale = "";
		this._flags_ = "";
	}
=====*/

dojo.declare(
	"dijit.form.ValidationTextBox",
	dijit.form.TextBox,
	{
		// summary:
		//		A TextBox subclass with the ability to validate content of various types and provide user feedback.

		templateString:"<div class=\"dijit dijitReset dijitInlineTable dijitLeft\"\r\n\tid=\"widget_${id}\"\r\n\tdojoAttachEvent=\"onmouseenter:_onMouse,onmouseleave:_onMouse,onmousedown:_onMouse\" waiRole=\"presentation\"\r\n\t><div style=\"overflow:hidden;\"\r\n\t\t><div class=\"dijitReset dijitValidationIcon\"><br></div\r\n\t\t><div class=\"dijitReset dijitValidationIconText\">&Chi;</div\r\n\t\t><div class=\"dijitReset dijitInputField\"\r\n\t\t\t><input class=\"dijitReset\" dojoAttachPoint='textbox,focusNode' dojoAttachEvent='onfocus:_update,onkeyup:_onkeyup,onblur:_onMouse,onkeypress:_onKeyPress' autocomplete=\"off\"\r\n\t\t\ttype='${type}' name='${name}'\r\n\t\t/></div\r\n\t></div\r\n></div>\r\n",
		baseClass: "dijitTextBox",

		// default values for new subclass properties
		// required: Boolean
		//		Can be true or false, default is false.
		required: false,

		// promptMessage: String
		//		Hint string
		promptMessage: "",

		// invalidMessage: String
		// 		The message to display if value is invalid.
		invalidMessage: "$_unset_$", // read from the message file if not overridden

		// constraints: dijit.form.ValidationTextBox.__Constraints
		//		user-defined object needed to pass parameters to the validator functions
		constraints: {},

		// regExp: String
		//		regular expression string used to validate the input
		//		Do not specify both regExp and regExpGen
		regExp: ".*",

		// regExpGen: Function
		//		user replaceable function used to generate regExp when dependent on constraints
		//		Do not specify both regExp and regExpGen
		regExpGen: function(/*dijit.form.ValidationTextBox.__Constraints*/constraints){ return this.regExp; },

		// state: String
		//		Shows current state (ie, validation result) of input (Normal, Warning, or Error)
		state: "",

		//	tooltipPosition: String[]
		//		See description of dijit.Tooltip.defaultPosition for details on this parameter.
		tooltipPosition: [],

		setValue: function(){
			this.inherited(arguments);
			this.validate(this._focused);
		},

		validator: function(/*anything*/value, /*dijit.form.ValidationTextBox.__Constraints*/constraints){
			// summary: user replaceable function used to validate the text input against the regular expression.
			return (new RegExp("^(" + this.regExpGen(constraints) + ")"+(this.required?"":"?")+"$")).test(value) &&
				(!this.required || !this._isEmpty(value)) &&
				(this._isEmpty(value) || this.parse(value, constraints) !== undefined); // Boolean
		},

		isValid: function(/*Boolean*/ isFocused){
			// summary: Need to over-ride with your own validation code in subclasses
			return this.validator(this.textbox.value, this.constraints);
		},

		_isEmpty: function(value){
			// summary: Checks for whitespace
			return /^\s*$/.test(value); // Boolean
		},

		getErrorMessage: function(/*Boolean*/ isFocused){
			// summary: return an error message to show if appropriate
			return this.invalidMessage; // String
		},

		getPromptMessage: function(/*Boolean*/ isFocused){
			// summary: return a hint to show if appropriate
			return this.promptMessage; // String
		},

		validate: function(/*Boolean*/ isFocused){
			// summary:
			//		Called by oninit, onblur, and onkeypress.
			// description:
			//		Show missing or invalid messages if appropriate, and highlight textbox field.
			var message = "";
			var isValid = this.isValid(isFocused);
			var isEmpty = this._isEmpty(this.textbox.value);
			this.state = (isValid || (!this._hasBeenBlurred && isEmpty)) ? "" : "Error";
			this._setStateClass();
			dijit.setWaiState(this.focusNode, "invalid", isValid ? "false" : "true");
			if(isFocused){
				if(isEmpty){
					message = this.getPromptMessage(true);
				}
				if(!message && this.state == "Error"){
					message = this.getErrorMessage(true);
				}
			}
			this.displayMessage(message);
			return isValid;
		},

		// currently displayed message
		_message: "",

		displayMessage: function(/*String*/ message){
			// summary:
			//		User overridable method to display validation errors/hints.
			//		By default uses a tooltip.
			if(this._message == message){ return; }
			this._message = message;
			dijit.hideTooltip(this.domNode);
			if(message){
				dijit.showTooltip(message, this.domNode, this.tooltipPosition);
			}
		},

		_refreshState: function(){
			this.validate(this._focused);
		},

		_update: function(/*Event*/e){
			this._refreshState();
			this._onMouse(e);	// update CSS classes
		},

		_onkeyup: function(/*Event*/e){
			this._update(e);
			this.onkeyup(e);
		},

		//////////// INITIALIZATION METHODS ///////////////////////////////////////

		constructor: function(){
			this.constraints = {};
		},

		postMixInProperties: function(){
			this.inherited(arguments);
			this.constraints.locale = this.lang;
			this.messages = dojo.i18n.getLocalization("dijit.form", "validate", this.lang);
			if(this.invalidMessage == "$_unset_$"){ this.invalidMessage = this.messages.invalidMessage; }
			var p = this.regExpGen(this.constraints);
			this.regExp = p;
		}
	}
);

dojo.declare(
	"dijit.form.MappedTextBox",
	dijit.form.ValidationTextBox,
	{
		// summary:
		//		A dijit.form.ValidationTextBox subclass which provides a visible formatted display and a serializable
		//		value in a hidden input field which is actually sent to the server.  The visible display may
		//		be locale-dependent and interactive.  The value sent to the server is stored in a hidden
		//		input field which uses the `name` attribute declared by the original widget.  That value sent
		//		to the serveris defined by the dijit.form.MappedTextBox.serialize method and is typically
		//		locale-neutral.

		serialize: function(/*anything*/val, /*Object?*/options){
			// summary: user replaceable function used to convert the getValue() result to a String
			return val.toString ? val.toString() : ""; // String
		},

		toString: function(){
			// summary: display the widget as a printable string using the widget's value
			var val = this.filter(this.getValue());
			return val != null ? (typeof val == "string" ? val : this.serialize(val, this.constraints)) : ""; // String
		},

		validate: function(){
			this.valueNode.value = this.toString();
			return this.inherited(arguments);
		},

		setAttribute: function(/*String*/ attr, /*anything*/ value){
			this.inherited(arguments);
			switch(attr){
				case "disabled":
					if(this.valueNode){
						this.valueNode.disabled = this.disabled;
					}
			}
		},

		postCreate: function(){
			var textbox = this.textbox;
			var valueNode = (this.valueNode = dojo.doc.createElement("input"));
			valueNode.setAttribute("type", textbox.type);
			valueNode.setAttribute("value", this.toString());
			dojo.style(valueNode, "display", "none");
			valueNode.name = this.textbox.name;
			valueNode.disabled = this.textbox.disabled;
			this.textbox.name = this.textbox.name + "_displayed_";
			this.textbox.removeAttribute("name");
			dojo.place(valueNode, textbox, "after");

			this.inherited(arguments);
		}
	}
);

/*=====
	dijit.form.RangeBoundTextBox.__Constraints = function(){
		// min: Number
		//		Minimum signed value.  Default is -Infinity
		// max: Number
		//		Maximum signed value.  Default is +Infinity
		this.min = min;
		this.max = max;
	}
=====*/

dojo.declare(
	"dijit.form.RangeBoundTextBox",
	dijit.form.MappedTextBox,
	{
		// summary:
		//		A dijit.form.MappedTextBox subclass which defines a range of valid values
		//
		// constraints: dijit.form.RangeBoundTextBox.__Constraints
		//
		// rangeMessage: String
		//		The message to display if value is out-of-range

		/*=====
		constraints: {},
		======*/
		rangeMessage: "",

		compare: function(/*anything*/val1, /*anything*/val2){
			// summary: compare 2 values
			return val1 - val2; // anything
		},

		rangeCheck: function(/*Number*/ primitive, /*dijit.form.RangeBoundTextBox.__Constraints*/ constraints){
			// summary: user replaceable function used to validate the range of the numeric input value
			var isMin = "min" in constraints;
			var isMax = "max" in constraints;
			if(isMin || isMax){
				return (!isMin || this.compare(primitive,constraints.min) >= 0) &&
					(!isMax || this.compare(primitive,constraints.max) <= 0);
			}
			return true; // Boolean
		},

		isInRange: function(/*Boolean*/ isFocused){
			// summary: Need to over-ride with your own validation code in subclasses
			return this.rangeCheck(this.getValue(), this.constraints);
		},

		isValid: function(/*Boolean*/ isFocused){
			return this.inherited(arguments) &&
				((this._isEmpty(this.textbox.value) && !this.required) || this.isInRange(isFocused)); // Boolean
		},

		getErrorMessage: function(/*Boolean*/ isFocused){
			if(dijit.form.RangeBoundTextBox.superclass.isValid.call(this, false) && !this.isInRange(isFocused)){ return this.rangeMessage; } // String
			return this.inherited(arguments);
		},

		postMixInProperties: function(){
			this.inherited(arguments);
			if(!this.rangeMessage){
				this.messages = dojo.i18n.getLocalization("dijit.form", "validate", this.lang);
				this.rangeMessage = this.messages.rangeMessage;
			}
		},

		postCreate: function(){
			this.inherited(arguments);
			if(this.constraints.min !== undefined){
				dijit.setWaiState(this.focusNode, "valuemin", this.constraints.min);
			}
			if(this.constraints.max !== undefined){
				dijit.setWaiState(this.focusNode, "valuemax", this.constraints.max);
			}
		},
		
		setValue: function(/*Number*/ value, /*Boolean?*/ priorityChange){
			dijit.setWaiState(this.focusNode, "valuenow", value);
			this.inherited('setValue', arguments);
		}
	}
);

}

if(!dojo._hasResource["dijit.form._DateTimeTextBox"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dijit.form._DateTimeTextBox"] = true;
dojo.provide("dijit.form._DateTimeTextBox");






/*=====
dojo.declare(
	"dijit.form._DateTimeTextBox.__Constraints",
	[dijit.form.RangeBoundTextBox.__Constraints, dojo.date.locale.__FormatOptions]
);
=====*/

dojo.declare(
	"dijit.form._DateTimeTextBox",
	dijit.form.RangeBoundTextBox,
	{
		// summary:
		//		A validating, serializable, range-bound date or time text box.
		//
		// constraints: dijit.form._DateTimeTextBox.__Constraints 

		/*=====
		constraints: {},
		======*/
		regExpGen: dojo.date.locale.regexp,
		compare: dojo.date.compare,
		format: function(/*Date*/ value, /*dojo.date.locale.__FormatOptions*/ constraints){
			//	summary: formats the value as a Date, according to constraints
			if(!value){ return ''; }
			return dojo.date.locale.format(value, constraints);
		},
		parse: function(/*String*/ value, /*dojo.date.locale.__FormatOptions*/ constraints){
			//	summary: parses the value as a Date, according to constraints
			return dojo.date.locale.parse(value, constraints) || undefined; /* can't return null to getValue since that's special */
		},

		serialize: dojo.date.stamp.toISOString,

		//	value: Date
		//		The value of this widget as a JavaScript Date object.  Use `getValue`/`setValue` to manipulate.
		//		When passed to the parser in markup, must be specified according to `dojo.date.stamp.fromISOString`
		value: new Date(""),	// value.toString()="NaN"

		//	popupClass: String
		//		Name of the popup widget class used to select a date/time
		popupClass: "", // default is no popup = text only
		_selector: "",

		postMixInProperties: function(){
			//dijit.form.RangeBoundTextBox.prototype.postMixInProperties.apply(this, arguments);
			this.inherited(arguments);
			if(!this.value || this.value.toString() == dijit.form._DateTimeTextBox.prototype.value.toString()){
				this.value = undefined;
			}
			var constraints = this.constraints;
			constraints.selector = this._selector;
			constraints.fullYear = true; // see #5465 - always format with 4-digit years
			var fromISO = dojo.date.stamp.fromISOString;
			if(typeof constraints.min == "string"){ constraints.min = fromISO(constraints.min); }
 			if(typeof constraints.max == "string"){ constraints.max = fromISO(constraints.max); }
		},

		_onFocus: function(/*Event*/ evt){
			// summary: open the TimePicker popup
			this._open();
		},

		setValue: function(/*Date*/ value, /*Boolean?*/ priorityChange, /*String?*/ formattedValue){
			// summary:
			//	Sets the date on this textbox.  Note that `value` must be a Javascript Date object.
			this.inherited(arguments);
			if(this._picker){
				// #3948: fix blank date on popup only
				if(!value){value=new Date();}
				this._picker.setValue(value);
			}
		},

		_open: function(){
			// summary:
			//	opens the TimePicker, and sets the onValueSelected value

			if(this.disabled || this.readOnly || !this.popupClass){return;}

			var textBox = this;

			if(!this._picker){
				var PopupProto=dojo.getObject(this.popupClass, false);
				this._picker = new PopupProto({
					onValueSelected: function(value){

						textBox.focus(); // focus the textbox before the popup closes to avoid reopening the popup
						setTimeout(dojo.hitch(textBox, "_close"), 1); // allow focus time to take

						// this will cause InlineEditBox and other handlers to do stuff so make sure it's last
						dijit.form._DateTimeTextBox.superclass.setValue.call(textBox, value, true);
					},
					lang: textBox.lang,
					constraints: textBox.constraints,
					isDisabledDate: function(/*Date*/ date){
						// summary:
						// 	disables dates outside of the min/max of the _DateTimeTextBox
						var compare = dojo.date.compare;
						var constraints = textBox.constraints;
						return constraints && (constraints.min && (compare(constraints.min, date, "date") > 0) || 
							(constraints.max && compare(constraints.max, date, "date") < 0));
					}
				});
				this._picker.setValue(this.getValue() || new Date());
			}
			if(!this._opened){
				dijit.popup.open({
					parent: this,
					popup: this._picker,
					around: this.domNode,
					onCancel: dojo.hitch(this, this._close),
					onClose: function(){ textBox._opened=false; }
				});
				this._opened=true;
			}
			
			dojo.marginBox(this._picker.domNode,{ w:this.domNode.offsetWidth });
		},

		_close: function(){
			if(this._opened){
				dijit.popup.close(this._picker);
				this._opened=false;
			}			
		},

		_onBlur: function(){
			// summary: called magically when focus has shifted away from this widget and it's dropdown
			this._close();
			if(this._picker){
				// teardown so that constraints will be rebuilt next time (redundant reference: #6002)
				this._picker.destroy();
				delete this._picker;
			}
			this.inherited(arguments);
			// don't focus on <input>.  the user has explicitly focused on something else.
		},

		getDisplayedValue:function(){
			return this.textbox.value;
		},

		setDisplayedValue:function(/*String*/ value, /*Boolean?*/ priorityChange){
			this.setValue(this.parse(value, this.constraints), priorityChange, value);
		},

		destroy: function(){
			if(this._picker){
				this._picker.destroy();
				delete this._picker;
			}
			this.inherited(arguments);
		},

		_onKeyPress: function(/*Event*/e){
			if(dijit.form._DateTimeTextBox.superclass._onKeyPress.apply(this, arguments)){
				if(this._opened && e.keyCode == dojo.keys.ESCAPE && !e.shiftKey && !e.ctrlKey && !e.altKey){
					this._close();
					dojo.stopEvent(e);
				}
			}
		}
	}
);

}

if(!dojo._hasResource["dijit.form.DateTextBox"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dijit.form.DateTextBox"] = true;
dojo.provide("dijit.form.DateTextBox");




dojo.declare(
	"dijit.form.DateTextBox",
	dijit.form._DateTimeTextBox,
	{
		// summary:
		//		A validating, serializable, range-bound date text box with a popup calendar

		popupClass: "dijit._Calendar",
		_selector: "date"
	}
);

}

if(!dojo._hasResource["dijit._TimePicker"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dijit._TimePicker"] = true;
dojo.provide("dijit._TimePicker");




/*=====
dojo.declare(
	"dijit._TimePicker.__Constraints",
	[dojo.date.locale.__FormatOptions],
	{
		// clickableIncrement: String
		//		see dijit._TimePicker.clickableIncrement
		clickableIncrement: "T00:15:00",

		// visibleIncrement: String
		//		see dijit._TimePicker.visibleIncrement
		visibleIncrement: "T01:00:00",

		// visibleRange: String
		//		see dijit._TimePicker.visibleRange
		visibleRange: "T05:00:00"
	}
);
=====*/

dojo.declare("dijit._TimePicker",
	[dijit._Widget, dijit._Templated],
	{
		//	summary:
		//		A graphical time picker.
		//		This widget is used internally by other widgets and is not accessible
		//		as a standalone widget.

		templateString:"<div id=\"widget_${id}\" class=\"dijitMenu\"\r\n    ><div dojoAttachPoint=\"upArrow\" class=\"dijitButtonNode\"><span class=\"dijitTimePickerA11yText\">&#9650;</span></div\r\n    ><div dojoAttachPoint=\"timeMenu,focusNode\" dojoAttachEvent=\"onclick:_onOptionSelected,onmouseover,onmouseout\"></div\r\n    ><div dojoAttachPoint=\"downArrow\" class=\"dijitButtonNode\"><span class=\"dijitTimePickerA11yText\">&#9660;</span></div\r\n></div>\r\n",
		baseClass: "dijitTimePicker",

		// clickableIncrement: String
		//		ISO-8601 string representing the amount by which
		//		every clickable element in the time picker increases.
		//		Set in local time, without a time zone.
		//		Example: `T00:15:00` creates 15 minute increments
		//		Must divide dijit._TimePicker.visibleIncrement evenly
		clickableIncrement: "T00:15:00",

		// visibleIncrement: String
		//		ISO-8601 string representing the amount by which
		//		every element with a visible time in the time picker increases.
		//		Set in local time, without a time zone.
		//		Example: `T01:00:00` creates text in every 1 hour increment
		visibleIncrement: "T01:00:00",

		// visibleRange: String
		//		ISO-8601 string representing the range of this TimePicker.
		//		The TimePicker will only display times in this range.
		//		Example: `T05:00:00` displays 5 hours of options
		visibleRange: "T05:00:00",

		// value: String
		//		Date to display.
		//		Defaults to current time and date.
		//		Can be a Date object or an ISO-8601 string.
		//		If you specify the GMT time zone (`-01:00`),
		//		the time will be converted to the local time in the local time zone.
		//		Otherwise, the time is considered to be in the local time zone.
		//		If you specify the date and isDate is true, the date is used.
		//		Example: if your local time zone is `GMT -05:00`,
		//		`T10:00:00` becomes `T10:00:00-05:00` (considered to be local time),
		//		`T10:00:00-01:00` becomes `T06:00:00-05:00` (4 hour difference),
		//		`T10:00:00Z` becomes `T05:00:00-05:00` (5 hour difference between Zulu and local time)
		//		`yyyy-mm-ddThh:mm:ss` is the format to set the date and time
		//		Example: `2007-06-01T09:00:00`
		value: new Date(),

		_visibleIncrement:2,
		_clickableIncrement:1,
		_totalIncrements:10,

		// constraints: dijit._TimePicker.__Constraints 
		constraints:{},

		serialize: dojo.date.stamp.toISOString,

//TODOC: what is priority?
		setValue:function(/*Date*/ date, /*Boolean*/ priority){
			// summary:
			//	Set the value of the TimePicker
			//	Redraws the TimePicker around the new date

			//dijit._TimePicker.superclass.setValue.apply(this, arguments);
			this.value=date;
			this._showText();
		},

		isDisabledDate: function(/*Date*/dateObject, /*String?*/locale){
			// summary:
			//	May be overridden to disable certain dates in the TimePicker e.g. `isDisabledDate=dojo.date.locale.isWeekend`
			return false; // Boolean
		},

		_showText:function(){
			this.timeMenu.innerHTML = "";
			var fromIso = dojo.date.stamp.fromISOString;
			this._clickableIncrementDate=fromIso(this.clickableIncrement);
			this._visibleIncrementDate=fromIso(this.visibleIncrement);
			this._visibleRangeDate=fromIso(this.visibleRange);
			// get the value of the increments and the range in seconds (since 00:00:00) to find out how many divs to create
			var sinceMidnight = function(/*Date*/ date){
				return date.getHours() * 60 * 60 + date.getMinutes() * 60 + date.getSeconds();
			};

			var clickableIncrementSeconds = sinceMidnight(this._clickableIncrementDate);
			var visibleIncrementSeconds = sinceMidnight(this._visibleIncrementDate);
			var visibleRangeSeconds = sinceMidnight(this._visibleRangeDate);

			// round reference date to previous visible increment
			var time = this.value.getTime();
			this._refDate = new Date(time - time % (visibleIncrementSeconds*1000));
			this._refDate.setFullYear(1970,0,1); // match parse defaults

			// assume clickable increment is the smallest unit
			this._clickableIncrement = 1;
			// divide the visible range by the clickable increment to get the number of divs to create
			// example: 10:00:00/00:15:00 -> display 40 divs
			this._totalIncrements = visibleRangeSeconds / clickableIncrementSeconds;
			// divide the visible increments by the clickable increments to get how often to display the time inline
			// example: 01:00:00/00:15:00 -> display the time every 4 divs
			this._visibleIncrement = visibleIncrementSeconds / clickableIncrementSeconds;
			for(var i = -(this._totalIncrements >> 1); i < (this._totalIncrements >> 1); i += this._clickableIncrement){
				this.timeMenu.appendChild(this._createOption(i));
			}
			
			// TODO:
			// I commented this out because it
			// causes problems for a TimeTextBox in a Dialog, or as the editor of an InlineEditBox,
			// because the timeMenu node isn't visible yet. -- Bill (Bug #????)
			// dijit.focus(this.timeMenu);
		},

		postCreate:function(){
			// instantiate constraints
			if(this.constraints===dijit._TimePicker.prototype.constraints){
				this.constraints={};
			}

			// brings in visibleRange, increments, etc.
			dojo.mixin(this, this.constraints);

			// dojo.date.locale needs the lang in the constraints as locale
			if(!this.constraints.locale){
				this.constraints.locale=this.lang;
			}

			// assign typematic mouse listeners to the arrow buttons
			this.connect(this.timeMenu, dojo.isIE ? "onmousewheel" : 'DOMMouseScroll', "_mouseWheeled");
			var typematic = dijit.typematic.addMouseListener;
			typematic(this.upArrow,this,this._onArrowUp, 0.8, 500);
			typematic(this.downArrow,this,this._onArrowDown, 0.8, 500);
			//dijit.typematic.addListener(this.upArrow,this.timeMenu, {keyCode:dojo.keys.UP_ARROW,ctrlKey:false,altKey:false,shiftKey:false}, this, "_onArrowUp", 0.8, 500);
			//dijit.typematic.addListener(this.downArrow, this.timeMenu, {keyCode:dojo.keys.DOWN_ARROW,ctrlKey:false,altKey:false,shiftKey:false}, this, "_onArrowDown", 0.8,500);

			this.inherited(arguments);
			this.setValue(this.value);
		},

		_createOption:function(/*Number*/ index){
			// summary: creates a clickable time option
			var div = dojo.doc.createElement("div");
			var date = (div.date = new Date(this._refDate));
			div.index = index;
			var incrementDate = this._clickableIncrementDate;
			date.setHours(date.getHours() + incrementDate.getHours() * index,
				date.getMinutes() + incrementDate.getMinutes() * index,
				date.getSeconds() + incrementDate.getSeconds() * index);

			var innerDiv = dojo.doc.createElement('div');
			dojo.addClass(div,this.baseClass+"Item");
			dojo.addClass(innerDiv,this.baseClass+"ItemInner");
			innerDiv.innerHTML = dojo.date.locale.format(date, this.constraints);				
			div.appendChild(innerDiv);

			if(index%this._visibleIncrement<1 && index%this._visibleIncrement>-1){
				dojo.addClass(div, this.baseClass+"Marker");
			}else if(!(index%this._clickableIncrement)){
				dojo.addClass(div, this.baseClass+"Tick");
			}
						
			if(this.isDisabledDate(date)){
				// set disabled
				dojo.addClass(div, this.baseClass+"ItemDisabled");
			}
			if(!dojo.date.compare(this.value, date, this.constraints.selector)){
				div.selected = true;
				dojo.addClass(div, this.baseClass+"ItemSelected");
			}
			return div;
		},

		_onOptionSelected:function(/*Object*/ tgt){
			var tdate = tgt.target.date || tgt.target.parentNode.date;			
			if(!tdate || this.isDisabledDate(tdate)){ return; }
			this.setValue(tdate);
			this.onValueSelected(tdate);
		},

		onValueSelected:function(value){
		},

		onmouseover:function(/*Event*/ e){
			var tgr = (e.target.parentNode === this.timeMenu) ? e.target : e.target.parentNode;			
			this._highlighted_option=tgr;
			dojo.addClass(tgr, this.baseClass+"ItemHover");
		},

		onmouseout:function(/*Event*/ e){
			var tgr = (e.target.parentNode === this.timeMenu) ? e.target : e.target.parentNode;
			if(this._highlighted_option===tgr){			
				dojo.removeClass(tgr, this.baseClass+"ItemHover");
			}
		},

		_mouseWheeled:function(/*Event*/e){
			// summary: handle the mouse wheel listener
			dojo.stopEvent(e);
			// we're not _measuring_ the scroll amount, just direction
			var scrollAmount = (dojo.isIE ? e.wheelDelta : -e.detail);
			this[(scrollAmount>0 ? "_onArrowUp" : "_onArrowDown")](); // yes, we're making a new dom node every time you mousewheel, or click
		},

		_onArrowUp:function(){
			// summary: remove the bottom time and add one to the top
			var index = this.timeMenu.childNodes[0].index - 1;
			var div = this._createOption(index);
			this.timeMenu.removeChild(this.timeMenu.childNodes[this.timeMenu.childNodes.length - 1]);
			this.timeMenu.insertBefore(div, this.timeMenu.childNodes[0]);
		},

		_onArrowDown:function(){
			// summary: remove the top time and add one to the bottom
			var index = this.timeMenu.childNodes[this.timeMenu.childNodes.length - 1].index + 1;
			var div = this._createOption(index);
			this.timeMenu.removeChild(this.timeMenu.childNodes[0]);
			this.timeMenu.appendChild(div);
		}
	}
);

}

if(!dojo._hasResource["dijit.form.TimeTextBox"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dijit.form.TimeTextBox"] = true;
dojo.provide("dijit.form.TimeTextBox");




/*=====
dojo.declare(
	"dijit.form.TimeTextBox.__Constraints",
	[dijit.form._DateTimeTextBox.__Constraints, dijit._TimePicker.__Constraints]
);
=====*/

dojo.declare(
	"dijit.form.TimeTextBox",
	dijit.form._DateTimeTextBox,
	{
		// summary:
		//		A validating, serializable, range-bound time text box with a popup time picker

		popupClass: "dijit._TimePicker",
		_selector: "time"

/*=====
		,
		// constraints: dijit.form.TimeTextBox.__Constraints 
		constraints:{}
=====*/
	}
);

}

if(!dojo._hasResource["dijit.form.ComboBox"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dijit.form.ComboBox"] = true;
dojo.provide("dijit.form.ComboBox");




dojo.declare(
	"dijit.form.ComboBoxMixin",
	null,
	{
		// item: Object
		//		This is the item returned by the dojo.data.store implementation that
		//		provides the data for this cobobox, it's the currently selected item.
		item: null,

		// pageSize: Integer
		//		Argument to data provider.
		//		Specifies number of search results per page (before hitting "next" button)
		pageSize: Infinity,

		// store: Object
		//		Reference to data provider object used by this ComboBox
		store: null,

		// query: Object
		//		A query that can be passed to 'store' to initially filter the items,
		//		before doing further filtering based on `searchAttr` and the key.
		//		Any reference to the `searchAttr` is ignored.
		query: {},

		// autoComplete: Boolean
		//		If you type in a partial string, and then tab out of the `<input>` box,
		//		automatically copy the first entry displayed in the drop down list to
		//		the `<input>` field
		autoComplete: true,

		// searchDelay: Integer
		//		Delay in milliseconds between when user types something and we start
		//		searching based on that value
		searchDelay: 100,

		// searchAttr: String
		//		Searches pattern match against this field
		searchAttr: "name",

		// queryExpr: String
		//		dojo.data query expression pattern.
		//		`${0}` will be substituted for the user text.
		//		`*` is used for wildcards.
		//		`${0}*` means "starts with", `*${0}*` means "contains", `${0}` means "is"
		queryExpr: "${0}*",

		// ignoreCase: Boolean
		//		Set true if the ComboBox should ignore case when matching possible items
		ignoreCase: true,

		// hasDownArrow: Boolean
		//		Set this textbox to have a down arrow button.
		//		Defaults to true.
		hasDownArrow:true,

		templateString:"<div class=\"dijit dijitReset dijitInlineTable dijitLeft\"\r\n\tid=\"widget_${id}\"\r\n\tdojoAttachEvent=\"onmouseenter:_onMouse,onmouseleave:_onMouse,onmousedown:_onMouse\" dojoAttachPoint=\"comboNode\" waiRole=\"combobox\" tabIndex=\"-1\"\r\n\t><div style=\"overflow:hidden;\"\r\n\t\t><div class='dijitReset dijitRight dijitButtonNode dijitArrowButton dijitDownArrowButton'\r\n\t\t\tdojoAttachPoint=\"downArrowNode\" waiRole=\"presentation\"\r\n\t\t\tdojoAttachEvent=\"onmousedown:_onArrowMouseDown,onmouseup:_onMouse,onmouseenter:_onMouse,onmouseleave:_onMouse\"\r\n\t\t\t><div class=\"dijitArrowButtonInner\">&thinsp;</div\r\n\t\t\t><div class=\"dijitArrowButtonChar\">&#9660;</div\r\n\t\t></div\r\n\t\t><div class=\"dijitReset dijitValidationIcon\"><br></div\r\n\t\t><div class=\"dijitReset dijitValidationIconText\">&Chi;</div\r\n\t\t><div class=\"dijitReset dijitInputField\"\r\n\t\t\t><input type=\"text\" autocomplete=\"off\" name=\"${name}\" class='dijitReset'\r\n\t\t\tdojoAttachEvent=\"onkeypress:_onKeyPress, onfocus:_update, compositionend,onkeyup\"\r\n\t\t\tdojoAttachPoint=\"textbox,focusNode\" waiRole=\"textbox\" waiState=\"haspopup-true,autocomplete-list\"\r\n\t\t/></div\r\n\t></div\r\n></div>\r\n",

		baseClass:"dijitComboBox",

		_getCaretPos: function(/*DomNode*/ element){
			// khtml 3.5.2 has selection* methods as does webkit nightlies from 2005-06-22
			var pos = 0;
			if(typeof(element.selectionStart)=="number"){
				// FIXME: this is totally borked on Moz < 1.3. Any recourse?
				pos = element.selectionStart;
			}else if(dojo.isIE){
				// in the case of a mouse click in a popup being handled,
				// then the dojo.doc.selection is not the textarea, but the popup
				// var r = dojo.doc.selection.createRange();
				// hack to get IE 6 to play nice. What a POS browser.
				var tr = dojo.doc.selection.createRange().duplicate();
				var ntr = element.createTextRange();
				tr.move("character",0);
				ntr.move("character",0);
				try{
					// If control doesnt have focus, you get an exception.
					// Seems to happen on reverse-tab, but can also happen on tab (seems to be a race condition - only happens sometimes).
					// There appears to be no workaround for this - googled for quite a while.
					ntr.setEndPoint("EndToEnd", tr);
					pos = String(ntr.text).replace(/\r/g,"").length;
				}catch(e){
					// If focus has shifted, 0 is fine for caret pos.
				}
			}
			return pos;
		},

		_setCaretPos: function(/*DomNode*/ element, /*Number*/ location){
			location = parseInt(location);
			dijit.selectInputText(element, location, location);
		},

		_setAttribute: function(/*String*/ attr, /*anything*/ value){
			// summary: additional code to set disablbed state of combobox node
			if (attr == "disabled"){
				dijit.setWaiState(this.comboNode, "disabled", value);
			}
		},	
		
		_onKeyPress: function(/*Event*/ evt){
			// summary: handles keyboard events

			//except for pasting case - ctrl + v(118)
			if(evt.altKey || (evt.ctrlKey && evt.charCode != 118)){
				return;
			}
			var doSearch = false;
			var pw = this._popupWidget;
			var dk = dojo.keys;
			if(this._isShowingNow){
				pw.handleKey(evt);
			}
			switch(evt.keyCode){
				case dk.PAGE_DOWN:
				case dk.DOWN_ARROW:
					if(!this._isShowingNow||this._prev_key_esc){
						this._arrowPressed();
						doSearch=true;
					}else{
						this._announceOption(pw.getHighlightedOption());
					}
					dojo.stopEvent(evt);
					this._prev_key_backspace = false;
					this._prev_key_esc = false;
					break;

				case dk.PAGE_UP:
				case dk.UP_ARROW:
					if(this._isShowingNow){
						this._announceOption(pw.getHighlightedOption());
					}
					dojo.stopEvent(evt);
					this._prev_key_backspace = false;
					this._prev_key_esc = false;
					break;

				case dk.ENTER:
					// prevent submitting form if user presses enter. Also
					// prevent accepting the value if either Next or Previous
					// are selected
					var highlighted;
					if(	this._isShowingNow && 
						(highlighted = pw.getHighlightedOption())
					){
						// only stop event on prev/next
						if(highlighted == pw.nextButton){
							this._nextSearch(1);
							dojo.stopEvent(evt);
							break;
						}else if(highlighted == pw.previousButton){
							this._nextSearch(-1);
							dojo.stopEvent(evt);
							break;
						}
					}else{
						this.setDisplayedValue(this.getDisplayedValue());
					}
					// default case:
					// prevent submit, but allow event to bubble
					evt.preventDefault();
					// fall through

				case dk.TAB:
					var newvalue = this.getDisplayedValue();
					// #4617: 
					//		if the user had More Choices selected fall into the
					//		_onBlur handler
					if(pw && (
						newvalue == pw._messages["previousMessage"] ||
						newvalue == pw._messages["nextMessage"])
					){
						break;
					}
					if(this._isShowingNow){
						this._prev_key_backspace = false;
						this._prev_key_esc = false;
						if(pw.getHighlightedOption()){
							pw.setValue({ target: pw.getHighlightedOption() }, true);
						}
						this._hideResultList();
					}
					break;

				case dk.SPACE:
					this._prev_key_backspace = false;
					this._prev_key_esc = false;
					if(this._isShowingNow && pw.getHighlightedOption()){
						dojo.stopEvent(evt);
						this._selectOption();
						this._hideResultList();
					}else{
						doSearch = true;
					}
					break;

				case dk.ESCAPE:
					this._prev_key_backspace = false;
					this._prev_key_esc = true;
					if(this._isShowingNow){
						dojo.stopEvent(evt);
						this._hideResultList();
					}
					this.inherited(arguments);
					break;

				case dk.DELETE:
				case dk.BACKSPACE:
					this._prev_key_esc = false;
					this._prev_key_backspace = true;
					doSearch = true;
					break;

				case dk.RIGHT_ARROW: // fall through
				case dk.LEFT_ARROW: 
					this._prev_key_backspace = false;
					this._prev_key_esc = false;
					break;

				default: // non char keys (F1-F12 etc..)  shouldn't open list
					this._prev_key_backspace = false;
					this._prev_key_esc = false;
					if(dojo.isIE || evt.charCode != 0){
						doSearch = true;
					}
			}
			if(this.searchTimer){
				clearTimeout(this.searchTimer);
			}
			if(doSearch){
				// need to wait a tad before start search so that the event
				// bubbles through DOM and we have value visible
				setTimeout(dojo.hitch(this, "_startSearchFromInput"),1);
			}
		},

		_autoCompleteText: function(/*String*/ text){
			// summary:
			// 		Fill in the textbox with the first item from the drop down
			// 		list, and highlight the characters that were
			// 		auto-completed. For example, if user typed "CA" and the
			// 		drop down list appeared, the textbox would be changed to
			// 		"California" and "ifornia" would be highlighted.

			var fn = this.focusNode;

			// IE7: clear selection so next highlight works all the time
			dijit.selectInputText(fn, fn.value.length);
			// does text autoComplete the value in the textbox?
			var caseFilter = this.ignoreCase? 'toLowerCase' : 'substr';
			if(text[caseFilter](0).indexOf(this.focusNode.value[caseFilter](0)) == 0){
				var cpos = this._getCaretPos(fn);
				// only try to extend if we added the last character at the end of the input
				if((cpos+1) > fn.value.length){
					// only add to input node as we would overwrite Capitalisation of chars
					// actually, that is ok
					fn.value = text;//.substr(cpos);
					// visually highlight the autocompleted characters
					dijit.selectInputText(fn, cpos);
				}
			}else{
				// text does not autoComplete; replace the whole value and highlight
				fn.value = text;
				dijit.selectInputText(fn);
			}
		},

		_openResultList: function(/*Object*/ results, /*Object*/ dataObject){
			if(	this.disabled || 
				this.readOnly || 
				(dataObject.query[this.searchAttr] != this._lastQuery)
			){
				return;
			}
			this._popupWidget.clearResultList();
			if(!results.length){
				this._hideResultList();
				return;
			}

			// Fill in the textbox with the first item from the drop down list,
			// and highlight the characters that were auto-completed. For
			// example, if user typed "CA" and the drop down list appeared, the
			// textbox would be changed to "California" and "ifornia" would be
			// highlighted.

			var zerothvalue = new String(this.store.getValue(results[0], this.searchAttr));
			if(zerothvalue && this.autoComplete && !this._prev_key_backspace &&
				(dataObject.query[this.searchAttr] != "*")){
				// when the user clicks the arrow button to show the full list,
				// startSearch looks for "*".
				// it does not make sense to autocomplete
				// if they are just previewing the options available.
				this._autoCompleteText(zerothvalue);
			}
			this._popupWidget.createOptions(
				results, 
				dataObject, 
				dojo.hitch(this, "_getMenuLabelFromItem")
			);

			// show our list (only if we have content, else nothing)
			this._showResultList();

			// #4091:
			//		tell the screen reader that the paging callback finished by
			//		shouting the next choice
			if(dataObject.direction){
				if(1 == dataObject.direction){
					this._popupWidget.highlightFirstOption();
				}else if(-1 == dataObject.direction){
					this._popupWidget.highlightLastOption();
				}
				this._announceOption(this._popupWidget.getHighlightedOption());
			}
		},

		_showResultList: function(){
			this._hideResultList();
			var items = this._popupWidget.getItems(),
				visibleCount = Math.min(items.length,this.maxListLength);
			this._arrowPressed();
			// hide the tooltip
			this.displayMessage("");
			
			// Position the list and if it's too big to fit on the screen then
			// size it to the maximum possible height
			// Our dear friend IE doesnt take max-height so we need to
			// calculate that on our own every time

			// TODO: want to redo this, see 
			//		http://trac.dojotoolkit.org/ticket/3272
			//	and
			//		http://trac.dojotoolkit.org/ticket/4108

			with(this._popupWidget.domNode.style){
				// natural size of the list has changed, so erase old
				// width/height settings, which were hardcoded in a previous
				// call to this function (via dojo.marginBox() call) 
				width = "";
				height = "";
			}
			var best = this.open();
			// #3212:
			//		only set auto scroll bars if necessary prevents issues with
			//		scroll bars appearing when they shouldn't when node is made
			//		wider (fractional pixels cause this)
			var popupbox = dojo.marginBox(this._popupWidget.domNode);
			this._popupWidget.domNode.style.overflow = 
				((best.h==popupbox.h)&&(best.w==popupbox.w)) ? "hidden" : "auto";
			// #4134:
			//		borrow TextArea scrollbar test so content isn't covered by
			//		scrollbar and horizontal scrollbar doesn't appear
			var newwidth = best.w;
			if(best.h < this._popupWidget.domNode.scrollHeight){
				newwidth += 16;
			}
			dojo.marginBox(this._popupWidget.domNode, {
				h: best.h,
				w: Math.max(newwidth, this.domNode.offsetWidth)
			});
			dijit.setWaiState(this.comboNode, "expanded", "true");
		},

		_hideResultList: function(){
			if(this._isShowingNow){
				dijit.popup.close(this._popupWidget);
				this._arrowIdle();
				this._isShowingNow=false;
				dijit.setWaiState(this.comboNode, "expanded", "false");
				dijit.removeWaiState(this.focusNode,"activedescendant");
			}
		},

		_setBlurValue: function(){
			// if the user clicks away from the textbox OR tabs away, set the
			// value to the textbox value
			// #4617: 
			//		if value is now more choices or previous choices, revert
			//		the value
			var newvalue=this.getDisplayedValue();
			var pw = this._popupWidget;
			if(pw && (
				newvalue == pw._messages["previousMessage"] ||
				newvalue == pw._messages["nextMessage"]
				)
			){
				this.setValue(this._lastValueReported, true);
			}else{
				this.setDisplayedValue(newvalue);
			}
		},

		_onBlur: function(){
			// summary: called magically when focus has shifted away from this widget and it's dropdown
			this._hideResultList();
			this._arrowIdle();
			this.inherited(arguments);
		},

		_announceOption: function(/*Node*/ node){
			// summary:
			//		a11y code that puts the highlighted option in the textbox
			//		This way screen readers will know what is happening in the
			//		menu

			if(node == null){
				return;
			}
			// pull the text value from the item attached to the DOM node
			var newValue;
			if( node == this._popupWidget.nextButton ||
				node == this._popupWidget.previousButton){
				newValue = node.innerHTML;
			}else{
				newValue = this.store.getValue(node.item, this.searchAttr);
			}
			// get the text that the user manually entered (cut off autocompleted text)
			this.focusNode.value = this.focusNode.value.substring(0, this._getCaretPos(this.focusNode));
			//set up ARIA activedescendant
			dijit.setWaiState(this.focusNode, "activedescendant", dojo.attr(node, "id")); 
			// autocomplete the rest of the option to announce change
			this._autoCompleteText(newValue);
		},

		_selectOption: function(/*Event*/ evt){
			var tgt = null;
			if(!evt){
				evt ={ target: this._popupWidget.getHighlightedOption()};
			}
				// what if nothing is highlighted yet?
			if(!evt.target){
				// handle autocompletion where the the user has hit ENTER or TAB
				this.setDisplayedValue(this.getDisplayedValue());
				return;
			// otherwise the user has accepted the autocompleted value
			}else{
				tgt = evt.target;
			}
			if(!evt.noHide){
				this._hideResultList();
				this._setCaretPos(this.focusNode, this.store.getValue(tgt.item, this.searchAttr).length);
			}
			this._doSelect(tgt);
		},

		_doSelect: function(tgt){
			this.item = tgt.item;
			this.setValue(this.store.getValue(tgt.item, this.searchAttr), true);
		},

		_onArrowMouseDown: function(evt){
			// summary: callback when arrow is clicked
			if(this.disabled || this.readOnly){
				return;
			}
			dojo.stopEvent(evt);
			this.focus();
			if(this._isShowingNow){
				this._hideResultList();
			}else{
				// forces full population of results, if they click
				// on the arrow it means they want to see more options
				this._startSearch("");
			}
		},

		_startSearchFromInput: function(){
			this._startSearch(this.focusNode.value);
		},

		_getQueryString: function(/*String*/ text){
			return dojo.string.substitute(this.queryExpr, [text]);
		},

		_startSearch: function(/*String*/ key){
			if(!this._popupWidget){
				var popupId = this.id + "_popup";
				this._popupWidget = new dijit.form._ComboBoxMenu({
					onChange: dojo.hitch(this, this._selectOption),
					id:popupId
				});
				dijit.removeWaiState(this.focusNode,"activedescendant");
				dijit.setWaiState(this.textbox,"owns",popupId); // associate popup with textbox
			}
			// create a new query to prevent accidentally querying for a hidden
			// value from FilteringSelect's keyField
			this.item = null; // #4872
			var query = dojo.clone(this.query); // #5970
			this._lastQuery = query[this.searchAttr] = this._getQueryString(key);
			// #5970: set _lastQuery, *then* start the timeout
			// otherwise, if the user types and the last query returns before the timeout,
			// _lastQuery won't be set and their input gets rewritten
			this.searchTimer=setTimeout(dojo.hitch(this, function(query, _this){
				var dataObject = this.store.fetch({
					queryOptions: {
						ignoreCase: this.ignoreCase, 
						deep: true
					},
					query: query,
					onComplete: dojo.hitch(this, "_openResultList"), 
					onError: function(errText){
						console.error('dijit.form.ComboBox: ' + errText);
						dojo.hitch(_this, "_hideResultList")();
					},
					start:0,
					count:this.pageSize
				});

				var nextSearch = function(dataObject, direction){
					dataObject.start += dataObject.count*direction;
					// #4091:
					//		tell callback the direction of the paging so the screen
					//		reader knows which menu option to shout
					dataObject.direction = direction;
					this.store.fetch(dataObject);
				}
				this._nextSearch = this._popupWidget.onPage = dojo.hitch(this, nextSearch, dataObject);
			}, query, this), this.searchDelay);
		},

		_getValueField:function(){
			return this.searchAttr;
		},

		/////////////// Event handlers /////////////////////

		_arrowPressed: function(){
			if(!this.disabled && !this.readOnly && this.hasDownArrow){
				dojo.addClass(this.downArrowNode, "dijitArrowButtonActive");
			}
		},

		_arrowIdle: function(){
			if(!this.disabled && !this.readOnly && this.hasDownArrow){
				dojo.removeClass(this.downArrowNode, "dojoArrowButtonPushed");
			}
		},

		// FIXME: 
		//		this is public so we can't remove until 2.0, but the name
		//		SHOULD be "compositionEnd"

		compositionend: function(/*Event*/ evt){
			//	summary:
			//		When inputting characters using an input method, such as
			//		Asian languages, it will generate this event instead of
			//		onKeyDown event Note: this event is only triggered in FF
			//		(not in IE)
			this.onkeypress({charCode:-1});
		},

		//////////// INITIALIZATION METHODS ///////////////////////////////////////

		constructor: function(){
			this.query={};
		},

		postMixInProperties: function(){
			if(!this.hasDownArrow){
				this.baseClass = "dijitTextBox";
			}
			if(!this.store){
				var srcNodeRef = this.srcNodeRef;

				// if user didn't specify store, then assume there are option tags
				this.store = new dijit.form._ComboBoxDataStore(srcNodeRef);

				// if there is no value set and there is an option list, set
				// the value to the first value to be consistent with native
				// Select

				// Firefox and Safari set value
				// IE6 and Opera set selectedIndex, which is automatically set
				// by the selected attribute of an option tag
				// IE6 does not set value, Opera sets value = selectedIndex
				if(	!this.value || (
						(typeof srcNodeRef.selectedIndex == "number") && 
						srcNodeRef.selectedIndex.toString() === this.value)
				){
					var item = this.store.fetchSelectedItem();
					if(item){
						this.value = this.store.getValue(item, this._getValueField());
					}
				}
			}
		},
		
		_postCreate:function(){
			//find any associated label element and add to combobox node.
			var label=dojo.query('label[for="'+this.id+'"]');
			if(label.length){
				label[0].id = (this.id+"_label");
				var cn=this.comboNode;
				dijit.setWaiState(cn, "labelledby", label[0].id);
				dijit.setWaiState(cn, "disabled", this.disabled);
				
			}
		},

		uninitialize:function(){
			if(this._popupWidget){
				this._hideResultList();
				this._popupWidget.destroy()
			}
		},

		_getMenuLabelFromItem:function(/*Item*/ item){
			return {
				html: false, 
				label: this.store.getValue(item, this.searchAttr)
			};
		},

		open:function(){
			this._isShowingNow=true;
			return dijit.popup.open({
				popup: this._popupWidget,
				around: this.domNode,
				parent: this
			});
		},
		
		reset:function(){
			//	summary:
			//		Additionally reset the .item (to clean up).
			this.item = null;
			this.inherited(arguments);
		}
		
	}
);

dojo.declare(
	"dijit.form._ComboBoxMenu",
	[dijit._Widget, dijit._Templated],

	{
		//	summary:
		//		Focus-less div based menu for internal use in ComboBox

		templateString: "<ul class='dijitMenu' dojoAttachEvent='onmousedown:_onMouseDown,onmouseup:_onMouseUp,onmouseover:_onMouseOver,onmouseout:_onMouseOut' tabIndex='-1' style='overflow:\"auto\";'>"
				+"<li class='dijitMenuItem dijitMenuPreviousButton' dojoAttachPoint='previousButton'></li>"
				+"<li class='dijitMenuItem dijitMenuNextButton' dojoAttachPoint='nextButton'></li>"
			+"</ul>",
		_messages: null,

		postMixInProperties: function(){
			this._messages = dojo.i18n.getLocalization("dijit.form", "ComboBox", this.lang);
			this.inherited("postMixInProperties", arguments);
		},

		setValue: function(/*Object*/ value){
			this.value = value;
			this.onChange(value);
		},

		// stubs
		onChange: function(/*Object*/ value){},
		onPage: function(/*Number*/ direction){},

		postCreate:function(){
			// fill in template with i18n messages
			this.previousButton.innerHTML = this._messages["previousMessage"];
			this.nextButton.innerHTML = this._messages["nextMessage"];
			this.inherited("postCreate", arguments);
		},

		onClose:function(){
			this._blurOptionNode();
		},

		_createOption:function(/*Object*/ item, labelFunc){
			//	summary: 
			//		creates an option to appear on the popup menu subclassed by
			//		FilteringSelect

			var labelObject = labelFunc(item);
			var menuitem = dojo.doc.createElement("li");
			dijit.setWaiRole(menuitem, "option");
			if(labelObject.html){
				menuitem.innerHTML = labelObject.label;
			}else{
				menuitem.appendChild(
					dojo.doc.createTextNode(labelObject.label)
				);
			}
			// #3250: in blank options, assign a normal height
			if(menuitem.innerHTML == ""){
				menuitem.innerHTML = "&nbsp;";
			}
			menuitem.item=item;
			return menuitem;
		},

		createOptions: function(results, dataObject, labelFunc){
			//this._dataObject=dataObject;
			//this._dataObject.onComplete=dojo.hitch(comboBox, comboBox._openResultList);
			// display "Previous . . ." button
			this.previousButton.style.display = (dataObject.start == 0) ? "none" : "";
			dojo.attr(this.previousButton, "id", this.id + "_prev");
			// create options using _createOption function defined by parent
			// ComboBox (or FilteringSelect) class
			// #2309:
			//		iterate over cache nondestructively
			dojo.forEach(results, function(item, i){
				var menuitem = this._createOption(item, labelFunc);
				menuitem.className = "dijitMenuItem";
				dojo.attr(menuitem, "id", this.id + i);
				this.domNode.insertBefore(menuitem, this.nextButton);
			}, this);
			// display "Next . . ." button
			this.nextButton.style.display = (dataObject.count == results.length) ? "" : "none";
			dojo.attr(this.nextButton,"id", this.id + "_next")
		},

		clearResultList: function(){
			// keep the previous and next buttons of course
			while(this.domNode.childNodes.length>2){
				this.domNode.removeChild(this.domNode.childNodes[this.domNode.childNodes.length-2]);
			}
		},

		// these functions are called in showResultList
		getItems: function(){
			return this.domNode.childNodes;
		},

		getListLength: function(){
			return this.domNode.childNodes.length-2;
		},

		_onMouseDown: function(/*Event*/ evt){
			dojo.stopEvent(evt);
		},

		_onMouseUp: function(/*Event*/ evt){
			if(evt.target === this.domNode){
				return;
			}else if(evt.target==this.previousButton){
				this.onPage(-1);
			}else if(evt.target==this.nextButton){
				this.onPage(1);
			}else{
				var tgt = evt.target;
				// while the clicked node is inside the div
				while(!tgt.item){
					// recurse to the top
					tgt = tgt.parentNode;
				}
				this.setValue({ target: tgt }, true);
			}
		},

		_onMouseOver: function(/*Event*/ evt){
			if(evt.target === this.domNode){ return; }
			var tgt = evt.target;
			if(!(tgt == this.previousButton || tgt == this.nextButton)){
				// while the clicked node is inside the div
				while(!tgt.item){
					// recurse to the top
					tgt = tgt.parentNode;
				}
			}
			this._focusOptionNode(tgt);
		},

		_onMouseOut:function(/*Event*/ evt){
			if(evt.target === this.domNode){ return; }
			this._blurOptionNode();
		},

		_focusOptionNode:function(/*DomNode*/ node){
			// summary:
			//	does the actual highlight
			if(this._highlighted_option != node){
				this._blurOptionNode();
				this._highlighted_option = node;
				dojo.addClass(this._highlighted_option, "dijitMenuItemHover");
			}
		},

		_blurOptionNode:function(){
			// summary:
			//	removes highlight on highlighted option
			if(this._highlighted_option){
				dojo.removeClass(this._highlighted_option, "dijitMenuItemHover");
				this._highlighted_option = null;
			}
		},

		_highlightNextOption:function(){
			//	summary:
			// 		Highlight the item just below the current selection.
			// 		If nothing selected, highlight first option

			// because each press of a button clears the menu,
			// the highlighted option sometimes becomes detached from the menu!
			// test to see if the option has a parent to see if this is the case.
			var fc = this.domNode.firstChild;
			if(!this.getHighlightedOption()){
				this._focusOptionNode(fc.style.display=="none" ? fc.nextSibling : fc);
			}else{
				var ns = this._highlighted_option.nextSibling;
				if(ns && ns.style.display!="none"){
					this._focusOptionNode(ns);
				}
			}
			// scrollIntoView is called outside of _focusOptionNode because in IE putting it inside causes the menu to scroll up on mouseover
			dijit.scrollIntoView(this._highlighted_option);
		},

		highlightFirstOption:function(){
			//	summary:
			// 		Highlight the first real item in the list (not Previous Choices).
			this._focusOptionNode(this.domNode.firstChild.nextSibling);
			dijit.scrollIntoView(this._highlighted_option);
		},

		highlightLastOption:function(){
			//	summary:
			// 		Highlight the last real item in the list (not More Choices).
			this._focusOptionNode(this.domNode.lastChild.previousSibling);
			dijit.scrollIntoView(this._highlighted_option);
		},

		_highlightPrevOption:function(){
			//	summary:
			// 		Highlight the item just above the current selection.
			// 		If nothing selected, highlight last option (if
			// 		you select Previous and try to keep scrolling up the list)
			var lc = this.domNode.lastChild;
			if(!this.getHighlightedOption()){
				this._focusOptionNode(lc.style.display == "none" ? lc.previousSibling : lc);
			}else{
				var ps = this._highlighted_option.previousSibling;
				if(ps && ps.style.display != "none"){
					this._focusOptionNode(ps);
				}
			}
			dijit.scrollIntoView(this._highlighted_option);
		},

		_page:function(/*Boolean*/ up){
			var scrollamount = 0;
			var oldscroll = this.domNode.scrollTop;
			var height = dojo.style(this.domNode, "height");
			// if no item is highlighted, highlight the first option
			if(!this.getHighlightedOption()){
				this._highlightNextOption();
			}
			while(scrollamount<height){
				if(up){
					// stop at option 1
					if(!this.getHighlightedOption().previousSibling ||
						this._highlighted_option.previousSibling.style.display == "none"){
						break;
					}
					this._highlightPrevOption();
				}else{
					// stop at last option
					if(!this.getHighlightedOption().nextSibling ||
						this._highlighted_option.nextSibling.style.display == "none"){
						break;
					}
					this._highlightNextOption();
				}
				// going backwards
				var newscroll=this.domNode.scrollTop;
				scrollamount+=(newscroll-oldscroll)*(up ? -1:1);
				oldscroll=newscroll;
			}
		},

		pageUp: function(){ this._page(true); },

		pageDown: function(){ this._page(false); },

		getHighlightedOption: function(){
			//	summary:
			//		Returns the highlighted option.
			var ho = this._highlighted_option;
			return (ho && ho.parentNode) ? ho : null;
		},

		handleKey: function(evt){
			switch(evt.keyCode){
				case dojo.keys.DOWN_ARROW:
					this._highlightNextOption();
					break;
				case dojo.keys.PAGE_DOWN:
					this.pageDown();
					break;	
				case dojo.keys.UP_ARROW:
					this._highlightPrevOption();
					break;
				case dojo.keys.PAGE_UP:
					this.pageUp();
					break;	
			}
		}
	}
);

dojo.declare(
	"dijit.form.ComboBox",
	[dijit.form.ValidationTextBox, dijit.form.ComboBoxMixin],
	{
		//	summary:
		//		Auto-completing text box, and base class for dijit.form.FilteringSelect.
		// 
		//	description:
		//		The drop down box's values are populated from an class called
		//		a data provider, which returns a list of values based on the characters
		//		that the user has typed into the input box.
		// 
		//		Some of the options to the ComboBox are actually arguments to the data
		//		provider.
		// 
		//		You can assume that all the form widgets (and thus anything that mixes
		//		in dijit.formComboBoxMixin) will inherit from dijit.form._FormWidget and thus the `this`
		//		reference will also "be a" _FormWidget.

		postMixInProperties: function(){
			// this.inherited(arguments); // ??
			dijit.form.ComboBoxMixin.prototype.postMixInProperties.apply(this, arguments);
			dijit.form.ValidationTextBox.prototype.postMixInProperties.apply(this, arguments);
		},

		postCreate: function(){
			dijit.form.ComboBoxMixin.prototype._postCreate.apply(this, arguments);
			dijit.form.ValidationTextBox.prototype.postCreate.apply(this, arguments);
		},
		setAttribute: function(/*String*/ attr, /*anything*/ value){
			dijit.form.ValidationTextBox.prototype.setAttribute.apply(this, arguments);
			dijit.form.ComboBoxMixin.prototype._setAttribute.apply(this, arguments);
		}
		
	}
);

dojo.declare("dijit.form._ComboBoxDataStore", null, {
	//	summary:
	//		Inefficient but small data store specialized for inlined ComboBox data
	//
	//	description:
	//		Provides a store for inlined data like:
	//
	//	|	<select>
	//	|		<option value="AL">Alabama</option>
	//	|		...
	//
	//		Actually. just implements the subset of dojo.data.Read/Notification
	//		needed for ComboBox and FilteringSelect to work.
	//
	//		Note that an item is just a pointer to the <option> DomNode.

	constructor: function( /*DomNode*/ root){
		this.root = root;
/*
		//	TODO: this was added in #3858 but unclear why/if it's needed;  doesn't seem to be.
		//	If it is needed then can we just hide the select itself instead?
		dojo.query("> option", root).forEach(function(node){
			node.style.display="none";
		});
*/
	},

	getValue: function(	/* item */ item, 
						/* attribute-name-string */ attribute, 
						/* value? */ defaultValue){
		return (attribute == "value") ? item.value : (item.innerText || item.textContent || '');
	},

	isItemLoaded: function(/* anything */ something) {
		return true;
	},

	fetch: function(/* Object */ args){
		//	summary:
		//		Given a query and set of defined options, such as a start and count of items to return,
		//		this method executes the query and makes the results available as data items.
		//		Refer to dojo.data.api.Read.fetch() more details.
		//
		//	description:
		//		Given a query like
		//
		//	|	{
		// 	|		query: {name: "Cal*"},
		//	|		start: 30,
		//	|		count: 20,
		//	|		ignoreCase: true,
		//	|		onComplete: function(/* item[] */ items, /* Object */ args){...}
		// 	|	}
		//
		//		will call `onComplete()` with the results of the query (and the argument to this method)

		// convert query to regex (ex: convert "first\last*" to /^first\\last.*$/i) and get matching vals
		var query = "^" + args.query.name
				.replace(/([\\\|\(\)\[\{\^\$\+\?\.\<\>])/g, "\\$1")
				.replace("*", ".*") + "$",
			matcher = new RegExp(query, args.queryOptions.ignoreCase ? "i" : ""),
			items = dojo.query("> option", this.root).filter(function(option){
				return (option.innerText || option.textContent || '').match(matcher);
			} );

		var start = args.start || 0,
			end = ("count" in args && args.count != Infinity) ? (start + args.count) : items.length ;
		args.onComplete(items.slice(start, end), args);
		return args; // Object
		// TODO: I don't need to return the length?
	},

	close: function(/*dojo.data.api.Request || args || null */ request){
		return;
	},

	getLabel: function(/* item */ item){
		return item.innerHTML;
	},

	getIdentity: function(/* item */ item){
		return dojo.attr(item, "value");
	},

	fetchItemByIdentity: function(/* Object */ args){
		//	summary:
		//		Given the identity of an item, this method returns the item that has
		//		that identity through the onItem callback.
		//		Refer to dojo.data.api.Identity.fetchItemByIdentity() for more details.
		//
		//	description:
		//		Given arguments like:
		//
		//	|		{identity: "CA", onItem: function(item){...}
		//
		//		Call `onItem()` with the DOM node `<option value="CA">California</option>`
		var item = dojo.query("option[value='" + args.identity + "']", this.root)[0];
		args.onItem(item);
	},
	
	fetchSelectedItem: function(){
		//	summary:
		//		Get the option marked as selected, like `<option selected>`.
		//		Not part of dojo.data API.
		var root = this.root,
			si = root.selectedIndex;
		return dojo.query("> option:nth-child(" +
			(si != -1 ? si+1 : 1) + ")",
			root)[0];	// dojo.data.Item
	}
});

}

if(!dojo._hasResource["dojo.data.util.filter"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojo.data.util.filter"] = true;
dojo.provide("dojo.data.util.filter");

dojo.data.util.filter.patternToRegExp = function(/*String*/pattern, /*boolean?*/ ignoreCase){
	//	summary:  
	//		Helper function to convert a simple pattern to a regular expression for matching.
	//	description:
	//		Returns a regular expression object that conforms to the defined conversion rules.
	//		For example:  
	//			ca*   -> /^ca.*$/
	//			*ca*  -> /^.*ca.*$/
	//			*c\*a*  -> /^.*c\*a.*$/
	//			*c\*a?*  -> /^.*c\*a..*$/
	//			and so on.
	//
	//	pattern: string
	//		A simple matching pattern to convert that follows basic rules:
	//			* Means match anything, so ca* means match anything starting with ca
	//			? Means match single character.  So, b?b will match to bob and bab, and so on.
	//      	\ is an escape character.  So for example, \* means do not treat * as a match, but literal character *.
	//				To use a \ as a character in the string, it must be escaped.  So in the pattern it should be 
	//				represented by \\ to be treated as an ordinary \ character instead of an escape.
	//
	//	ignoreCase:
	//		An optional flag to indicate if the pattern matching should be treated as case-sensitive or not when comparing
	//		By default, it is assumed case sensitive.

	var rxp = "^";
	var c = null;
	for(var i = 0; i < pattern.length; i++){
		c = pattern.charAt(i);
		switch (c) {
			case '\\':
				rxp += c;
				i++;
				rxp += pattern.charAt(i);
				break;
			case '*':
				rxp += ".*"; break;
			case '?':
				rxp += "."; break;
			case '$':
			case '^':
			case '/':
			case '+':
			case '.':
			case '|':
			case '(':
			case ')':
			case '{':
			case '}':
			case '[':
			case ']':
				rxp += "\\"; //fallthrough
			default:
				rxp += c;
		}
	}
	rxp += "$";
	if(ignoreCase){
		return new RegExp(rxp,"i"); //RegExp
	}else{
		return new RegExp(rxp); //RegExp
	}
	
};

}

if(!dojo._hasResource["dojo.data.util.sorter"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojo.data.util.sorter"] = true;
dojo.provide("dojo.data.util.sorter");

dojo.data.util.sorter.basicComparator = function(	/*anything*/ a, 
													/*anything*/ b){
	//	summary:  
	//		Basic comparision function that compares if an item is greater or less than another item
	//	description:  
	//		returns 1 if a > b, -1 if a < b, 0 if equal.
	//		undefined values are treated as larger values so that they're pushed to the end of the list.

	var ret = 0;
	if(a > b || typeof a === "undefined" || a === null){
		ret = 1;
	}else if(a < b || typeof b === "undefined" || b === null){
		ret = -1;
	}
	return ret; //int, {-1,0,1}
};

dojo.data.util.sorter.createSortFunction = function(	/* attributes array */sortSpec,
														/*dojo.data.core.Read*/ store){
	//	summary:  
	//		Helper function to generate the sorting function based off the list of sort attributes.
	//	description:  
	//		The sort function creation will look for a property on the store called 'comparatorMap'.  If it exists
	//		it will look in the mapping for comparisons function for the attributes.  If one is found, it will
	//		use it instead of the basic comparator, which is typically used for strings, ints, booleans, and dates.
	//		Returns the sorting function for this particular list of attributes and sorting directions.
	//
	//	sortSpec: array
	//		A JS object that array that defines out what attribute names to sort on and whether it should be descenting or asending.
	//		The objects should be formatted as follows:
	//		{
	//			attribute: "attributeName-string" || attribute,
	//			descending: true|false;   // Default is false.
	//		}
	//	store: object
	//		The datastore object to look up item values from.
	//
	var sortFunctions=[];   

	function createSortFunction(attr, dir){
		return function(itemA, itemB){
			var a = store.getValue(itemA, attr);
			var b = store.getValue(itemB, attr);
			//See if we have a override for an attribute comparison.
			var comparator = null;
			if(store.comparatorMap){
				if(typeof attr !== "string"){
					 attr = store.getIdentity(attr);
				}
				comparator = store.comparatorMap[attr]||dojo.data.util.sorter.basicComparator;
			}
			comparator = comparator||dojo.data.util.sorter.basicComparator; 
			return dir * comparator(a,b); //int
		};
	}

	for(var i = 0; i < sortSpec.length; i++){
		sortAttribute = sortSpec[i];
		if(sortAttribute.attribute){
			var direction = (sortAttribute.descending) ? -1 : 1;
			sortFunctions.push(createSortFunction(sortAttribute.attribute, direction));
		}
	}

	return function(rowA, rowB){
		var i=0;
		while(i < sortFunctions.length){
			var ret = sortFunctions[i++](rowA, rowB);
			if(ret !== 0){
				return ret;//int
			}
		}
		return 0; //int  
	};  //  Function
};

}

if(!dojo._hasResource["dojo.data.util.simpleFetch"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojo.data.util.simpleFetch"] = true;
dojo.provide("dojo.data.util.simpleFetch");


dojo.data.util.simpleFetch.fetch = function(/* Object? */ request){
	//	summary:
	//		The simpleFetch mixin is designed to serve as a set of function(s) that can
	//		be mixed into other datastore implementations to accelerate their development.  
	//		The simpleFetch mixin should work well for any datastore that can respond to a _fetchItems() 
	//		call by returning an array of all the found items that matched the query.  The simpleFetch mixin
	//		is not designed to work for datastores that respond to a fetch() call by incrementally
	//		loading items, or sequentially loading partial batches of the result
	//		set.  For datastores that mixin simpleFetch, simpleFetch 
	//		implements a fetch method that automatically handles eight of the fetch()
	//		arguments -- onBegin, onItem, onComplete, onError, start, count, sort and scope
	//		The class mixing in simpleFetch should not implement fetch(),
	//		but should instead implement a _fetchItems() method.  The _fetchItems() 
	//		method takes three arguments, the keywordArgs object that was passed 
	//		to fetch(), a callback function to be called when the result array is
	//		available, and an error callback to be called if something goes wrong.
	//		The _fetchItems() method should ignore any keywordArgs parameters for
	//		start, count, onBegin, onItem, onComplete, onError, sort, and scope.  
	//		The _fetchItems() method needs to correctly handle any other keywordArgs
	//		parameters, including the query parameter and any optional parameters 
	//		(such as includeChildren).  The _fetchItems() method should create an array of 
	//		result items and pass it to the fetchHandler along with the original request object 
	//		-- or, the _fetchItems() method may, if it wants to, create an new request object 
	//		with other specifics about the request that are specific to the datastore and pass 
	//		that as the request object to the handler.
	//
	//		For more information on this specific function, see dojo.data.api.Read.fetch()
	request = request || {};
	if(!request.store){
		request.store = this;
	}
	var self = this;

	var _errorHandler = function(errorData, requestObject){
		if(requestObject.onError){
			var scope = requestObject.scope || dojo.global;
			requestObject.onError.call(scope, errorData, requestObject);
		}
	};

	var _fetchHandler = function(items, requestObject){
		var oldAbortFunction = requestObject.abort || null;
		var aborted = false;

		var startIndex = requestObject.start?requestObject.start:0;
		var endIndex   = requestObject.count?(startIndex + requestObject.count):items.length;

		requestObject.abort = function(){
			aborted = true;
			if(oldAbortFunction){
				oldAbortFunction.call(requestObject);
			}
		};

		var scope = requestObject.scope || dojo.global;
		if(!requestObject.store){
			requestObject.store = self;
		}
		if(requestObject.onBegin){
			requestObject.onBegin.call(scope, items.length, requestObject);
		}
		if(requestObject.sort){
			items.sort(dojo.data.util.sorter.createSortFunction(requestObject.sort, self));
		}
		if(requestObject.onItem){
			for(var i = startIndex; (i < items.length) && (i < endIndex); ++i){
				var item = items[i];
				if(!aborted){
					requestObject.onItem.call(scope, item, requestObject);
				}
			}
		}
		if(requestObject.onComplete && !aborted){
			var subset = null;
			if (!requestObject.onItem) {
				subset = items.slice(startIndex, endIndex);
			}
			requestObject.onComplete.call(scope, subset, requestObject);   
		}
	};
	this._fetchItems(request, _fetchHandler, _errorHandler);
	return request;	// Object
};

}

if(!dojo._hasResource["dojo.data.ItemFileReadStore"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojo.data.ItemFileReadStore"] = true;
dojo.provide("dojo.data.ItemFileReadStore");





dojo.declare("dojo.data.ItemFileReadStore", null,{
	//	summary:
	//		The ItemFileReadStore implements the dojo.data.api.Read API and reads
	//		data from JSON files that have contents in this format --
	//		{ items: [
	//			{ name:'Kermit', color:'green', age:12, friends:['Gonzo', {_reference:{name:'Fozzie Bear'}}]},
	//			{ name:'Fozzie Bear', wears:['hat', 'tie']},
	//			{ name:'Miss Piggy', pets:'Foo-Foo'}
	//		]}
	//		Note that it can also contain an 'identifer' property that specified which attribute on the items 
	//		in the array of items that acts as the unique identifier for that item.
	//
	constructor: function(/* Object */ keywordParameters){
		//	summary: constructor
		//	keywordParameters: {url: String}
		//	keywordParameters: {data: jsonObject}
		//	keywordParameters: {typeMap: object)
		//		The structure of the typeMap object is as follows:
		//		{
		//			type0: function || object,
		//			type1: function || object,
		//			...
		//			typeN: function || object
		//		}
		//		Where if it is a function, it is assumed to be an object constructor that takes the 
		//		value of _value as the initialization parameters.  If it is an object, then it is assumed
		//		to be an object of general form:
		//		{
		//			type: function, //constructor.
		//			deserialize:	function(value) //The function that parses the value and constructs the object defined by type appropriately.
		//		}
	
		this._arrayOfAllItems = [];
		this._arrayOfTopLevelItems = [];
		this._loadFinished = false;
		this._jsonFileUrl = keywordParameters.url;
		this._jsonData = keywordParameters.data;
		this._datatypeMap = keywordParameters.typeMap || {};
		if(!this._datatypeMap['Date']){
			//If no default mapping for dates, then set this as default.
			//We use the dojo.date.stamp here because the ISO format is the 'dojo way'
			//of generically representing dates.
			this._datatypeMap['Date'] = {
											type: Date,
											deserialize: function(value){
												return dojo.date.stamp.fromISOString(value);
											}
										};
		}
		this._features = {'dojo.data.api.Read':true, 'dojo.data.api.Identity':true};
		this._itemsByIdentity = null;
		this._storeRefPropName = "_S";  // Default name for the store reference to attach to every item.
		this._itemNumPropName = "_0"; // Default Item Id for isItem to attach to every item.
		this._rootItemPropName = "_RI"; // Default Item Id for isItem to attach to every item.
		this._reverseRefMap = "_RRM"; // Default attribute for constructing a reverse reference map for use with reference integrity
		this._loadInProgress = false;	//Got to track the initial load to prevent duelling loads of the dataset.
		this._queuedFetches = [];
	},
	
	url: "",	// use "" rather than undefined for the benefit of the parser (#3539)

	_assertIsItem: function(/* item */ item){
		//	summary:
		//		This function tests whether the item passed in is indeed an item in the store.
		//	item: 
		//		The item to test for being contained by the store.
		if(!this.isItem(item)){ 
			throw new Error("dojo.data.ItemFileReadStore: Invalid item argument.");
		}
	},

	_assertIsAttribute: function(/* attribute-name-string */ attribute){
		//	summary:
		//		This function tests whether the item passed in is indeed a valid 'attribute' like type for the store.
		//	attribute: 
		//		The attribute to test for being contained by the store.
		if(typeof attribute !== "string"){ 
			throw new Error("dojo.data.ItemFileReadStore: Invalid attribute argument.");
		}
	},

	getValue: function(	/* item */ item, 
						/* attribute-name-string */ attribute, 
						/* value? */ defaultValue){
		//	summary: 
		//		See dojo.data.api.Read.getValue()
		var values = this.getValues(item, attribute);
		return (values.length > 0)?values[0]:defaultValue; // mixed
	},

	getValues: function(/* item */ item, 
						/* attribute-name-string */ attribute){
		//	summary: 
		//		See dojo.data.api.Read.getValues()

		this._assertIsItem(item);
		this._assertIsAttribute(attribute);
		return item[attribute] || []; // Array
	},

	getAttributes: function(/* item */ item){
		//	summary: 
		//		See dojo.data.api.Read.getAttributes()
		this._assertIsItem(item);
		var attributes = [];
		for(var key in item){
			// Save off only the real item attributes, not the special id marks for O(1) isItem.
			if((key !== this._storeRefPropName) && (key !== this._itemNumPropName) && (key !== this._rootItemPropName) && (key !== this._reverseRefMap)){
				attributes.push(key);
			}
		}
		return attributes; // Array
	},

	hasAttribute: function(	/* item */ item,
							/* attribute-name-string */ attribute) {
		//	summary: 
		//		See dojo.data.api.Read.hasAttribute()
		return this.getValues(item, attribute).length > 0;
	},

	containsValue: function(/* item */ item, 
							/* attribute-name-string */ attribute, 
							/* anything */ value){
		//	summary: 
		//		See dojo.data.api.Read.containsValue()
		var regexp = undefined;
		if(typeof value === "string"){
			regexp = dojo.data.util.filter.patternToRegExp(value, false);
		}
		return this._containsValue(item, attribute, value, regexp); //boolean.
	},

	_containsValue: function(	/* item */ item, 
								/* attribute-name-string */ attribute, 
								/* anything */ value,
								/* RegExp?*/ regexp){
		//	summary: 
		//		Internal function for looking at the values contained by the item.
		//	description: 
		//		Internal function for looking at the values contained by the item.  This 
		//		function allows for denoting if the comparison should be case sensitive for
		//		strings or not (for handling filtering cases where string case should not matter)
		//	
		//	item:
		//		The data item to examine for attribute values.
		//	attribute:
		//		The attribute to inspect.
		//	value:	
		//		The value to match.
		//	regexp:
		//		Optional regular expression generated off value if value was of string type to handle wildcarding.
		//		If present and attribute values are string, then it can be used for comparison instead of 'value'
		return dojo.some(this.getValues(item, attribute), function(possibleValue){
			if(possibleValue !== null && !dojo.isObject(possibleValue) && regexp){
				if(possibleValue.toString().match(regexp)){
					return true; // Boolean
				}
			}else if(value === possibleValue){
				return true; // Boolean
			}
		});
	},

	isItem: function(/* anything */ something){
		//	summary: 
		//		See dojo.data.api.Read.isItem()
		if(something && something[this._storeRefPropName] === this){
			if(this._arrayOfAllItems[something[this._itemNumPropName]] === something){
				return true;
			}
		}
		return false; // Boolean
	},

	isItemLoaded: function(/* anything */ something){
		//	summary: 
		//		See dojo.data.api.Read.isItemLoaded()
		return this.isItem(something); //boolean
	},

	loadItem: function(/* object */ keywordArgs){
		//	summary: 
		//		See dojo.data.api.Read.loadItem()
		this._assertIsItem(keywordArgs.item);
	},

	getFeatures: function(){
		//	summary: 
		//		See dojo.data.api.Read.getFeatures()
		return this._features; //Object
	},

	getLabel: function(/* item */ item){
		//	summary: 
		//		See dojo.data.api.Read.getLabel()
		if(this._labelAttr && this.isItem(item)){
			return this.getValue(item,this._labelAttr); //String
		}
		return undefined; //undefined
	},

	getLabelAttributes: function(/* item */ item){
		//	summary: 
		//		See dojo.data.api.Read.getLabelAttributes()
		if(this._labelAttr){
			return [this._labelAttr]; //array
		}
		return null; //null
	},

	_fetchItems: function(	/* Object */ keywordArgs, 
							/* Function */ findCallback, 
							/* Function */ errorCallback){
		//	summary: 
		//		See dojo.data.util.simpleFetch.fetch()
		var self = this;
		var filter = function(requestArgs, arrayOfItems){
			var items = [];
			if(requestArgs.query){
				var ignoreCase = requestArgs.queryOptions ? requestArgs.queryOptions.ignoreCase : false; 

				//See if there are any string values that can be regexp parsed first to avoid multiple regexp gens on the
				//same value for each item examined.  Much more efficient.
				var regexpList = {};
				for(var key in requestArgs.query){
					var value = requestArgs.query[key];
					if(typeof value === "string"){
						regexpList[key] = dojo.data.util.filter.patternToRegExp(value, ignoreCase);
					}
				}

				for(var i = 0; i < arrayOfItems.length; ++i){
					var match = true;
					var candidateItem = arrayOfItems[i];
					if(candidateItem === null){
						match = false;
					}else{
						for(var key in requestArgs.query) {
							var value = requestArgs.query[key];
							if (!self._containsValue(candidateItem, key, value, regexpList[key])){
								match = false;
							}
						}
					}
					if(match){
						items.push(candidateItem);
					}
				}
				findCallback(items, requestArgs);
			}else{
				// We want a copy to pass back in case the parent wishes to sort the array. 
				// We shouldn't allow resort of the internal list, so that multiple callers 
				// can get lists and sort without affecting each other.  We also need to
				// filter out any null values that have been left as a result of deleteItem()
				// calls in ItemFileWriteStore.
				for(var i = 0; i < arrayOfItems.length; ++i){
					var item = arrayOfItems[i];
					if(item !== null){
						items.push(item);
					}
				}
				findCallback(items, requestArgs);
			}
		};

		if(this._loadFinished){
			filter(keywordArgs, this._getItemsArray(keywordArgs.queryOptions));
		}else{

			if(this._jsonFileUrl){
				//If fetches come in before the loading has finished, but while
				//a load is in progress, we have to defer the fetching to be 
				//invoked in the callback.
				if(this._loadInProgress){
					this._queuedFetches.push({args: keywordArgs, filter: filter});
				}else{
					this._loadInProgress = true;
					var getArgs = {
							url: self._jsonFileUrl, 
							handleAs: "json-comment-optional"
						};
					var getHandler = dojo.xhrGet(getArgs);
					getHandler.addCallback(function(data){
						try{
							self._getItemsFromLoadedData(data);
							self._loadFinished = true;
							self._loadInProgress = false;
							
							filter(keywordArgs, self._getItemsArray(keywordArgs.queryOptions));
							self._handleQueuedFetches();
						}catch(e){
							self._loadFinished = true;
							self._loadInProgress = false;
							errorCallback(e, keywordArgs);
						}
					});
					getHandler.addErrback(function(error){
						self._loadInProgress = false;
						errorCallback(error, keywordArgs);
					});
				}
			}else if(this._jsonData){
				try{
					this._loadFinished = true;
					this._getItemsFromLoadedData(this._jsonData);
					this._jsonData = null;
					filter(keywordArgs, this._getItemsArray(keywordArgs.queryOptions));
				}catch(e){
					errorCallback(e, keywordArgs);
				}
			}else{
				errorCallback(new Error("dojo.data.ItemFileReadStore: No JSON source data was provided as either URL or a nested Javascript object."), keywordArgs);
			}
		}
	},

	_handleQueuedFetches: function(){
		//	summary: 
		//		Internal function to execute delayed request in the store.
		//Execute any deferred fetches now.
		if (this._queuedFetches.length > 0) {
			for(var i = 0; i < this._queuedFetches.length; i++){
				var fData = this._queuedFetches[i];
				var delayedQuery = fData.args;
				var delayedFilter = fData.filter;
				if(delayedFilter){
					delayedFilter(delayedQuery, this._getItemsArray(delayedQuery.queryOptions)); 
				}else{
					this.fetchItemByIdentity(delayedQuery);
				}
			}
			this._queuedFetches = [];
		}
	},

	_getItemsArray: function(/*object?*/queryOptions){
		//	summary: 
		//		Internal function to determine which list of items to search over.
		//	queryOptions: The query options parameter, if any.
		if(queryOptions && queryOptions.deep) {
			return this._arrayOfAllItems; 
		}
		return this._arrayOfTopLevelItems;
	},

	close: function(/*dojo.data.api.Request || keywordArgs || null */ request){
		 //	summary: 
		 //		See dojo.data.api.Read.close()
	},

	_getItemsFromLoadedData: function(/* Object */ dataObject){
		//	summary:
		//		Function to parse the loaded data into item format and build the internal items array.
		//	description:
		//		Function to parse the loaded data into item format and build the internal items array.
		//
		//	dataObject:
		//		The JS data object containing the raw data to convery into item format.
		//
		// 	returns: array
		//		Array of items in store item format.
		
		// First, we define a couple little utility functions...
		
		function valueIsAnItem(/* anything */ aValue){
			// summary:
			//		Given any sort of value that could be in the raw json data,
			//		return true if we should interpret the value as being an
			//		item itself, rather than a literal value or a reference.
			// example:
			// 	|	false == valueIsAnItem("Kermit");
			// 	|	false == valueIsAnItem(42);
			// 	|	false == valueIsAnItem(new Date());
			// 	|	false == valueIsAnItem({_type:'Date', _value:'May 14, 1802'});
			// 	|	false == valueIsAnItem({_reference:'Kermit'});
			// 	|	true == valueIsAnItem({name:'Kermit', color:'green'});
			// 	|	true == valueIsAnItem({iggy:'pop'});
			// 	|	true == valueIsAnItem({foo:42});
			var isItem = (
				(aValue != null) &&
				(typeof aValue == "object") &&
				(!dojo.isArray(aValue)) &&
				(!dojo.isFunction(aValue)) &&
				(aValue.constructor == Object) &&
				(typeof aValue._reference == "undefined") && 
				(typeof aValue._type == "undefined") && 
				(typeof aValue._value == "undefined")
			);
			return isItem;
		}
		
		var self = this;
		function addItemAndSubItemsToArrayOfAllItems(/* Item */ anItem){
			self._arrayOfAllItems.push(anItem);
			for(var attribute in anItem){
				var valueForAttribute = anItem[attribute];
				if(valueForAttribute){
					if(dojo.isArray(valueForAttribute)){
						var valueArray = valueForAttribute;
						for(var k = 0; k < valueArray.length; ++k){
							var singleValue = valueArray[k];
							if(valueIsAnItem(singleValue)){
								addItemAndSubItemsToArrayOfAllItems(singleValue);
							}
						}
					}else{
						if(valueIsAnItem(valueForAttribute)){
							addItemAndSubItemsToArrayOfAllItems(valueForAttribute);
						}
					}
				}
			}
		}

		this._labelAttr = dataObject.label;

		// We need to do some transformations to convert the data structure
		// that we read from the file into a format that will be convenient
		// to work with in memory.

		// Step 1: Walk through the object hierarchy and build a list of all items
		var i;
		var item;
		this._arrayOfAllItems = [];
		this._arrayOfTopLevelItems = dataObject.items;

		for(i = 0; i < this._arrayOfTopLevelItems.length; ++i){
			item = this._arrayOfTopLevelItems[i];
			addItemAndSubItemsToArrayOfAllItems(item);
			item[this._rootItemPropName]=true;
		}

		// Step 2: Walk through all the attribute values of all the items, 
		// and replace single values with arrays.  For example, we change this:
		//		{ name:'Miss Piggy', pets:'Foo-Foo'}
		// into this:
		//		{ name:['Miss Piggy'], pets:['Foo-Foo']}
		// 
		// We also store the attribute names so we can validate our store  
		// reference and item id special properties for the O(1) isItem
		var allAttributeNames = {};
		var key;

		for(i = 0; i < this._arrayOfAllItems.length; ++i){
			item = this._arrayOfAllItems[i];
			for(key in item){
				if (key !== this._rootItemPropName)
				{
					var value = item[key];
					if(value !== null){
						if(!dojo.isArray(value)){
							item[key] = [value];
						}
					}else{
						item[key] = [null];
					}
				}
				allAttributeNames[key]=key;
			}
		}

		// Step 3: Build unique property names to use for the _storeRefPropName and _itemNumPropName
		// This should go really fast, it will generally never even run the loop.
		while(allAttributeNames[this._storeRefPropName]){
			this._storeRefPropName += "_";
		}
		while(allAttributeNames[this._itemNumPropName]){
			this._itemNumPropName += "_";
		}
		while(allAttributeNames[this._reverseRefMap]){
			this._reverseRefMap += "_";
		}

		// Step 4: Some data files specify an optional 'identifier', which is 
		// the name of an attribute that holds the identity of each item. 
		// If this data file specified an identifier attribute, then build a 
		// hash table of items keyed by the identity of the items.
		var arrayOfValues;

		var identifier = dataObject.identifier;
		if(identifier){
			this._itemsByIdentity = {};
			this._features['dojo.data.api.Identity'] = identifier;
			for(i = 0; i < this._arrayOfAllItems.length; ++i){
				item = this._arrayOfAllItems[i];
				arrayOfValues = item[identifier];
				var identity = arrayOfValues[0];
				if(!this._itemsByIdentity[identity]){
					this._itemsByIdentity[identity] = item;
				}else{
					if(this._jsonFileUrl){
						throw new Error("dojo.data.ItemFileReadStore:  The json data as specified by: [" + this._jsonFileUrl + "] is malformed.  Items within the list have identifier: [" + identifier + "].  Value collided: [" + identity + "]");
					}else if(this._jsonData){
						throw new Error("dojo.data.ItemFileReadStore:  The json data provided by the creation arguments is malformed.  Items within the list have identifier: [" + identifier + "].  Value collided: [" + identity + "]");
					}
				}
			}
		}else{
			this._features['dojo.data.api.Identity'] = Number;
		}

		// Step 5: Walk through all the items, and set each item's properties 
		// for _storeRefPropName and _itemNumPropName, so that store.isItem() will return true.
		for(i = 0; i < this._arrayOfAllItems.length; ++i){
			item = this._arrayOfAllItems[i];
			item[this._storeRefPropName] = this;
			item[this._itemNumPropName] = i;
		}

		// Step 6: We walk through all the attribute values of all the items,
		// looking for type/value literals and item-references.
		//
		// We replace item-references with pointers to items.  For example, we change:
		//		{ name:['Kermit'], friends:[{_reference:{name:'Miss Piggy'}}] }
		// into this:
		//		{ name:['Kermit'], friends:[miss_piggy] } 
		// (where miss_piggy is the object representing the 'Miss Piggy' item).
		//
		// We replace type/value pairs with typed-literals.  For example, we change:
		//		{ name:['Nelson Mandela'], born:[{_type:'Date', _value:'July 18, 1918'}] }
		// into this:
		//		{ name:['Kermit'], born:(new Date('July 18, 1918')) } 
		//
		// We also generate the associate map for all items for the O(1) isItem function.
		for(i = 0; i < this._arrayOfAllItems.length; ++i){
			item = this._arrayOfAllItems[i]; // example: { name:['Kermit'], friends:[{_reference:{name:'Miss Piggy'}}] }
			for(key in item){
				arrayOfValues = item[key]; // example: [{_reference:{name:'Miss Piggy'}}]
				for(var j = 0; j < arrayOfValues.length; ++j) {
					value = arrayOfValues[j]; // example: {_reference:{name:'Miss Piggy'}}
					if(value !== null && typeof value == "object"){
						if(value._type && value._value){
							var type = value._type; // examples: 'Date', 'Color', or 'ComplexNumber'
							var mappingObj = this._datatypeMap[type]; // examples: Date, dojo.Color, foo.math.ComplexNumber, {type: dojo.Color, deserialize(value){ return new dojo.Color(value)}}
							if(!mappingObj){ 
								throw new Error("dojo.data.ItemFileReadStore: in the typeMap constructor arg, no object class was specified for the datatype '" + type + "'");
							}else if(dojo.isFunction(mappingObj)){
								arrayOfValues[j] = new mappingObj(value._value);
							}else if(dojo.isFunction(mappingObj.deserialize)){
								arrayOfValues[j] = mappingObj.deserialize(value._value);
							}else{
								throw new Error("dojo.data.ItemFileReadStore: Value provided in typeMap was neither a constructor, nor a an object with a deserialize function");
							}
						}
						if(value._reference){
							var referenceDescription = value._reference; // example: {name:'Miss Piggy'}
							if(!dojo.isObject(referenceDescription)){
								// example: 'Miss Piggy'
								// from an item like: { name:['Kermit'], friends:[{_reference:'Miss Piggy'}]}
								arrayOfValues[j] = this._itemsByIdentity[referenceDescription];
							}else{
								// example: {name:'Miss Piggy'}
								// from an item like: { name:['Kermit'], friends:[{_reference:{name:'Miss Piggy'}}] }
								for(var k = 0; k < this._arrayOfAllItems.length; ++k){
									var candidateItem = this._arrayOfAllItems[k];
									var found = true;
									for(var refKey in referenceDescription){
										if(candidateItem[refKey] != referenceDescription[refKey]){ 
											found = false; 
										}
									}
									if(found){ 
										arrayOfValues[j] = candidateItem; 
									}
								}
							}
							if(this.referenceIntegrity){
								var refItem = arrayOfValues[j];
								if(this.isItem(refItem)){
									this._addReferenceToMap(refItem, item, key);
								}
							}
						}else if(this.isItem(value)){
							//It's a child item (not one referenced through _reference).  
							//We need to treat this as a referenced item, so it can be cleaned up
							//in a write store easily.
							if(this.referenceIntegrity){
								this._addReferenceToMap(value, item, key);
							}
						}
					}
				}
			}
		}
	},

	_addReferenceToMap: function(/*item*/ refItem, /*item*/ parentItem, /*string*/ attribute){
		 //	summary:
		 //		Method to add an reference map entry for an item and attribute.
		 //	description:
		 //		Method to add an reference map entry for an item and attribute. 		 //
		 //	refItem:
		 //		The item that is referenced.
		 //	parentItem:
		 //		The item that holds the new reference to refItem.
		 //	attribute:
		 //		The attribute on parentItem that contains the new reference.
		 
		 //Stub function, does nothing.  Real processing is in ItemFileWriteStore.
	},

	getIdentity: function(/* item */ item){
		//	summary: 
		//		See dojo.data.api.Identity.getIdentity()
		var identifier = this._features['dojo.data.api.Identity'];
		if(identifier === Number){
			return item[this._itemNumPropName]; // Number
		}else{
			var arrayOfValues = item[identifier];
			if(arrayOfValues){
				return arrayOfValues[0]; // Object || String
			}
		}
		return null; // null
	},

	fetchItemByIdentity: function(/* Object */ keywordArgs){
		//	summary: 
		//		See dojo.data.api.Identity.fetchItemByIdentity()

		// Hasn't loaded yet, we have to trigger the load.
		if(!this._loadFinished){
			var self = this;
			if(this._jsonFileUrl){

				if(this._loadInProgress){
					this._queuedFetches.push({args: keywordArgs});
				}else{
					this._loadInProgress = true;
					var getArgs = {
							url: self._jsonFileUrl, 
							handleAs: "json-comment-optional"
					};
					var getHandler = dojo.xhrGet(getArgs);
					getHandler.addCallback(function(data){
						var scope =  keywordArgs.scope?keywordArgs.scope:dojo.global;
						try{
							self._getItemsFromLoadedData(data);
							self._loadFinished = true;
							self._loadInProgress = false;
							var item = self._getItemByIdentity(keywordArgs.identity);
							if(keywordArgs.onItem){
								keywordArgs.onItem.call(scope, item);
							}
							self._handleQueuedFetches();
						}catch(error){
							self._loadInProgress = false;
							if(keywordArgs.onError){
								keywordArgs.onError.call(scope, error);
							}
						}
					});
					getHandler.addErrback(function(error){
						self._loadInProgress = false;
						if(keywordArgs.onError){
							var scope =  keywordArgs.scope?keywordArgs.scope:dojo.global;
							keywordArgs.onError.call(scope, error);
						}
					});
				}

			}else if(this._jsonData){
				// Passed in data, no need to xhr.
				self._getItemsFromLoadedData(self._jsonData);
				self._jsonData = null;
				self._loadFinished = true;
				var item = self._getItemByIdentity(keywordArgs.identity);
				if(keywordArgs.onItem){
					var scope =  keywordArgs.scope?keywordArgs.scope:dojo.global;
					keywordArgs.onItem.call(scope, item);
				}
			} 
		}else{
			// Already loaded.  We can just look it up and call back.
			var item = this._getItemByIdentity(keywordArgs.identity);
			if(keywordArgs.onItem){
				var scope =  keywordArgs.scope?keywordArgs.scope:dojo.global;
				keywordArgs.onItem.call(scope, item);
			}
		}
	},

	_getItemByIdentity: function(/* Object */ identity){
		//	summary:
		//		Internal function to look an item up by its identity map.
		var item = null;
		if(this._itemsByIdentity){
			item = this._itemsByIdentity[identity];
		}else{
			item = this._arrayOfAllItems[identity];
		}
		if(item === undefined){
			item = null;
		}
		return item; // Object
	},

	getIdentityAttributes: function(/* item */ item){
		//	summary: 
		//		See dojo.data.api.Identity.getIdentifierAttributes()
		 
		var identifier = this._features['dojo.data.api.Identity'];
		if(identifier === Number){
			// If (identifier === Number) it means getIdentity() just returns
			// an integer item-number for each item.  The dojo.data.api.Identity
			// spec says we need to return null if the identity is not composed 
			// of attributes 
			return null; // null
		}else{
			return [identifier]; // Array
		}
	},
	
	_forceLoad: function(){
		//	summary: 
		//		Internal function to force a load of the store if it hasn't occurred yet.  This is required
		//		for specific functions to work properly.  
		var self = this;
		if(this._jsonFileUrl){
				var getArgs = {
					url: self._jsonFileUrl, 
					handleAs: "json-comment-optional",
					sync: true
				};
			var getHandler = dojo.xhrGet(getArgs);
			getHandler.addCallback(function(data){
				try{
					//Check to be sure there wasn't another load going on concurrently 
					//So we don't clobber data that comes in on it.  If there is a load going on
					//then do not save this data.  It will potentially clobber current data.
					//We mainly wanted to sync/wait here.
					//TODO:  Revisit the loading scheme of this store to improve multi-initial
					//request handling.
					if (self._loadInProgress !== true && !self._loadFinished) {
						self._getItemsFromLoadedData(data);
						self._loadFinished = true;
					}
				}catch(e){
					console.log(e);
					throw e;
				}
			});
			getHandler.addErrback(function(error){
				throw error;
			});
		}else if(this._jsonData){
			self._getItemsFromLoadedData(self._jsonData);
			self._jsonData = null;
			self._loadFinished = true;
		} 
	}
});
//Mix in the simple fetch implementation to this class.
dojo.extend(dojo.data.ItemFileReadStore,dojo.data.util.simpleFetch);

}

if(!dojo._hasResource["dijit._Container"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dijit._Container"] = true;
dojo.provide("dijit._Container");

dojo.declare("dijit._Contained",
	null,
	{
		// summary
		//		Mixin for widgets that are children of a container widget
		//
		// example:
		// | 	// make a basic custom widget that knows about it's parents
		// |	dojo.declare("my.customClass",[dijit._Widget,dijit._Contained],{});
		// 
		getParent: function(){
			// summary:
			//		Returns the parent widget of this widget, assuming the parent
			//		implements dijit._Container
			for(var p=this.domNode.parentNode; p; p=p.parentNode){
				var id = p.getAttribute && p.getAttribute("widgetId");
				if(id){
					var parent = dijit.byId(id);
					return parent.isContainer ? parent : null;
				}
			}
			return null;
		},

		_getSibling: function(which){
			var node = this.domNode;
			do{
				node = node[which+"Sibling"];
			}while(node && node.nodeType != 1);
			if(!node){ return null; } // null
			var id = node.getAttribute("widgetId");
			return dijit.byId(id);
		},

		getPreviousSibling: function(){
			// summary:
			//		Returns null if this is the first child of the parent,
			//		otherwise returns the next element sibling to the "left".

			return this._getSibling("previous"); // Mixed
		},

		getNextSibling: function(){
			// summary:
			//		Returns null if this is the last child of the parent,
			//		otherwise returns the next element sibling to the "right".

			return this._getSibling("next"); // Mixed
		}
	}
);

dojo.declare("dijit._Container",
	null,
	{
		// summary:
		//		Mixin for widgets that contain a list of children.
		// description:
		//		Use this mixin when the widget needs to know about and
		//		keep track of it's widget children. Widgets like SplitContainer
		//		and TabContainer.  

		isContainer: true,

		addChild: function(/*Widget*/ widget, /*int?*/ insertIndex){
			// summary:
			//		Process the given child widget, inserting it's dom node as
			//		a child of our dom node

			if(insertIndex === undefined){
				insertIndex = "last";
			}
			var refNode = this.containerNode || this.domNode;
			if(insertIndex && typeof insertIndex == "number"){
				var children = dojo.query("> [widgetid]", refNode);
				if(children && children.length >= insertIndex){
					refNode = children[insertIndex-1]; insertIndex = "after";
				}
			}
			dojo.place(widget.domNode, refNode, insertIndex);

			// If I've been started but the child widget hasn't been started,
			// start it now.  Make sure to do this after widget has been
			// inserted into the DOM tree, so it can see that it's being controlled by me,
			// so it doesn't try to size itself.
			if(this._started && !widget._started){
				widget.startup();
			}
		},

		removeChild: function(/*Widget*/ widget){
			// summary:
			//		Removes the passed widget instance from this widget but does
			//		not destroy it
			var node = widget.domNode;
			node.parentNode.removeChild(node);	// detach but don't destroy
		},

		_nextElement: function(node){
			do{
				node = node.nextSibling;
			}while(node && node.nodeType != 1);
			return node;
		},

		_firstElement: function(node){
			node = node.firstChild;
			if(node && node.nodeType != 1){
				node = this._nextElement(node);
			}
			return node;
		},

		getChildren: function(){
			// summary:
			//		Returns array of children widgets
			return dojo.query("> [widgetId]", this.containerNode || this.domNode).map(dijit.byNode); // Array
		},

		hasChildren: function(){
			// summary:
			//		Returns true if widget has children
			var cn = this.containerNode || this.domNode;
			return !!this._firstElement(cn); // Boolean
		},

		_getSiblingOfChild: function(/*Widget*/ child, /*int*/ dir){
			// summary:
			//		Get the next or previous widget sibling of child
			// dir:
			//		if 1, get the next sibling
			//		if -1, get the previous sibling
			var node = child.domNode;
			var which = (dir>0 ? "nextSibling" : "previousSibling");
			do{
				node = node[which];
			}while(node && (node.nodeType != 1 || !dijit.byNode(node)));
			return node ? dijit.byNode(node) : null;
		}
	}
);

dojo.declare("dijit._KeyNavContainer",
	[dijit._Container],
	{

		// summary: A _Container with keyboard navigation of its children.
		// decscription:
		//		To use this mixin, call connectKeyNavHandlers() in
		//		postCreate() and call startupKeyNavChildren() in startup().
		//		It provides normalized keyboard and focusing code for Container
		//		widgets.
/*=====
		// focusedChild: Widget
		//		The currently focused child widget, or null if there isn't one
		focusedChild: null,
=====*/

		_keyNavCodes: {},

		connectKeyNavHandlers: function(/*Array*/ prevKeyCodes, /*Array*/ nextKeyCodes){
			// summary:
			//		Call in postCreate() to attach the keyboard handlers
			//		to the container.
			// preKeyCodes: Array
			//		Key codes for navigating to the previous child.
			// nextKeyCodes: Array
			//		Key codes for navigating to the next child.

			var keyCodes = this._keyNavCodes = {};
			var prev = dojo.hitch(this, this.focusPrev);
			var next = dojo.hitch(this, this.focusNext);
			dojo.forEach(prevKeyCodes, function(code){ keyCodes[code] = prev });
			dojo.forEach(nextKeyCodes, function(code){ keyCodes[code] = next });
			this.connect(this.domNode, "onkeypress", "_onContainerKeypress");
			this.connect(this.domNode, "onfocus", "_onContainerFocus");
		},

		startupKeyNavChildren: function(){
			// summary:
			//		Call in startup() to set child tabindexes to -1
			dojo.forEach(this.getChildren(), dojo.hitch(this, "_startupChild"));
		},

		addChild: function(/*Widget*/ widget, /*int?*/ insertIndex){
			// summary: Add a child to our _Container
			dijit._KeyNavContainer.superclass.addChild.apply(this, arguments);
			this._startupChild(widget);
		},

		focus: function(){
			// summary: Default focus() implementation: focus the first child.
			this.focusFirstChild();
		},

		focusFirstChild: function(){
			// summary: Focus the first focusable child in the container.
			this.focusChild(this._getFirstFocusableChild());
		},

		focusNext: function(){
			// summary: Focus the next widget or focal node (for widgets
			//		with multiple focal nodes) within this container.
			if(this.focusedChild && this.focusedChild.hasNextFocalNode
					&& this.focusedChild.hasNextFocalNode()){
				this.focusedChild.focusNext();
				return;
			}
			var child = this._getNextFocusableChild(this.focusedChild, 1);
			if(child.getFocalNodes){
				this.focusChild(child, child.getFocalNodes()[0]);
			}else{
				this.focusChild(child);
			}
		},

		focusPrev: function(){
			// summary: Focus the previous widget or focal node (for widgets
			//		with multiple focal nodes) within this container.
			if(this.focusedChild && this.focusedChild.hasPrevFocalNode
					&& this.focusedChild.hasPrevFocalNode()){
				this.focusedChild.focusPrev();
				return;
			}
			var child = this._getNextFocusableChild(this.focusedChild, -1);
			if(child.getFocalNodes){
				var nodes = child.getFocalNodes();
				this.focusChild(child, nodes[nodes.length-1]);
			}else{
				this.focusChild(child);
			}
		},

		focusChild: function(/*Widget*/ widget, /*Node?*/ node){
			// summary: Focus widget. Optionally focus 'node' within widget.
			if(widget){
				if(this.focusedChild && widget !== this.focusedChild){
					this._onChildBlur(this.focusedChild);
				}
				this.focusedChild = widget;
				if(node && widget.focusFocalNode){
					widget.focusFocalNode(node);
				}else{
					widget.focus();
				}
			}
		},

		_startupChild: function(/*Widget*/ widget){
			// summary:
			//		Set tabindex="-1" on focusable widgets so that we
			// 		can focus them programmatically and by clicking.
			//		Connect focus and blur handlers.
			if(widget.getFocalNodes){
				dojo.forEach(widget.getFocalNodes(), function(node){
					dojo.attr(node, "tabindex", -1);
					this._connectNode(node);
				}, this);
			}else{
				var node = widget.focusNode || widget.domNode;
				if(widget.isFocusable()){
					dojo.attr(node, "tabindex", -1);
				}
				this._connectNode(node);
			}
		},

		_connectNode: function(/*Element*/ node){
			this.connect(node, "onfocus", "_onNodeFocus");
			this.connect(node, "onblur", "_onNodeBlur");
		},

		_onContainerFocus: function(evt){
			// focus bubbles on Firefox,
			// so just make sure that focus has really gone to the container
			if(evt.target === this.domNode){
				this.focusFirstChild();
			}
		},

		_onContainerKeypress: function(evt){
			if(evt.ctrlKey || evt.altKey){ return; }
			var func = this._keyNavCodes[evt.keyCode];
			if(func){
				func();
				dojo.stopEvent(evt);
			}
		},

		_onNodeFocus: function(evt){
			// while focus is on a child,
			// take the container out of the tab order so that
			// we can shift-tab to the element before the container
			dojo.attr(this.domNode, "tabindex", -1);
			// record the child that has been focused
			var widget = dijit.getEnclosingWidget(evt.target);
			if(widget && widget.isFocusable()){
				this.focusedChild = widget;
			}
			dojo.stopEvent(evt);
		},

		_onNodeBlur: function(evt){
			// when focus leaves a child,
			// reinstate the container's tabindex
			if(this.tabIndex){
				dojo.attr(this.domNode, "tabindex", this.tabIndex);
			}
			dojo.stopEvent(evt);
		},

		_onChildBlur: function(/*Widget*/ widget){
			// summary:
			//		Called when focus leaves a child widget to go
			//		to a sibling widget.
		},

		_getFirstFocusableChild: function(){
			return this._getNextFocusableChild(null, 1);
		},

		_getNextFocusableChild: function(child, dir){
			if(child){
				child = this._getSiblingOfChild(child, dir);
			}
			var children = this.getChildren();
			for(var i=0; i < children.length; i++){
				if(!child){
					child = children[(dir>0) ? 0 : (children.length-1)];
				}
				if(child.isFocusable()){
					return child;
				}
				child = this._getSiblingOfChild(child, dir);
			}
			// no focusable child found
			return null;
		}
	}
);

}

if(!dojo._hasResource["dijit.form.Button"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dijit.form.Button"] = true;
dojo.provide("dijit.form.Button");




dojo.declare("dijit.form.Button",
	dijit.form._FormWidget,
	{
	// summary:
	//	Basically the same thing as a normal HTML button, but with special styling.
	//
	// example:
	// |	<button dojoType="dijit.form.Button" onClick="...">Hello world</button>
	// 
	// example:
	// |	var button1 = new dijit.form.Button({label: "hello world", onClick: foo});
	// |	dojo.body().appendChild(button1.domNode);
	//
	// label: String
	//	text to display in button
	label: "",

	// showLabel: Boolean
	//	whether or not to display the text label in button
	showLabel: true,

	// iconClass: String
	//	class to apply to div in button to make it display an icon
	iconClass: "",

	type: "button",
	baseClass: "dijitButton",
	templateString:"<div class=\"dijit dijitReset dijitLeft dijitInline\"\r\n\tdojoAttachEvent=\"onclick:_onButtonClick,onmouseenter:_onMouse,onmouseleave:_onMouse,onmousedown:_onMouse\"\r\n\twaiRole=\"presentation\"\r\n\t><button class=\"dijitReset dijitStretch dijitButtonNode dijitButtonContents\" dojoAttachPoint=\"focusNode,titleNode\"\r\n\t\ttype=\"${type}\" waiRole=\"button\" waiState=\"labelledby-${id}_label\"\r\n\t\t><span class=\"dijitReset dijitInline ${iconClass}\" dojoAttachPoint=\"iconNode\" \r\n \t\t\t><span class=\"dijitReset dijitToggleButtonIconChar\">&#10003;</span \r\n\t\t></span\r\n\t\t><div class=\"dijitReset dijitInline\"><center class=\"dijitReset dijitButtonText\" id=\"${id}_label\" dojoAttachPoint=\"containerNode\">${label}</center></div\r\n\t></button\r\n></div>\r\n",

	_onChangeMonitor: '',
	// TODO: set button's title to this.containerNode.innerText

	_onClick: function(/*Event*/ e){
		// summary: internal function to handle click actions
		if(this.disabled || this.readOnly){
			dojo.stopEvent(e); // needed for checkbox
			return false;
		}
		this._clicked(); // widget click actions
		return this.onClick(e); // user click actions
	},

	_onButtonClick: function(/*Event*/ e){
		// summary: callback when the user mouse clicks the button portion
		if(this._onClick(e) === false){ // returning nothing is same as true
			dojo.stopEvent(e);
		}else if(this.type=="submit" && !this.focusNode.form){ // see if a nonform widget needs to be signalled
			for(var node=this.domNode; node.parentNode/*#5935*/; node=node.parentNode){
				var widget=dijit.byNode(node);
				if(widget && typeof widget._onSubmit == "function"){
					widget._onSubmit(e);
					break;
				}
			}
		}
	},

	postCreate: function(){
		// summary:
		//	get label and set as title on button icon if necessary
		if (this.showLabel == false){
			var labelText = "";
			this.label = this.containerNode.innerHTML;
			labelText = dojo.trim(this.containerNode.innerText || this.containerNode.textContent || '');
			// set title attrib on iconNode
			this.titleNode.title=labelText;
			dojo.addClass(this.containerNode,"dijitDisplayNone");
		}
		dojo.setSelectable(this.focusNode, false);
		this.inherited(arguments);
	},

	onClick: function(/*Event*/ e){
		// summary: user callback for when button is clicked
		//      if type="submit", return true to perform submit
		return true;
	},

	_clicked: function(/*Event*/ e){
		// summary: internal replaceable function for when the button is clicked
	},

	setLabel: function(/*String*/ content){
		// summary: reset the label (text) of the button; takes an HTML string
		this.containerNode.innerHTML = this.label = content;
		this._layoutHack();
		if (this.showLabel == false){
			this.titleNode.title=dojo.trim(this.containerNode.innerText || this.containerNode.textContent || '');
		}
	}		
});


dojo.declare("dijit.form.DropDownButton", [dijit.form.Button, dijit._Container], {
	// summary: A button with a popup
	//
	// example:
	// |	<button dojoType="dijit.form.DropDownButton" label="Hello world">
	// |		<div dojotype="dijit.Menu">...</div>
	// |	</button>
	//
	// example:
	// |	var button1 = new dijit.form.DropDownButton({ label: "hi", dropDown: new dijit.Menu(...) });
	// |	dojo.body().appendChild(button1);
	// 	
	
	baseClass : "dijitDropDownButton",

	templateString:"<div class=\"dijit dijitReset dijitLeft dijitInline\"\r\n\tdojoAttachEvent=\"onmouseenter:_onMouse,onmouseleave:_onMouse,onmousedown:_onMouse,onclick:_onDropDownClick,onkeydown:_onDropDownKeydown,onblur:_onDropDownBlur,onkeypress:_onKey\"\r\n\twaiRole=\"presentation\"\r\n\t><div class='dijitReset dijitRight' waiRole=\"presentation\"\r\n\t><button class=\"dijitReset dijitStretch dijitButtonNode dijitButtonContents\" type=\"${type}\"\r\n\t\tdojoAttachPoint=\"focusNode,titleNode\" waiRole=\"button\" waiState=\"haspopup-true,labelledby-${id}_label\"\r\n\t\t><div class=\"dijitReset dijitInline ${iconClass}\" dojoAttachPoint=\"iconNode\" waiRole=\"presentation\"></div\r\n\t\t><div class=\"dijitReset dijitInline dijitButtonText\"  dojoAttachPoint=\"containerNode,popupStateNode\" waiRole=\"presentation\"\r\n\t\t\tid=\"${id}_label\">${label}</div\r\n\t\t><div class=\"dijitReset dijitInline dijitArrowButtonInner\" waiRole=\"presentation\">&thinsp;</div\r\n\t\t><div class=\"dijitReset dijitInline dijitArrowButtonChar\" waiRole=\"presentation\">&#9660;</div\r\n\t></button\r\n></div></div>\r\n",

	_fillContent: function(){
		// my inner HTML contains both the button contents and a drop down widget, like
		// <DropDownButton>  <span>push me</span>  <Menu> ... </Menu> </DropDownButton>
		// The first node is assumed to be the button content. The widget is the popup.
		if(this.srcNodeRef){ // programatically created buttons might not define srcNodeRef
			//FIXME: figure out how to filter out the widget and use all remaining nodes as button
			//	content, not just nodes[0]
			var nodes = dojo.query("*", this.srcNodeRef);
			dijit.form.DropDownButton.superclass._fillContent.call(this, nodes[0]);

			// save pointer to srcNode so we can grab the drop down widget after it's instantiated
			this.dropDownContainer = this.srcNodeRef;
		}
	},

	startup: function(){
		if(this._started){ return; }

		// the child widget from srcNodeRef is the dropdown widget.  Insert it in the page DOM,
		// make it invisible, and store a reference to pass to the popup code.
		if(!this.dropDown){
			var dropDownNode = dojo.query("[widgetId]", this.dropDownContainer)[0];
			this.dropDown = dijit.byNode(dropDownNode);
			delete this.dropDownContainer;
		}
		dijit.popup.prepare(this.dropDown.domNode);

		this.inherited(arguments);
	},

	destroyDescendants: function(){
		if(this.dropDown){
			this.dropDown.destroyRecursive();
			delete this.dropDown;
		}
		this.inherited(arguments);
	},

	_onArrowClick: function(/*Event*/ e){
		// summary: callback when the user mouse clicks on menu popup node
		if(this.disabled || this.readOnly){ return; }
		this._toggleDropDown();
	},

	_onDropDownClick: function(/*Event*/ e){
		// on Firefox 2 on the Mac it is possible to fire onclick
		// by pressing enter down on a second element and transferring
		// focus to the DropDownButton;
		// we want to prevent opening our menu in this situation
		// and only do so if we have seen a keydown on this button;
		// e.detail != 0 means that we were fired by mouse
		var isMacFFlessThan3 = dojo.isFF && dojo.isFF < 3
			&& navigator.appVersion.indexOf("Macintosh") != -1;
		if(!isMacFFlessThan3 || e.detail != 0 || this._seenKeydown){
			this._onArrowClick(e);
		}
		this._seenKeydown = false;
	},

	_onDropDownKeydown: function(/*Event*/ e){
		this._seenKeydown = true;
	},

	_onDropDownBlur: function(/*Event*/ e){
		this._seenKeydown = false;
	},

	_onKey: function(/*Event*/ e){
		// summary: callback when the user presses a key on menu popup node
		if(this.disabled || this.readOnly){ return; }
		if(e.keyCode == dojo.keys.DOWN_ARROW){
			if(!this.dropDown || this.dropDown.domNode.style.visibility=="hidden"){
				dojo.stopEvent(e);
				this._toggleDropDown();
			}
		}
	},

	_onBlur: function(){
		// summary: called magically when focus has shifted away from this widget and it's dropdown
		this._closeDropDown();
		// don't focus on button.  the user has explicitly focused on something else.
		this.inherited(arguments);
	},

	_toggleDropDown: function(){
		// summary: toggle the drop-down widget; if it is up, close it, if not, open it
		if(this.disabled || this.readOnly){ return; }
		dijit.focus(this.popupStateNode);
		var dropDown = this.dropDown;
		if(!dropDown){ return; }
		if(!this._opened){
			// If there's an href, then load that first, so we don't get a flicker
			if(dropDown.href && !dropDown.isLoaded){
				var self = this;
				var handler = dojo.connect(dropDown, "onLoad", function(){
					dojo.disconnect(handler);
					self._openDropDown();
				});
				dropDown._loadCheck(true);
				return;
			}else{
				this._openDropDown();
			}
		}else{
			this._closeDropDown();
		}
	},

	_openDropDown: function(){
		var dropDown = this.dropDown;
		var oldWidth=dropDown.domNode.style.width;
		var self = this;

		dijit.popup.open({
			parent: this,
			popup: dropDown,
			around: this.domNode,
			orient:
				// TODO: add user-defined positioning option, like in Tooltip.js
				this.isLeftToRight() ? {'BL':'TL', 'BR':'TR', 'TL':'BL', 'TR':'BR'}
				: {'BR':'TR', 'BL':'TL', 'TR':'BR', 'TL':'BL'},
			onExecute: function(){
				self._closeDropDown(true);
			},
			onCancel: function(){
				self._closeDropDown(true);
			},
			onClose: function(){
				dropDown.domNode.style.width = oldWidth;
				self.popupStateNode.removeAttribute("popupActive");
				this._opened = false;
			}
		});
		if(this.domNode.offsetWidth > dropDown.domNode.offsetWidth){
			var adjustNode = null;
			if(!this.isLeftToRight()){
				adjustNode = dropDown.domNode.parentNode;
				var oldRight = adjustNode.offsetLeft + adjustNode.offsetWidth;
			}
			// make menu at least as wide as the button
			dojo.marginBox(dropDown.domNode, {w: this.domNode.offsetWidth});
			if(adjustNode){
				adjustNode.style.left = oldRight - this.domNode.offsetWidth + "px";
			}
		}
		this.popupStateNode.setAttribute("popupActive", "true");
		this._opened=true;
		if(dropDown.focus){
			dropDown.focus();
		}
		// TODO: set this.checked and call setStateClass(), to affect button look while drop down is shown
	},
	
	_closeDropDown: function(/*Boolean*/ focus){
		if(this._opened){
			dijit.popup.close(this.dropDown);
			if(focus){ this.focus(); }
			this._opened = false;			
		}
	}
});

dojo.declare("dijit.form.ComboButton", dijit.form.DropDownButton, {
	// summary: A Normal Button with a DropDown
	//
	// example:
	// |	<button dojoType="dijit.form.ComboButton" onClick="...">
	// |		<span>Hello world</span>
	// |		<div dojoType="dijit.Menu">...</div>
	// |	</button>
	//
	// example:
	// |	var button1 = new dijit.form.ComboButton({label: "hello world", onClick: foo, dropDown: "myMenu"});
	// |	dojo.body().appendChild(button1.domNode);
	// 

	templateString:"<table class='dijit dijitReset dijitInline dijitLeft'\r\n\tcellspacing='0' cellpadding='0' waiRole=\"presentation\"\r\n\t><tbody waiRole=\"presentation\"><tr waiRole=\"presentation\"\r\n\t\t><td\tclass=\"dijitReset dijitStretch dijitButtonContents dijitButtonNode\"\r\n\t\t\ttabIndex=\"${tabIndex}\"\r\n\t\t\tdojoAttachEvent=\"ondijitclick:_onButtonClick,onmouseenter:_onMouse,onmouseleave:_onMouse,onmousedown:_onMouse\"  dojoAttachPoint=\"titleNode\"\r\n\t\t\twaiRole=\"button\" waiState=\"labelledby-${id}_label\"\r\n\t\t\t><div class=\"dijitReset dijitInline ${iconClass}\" dojoAttachPoint=\"iconNode\" waiRole=\"presentation\"></div\r\n\t\t\t><div class=\"dijitReset dijitInline dijitButtonText\" id=\"${id}_label\" dojoAttachPoint=\"containerNode\" waiRole=\"presentation\">${label}</div\r\n\t\t></td\r\n\t\t><td class='dijitReset dijitStretch dijitButtonNode dijitArrowButton dijitDownArrowButton'\r\n\t\t\tdojoAttachPoint=\"popupStateNode,focusNode\"\r\n\t\t\tdojoAttachEvent=\"ondijitclick:_onArrowClick, onkeypress:_onKey,onmouseenter:_onMouse,onmouseleave:_onMouse\"\r\n\t\t\tstateModifier=\"DownArrow\"\r\n\t\t\ttitle=\"${optionsTitle}\" name=\"${name}\"\r\n\t\t\twaiRole=\"button\" waiState=\"haspopup-true\"\r\n\t\t\t><div class=\"dijitReset dijitArrowButtonInner\" waiRole=\"presentation\">&thinsp;</div\r\n\t\t\t><div class=\"dijitReset dijitArrowButtonChar\" waiRole=\"presentation\">&#9660;</div\r\n\t\t></td\r\n\t></tr></tbody\r\n></table>\r\n",

	attributeMap: dojo.mixin(dojo.clone(dijit.form._FormWidget.prototype.attributeMap),
		{id:"", name:""}),

	// optionsTitle: String
	//  text that describes the options menu (accessibility)
	optionsTitle: "",

	baseClass: "dijitComboButton",

	_focusedNode: null,

	postCreate: function(){
		this.inherited(arguments);
		this._focalNodes = [this.titleNode, this.popupStateNode];
		dojo.forEach(this._focalNodes, dojo.hitch(this, function(node){
			if(dojo.isIE){
				this.connect(node, "onactivate", this._onNodeFocus);
				this.connect(node, "ondeactivate", this._onNodeBlur);
			}else{
				this.connect(node, "onfocus", this._onNodeFocus);
				this.connect(node, "onblur", this._onNodeBlur);
			}
		}));
	},

	focusFocalNode: function(node){
		// summary: Focus the focal node node.
		this._focusedNode = node;
		dijit.focus(node);
	},

	hasNextFocalNode: function(){
		// summary: Returns true if this widget has no node currently
		//		focused or if there is a node following the focused one.
		//		False is returned if the last node has focus.
		return this._focusedNode !== this.getFocalNodes()[1];
	},

	focusNext: function(){
		// summary: Focus the focal node following the current node with focus
		//		or the first one if no node currently has focus.
		this._focusedNode = this.getFocalNodes()[this._focusedNode ? 1 : 0];
		dijit.focus(this._focusedNode);
	},

	hasPrevFocalNode: function(){
		// summary: Returns true if this widget has no node currently
		//		focused or if there is a node before the focused one.
		//		False is returned if the first node has focus.
		return this._focusedNode !== this.getFocalNodes()[0];
	},

	focusPrev: function(){
		// summary: Focus the focal node before the current node with focus
		//		or the last one if no node currently has focus.
		this._focusedNode = this.getFocalNodes()[this._focusedNode ? 0 : 1];
		dijit.focus(this._focusedNode);
	},

	getFocalNodes: function(){
		// summary: Returns an array of focal nodes for this widget.
		return this._focalNodes;
	},

	_onNodeFocus: function(evt){
		this._focusedNode = evt.currentTarget;
		var fnc = this._focusedNode == this.focusNode ? "dijitDownArrowButtonFocused" : "dijitButtonContentsFocused";
		dojo.addClass(this._focusedNode, fnc);
	},

	_onNodeBlur: function(evt){
		var fnc = evt.currentTarget == this.focusNode ? "dijitDownArrowButtonFocused" : "dijitButtonContentsFocused";
		dojo.removeClass(evt.currentTarget, fnc);
	},

	_onBlur: function(){
		this.inherited(arguments);
		this._focusedNode = null;
	}
});

dojo.declare("dijit.form.ToggleButton", dijit.form.Button, {
	// summary:
	//	A button that can be in two states (checked or not).
	//	Can be base class for things like tabs or checkbox or radio buttons

	baseClass: "dijitToggleButton",

	// checked: Boolean
	//		Corresponds to the native HTML <input> element's attribute.
	//		In markup, specified as "checked='checked'" or just "checked".
	//		True if the button is depressed, or the checkbox is checked,
	//		or the radio button is selected, etc.
	checked: false,

	_onChangeMonitor: 'checked',

	attributeMap: dojo.mixin(dojo.clone(dijit.form.Button.prototype.attributeMap),
		{checked:"focusNode"}),

	_clicked: function(/*Event*/ evt){
		this.setAttribute('checked', !this.checked);
	},

	setAttribute: function(/*String*/ attr, /*anything*/ value){
		this.inherited(arguments);
		switch(attr){
			case "checked":
				dijit.setWaiState(this.focusNode || this.domNode, "pressed", this.checked);
				this._setStateClass();		
				this._handleOnChange(this.checked, true);
		}
	},


	setChecked: function(/*Boolean*/ checked){
		// summary:
		//	Programatically deselect the button
		dojo.deprecated("setChecked("+checked+") is deprecated. Use setAttribute('checked',"+checked+") instead.", "", "2.0");
		this.setAttribute('checked', checked);
	},
	
	postCreate: function(){
		this.inherited(arguments);
		this.setAttribute('checked', this.checked); //to initially set wai pressed state 
	}
});

}

if(!dojo._hasResource["dijit.form.CheckBox"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dijit.form.CheckBox"] = true;
dojo.provide("dijit.form.CheckBox");



dojo.declare(
	"dijit.form.CheckBox",
	dijit.form.ToggleButton,
	{
		// summary:
		// 		Same as an HTML checkbox, but with fancy styling.
		//
		// description:
		// User interacts with real html inputs.
		// On onclick (which occurs by mouse click, space-bar, or
		// using the arrow keys to switch the selected radio button),
		// we update the state of the checkbox/radio.
		//
		// There are two modes:
		//   1. High contrast mode
		//   2. Normal mode
		// In case 1, the regular html inputs are shown and used by the user.
		// In case 2, the regular html inputs are invisible but still used by
		// the user. They are turned quasi-invisible and overlay the background-image.

		templateString:"<div class=\"dijitReset dijitInline\" waiRole=\"presentation\"\r\n\t><input\r\n\t \ttype=\"${type}\" name=\"${name}\"\r\n\t\tclass=\"dijitReset dijitCheckBoxInput\"\r\n\t\tdojoAttachPoint=\"focusNode\"\r\n\t \tdojoAttachEvent=\"onmouseover:_onMouse,onmouseout:_onMouse,onclick:_onClick\"\r\n/></div>\r\n",

		baseClass: "dijitCheckBox",

		//	Value of "type" attribute for <input>
		type: "checkbox",

		// value: Value
		//	equivalent to value field on normal checkbox (if checked, the value is passed as
		//	the value when form is submitted)
		value: "on",

		setValue: function(/*String or Boolean*/ newValue){
			// summary:
			//		When passed a boolean, controls whether or not the CheckBox is checked.
			//		If passed a string, changes the value attribute of the CheckBox (the one
			//		specified as "value" when the CheckBox was constructed (ex: <input
			//		dojoType="dijit.CheckBox" value="chicken">)
			if(typeof newValue == "string"){
				this.setAttribute('value', newValue);
				newValue = true;
			}
			this.setAttribute('checked', newValue);
		},

		_getValueDeprecated: false, // remove when _FormWidget:_getValueDeprecated is removed
		getValue: function(){
			// summary:
			//		If the CheckBox is checked, returns the value attribute.
			//		Otherwise returns false.
			return (this.checked ? this.value : false);
		},

		reset: function(){
			this.inherited(arguments);
			this.setAttribute('value', this._resetValueAttr);
		},

		postCreate: function(){
			this.inherited(arguments);
			this._resetValueAttr = this.value;
		}
	}
);

dojo.declare(
	"dijit.form.RadioButton",
	dijit.form.CheckBox,
	{
		// summary:
		// 		Same as an HTML radio, but with fancy styling.
		//
		// description:
		// Implementation details
		//
		// Specialization:
		// We keep track of dijit radio groups so that we can update the state
		// of all the siblings (the "context") in a group based on input
		// events. We don't rely on browser radio grouping.

		type: "radio",
		baseClass: "dijitRadio",

		// This shared object keeps track of all widgets, grouped by name
		_groups: {},

		postCreate: function(){
			// add this widget to _groups
			(this._groups[this.name] = this._groups[this.name] || []).push(this);

			this.inherited(arguments);
		},

		uninitialize: function(){
			// remove this widget from _groups
			dojo.forEach(this._groups[this.name], function(widget, i, arr){
				if(widget === this){
					arr.splice(i, 1);
					return;
				}
			}, this);
		},

		setAttribute: function(/*String*/ attr, /*anything*/ value){
			// If I am being checked then have to deselect currently checked radio button
			this.inherited(arguments);
			switch(attr){
				case "checked":
					if(this.checked){
						dojo.forEach(this._groups[this.name], function(widget){
							if(widget != this && widget.checked){
								widget.setAttribute('checked', false);
							}
						}, this);
					}
			}
		},

		_clicked: function(/*Event*/ e){
			if(!this.checked){
				this.setAttribute('checked', true);
			}
		}
	}
);

}

if(!dojo._hasResource["dijit.form._Spinner"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dijit.form._Spinner"] = true;
dojo.provide("dijit.form._Spinner");



dojo.declare(
	"dijit.form._Spinner",
	dijit.form.RangeBoundTextBox,
	{

		// summary: Mixin for validation widgets with a spinner
		// description: This class basically (conceptually) extends dijit.form.ValidationTextBox.
		//	It modifies the template to have up/down arrows, and provides related handling code.

		// defaultTimeout: Number
		//	  number of milliseconds before a held key or button becomes typematic
		defaultTimeout: 500,

		// timeoutChangeRate: Number
		//	  fraction of time used to change the typematic timer between events
		//	  1.0 means that each typematic event fires at defaultTimeout intervals
		//	  < 1.0 means that each typematic event fires at an increasing faster rate
		timeoutChangeRate: 0.90,

		// smallDelta: Number
		//	  adjust the value by this much when spinning using the arrow keys/buttons
		smallDelta: 1,
		// largeDelta: Number
		//	  adjust the value by this much when spinning using the PgUp/Dn keys
		largeDelta: 10,

		templateString:"<div class=\"dijit dijitReset dijitInlineTable dijitLeft\"\r\n\tid=\"widget_${id}\"\r\n\tdojoAttachEvent=\"onmouseenter:_onMouse,onmouseleave:_onMouse,onmousedown:_onMouse\" waiRole=\"presentation\"\r\n\t><div class=\"dijitInputLayoutContainer\"\r\n\t\t><div class=\"dijitReset dijitSpinnerButtonContainer\"\r\n\t\t\t>&nbsp;<div class=\"dijitReset dijitLeft dijitButtonNode dijitArrowButton dijitUpArrowButton\"\r\n\t\t\t\tdojoAttachPoint=\"upArrowNode\"\r\n\t\t\t\tdojoAttachEvent=\"onmouseenter:_onMouse,onmouseleave:_onMouse\"\r\n\t\t\t\tstateModifier=\"UpArrow\"\r\n\t\t\t\t><div class=\"dijitArrowButtonInner\">&thinsp;</div\r\n\t\t\t\t><div class=\"dijitArrowButtonChar\">&#9650;</div\r\n\t\t\t></div\r\n\t\t\t><div class=\"dijitReset dijitLeft dijitButtonNode dijitArrowButton dijitDownArrowButton\"\r\n\t\t\t\tdojoAttachPoint=\"downArrowNode\"\r\n\t\t\t\tdojoAttachEvent=\"onmouseenter:_onMouse,onmouseleave:_onMouse\"\r\n\t\t\t\tstateModifier=\"DownArrow\"\r\n\t\t\t\t><div class=\"dijitArrowButtonInner\">&thinsp;</div\r\n\t\t\t\t><div class=\"dijitArrowButtonChar\">&#9660;</div\r\n\t\t\t></div\r\n\t\t></div\r\n\t\t><div class=\"dijitReset dijitValidationIcon\"><br></div\r\n\t\t><div class=\"dijitReset dijitValidationIconText\">&Chi;</div\r\n\t\t><div class=\"dijitReset dijitInputField\"\r\n\t\t\t><input class='dijitReset' dojoAttachPoint=\"textbox,focusNode\" type=\"${type}\" dojoAttachEvent=\"onfocus:_update,onkeyup:_onkeyup,onkeypress:_onKeyPress\"\r\n\t\t\t\twaiRole=\"spinbutton\" autocomplete=\"off\" name=\"${name}\"\r\n\t\t/></div\r\n\t></div\r\n></div>\r\n",
		baseClass: "dijitSpinner",

		adjust: function(/* Object */ val, /*Number*/ delta){
			// summary: user replaceable function used to adjust a primitive value(Number/Date/...) by the delta amount specified
			// the val is adjusted in a way that makes sense to the object type
			return val;
		},

		_arrowState: function(/*Node*/ node, /*Boolean*/ pressed){
			this._active = pressed;
			this.stateModifier = node.getAttribute("stateModifier") || "";
			this._setStateClass();
		},

		_arrowPressed: function(/*Node*/ nodePressed, /*Number*/ direction){
			if(this.disabled || this.readOnly){ return; }
			this._arrowState(nodePressed, true);
			this.setValue(this.adjust(this.getValue(), direction*this.smallDelta), false);
			dijit.selectInputText(this.textbox, this.textbox.value.length);
		},

		_arrowReleased: function(/*Node*/ node){
			this._wheelTimer = null;
			if(this.disabled || this.readOnly){ return; }
			this._arrowState(node, false);
		},

		_typematicCallback: function(/*Number*/ count, /*DOMNode*/ node, /*Event*/ evt){
			if(node == this.textbox){ node = (evt.keyCode == dojo.keys.UP_ARROW) ? this.upArrowNode : this.downArrowNode; }
			if(count == -1){ this._arrowReleased(node); }
			else{ this._arrowPressed(node, (node == this.upArrowNode) ? 1 : -1); }
		},

		_wheelTimer: null,
		_mouseWheeled: function(/*Event*/ evt){
			dojo.stopEvent(evt);
			var scrollAmount = 0;
			if(typeof evt.wheelDelta == 'number'){ // IE
				scrollAmount = evt.wheelDelta;
			}else if(typeof evt.detail == 'number'){ // Mozilla+Firefox
				scrollAmount = -evt.detail;
			}
			var node, dir;
			if(scrollAmount > 0){
				node = this.upArrowNode;
				dir = +1;
			}else if(scrollAmount < 0){
				node = this.downArrowNode;
				dir = -1;
			}else{ return; }
			this._arrowPressed(node, dir);
			if(this._wheelTimer != null){
				clearTimeout(this._wheelTimer);
			}
			var _this = this;
			this._wheelTimer = setTimeout(function(){_this._arrowReleased(node);}, 50);
		},

		postCreate: function(){
			this.inherited('postCreate', arguments);

			// extra listeners
			this.connect(this.textbox, dojo.isIE ? "onmousewheel" : 'DOMMouseScroll', "_mouseWheeled");
			this._connects.push(dijit.typematic.addListener(this.upArrowNode, this.textbox, {keyCode:dojo.keys.UP_ARROW,ctrlKey:false,altKey:false,shiftKey:false}, this, "_typematicCallback", this.timeoutChangeRate, this.defaultTimeout));
			this._connects.push(dijit.typematic.addListener(this.downArrowNode, this.textbox, {keyCode:dojo.keys.DOWN_ARROW,ctrlKey:false,altKey:false,shiftKey:false}, this, "_typematicCallback", this.timeoutChangeRate, this.defaultTimeout));
			if(dojo.isIE){
				// When spinner is moved from hidden to visible, call _setStateClass to remind IE to render it. (#6123)
				var _this = this;
				this.connect(this.domNode, "onresize", 
					function(){ setTimeout(dojo.hitch(_this,
						function(){
							// cause the IE expressions to rerun
							this.upArrowNode.style.behavior = '';
							this.downArrowNode.style.behavior = '';
							// cause IE to rerender
							this._setStateClass();
						}), 0);
					}
				);
			}
		}
});

}

if(!dojo._hasResource["dijit.form.NumberTextBox"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dijit.form.NumberTextBox"] = true;
dojo.provide("dijit.form.NumberTextBox");




/*=====
dojo.declare(
	"dijit.form.NumberTextBox.__Constraints",
	[dijit.form.RangeBoundTextBox.__Constraints, dojo.number.__FormatOptions, dojo.number.__ParseOptions]
);
=====*/

dojo.declare(
	"dijit.form.NumberTextBoxMixin",
	null,
	{
		// summary:
		//		A mixin for all number textboxes

		regExpGen: dojo.number.regexp,

		/*=====
		// constraints: dijit.form.NumberTextBox.__Constraints 
		constraints: {},
		======*/

		// editOptions: Object
		//		properties to mix into constraints when the value is being edited
		editOptions: { pattern: '#.######' },

		_onFocus: function(){
			this.setValue(this.getValue(), false);	
			this.inherited(arguments);
		},

		_formatter: dojo.number.format,

		format: function(/*Number*/ value, /*dojo.number.__FormatOptions*/ constraints){
			//	summary: formats the value as a Number, according to constraints

			if(typeof value == "string") { return value; }
			if(isNaN(value)){ return ""; }
			if(this.editOptions && this._focused){
				constraints = dojo.mixin(dojo.mixin({}, this.editOptions), this.constraints);
			}
			return this._formatter(value, constraints);
		},

		parse: dojo.number.parse,
		/*=====
		parse: function(value, constraints){
			//	summary: parses the value as a Number, according to constraints
			//	value: String
			//
			//	constraints: dojo.number.__ParseOptions
		},
		=====*/

		filter: function(/*Number*/ value){
			if(typeof value == "string"){ return this.inherited('filter', arguments); }
			return isNaN(value) ? '' : value;
		},

		value: NaN
	}
);

dojo.declare(
	"dijit.form.NumberTextBox",
	[dijit.form.RangeBoundTextBox,dijit.form.NumberTextBoxMixin],
	{
		// summary:
		//		A validating, serializable, range-bound text box.
	}
);

}

if(!dojo._hasResource["dijit.form.NumberSpinner"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dijit.form.NumberSpinner"] = true;
dojo.provide("dijit.form.NumberSpinner");




dojo.declare(
"dijit.form.NumberSpinner",
[dijit.form._Spinner, dijit.form.NumberTextBoxMixin],
{
	// summary:
	// extends NumberTextBox to add up/down arrows for incremental change to the value

	required: true,

	adjust: function(/* Object */ val, /*Number*/ delta){
		// summary: change Number val by the given amount
		var newval = val+delta;
		if(isNaN(val) || isNaN(newval)){ return val; }
		if((typeof this.constraints.max == "number") && (newval > this.constraints.max)){
			newval = this.constraints.max;
		}
		if((typeof this.constraints.min == "number") && (newval < this.constraints.min)){
			newval = this.constraints.min;
		}
		return newval;
	}
});

}

if(!dojo._hasResource["dijit.form.CurrencyTextBox"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dijit.form.CurrencyTextBox"] = true;
dojo.provide("dijit.form.CurrencyTextBox");

//FIXME: dojo.experimental throws an unreadable exception?
//dojo.experimental("dijit.form.CurrencyTextBox");




dojo.declare(
	"dijit.form.CurrencyTextBox",
	dijit.form.NumberTextBox,
	{
		// summary:
		//		A validating currency textbox
		//
		// constraints: dijit.form._DateTimeTextBox.__Constraints 
		//
		// currency: String
		//		the [ISO4217](http://en.wikipedia.org/wiki/ISO_4217) currency code, a three letter sequence like "USD"
		currency: "",

		/*=====
		constraints: {},
		======*/

		regExpGen: dojo.currency.regexp,
		_formatter: dojo.currency.format,
/*=====
		parse: function(value, constraints){
			//	summary: parses the value as a Currency, according to constraints
			//	value: String
			//
			//	constraints: dojo.currency.__ParseOptions
		},
=====*/
		parse: dojo.currency.parse,

		postMixInProperties: function(){
			if(this.constraints === dijit.form.ValidationTextBox.prototype.constraints){
				// declare a constraints property on 'this' so we don't overwrite the shared default object in 'prototype'
				this.constraints = {};
			}
			this.constraints.currency = this.currency;
			dijit.form.CurrencyTextBox.superclass.postMixInProperties.apply(this, arguments);
		}
	}
);

}

if(!dojo._hasResource["dojo.dnd.common"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojo.dnd.common"] = true;
dojo.provide("dojo.dnd.common");

dojo.dnd._copyKey = navigator.appVersion.indexOf("Macintosh") < 0 ? "ctrlKey" : "metaKey";

dojo.dnd.getCopyKeyState = function(e) {
	// summary: abstracts away the difference between selection on Mac and PC,
	//	and returns the state of the "copy" key to be pressed.
	// e: Event: mouse event
	return e[dojo.dnd._copyKey];	// Boolean
};

dojo.dnd._uniqueId = 0;
dojo.dnd.getUniqueId = function(){
	// summary: returns a unique string for use with any DOM element
	var id;
	do{
		id = dojo._scopeName + "Unique" + (++dojo.dnd._uniqueId);
	}while(dojo.byId(id));
	return id;
};

dojo.dnd._empty = {};

dojo.dnd.isFormElement = function(/*Event*/ e){
	// summary: returns true, if user clicked on a form element
	var t = e.target;
	if(t.nodeType == 3 /*TEXT_NODE*/){
		t = t.parentNode;
	}
	return " button textarea input select option ".indexOf(" " + t.tagName.toLowerCase() + " ") >= 0;	// Boolean
};

}

if(!dojo._hasResource["dojo.dnd.autoscroll"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojo.dnd.autoscroll"] = true;
dojo.provide("dojo.dnd.autoscroll");

dojo.dnd.getViewport = function(){
	// summary: returns a viewport size (visible part of the window)

	// FIXME: need more docs!!
	var d = dojo.doc, dd = d.documentElement, w = window, b = dojo.body();
	if(dojo.isMozilla){
		return {w: dd.clientWidth, h: w.innerHeight};	// Object
	}else if(!dojo.isOpera && w.innerWidth){
		return {w: w.innerWidth, h: w.innerHeight};		// Object
	}else if (!dojo.isOpera && dd && dd.clientWidth){
		return {w: dd.clientWidth, h: dd.clientHeight};	// Object
	}else if (b.clientWidth){
		return {w: b.clientWidth, h: b.clientHeight};	// Object
	}
	return null;	// Object
};

dojo.dnd.V_TRIGGER_AUTOSCROLL = 32;
dojo.dnd.H_TRIGGER_AUTOSCROLL = 32;

dojo.dnd.V_AUTOSCROLL_VALUE = 16;
dojo.dnd.H_AUTOSCROLL_VALUE = 16;

dojo.dnd.autoScroll = function(e){
	// summary:
	//		a handler for onmousemove event, which scrolls the window, if
	//		necesary
	// e: Event:
	//		onmousemove event

	// FIXME: needs more docs!
	var v = dojo.dnd.getViewport(), dx = 0, dy = 0;
	if(e.clientX < dojo.dnd.H_TRIGGER_AUTOSCROLL){
		dx = -dojo.dnd.H_AUTOSCROLL_VALUE;
	}else if(e.clientX > v.w - dojo.dnd.H_TRIGGER_AUTOSCROLL){
		dx = dojo.dnd.H_AUTOSCROLL_VALUE;
	}
	if(e.clientY < dojo.dnd.V_TRIGGER_AUTOSCROLL){
		dy = -dojo.dnd.V_AUTOSCROLL_VALUE;
	}else if(e.clientY > v.h - dojo.dnd.V_TRIGGER_AUTOSCROLL){
		dy = dojo.dnd.V_AUTOSCROLL_VALUE;
	}
	window.scrollBy(dx, dy);
};

dojo.dnd._validNodes = {"div": 1, "p": 1, "td": 1};
dojo.dnd._validOverflow = {"auto": 1, "scroll": 1};

dojo.dnd.autoScrollNodes = function(e){
	// summary:
	//		a handler for onmousemove event, which scrolls the first avaialble
	//		Dom element, it falls back to dojo.dnd.autoScroll()
	// e: Event:
	//		onmousemove event

	// FIXME: needs more docs!
	for(var n = e.target; n;){
		if(n.nodeType == 1 && (n.tagName.toLowerCase() in dojo.dnd._validNodes)){
			var s = dojo.getComputedStyle(n);
			if(s.overflow.toLowerCase() in dojo.dnd._validOverflow){
				var b = dojo._getContentBox(n, s), t = dojo._abs(n, true);
				// console.debug(b.l, b.t, t.x, t.y, n.scrollLeft, n.scrollTop);
				b.l += t.x + n.scrollLeft;
				b.t += t.y + n.scrollTop;
				var w = Math.min(dojo.dnd.H_TRIGGER_AUTOSCROLL, b.w / 2), 
					h = Math.min(dojo.dnd.V_TRIGGER_AUTOSCROLL, b.h / 2),
					rx = e.pageX - b.l, ry = e.pageY - b.t, dx = 0, dy = 0;
				if(rx > 0 && rx < b.w){
					if(rx < w){
						dx = -dojo.dnd.H_AUTOSCROLL_VALUE;
					}else if(rx > b.w - w){
						dx = dojo.dnd.H_AUTOSCROLL_VALUE;
					}
				}
				//console.debug("ry =", ry, "b.h =", b.h, "h =", h);
				if(ry > 0 && ry < b.h){
					if(ry < h){
						dy = -dojo.dnd.V_AUTOSCROLL_VALUE;
					}else if(ry > b.h - h){
						dy = dojo.dnd.V_AUTOSCROLL_VALUE;
					}
				}
				var oldLeft = n.scrollLeft, oldTop = n.scrollTop;
				n.scrollLeft = n.scrollLeft + dx;
				n.scrollTop  = n.scrollTop  + dy;
				// if(dx || dy){ console.debug(oldLeft + ", " + oldTop + "\n" + dx + ", " + dy + "\n" + n.scrollLeft + ", " + n.scrollTop); }
				if(oldLeft != n.scrollLeft || oldTop != n.scrollTop){ return; }
			}
		}
		try{
			n = n.parentNode;
		}catch(x){
			n = null;
		}
	}
	dojo.dnd.autoScroll(e);
};

}

if(!dojo._hasResource["dojo.dnd.Mover"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojo.dnd.Mover"] = true;
dojo.provide("dojo.dnd.Mover");




dojo.declare("dojo.dnd.Mover", null, {
	constructor: function(node, e, host){
		// summary: an object, which makes a node follow the mouse, 
		//	used as a default mover, and as a base class for custom movers
		// node: Node: a node (or node's id) to be moved
		// e: Event: a mouse event, which started the move;
		//	only pageX and pageY properties are used
		// host: Object?: object which implements the functionality of the move,
		//	 and defines proper events (onMoveStart and onMoveStop)
		this.node = dojo.byId(node);
		this.marginBox = {l: e.pageX, t: e.pageY};
		this.mouseButton = e.button;
		var h = this.host = host, d = node.ownerDocument, 
			firstEvent = dojo.connect(d, "onmousemove", this, "onFirstMove");
		this.events = [
			dojo.connect(d, "onmousemove", this, "onMouseMove"),
			dojo.connect(d, "onmouseup",   this, "onMouseUp"),
			// cancel text selection and text dragging
			dojo.connect(d, "ondragstart",   dojo, "stopEvent"),
			dojo.connect(d, "onselectstart", dojo, "stopEvent"),
			firstEvent
		];
		// notify that the move has started
		if(h && h.onMoveStart){
			h.onMoveStart(this);
		}
	},
	// mouse event processors
	onMouseMove: function(e){
		// summary: event processor for onmousemove
		// e: Event: mouse event
		dojo.dnd.autoScroll(e);
		var m = this.marginBox;
		this.host.onMove(this, {l: m.l + e.pageX, t: m.t + e.pageY});
	},
	onMouseUp: function(e){
		if(this.mouseButton == e.button){
			this.destroy();
		}
	},
	// utilities
	onFirstMove: function(){
		// summary: makes the node absolute; it is meant to be called only once
		var s = this.node.style, l, t;
		switch(s.position){
			case "relative":
			case "absolute":
				// assume that left and top values are in pixels already
				l = Math.round(parseFloat(s.left));
				t = Math.round(parseFloat(s.top));
				break;
			default:
				s.position = "absolute";	// enforcing the absolute mode
				var m = dojo.marginBox(this.node);
				l = m.l;
				t = m.t;
				break;
		}
		this.marginBox.l = l - this.marginBox.l;
		this.marginBox.t = t - this.marginBox.t;
		this.host.onFirstMove(this);
		dojo.disconnect(this.events.pop());
	},
	destroy: function(){
		// summary: stops the move, deletes all references, so the object can be garbage-collected
		dojo.forEach(this.events, dojo.disconnect);
		// undo global settings
		var h = this.host;
		if(h && h.onMoveStop){
			h.onMoveStop(this);
		}
		// destroy objects
		this.events = this.node = null;
	}
});

}

if(!dojo._hasResource["dojo.dnd.Moveable"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojo.dnd.Moveable"] = true;
dojo.provide("dojo.dnd.Moveable");



dojo.declare("dojo.dnd.Moveable", null, {
	// object attributes (for markup)
	handle: "",
	delay: 0,
	skip: false,
	
	constructor: function(node, params){
		// summary: an object, which makes a node moveable
		// node: Node: a node (or node's id) to be moved
		// params: Object: an optional object with additional parameters;
		//	following parameters are recognized:
		//		handle: Node: a node (or node's id), which is used as a mouse handle
		//			if omitted, the node itself is used as a handle
		//		delay: Number: delay move by this number of pixels
		//		skip: Boolean: skip move of form elements
		//		mover: Object: a constructor of custom Mover
		this.node = dojo.byId(node);
		if(!params){ params = {}; }
		this.handle = params.handle ? dojo.byId(params.handle) : null;
		if(!this.handle){ this.handle = this.node; }
		this.delay = params.delay > 0 ? params.delay : 0;
		this.skip  = params.skip;
		this.mover = params.mover ? params.mover : dojo.dnd.Mover;
		this.events = [
			dojo.connect(this.handle, "onmousedown", this, "onMouseDown"),
			// cancel text selection and text dragging
			dojo.connect(this.handle, "ondragstart",   this, "onSelectStart"),
			dojo.connect(this.handle, "onselectstart", this, "onSelectStart")
		];
	},

	// markup methods
	markupFactory: function(params, node){
		return new dojo.dnd.Moveable(node, params);
	},

	// methods
	destroy: function(){
		// summary: stops watching for possible move, deletes all references, so the object can be garbage-collected
		dojo.forEach(this.events, dojo.disconnect);
		this.events = this.node = this.handle = null;
	},
	
	// mouse event processors
	onMouseDown: function(e){
		// summary: event processor for onmousedown, creates a Mover for the node
		// e: Event: mouse event
		if(this.skip && dojo.dnd.isFormElement(e)){ return; }
		if(this.delay){
			this.events.push(dojo.connect(this.handle, "onmousemove", this, "onMouseMove"));
			this.events.push(dojo.connect(this.handle, "onmouseup", this, "onMouseUp"));
			this._lastX = e.pageX;
			this._lastY = e.pageY;
		}else{
			new this.mover(this.node, e, this);
		}
		dojo.stopEvent(e);
	},
	onMouseMove: function(e){
		// summary: event processor for onmousemove, used only for delayed drags
		// e: Event: mouse event
		if(Math.abs(e.pageX - this._lastX) > this.delay || Math.abs(e.pageY - this._lastY) > this.delay){
			this.onMouseUp(e);
			new this.mover(this.node, e, this);
		}
		dojo.stopEvent(e);
	},
	onMouseUp: function(e){
		// summary: event processor for onmouseup, used only for delayed delayed drags
		// e: Event: mouse event
		dojo.disconnect(this.events.pop());
		dojo.disconnect(this.events.pop());
	},
	onSelectStart: function(e){
		// summary: event processor for onselectevent and ondragevent
		// e: Event: mouse event
		if(!this.skip || !dojo.dnd.isFormElement(e)){
			dojo.stopEvent(e);
		}
	},
	
	// local events
	onMoveStart: function(/* dojo.dnd.Mover */ mover){
		// summary: called before every move operation
		dojo.publish("/dnd/move/start", [mover]);
		dojo.addClass(dojo.body(), "dojoMove"); 
		dojo.addClass(this.node, "dojoMoveItem"); 
	},
	onMoveStop: function(/* dojo.dnd.Mover */ mover){
		// summary: called after every move operation
		dojo.publish("/dnd/move/stop", [mover]);
		dojo.removeClass(dojo.body(), "dojoMove");
		dojo.removeClass(this.node, "dojoMoveItem");
	},
	onFirstMove: function(/* dojo.dnd.Mover */ mover){
		// summary: called during the very first move notification,
		//	can be used to initialize coordinates, can be overwritten.
		
		// default implementation does nothing
	},
	onMove: function(/* dojo.dnd.Mover */ mover, /* Object */ leftTop){
		// summary: called during every move notification,
		//	should actually move the node, can be overwritten.
		this.onMoving(mover, leftTop);
		var s = mover.node.style;
		s.left = leftTop.l + "px";
		s.top  = leftTop.t + "px";
		this.onMoved(mover, leftTop);
	},
	onMoving: function(/* dojo.dnd.Mover */ mover, /* Object */ leftTop){
		// summary: called before every incremental move,
		//	can be overwritten.
		
		// default implementation does nothing
	},
	onMoved: function(/* dojo.dnd.Mover */ mover, /* Object */ leftTop){
		// summary: called after every incremental move,
		//	can be overwritten.
		
		// default implementation does nothing
	}
});

}

if(!dojo._hasResource["dojo.dnd.move"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojo.dnd.move"] = true;
dojo.provide("dojo.dnd.move");




dojo.declare("dojo.dnd.move.constrainedMoveable", dojo.dnd.Moveable, {
	// object attributes (for markup)
	constraints: function(){},
	within: false,
	
	// markup methods
	markupFactory: function(params, node){
		return new dojo.dnd.move.constrainedMoveable(node, params);
	},

	constructor: function(node, params){
		// summary: an object, which makes a node moveable
		// node: Node: a node (or node's id) to be moved
		// params: Object: an optional object with additional parameters;
		//	following parameters are recognized:
		//		constraints: Function: a function, which calculates a constraint box,
		//			it is called in a context of the moveable object.
		//		within: Boolean: restrict move within boundaries.
		//	the rest is passed to the base class
		if(!params){ params = {}; }
		this.constraints = params.constraints;
		this.within = params.within;
	},
	onFirstMove: function(/* dojo.dnd.Mover */ mover){
		// summary: called during the very first move notification,
		//	can be used to initialize coordinates, can be overwritten.
		var c = this.constraintBox = this.constraints.call(this, mover);
		c.r = c.l + c.w;
		c.b = c.t + c.h;
		if(this.within){
			var mb = dojo.marginBox(mover.node);
			c.r -= mb.w;
			c.b -= mb.h;
		}
	},
	onMove: function(/* dojo.dnd.Mover */ mover, /* Object */ leftTop){
		// summary: called during every move notification,
		//	should actually move the node, can be overwritten.
		var c = this.constraintBox, s = mover.node.style;
		s.left = (leftTop.l < c.l ? c.l : c.r < leftTop.l ? c.r : leftTop.l) + "px";
		s.top  = (leftTop.t < c.t ? c.t : c.b < leftTop.t ? c.b : leftTop.t) + "px";
	}
});

dojo.declare("dojo.dnd.move.boxConstrainedMoveable", dojo.dnd.move.constrainedMoveable, {
	// object attributes (for markup)
	box: {},
	
	// markup methods
	markupFactory: function(params, node){
		return new dojo.dnd.move.boxConstrainedMoveable(node, params);
	},

	constructor: function(node, params){
		// summary: an object, which makes a node moveable
		// node: Node: a node (or node's id) to be moved
		// params: Object: an optional object with additional parameters;
		//	following parameters are recognized:
		//		box: Object: a constraint box
		//	the rest is passed to the base class
		var box = params && params.box;
		this.constraints = function(){ return box; };
	}
});

dojo.declare("dojo.dnd.move.parentConstrainedMoveable", dojo.dnd.move.constrainedMoveable, {
	// object attributes (for markup)
	area: "content",

	// markup methods
	markupFactory: function(params, node){
		return new dojo.dnd.move.parentConstrainedMoveable(node, params);
	},

	constructor: function(node, params){
		// summary: an object, which makes a node moveable
		// node: Node: a node (or node's id) to be moved
		// params: Object: an optional object with additional parameters;
		//	following parameters are recognized:
		//		area: String: a parent's area to restrict the move,
		//			can be "margin", "border", "padding", or "content".
		//	the rest is passed to the base class
		var area = params && params.area;
		this.constraints = function(){
			var n = this.node.parentNode, 
				s = dojo.getComputedStyle(n), 
				mb = dojo._getMarginBox(n, s);
			if(area == "margin"){
				return mb;	// Object
			}
			var t = dojo._getMarginExtents(n, s);
			mb.l += t.l, mb.t += t.t, mb.w -= t.w, mb.h -= t.h;
			if(area == "border"){
				return mb;	// Object
			}
			t = dojo._getBorderExtents(n, s);
			mb.l += t.l, mb.t += t.t, mb.w -= t.w, mb.h -= t.h;
			if(area == "padding"){
				return mb;	// Object
			}
			t = dojo._getPadExtents(n, s);
			mb.l += t.l, mb.t += t.t, mb.w -= t.w, mb.h -= t.h;
			return mb;	// Object
		};
	}
});

// WARNING: below are obsolete objects, instead of custom movers use custom moveables (above)

dojo.dnd.move.constrainedMover = function(fun, within){
	// summary: returns a constrained version of dojo.dnd.Mover
	// description: this function produces n object, which will put a constraint on 
	//	the margin box of dragged object in absolute coordinates
	// fun: Function: called on drag, and returns a constraint box
	// within: Boolean: if true, constraints the whole dragged object withtin the rectangle, 
	//	otherwise the constraint is applied to the left-top corner
	dojo.deprecated("dojo.dnd.move.constrainedMover, use dojo.dnd.move.constrainedMoveable instead");
	var mover = function(node, e, notifier){
		dojo.dnd.Mover.call(this, node, e, notifier);
	};
	dojo.extend(mover, dojo.dnd.Mover.prototype);
	dojo.extend(mover, {
		onMouseMove: function(e){
			// summary: event processor for onmousemove
			// e: Event: mouse event
			dojo.dnd.autoScroll(e);
			var m = this.marginBox, c = this.constraintBox,
				l = m.l + e.pageX, t = m.t + e.pageY;
			l = l < c.l ? c.l : c.r < l ? c.r : l;
			t = t < c.t ? c.t : c.b < t ? c.b : t;
			this.host.onMove(this, {l: l, t: t});
		},
		onFirstMove: function(){
			// summary: called once to initialize things; it is meant to be called only once
			dojo.dnd.Mover.prototype.onFirstMove.call(this);
			var c = this.constraintBox = fun.call(this);
			c.r = c.l + c.w;
			c.b = c.t + c.h;
			if(within){
				var mb = dojo.marginBox(this.node);
				c.r -= mb.w;
				c.b -= mb.h;
			}
		}
	});
	return mover;	// Object
};

dojo.dnd.move.boxConstrainedMover = function(box, within){
	// summary: a specialization of dojo.dnd.constrainedMover, which constrains to the specified box
	// box: Object: a constraint box (l, t, w, h)
	// within: Boolean: if true, constraints the whole dragged object withtin the rectangle, 
	//	otherwise the constraint is applied to the left-top corner
	dojo.deprecated("dojo.dnd.move.boxConstrainedMover, use dojo.dnd.move.boxConstrainedMoveable instead");
	return dojo.dnd.move.constrainedMover(function(){ return box; }, within);	// Object
};

dojo.dnd.move.parentConstrainedMover = function(area, within){
	// summary: a specialization of dojo.dnd.constrainedMover, which constrains to the parent node
	// area: String: "margin" to constrain within the parent's margin box, "border" for the border box,
	//	"padding" for the padding box, and "content" for the content box; "content" is the default value.
	// within: Boolean: if true, constraints the whole dragged object withtin the rectangle, 
	//	otherwise the constraint is applied to the left-top corner
	dojo.deprecated("dojo.dnd.move.parentConstrainedMover, use dojo.dnd.move.parentConstrainedMoveable instead");
	var fun = function(){
		var n = this.node.parentNode, 
			s = dojo.getComputedStyle(n), 
			mb = dojo._getMarginBox(n, s);
		if(area == "margin"){
			return mb;	// Object
		}
		var t = dojo._getMarginExtents(n, s);
		mb.l += t.l, mb.t += t.t, mb.w -= t.w, mb.h -= t.h;
		if(area == "border"){
			return mb;	// Object
		}
		t = dojo._getBorderExtents(n, s);
		mb.l += t.l, mb.t += t.t, mb.w -= t.w, mb.h -= t.h;
		if(area == "padding"){
			return mb;	// Object
		}
		t = dojo._getPadExtents(n, s);
		mb.l += t.l, mb.t += t.t, mb.w -= t.w, mb.h -= t.h;
		return mb;	// Object
	};
	return dojo.dnd.move.constrainedMover(fun, within);	// Object
};

// patching functions one level up for compatibility

dojo.dnd.constrainedMover = dojo.dnd.move.constrainedMover;
dojo.dnd.boxConstrainedMover = dojo.dnd.move.boxConstrainedMover;
dojo.dnd.parentConstrainedMover = dojo.dnd.move.parentConstrainedMover;

}

if(!dojo._hasResource["dijit.form.Slider"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dijit.form.Slider"] = true;
dojo.provide("dijit.form.Slider");








dojo.declare(
	"dijit.form.HorizontalSlider",
	[dijit.form._FormValueWidget, dijit._Container],
{
	// summary
	//	A form widget that allows one to select a value with a horizontally draggable image

	templateString:"<table class=\"dijit dijitReset dijitSlider\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" rules=\"none\"\r\n\t><tr class=\"dijitReset\"\r\n\t\t><td class=\"dijitReset\" colspan=\"2\"></td\r\n\t\t><td dojoAttachPoint=\"containerNode,topDecoration\" class=\"dijitReset\" style=\"text-align:center;width:100%;\"></td\r\n\t\t><td class=\"dijitReset\" colspan=\"2\"></td\r\n\t></tr\r\n\t><tr class=\"dijitReset\"\r\n\t\t><td class=\"dijitReset dijitSliderButtonContainer dijitSliderButtonContainerH\"\r\n\t\t\t><div class=\"dijitSliderDecrementIconH\" tabIndex=\"-1\" style=\"display:none\" dojoAttachPoint=\"decrementButton\" dojoAttachEvent=\"onclick: decrement\"><span class=\"dijitSliderButtonInner\">-</span></div\r\n\t\t></td\r\n\t\t><td class=\"dijitReset\"\r\n\t\t\t><div class=\"dijitSliderBar dijitSliderBumper dijitSliderBumperH dijitSliderLeftBumper dijitSliderLeftBumper\" dojoAttachEvent=\"onclick:_onClkDecBumper\"></div\r\n\t\t></td\r\n\t\t><td class=\"dijitReset\"\r\n\t\t\t><input dojoAttachPoint=\"valueNode\" type=\"hidden\" name=\"${name}\"\r\n\t\t\t/><div waiRole=\"presentation\" style=\"position:relative;\" dojoAttachPoint=\"sliderBarContainer\"\r\n\t\t\t\t><div waiRole=\"presentation\" dojoAttachPoint=\"progressBar\" class=\"dijitSliderBar dijitSliderBarH dijitSliderProgressBar dijitSliderProgressBarH\" dojoAttachEvent=\"onclick:_onBarClick\"\r\n\t\t\t\t\t><div dojoAttachPoint=\"sliderHandle,focusNode\" class=\"dijitSliderMoveable dijitSliderMoveableH\" dojoAttachEvent=\"onkeypress:_onKeyPress,onmousedown:_onHandleClick\" waiRole=\"slider\" valuemin=\"${minimum}\" valuemax=\"${maximum}\"\r\n\t\t\t\t\t\t><div class=\"dijitSliderImageHandle dijitSliderImageHandleH\"></div\r\n\t\t\t\t\t></div\r\n\t\t\t\t></div\r\n\t\t\t\t><div waiRole=\"presentation\" dojoAttachPoint=\"remainingBar\" class=\"dijitSliderBar dijitSliderBarH dijitSliderRemainingBar dijitSliderRemainingBarH\" dojoAttachEvent=\"onclick:_onBarClick\"></div\r\n\t\t\t></div\r\n\t\t></td\r\n\t\t><td class=\"dijitReset\"\r\n\t\t\t><div class=\"dijitSliderBar dijitSliderBumper dijitSliderBumperH dijitSliderRightBumper dijitSliderRightBumper\" dojoAttachEvent=\"onclick:_onClkIncBumper\"></div\r\n\t\t></td\r\n\t\t><td class=\"dijitReset dijitSliderButtonContainer dijitSliderButtonContainerH\" style=\"right:0px;\"\r\n\t\t\t><div class=\"dijitSliderIncrementIconH\" tabIndex=\"-1\" style=\"display:none\" dojoAttachPoint=\"incrementButton\" dojoAttachEvent=\"onclick: increment\"><span class=\"dijitSliderButtonInner\">+</span></div\r\n\t\t></td\r\n\t></tr\r\n\t><tr class=\"dijitReset\"\r\n\t\t><td class=\"dijitReset\" colspan=\"2\"></td\r\n\t\t><td dojoAttachPoint=\"containerNode,bottomDecoration\" class=\"dijitReset\" style=\"text-align:center;\"></td\r\n\t\t><td class=\"dijitReset\" colspan=\"2\"></td\r\n\t></tr\r\n></table>\r\n",
	value: 0,

	// showButtons: boolean
	//	Show increment/decrement buttons at the ends of the slider?
	showButtons: true,

	// minimum:: integer
	//	The minimum value allowed.
	minimum: 0,

	// maximum: integer
	//	The maximum allowed value.
	maximum: 100,

	// discreteValues: integer
	//	The maximum allowed values dispersed evenly between minimum and maximum (inclusive).
	discreteValues: Infinity,

	// pageIncrement: integer
	//	The amount of change with shift+arrow
	pageIncrement: 2,

	// clickSelect: boolean
	//	If clicking the progress bar changes the value or not
	clickSelect: true,

	// slideDuration: Number
	//	The time in ms to take to animate the slider handle from 0% to 100%
	slideDuration: 1000,

	widgetsInTemplate: true,

	attributeMap: dojo.mixin(dojo.clone(dijit.form._FormWidget.prototype.attributeMap),
		{id:"", name:"valueNode"}),

	baseClass: "dijitSlider",

	_mousePixelCoord: "pageX",
	_pixelCount: "w",
	_startingPixelCoord: "x",
	_startingPixelCount: "l",
	_handleOffsetCoord: "left",
	_progressPixelSize: "width",

	_onKeyPress: function(/*Event*/ e){
		if(this.disabled || this.readOnly || e.altKey || e.ctrlKey){ return; }
		switch(e.keyCode){
			case dojo.keys.HOME:
				this.setValue(this.minimum, true);
				break;
			case dojo.keys.END:
				this.setValue(this.maximum, true);
				break;
			// this._descending === false: if ascending vertical (min on top)
			// (this._descending || this.isLeftToRight()): if left-to-right horizontal or descending vertical
			case ((this._descending || this.isLeftToRight()) ? dojo.keys.RIGHT_ARROW : dojo.keys.LEFT_ARROW):
			case (this._descending === false ? dojo.keys.DOWN_ARROW : dojo.keys.UP_ARROW):
			case (this._descending === false ? dojo.keys.PAGE_DOWN : dojo.keys.PAGE_UP):
				this.increment(e);
				break;
			case ((this._descending || this.isLeftToRight()) ? dojo.keys.LEFT_ARROW : dojo.keys.RIGHT_ARROW):
			case (this._descending === false ? dojo.keys.UP_ARROW : dojo.keys.DOWN_ARROW):
			case (this._descending === false ? dojo.keys.PAGE_UP : dojo.keys.PAGE_DOWN):
				this.decrement(e);
				break;
			default:
				this.inherited(arguments);
				return;
		}
		dojo.stopEvent(e);
	},

	_onHandleClick: function(e){
		if(this.disabled || this.readOnly){ return; }
		if(!dojo.isIE){
			// make sure you get focus when dragging the handle
			// (but don't do on IE because it causes a flicker on mouse up (due to blur then focus)
			dijit.focus(this.sliderHandle);
		}
		dojo.stopEvent(e);
	},
	
	_isReversed: function(){
		return !this.isLeftToRight();
	},

	_onBarClick: function(e){
		if(this.disabled || this.readOnly || !this.clickSelect){ return; }
		dijit.focus(this.sliderHandle);
		dojo.stopEvent(e);
		var abspos = dojo.coords(this.sliderBarContainer, true);
		var pixelValue = e[this._mousePixelCoord] - abspos[this._startingPixelCoord];
		this._setPixelValue(this._isReversed() ? (abspos[this._pixelCount] - pixelValue) : pixelValue, abspos[this._pixelCount], true);
	},

	_setPixelValue: function(/*Number*/ pixelValue, /*Number*/ maxPixels, /*Boolean, optional*/ priorityChange){
		if(this.disabled || this.readOnly){ return; }
		pixelValue = pixelValue < 0 ? 0 : maxPixels < pixelValue ? maxPixels : pixelValue;
		var count = this.discreteValues;
		if(count <= 1 || count == Infinity){ count = maxPixels; }
		count--;
		var pixelsPerValue = maxPixels / count;
		var wholeIncrements = Math.round(pixelValue / pixelsPerValue);
		this.setValue((this.maximum-this.minimum)*wholeIncrements/count + this.minimum, priorityChange);
	},

	setValue: function(/*Number*/ value, /*Boolean, optional*/ priorityChange){
		this.valueNode.value = this.value = value;
		dijit.setWaiState(this.focusNode, "valuenow", value);
		this.inherited(arguments);
		var percent = (value - this.minimum) / (this.maximum - this.minimum);
		var progressBar = (this._descending === false) ? this.remainingBar : this.progressBar;
		var remainingBar = (this._descending === false) ? this.progressBar : this.remainingBar;
		if(priorityChange && this.slideDuration > 0 && progressBar.style[this._progressPixelSize]){
			// animate the slider
			var _this = this;
			var props = {};
			var start = parseFloat(progressBar.style[this._progressPixelSize]);
			var duration = this.slideDuration * (percent-start/100);
			if(duration == 0){ return; }
			if(duration < 0){ duration = 0 - duration; }
			props[this._progressPixelSize] = { start: start, end: percent*100, units:"%" };
			dojo.animateProperty({ node: progressBar, duration: duration, 
				onAnimate: function(v){ remainingBar.style[_this._progressPixelSize] = (100-parseFloat(v[_this._progressPixelSize])) + "%"; },
			        properties: props
			}).play();
		}
		else{
			progressBar.style[this._progressPixelSize] = (percent*100) + "%";
			remainingBar.style[this._progressPixelSize] = ((1-percent)*100) + "%";
		}
	},

	_bumpValue: function(signedChange){
		if(this.disabled || this.readOnly){ return; }
		var s = dojo.getComputedStyle(this.sliderBarContainer);
		var c = dojo._getContentBox(this.sliderBarContainer, s);
		var count = this.discreteValues;
		if(count <= 1 || count == Infinity){ count = c[this._pixelCount]; }
		count--;
		var value = (this.value - this.minimum) * count / (this.maximum - this.minimum) + signedChange;
		if(value < 0){ value = 0; }
		if(value > count){ value = count; }
		value = value * (this.maximum - this.minimum) / count + this.minimum;
		this.setValue(value, true);
	},

	_onClkIncBumper: function(){
		this.setValue(this._descending === false ? this.minimum : this.maximum, true);
	},

	_onClkDecBumper: function(){
		this.setValue(this._descending === false ? this.maximum : this.minimum, true);
	},

	decrement: function(e){
		// summary
		//	decrement slider by 1 unit
		this._bumpValue(e.keyCode == dojo.keys.PAGE_DOWN?-this.pageIncrement:-1);
	},

	increment: function(e){
		// summary
		//	increment slider by 1 unit
		this._bumpValue(e.keyCode == dojo.keys.PAGE_UP?this.pageIncrement:1);
	},

	_mouseWheeled: function(/*Event*/ evt){
		dojo.stopEvent(evt);
		var scrollAmount = 0;
		if(typeof evt.wheelDelta == 'number'){ // IE
			scrollAmount = evt.wheelDelta;
		}else if(typeof evt.detail == 'number'){ // Mozilla+Firefox
			scrollAmount = -evt.detail;
		}
		if(scrollAmount > 0){
			this.increment(evt);
		}else if(scrollAmount < 0){
			this.decrement(evt);
		}
	},

	startup: function(){
		dojo.forEach(this.getChildren(), function(child){
			if(this[child.container] != this.containerNode){
				this[child.container].appendChild(child.domNode);
			}
		}, this);
	},

	postCreate: function(){
		if(this.showButtons){
			this.incrementButton.style.display="";
			this.decrementButton.style.display="";
		}
		this.connect(this.domNode, dojo.isIE ? "onmousewheel" : 'DOMMouseScroll', "_mouseWheeled");

		// define a custom constructor for a SliderMover that points back to me
		var _self = this;
		var mover = function(){
			dijit.form._SliderMover.apply(this, arguments);
			this.widget = _self;
		};
		dojo.extend(mover, dijit.form._SliderMover.prototype);

		this._movable = new dojo.dnd.Moveable(this.sliderHandle, {mover: mover});
		dijit.setWaiState(this.focusNode, "valuemin", this.minimum);
		dijit.setWaiState(this.focusNode, "valuemax", this.maximum);

		this.inherited(arguments);
	},

	destroy: function(){
		this._movable.destroy();
		this.inherited(arguments);	
	}
});

dojo.declare(
	"dijit.form.VerticalSlider",
	dijit.form.HorizontalSlider,
{
	// summary
	//	A form widget that allows one to select a value with a vertically draggable image

	templateString:"<table class=\"dijitReset dijitSlider\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" rules=\"none\"\r\n><tbody class=\"dijitReset\"\r\n\t><tr class=\"dijitReset\"\r\n\t\t><td class=\"dijitReset\"></td\r\n\t\t><td class=\"dijitReset dijitSliderButtonContainer dijitSliderButtonContainerV\"\r\n\t\t\t><div class=\"dijitSliderIncrementIconV\" tabIndex=\"-1\" style=\"display:none\" dojoAttachPoint=\"incrementButton\" dojoAttachEvent=\"onclick:_topButtonClicked\"><span class=\"dijitSliderButtonInner\">+</span></div\r\n\t\t></td\r\n\t\t><td class=\"dijitReset\"></td\r\n\t></tr\r\n\t><tr class=\"dijitReset\"\r\n\t\t><td class=\"dijitReset\"></td\r\n\t\t><td class=\"dijitReset\"\r\n\t\t\t><center><div class=\"dijitSliderBar dijitSliderBumper dijitSliderBumperV dijitSliderTopBumper dijitSliderTopBumper\" dojoAttachEvent=\"onclick:_onClkIncBumper\"></div></center\r\n\t\t></td\r\n\t\t><td class=\"dijitReset\"></td\r\n\t></tr\r\n\t><tr class=\"dijitReset\"\r\n\t\t><td dojoAttachPoint=\"leftDecoration\" class=\"dijitReset\" style=\"text-align:center;height:100%;\"></td\r\n\t\t><td class=\"dijitReset\" style=\"height:100%;\"\r\n\t\t\t><input dojoAttachPoint=\"valueNode\" type=\"hidden\" name=\"${name}\"\r\n\t\t\t/><center waiRole=\"presentation\" style=\"position:relative;height:100%;\" dojoAttachPoint=\"sliderBarContainer\"\r\n\t\t\t\t><div waiRole=\"presentation\" dojoAttachPoint=\"remainingBar\" class=\"dijitSliderBar dijitSliderBarV dijitSliderRemainingBar dijitSliderRemainingBarV\" dojoAttachEvent=\"onclick:_onBarClick\"><!--#5629--></div\r\n\t\t\t\t><div waiRole=\"presentation\" dojoAttachPoint=\"progressBar\" class=\"dijitSliderBar dijitSliderBarV dijitSliderProgressBar dijitSliderProgressBarV\" dojoAttachEvent=\"onclick:_onBarClick\"\r\n\t\t\t\t\t><div dojoAttachPoint=\"sliderHandle,focusNode\" class=\"dijitSliderMoveable\" dojoAttachEvent=\"onkeypress:_onKeyPress,onmousedown:_onHandleClick\" style=\"vertical-align:top;\" waiRole=\"slider\" valuemin=\"${minimum}\" valuemax=\"${maximum}\"\r\n\t\t\t\t\t\t><div class=\"dijitSliderImageHandle dijitSliderImageHandleV\"></div\r\n\t\t\t\t\t></div\r\n\t\t\t\t></div\r\n\t\t\t></center\r\n\t\t></td\r\n\t\t><td dojoAttachPoint=\"containerNode,rightDecoration\" class=\"dijitReset\" style=\"text-align:center;height:100%;\"></td\r\n\t></tr\r\n\t><tr class=\"dijitReset\"\r\n\t\t><td class=\"dijitReset\"></td\r\n\t\t><td class=\"dijitReset\"\r\n\t\t\t><center><div class=\"dijitSliderBar dijitSliderBumper dijitSliderBumperV dijitSliderBottomBumper dijitSliderBottomBumper\" dojoAttachEvent=\"onclick:_onClkDecBumper\"></div></center\r\n\t\t></td\r\n\t\t><td class=\"dijitReset\"></td\r\n\t></tr\r\n\t><tr class=\"dijitReset\"\r\n\t\t><td class=\"dijitReset\"></td\r\n\t\t><td class=\"dijitReset dijitSliderButtonContainer dijitSliderButtonContainerV\"\r\n\t\t\t><div class=\"dijitSliderDecrementIconV\" tabIndex=\"-1\" style=\"display:none\" dojoAttachPoint=\"decrementButton\" dojoAttachEvent=\"onclick:_bottomButtonClicked\"><span class=\"dijitSliderButtonInner\">-</span></div\r\n\t\t></td\r\n\t\t><td class=\"dijitReset\"></td\r\n\t></tr\r\n></tbody></table>\r\n",
	_mousePixelCoord: "pageY",
	_pixelCount: "h",
	_startingPixelCoord: "y",
	_startingPixelCount: "t",
	_handleOffsetCoord: "top",
	_progressPixelSize: "height",

	// _descending: boolean
	//      Specifies if the slider values go from high-on-top (true), or low-on-top (false)
	//	TODO: expose this in 1.2 - the css progress/remaining bar classes need to be reversed
	_descending: true,

	startup: function(){
		if(this._started){ return; }

		if(!this.isLeftToRight() && dojo.isMoz){
			if(this.leftDecoration){this._rtlRectify(this.leftDecoration);}
			if(this.rightDecoration){this._rtlRectify(this.rightDecoration);}
		}

		this.inherited(arguments);
	},
		
	_isReversed: function(){
		return this._descending;
	},

	_topButtonClicked: function(e){
		if(this._descending){
			this.increment(e);
		}else{
			this.decrement(e);
		}
	},

	_bottomButtonClicked: function(e){
		if(this._descending){
			this.decrement(e);
		}else{
			this.increment(e);
		}
	},

	_rtlRectify: function(decorationNode/*NodeList*/){
		// summary:
		//      Rectify children nodes for left/right decoration in rtl case.
		//		Simply switch the rule and label child for each decoration node.
		var childNodes = [];
		while(decorationNode.firstChild){
				childNodes.push(decorationNode.firstChild);
				decorationNode.removeChild(decorationNode.firstChild);
		}
		for(var i = childNodes.length-1; i >=0; i--){
			if(childNodes[i]){
				decorationNode.appendChild(childNodes[i]);
			}
		}
	}
});

dojo.declare("dijit.form._SliderMover",
	dojo.dnd.Mover,
{
	onMouseMove: function(e){
		var widget = this.widget;
		var abspos = widget._abspos;
		if(!abspos){
			abspos = widget._abspos = dojo.coords(widget.sliderBarContainer, true);
			widget._setPixelValue_ = dojo.hitch(widget, "_setPixelValue");
			widget._isReversed_ = widget._isReversed();
		}
		var pixelValue = e[widget._mousePixelCoord] - abspos[widget._startingPixelCoord];
		widget._setPixelValue_(widget._isReversed_ ? (abspos[widget._pixelCount]-pixelValue) : pixelValue, abspos[widget._pixelCount], false);
	},
	
	destroy: function(e){
		dojo.dnd.Mover.prototype.destroy.apply(this, arguments);
		var widget = this.widget;
		widget.setValue(widget.value, true);
	}
});


dojo.declare("dijit.form.HorizontalRule", [dijit._Widget, dijit._Templated],
{
	//	Summary:
	//		Create hash marks for the Horizontal slider
	templateString: '<div class="dijitRuleContainer dijitRuleContainerH"></div>',

	// count: Integer
	//		Number of hash marks to generate
	count: 3,

	// container: Node
	//		If this is a child widget, connect it to this parent node
	container: "containerNode",

	// ruleStyle: String
	//		CSS style to apply to individual hash marks
	ruleStyle: "",

	_positionPrefix: '<div class="dijitRuleMark dijitRuleMarkH" style="left:',
	_positionSuffix: '%;',
	_suffix: '"></div>',

	_genHTML: function(pos, ndx){
		return this._positionPrefix + pos + this._positionSuffix + this.ruleStyle + this._suffix;
	},
	
	_isHorizontal: true,

	postCreate: function(){
		var innerHTML;
		if(this.count==1){
			innerHTML = this._genHTML(50, 0);
		}else{
			var i;
			var interval = 100 / (this.count-1);
			if(!this._isHorizontal || this.isLeftToRight()){
				innerHTML = this._genHTML(0, 0);
				for(i=1; i < this.count-1; i++){
					innerHTML += this._genHTML(interval*i, i);
				}
				innerHTML += this._genHTML(100, this.count-1);
			}else{
				innerHTML = this._genHTML(100, 0);
				for(i=1; i < this.count-1; i++){
					innerHTML += this._genHTML(100-interval*i, i);
				}
				innerHTML += this._genHTML(0, this.count-1);
			}
		}
		this.domNode.innerHTML = innerHTML;
	}
});

dojo.declare("dijit.form.VerticalRule", dijit.form.HorizontalRule,
{
	//	Summary:
	//		Create hash marks for the Vertical slider
	templateString: '<div class="dijitRuleContainer dijitRuleContainerV"></div>',
	_positionPrefix: '<div class="dijitRuleMark dijitRuleMarkV" style="top:',
	
	_isHorizontal: false
});

dojo.declare("dijit.form.HorizontalRuleLabels", dijit.form.HorizontalRule,
{
	//	Summary:
	//		Create labels for the Horizontal slider
	templateString: '<div class="dijitRuleContainer dijitRuleContainerH"></div>',

	// labelStyle: String
	//		CSS style to apply to individual text labels
	labelStyle: "",

	// labels: Array
	//	Array of text labels to render - evenly spaced from left-to-right or bottom-to-top
	labels: [],

	// numericMargin: Integer
	//	Number of generated numeric labels that should be rendered as '' on the ends when labels[] are not specified
	numericMargin: 0,

	// numericMinimum: Integer
	//	Leftmost label value for generated numeric labels when labels[] are not specified
	minimum: 0,

	// numericMaximum: Integer
	//	Rightmost label value for generated numeric labels when labels[] are not specified
	maximum: 1,

	// constraints: object
	//	pattern, places, lang, et al (see dojo.number) for generated numeric labels when labels[] are not specified
	constraints: {pattern:"#%"},

	_positionPrefix: '<div class="dijitRuleLabelContainer dijitRuleLabelContainerH" style="left:',
	_labelPrefix: '"><span class="dijitRuleLabel dijitRuleLabelH">',
	_suffix: '</span></div>',

	_calcPosition: function(pos){
		return pos;
	},

	_genHTML: function(pos, ndx){
		return this._positionPrefix + this._calcPosition(pos) + this._positionSuffix + this.labelStyle + this._labelPrefix + this.labels[ndx] + this._suffix;
	},

	getLabels: function(){
		// summary: user replaceable function to return the labels array

		// if the labels array was not specified directly, then see if <li> children were
		var labels = this.labels;
		if(!labels.length){
			// for markup creation, labels are specified as child elements
			labels = dojo.query("> li", this.srcNodeRef).map(function(node){
				return String(node.innerHTML);
			});
		}
		this.srcNodeRef.innerHTML = '';
		// if the labels were not specified directly and not as <li> children, then calculate numeric labels
		if(!labels.length && this.count > 1){
			var start = this.minimum;
			var inc = (this.maximum - start) / (this.count-1);
			for (var i=0; i < this.count; i++){
				labels.push((i<this.numericMargin||i>=(this.count-this.numericMargin))? '' : dojo.number.format(start, this.constraints));
				start += inc;
			}
		}
		return labels;
	},

	postMixInProperties: function(){
		this.inherited(arguments);
		this.labels = this.getLabels();
		this.count = this.labels.length;
	}
});

dojo.declare("dijit.form.VerticalRuleLabels", dijit.form.HorizontalRuleLabels,
{
	//	Summary:
	//		Create labels for the Vertical slider
	templateString: '<div class="dijitRuleContainer dijitRuleContainerV"></div>',

	_positionPrefix: '<div class="dijitRuleLabelContainer dijitRuleLabelContainerV" style="top:',
	_labelPrefix: '"><span class="dijitRuleLabel dijitRuleLabelV">',

	_calcPosition: function(pos){
		return 100-pos;
	},
	
	_isHorizontal: false
});

}

if(!dojo._hasResource["dijit._editor.selection"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dijit._editor.selection"] = true;
dojo.provide("dijit._editor.selection");

// FIXME:
//		all of these methods branch internally for IE. This is probably
//		sub-optimal in terms of runtime performance. We should investigate the
//		size difference for differentiating at definition time.

dojo.mixin(dijit._editor.selection, {
	getType: function(){
		// summary: Get the selection type (like dojo.doc.select.type in IE).
		if(dojo.doc.selection){ //IE
			return dojo.doc.selection.type.toLowerCase();
		}else{
			var stype = "text";

			// Check if the actual selection is a CONTROL (IMG, TABLE, HR, etc...).
			var oSel;
			try{
				oSel = dojo.global.getSelection();
			}catch(e){ /*squelch*/ }

			if(oSel && oSel.rangeCount==1){
				var oRange = oSel.getRangeAt(0);
				if(	(oRange.startContainer == oRange.endContainer) &&
					((oRange.endOffset - oRange.startOffset) == 1) &&
					(oRange.startContainer.nodeType != 3 /* text node*/)
				){
					stype = "control";
				}
			}
			return stype;
		}
	},

	getSelectedText: function(){
		// summary:
		//		Return the text (no html tags) included in the current selection or null if no text is selected
		if(dojo.doc.selection){ //IE
			if(dijit._editor.selection.getType() == 'control'){
				return null;
			}
			return dojo.doc.selection.createRange().text;
		}else{
			var selection = dojo.global.getSelection();
			if(selection){
				return selection.toString();
			}
		}
		return ''
	},

	getSelectedHtml: function(){
		// summary:
		//		Return the html of the current selection or null if unavailable
		if(dojo.doc.selection){ //IE
			if(dijit._editor.selection.getType() == 'control'){
				return null;
			}
			return dojo.doc.selection.createRange().htmlText;
		}else{
			var selection = dojo.global.getSelection();
			if(selection && selection.rangeCount){
				var frag = selection.getRangeAt(0).cloneContents();
				var div = dojo.doc.createElement("div");
				div.appendChild(frag);
				return div.innerHTML;
			}
			return null;
		}
	},

	getSelectedElement: function(){
		// summary:
		//		Retrieves the selected element (if any), just in the case that
		//		a single element (object like and image or a table) is
		//		selected.
		if(this.getType() == "control"){
			if(dojo.doc.selection){ //IE
				var range = dojo.doc.selection.createRange();
				if(range && range.item){
					return dojo.doc.selection.createRange().item(0);
				}
			}else{
				var selection = dojo.global.getSelection();
				return selection.anchorNode.childNodes[ selection.anchorOffset ];
			}
		}
		return null;
	},

	getParentElement: function(){
		// summary:
		//		Get the parent element of the current selection
		if(this.getType() == "control"){
			var p = this.getSelectedElement();
			if(p){ return p.parentNode; }
		}else{
			if(dojo.doc.selection){ //IE
				return dojo.doc.selection.createRange().parentElement();
			}else{
				var selection = dojo.global.getSelection();
				if(selection){
					var node = selection.anchorNode;

					while(node && (node.nodeType != 1)){ // not an element
						node = node.parentNode;
					}

					return node;
				}
			}
		}
		return null;
	},

	hasAncestorElement: function(/*String*/tagName /* ... */){
		// summary:
		// 		Check whether current selection has a  parent element which is
		// 		of type tagName (or one of the other specified tagName)
		return this.getAncestorElement.apply(this, arguments) != null;
	},

	getAncestorElement: function(/*String*/tagName /* ... */){
		// summary:
		//		Return the parent element of the current selection which is of
		//		type tagName (or one of the other specified tagName)

		var node = this.getSelectedElement() || this.getParentElement();
		return this.getParentOfType(node, arguments);
	},

	isTag: function(/*DomNode*/node, /*Array*/tags){
		if(node && node.tagName){
			var _nlc = node.tagName.toLowerCase();
			for(var i=0; i<tags.length; i++){
				var _tlc = String(tags[i]).toLowerCase();
				if(_nlc == _tlc){
					return _tlc;
				}
			}
		}
		return "";
	},

	getParentOfType: function(/*DomNode*/node, /*Array*/tags){
		while(node){
			if(this.isTag(node, tags).length){
				return node;
			}
			node = node.parentNode;
		}
		return null;
	},

	collapse: function(/*Boolean*/beginning) {
		// summary: clear current selection
	  if(window['getSelection']){
	          var selection = dojo.global.getSelection();
	          if(selection.removeAllRanges){ // Mozilla
	                  if(beginning){
	                          selection.collapseToStart();
	                  }else{
	                          selection.collapseToEnd();
	                  }
	          }else{ // Safari
	                  // pulled from WebCore/ecma/kjs_window.cpp, line 2536
	                   selection.collapse(beginning);
	          }
	  }else if(dojo.doc.selection){ // IE
	          var range = dojo.doc.selection.createRange();
	          range.collapse(beginning);
	          range.select();
	  }
	},

	remove: function(){
		// summary: delete current selection
		var _s = dojo.doc.selection;
		if(_s){ //IE
			if(_s.type.toLowerCase() != "none"){
				_s.clear();
			}
			return _s;
		}else{
			_s = dojo.global.getSelection();
			_s.deleteFromDocument();
			return _s;
		}
	},

	selectElementChildren: function(/*DomNode*/element,/*Boolean?*/nochangefocus){
		// summary:
		//		clear previous selection and select the content of the node
		//		(excluding the node itself)
		var _window = dojo.global;
		var _document = dojo.doc;
		element = dojo.byId(element);
		if(_document.selection && dojo.body().createTextRange){ // IE
			var range = element.ownerDocument.body.createTextRange();
			range.moveToElementText(element);
			if(!nochangefocus){
				try{
					range.select(); // IE throws an exception here if the widget is hidden.  See #5439
				}catch(e){ /* squelch */}
			}
		}else if(_window.getSelection){
			var selection = _window.getSelection();
			if(selection.setBaseAndExtent){ // Safari
				selection.setBaseAndExtent(element, 0, element, element.innerText.length - 1);
			}else if(selection.selectAllChildren){ // Mozilla
				selection.selectAllChildren(element);
			}
		}
	},

	selectElement: function(/*DomNode*/element,/*Boolean?*/nochangefocus){
		// summary:
		//		clear previous selection and select element (including all its children)
		var range, _document = dojo.doc;
		element = dojo.byId(element);
		if(_document.selection && dojo.body().createTextRange){ // IE
			try{
				range = dojo.body().createControlRange();
				range.addElement(element);
				if(!nochangefocus){
					range.select();
				}
			}catch(e){
				this.selectElementChildren(element,nochangefocus);
			}
		}else if(dojo.global.getSelection){
			var selection = dojo.global.getSelection();
			// FIXME: does this work on Safari?
			if(selection.removeAllRanges){ // Mozilla
				range = _document.createRange();
				range.selectNode(element);
				selection.removeAllRanges();
				selection.addRange(range);
			}
		}
	}
});

}

if(!dojo._hasResource["dijit._editor.html"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dijit._editor.html"] = true;
dojo.provide("dijit._editor.html");

dijit._editor.escapeXml=function(/*String*/str, /*Boolean*/noSingleQuotes){
	//summary:
	//		Adds escape sequences for special characters in XML: &<>"'
	//		Optionally skips escapes for single quotes
	str = str.replace(/&/gm, "&amp;").replace(/</gm, "&lt;").replace(/>/gm, "&gt;").replace(/"/gm, "&quot;");
	if(!noSingleQuotes){
		str = str.replace(/'/gm, "&#39;");
	}
	return str; // string
};

dijit._editor.getNodeHtml=function(/* DomNode */node){
	var output;
	switch(node.nodeType){
		case 1: //element node
			output = '<'+node.nodeName.toLowerCase();

			//store the list of attributes and sort it to have the
			//attributes appear in the dictionary order
			var attrarray = [];
			if(dojo.isIE && node.outerHTML){
				var s = node.outerHTML;
				s = s.substr(0,s.indexOf('>'));
				s = s.replace(/(['"])[^"']*\1/g, '');//to make the following regexp safe
				var reg = /([^\s=]+)=/g;
				var m, key;
				while((m = reg.exec(s))){
					key=m[1];
					if(key.substr(0,3) != '_dj'){
						if(key == 'src' || key == 'href'){
							if(node.getAttribute('_djrealurl')){
								attrarray.push([key,node.getAttribute('_djrealurl')]);
								continue;
							}
						}
						if(key=='style'){
							attrarray.push([key, node.style.cssText.toLowerCase()]);
						}else{
							attrarray.push([key, key=='class'?node.className:node.getAttribute(key)]);
						}
					}
				}
			}else{
				var attr, i=0, attrs = node.attributes;
				while((attr=attrs[i++])){
					//ignore all attributes starting with _dj which are
					//internal temporary attributes used by the editor
					var n=attr.name;
					if(n.substr(0,3) != '_dj' /*&&
						(attr.specified == undefined || attr.specified)*/){
						var v = attr.value;
						if(n == 'src' || n == 'href'){
							if(node.getAttribute('_djrealurl')){
								v = node.getAttribute('_djrealurl');
							}
						}
						attrarray.push([n,v]);
					}
				}
			}
			attrarray.sort(function(a,b){
				return a[0]<b[0]?-1:(a[0]==b[0]?0:1);
			});
			i=0;
			while((attr=attrarray[i++])){
				output += ' '+attr[0]+'="'+
					(dojo.isString(attr[1]) ? dijit._editor.escapeXml(attr[1],true) : attr[1])+'"';
			}
			if(node.childNodes.length){
				output += '>' + dijit._editor.getChildrenHtml(node)+'</'+node.nodeName.toLowerCase()+'>';
			}else{
				output += ' />';
			}
			break;
		case 3: //text
			// FIXME:
			output = dijit._editor.escapeXml(node.nodeValue,true);
			break;
		case 8: //comment
			// FIXME:
			output = '<!--'+dijit._editor.escapeXml(node.nodeValue,true)+'-->';
			break;
		default:
			output = "Element not recognized - Type: " + node.nodeType + " Name: " + node.nodeName;
	}
	return output;
};

dijit._editor.getChildrenHtml = function(/* DomNode */dom){
	// summary: Returns the html content of a DomNode and children
	var out = "";
	if(!dom){ return out; }
	var nodes = dom["childNodes"]||dom;
	var i=0;
	var node;
	while((node=nodes[i++])){
		out += dijit._editor.getNodeHtml(node);
	}
	return out; // String
}

}

if(!dojo._hasResource["dijit._editor.RichText"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dijit._editor.RichText"] = true;
dojo.provide("dijit._editor.RichText");







// used to restore content when user leaves this page then comes back
// but do not try doing dojo.doc.write if we are using xd loading.
// dojo.doc.write will only work if RichText.js is included in the dojo.js
// file. If it is included in dojo.js and you want to allow rich text saving
// for back/forward actions, then set dojo.config.allowXdRichTextSave = true.
if(!dojo.config["useXDomain"] || dojo.config["allowXdRichTextSave"]){
	if(dojo._postLoad){
		(function(){
			var savetextarea = dojo.doc.createElement('textarea');
			savetextarea.id = dijit._scopeName + "._editor.RichText.savedContent";
			var s = savetextarea.style;
			s.display='none';
			s.position='absolute';
			s.top="-100px";
			s.left="-100px";
			s.height="3px";
			s.width="3px";
			dojo.body().appendChild(savetextarea);
		})();
	}else{
		//dojo.body() is not available before onLoad is fired
		try{
			dojo.doc.write('<textarea id="' + dijit._scopeName + '._editor.RichText.savedContent" ' +
				'style="display:none;position:absolute;top:-100px;left:-100px;height:3px;width:3px;overflow:hidden;"></textarea>');
		}catch(e){ }
	}
}
dojo.declare("dijit._editor.RichText", dijit._Widget, {
	constructor: function(){
		// summary:
		//		dijit._editor.RichText is the core of the WYSIWYG editor in dojo, which
		//		provides the basic editing features. It also encapsulates the differences
		//		of different js engines for various browsers
		//
		// contentPreFilters: Array
		//		pre content filter function register array.
		//		these filters will be executed before the actual
		//		editing area get the html content
		this.contentPreFilters = [];

		// contentPostFilters: Array
		//		post content filter function register array.
		//		these will be used on the resulting html
		//		from contentDomPostFilters. The resuling
		//		content is the final html (returned by getValue())
		this.contentPostFilters = [];

		// contentDomPreFilters: Array
		//		pre content dom filter function register array.
		//		these filters are applied after the result from
		//		contentPreFilters are set to the editing area
		this.contentDomPreFilters = [];

		// contentDomPostFilters: Array
		//		post content dom filter function register array.
		//		these filters are executed on the editing area dom
		//		the result from these will be passed to contentPostFilters
		this.contentDomPostFilters = [];

		// editingAreaStyleSheets: Array
		//		array to store all the stylesheets applied to the editing area
		this.editingAreaStyleSheets=[];

		this._keyHandlers = {};
		this.contentPreFilters.push(dojo.hitch(this, "_preFixUrlAttributes"));
		if(dojo.isMoz){
			this.contentPreFilters.push(this._fixContentForMoz);
			this.contentPostFilters.push(this._removeMozBogus);
		}else if(dojo.isSafari){
			this.contentPostFilters.push(this._removeSafariBogus);
		}
		//this.contentDomPostFilters.push(this._postDomFixUrlAttributes);

		this.onLoadDeferred = new dojo.Deferred();
	},

	// inheritWidth: Boolean
	//		whether to inherit the parent's width or simply use 100%
	inheritWidth: false,

	// focusOnLoad: Boolean
	//		whether focusing into this instance of richtext when page onload
	focusOnLoad: false,

	// name: String
	//		If a save name is specified the content is saved and restored when the user
	//		leave this page can come back, or if the editor is not properly closed after
	//		editing has started.
	name: "",

	// styleSheets: String
	//		semicolon (";") separated list of css files for the editing area
	styleSheets: "",

	// _content: String
	//		temporary content storage
	_content: "",

	// height: String
	//		set height to fix the editor at a specific height, with scrolling.
	//		By default, this is 300px. If you want to have the editor always
	//		resizes to accommodate the content, use AlwaysShowToolbar plugin
	//		and set height=""
	height: "300px",

	// minHeight: String
	//		The minimum height that the editor should have
	minHeight: "1em",
	
	// isClosed: Boolean
	isClosed: true,

	// isLoaded: Boolean
	isLoaded: false,

	// _SEPARATOR: String
	//		used to concat contents from multiple textareas into a single string
	_SEPARATOR: "@@**%%__RICHTEXTBOUNDRY__%%**@@",

	// onLoadDeferred: dojo.Deferred
	//		deferred which is fired when the editor finishes loading
	onLoadDeferred: null,

	postCreate: function(){
		// summary: init
		dojo.publish(dijit._scopeName + "._editor.RichText::init", [this]);
		this.open();
		this.setupDefaultShortcuts();
	},

	setupDefaultShortcuts: function(){
		// summary: add some default key handlers
		// description:
		// 		Overwrite this to setup your own handlers. The default
		// 		implementation does not use Editor commands, but directly
		//		executes the builtin commands within the underlying browser
		//		support.
		var exec = function(cmd, arg){
			return arguments.length == 1 ? function(){ this.execCommand(cmd); } :
				function(){ this.execCommand(cmd, arg); };
		};

		var ctrlKeyHandlers = { b: exec("bold"),
			i: exec("italic"),
			u: exec("underline"),
			a: exec("selectall"),
			s: function(){ this.save(true); },

			"1": exec("formatblock", "h1"),
			"2": exec("formatblock", "h2"),
			"3": exec("formatblock", "h3"),
			"4": exec("formatblock", "h4"),

			"\\": exec("insertunorderedlist") };

		if(!dojo.isIE){
			ctrlKeyHandlers.Z = exec("redo"); //FIXME: undo?
		}

		for(var key in ctrlKeyHandlers){
			this.addKeyHandler(key, this.KEY_CTRL, ctrlKeyHandlers[key]);
		}
	},

	// events: Array
	//		 events which should be connected to the underlying editing area
	events: ["onKeyPress", "onKeyDown", "onKeyUp", "onClick"],

	// events: Array
	//		 events which should be connected to the underlying editing
	//		 area, events in this array will be addListener with
	//		 capture=true
	captureEvents: [],

	_editorCommandsLocalized: false,
	_localizeEditorCommands: function(){
		if(this._editorCommandsLocalized){
			return;
		}
		this._editorCommandsLocalized = true;

		//in IE, names for blockformat is locale dependent, so we cache the values here

		//if the normal way fails, we try the hard way to get the list

		//do not use _cacheLocalBlockFormatNames here, as it will
		//trigger security warning in IE7

		//in the array below, ul can not come directly after ol,
		//otherwise the queryCommandValue returns Normal for it
		var formats = ['p', 'pre', 'address', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6', 'ol', 'div', 'ul'];
		var localhtml = "", format, i=0;
		while((format=formats[i++])){
			if(format.charAt(1) != 'l'){
				localhtml += "<"+format+"><span>content</span></"+format+">";
			}else{
				localhtml += "<"+format+"><li>content</li></"+format+">";
			}
		}
		//queryCommandValue returns empty if we hide editNode, so move it out of screen temporary
		var div=dojo.doc.createElement('div');
		div.style.position = "absolute";
		div.style.left = "-2000px";
		div.style.top = "-2000px";
		dojo.doc.body.appendChild(div);
		div.innerHTML = localhtml;
		var node = div.firstChild;
		while(node){
			dijit._editor.selection.selectElement(node.firstChild);
			dojo.withGlobal(this.window, "selectElement", dijit._editor.selection, [node.firstChild]);
			var nativename = node.tagName.toLowerCase();
			this._local2NativeFormatNames[nativename] = dojo.doc.queryCommandValue("formatblock");//this.queryCommandValue("formatblock");
			this._native2LocalFormatNames[this._local2NativeFormatNames[nativename]] = nativename;
			node = node.nextSibling;
		}
		dojo.doc.body.removeChild(div);
	},

	open: function(/*DomNode?*/element){
		// summary:
		//		Transforms the node referenced in this.domNode into a rich text editing
		//		node. This will result in the creation and replacement with an <iframe>
		//		if designMode(FF)/contentEditable(IE) is used.

		if((!this.onLoadDeferred)||(this.onLoadDeferred.fired >= 0)){
			this.onLoadDeferred = new dojo.Deferred();
		}

		if(!this.isClosed){ this.close(); }
		dojo.publish(dijit._scopeName + "._editor.RichText::open", [ this ]);

		this._content = "";
		if((arguments.length == 1)&&(element["nodeName"])){ this.domNode = element; } // else unchanged

		var html;
		if(	(this.domNode["nodeName"])&&
			(this.domNode.nodeName.toLowerCase() == "textarea")){
			// if we were created from a textarea, then we need to create a
			// new editing harness node.
			this.textarea = this.domNode;
			this.name=this.textarea.name;
			html = this._preFilterContent(this.textarea.value);
			this.domNode = dojo.doc.createElement("div");
			this.domNode.setAttribute('widgetId',this.id);
			this.textarea.removeAttribute('widgetId');
			this.domNode.cssText = this.textarea.cssText;
			this.domNode.className += " "+this.textarea.className;
			dojo.place(this.domNode, this.textarea, "before");
			var tmpFunc = dojo.hitch(this, function(){
				//some browsers refuse to submit display=none textarea, so
				//move the textarea out of screen instead
				dojo.attr(this.textarea, 'tabIndex', '-1');
				with(this.textarea.style){
					display = "block";
					position = "absolute";
					left = top = "-1000px";

					if(dojo.isIE){ //nasty IE bug: abnormal formatting if overflow is not hidden
						this.__overflow = overflow;
						overflow = "hidden";
					}
				}
			});
			if(dojo.isIE){
				setTimeout(tmpFunc, 10);
			}else{
				tmpFunc();
			}

			// this.domNode.innerHTML = html;

//				if(this.textarea.form){
//					// FIXME: port: this used to be before advice!!!
//					dojo.connect(this.textarea.form, "onsubmit", this, function(){
//						// FIXME: should we be calling close() here instead?
//						this.textarea.value = this.getValue();
//					});
//				}
		}else{
			html = this._preFilterContent(dijit._editor.getChildrenHtml(this.domNode));
			this.domNode.innerHTML = '';
		}
		if(html == ""){ html = "&nbsp;"; }

		var content = dojo.contentBox(this.domNode);
		// var content = dojo.contentBox(this.srcNodeRef);
		this._oldHeight = content.h;
		this._oldWidth = content.w;

		// If we're a list item we have to put in a blank line to force the
		// bullet to nicely align at the top of text
		if(	(this.domNode["nodeName"]) &&
			(this.domNode.nodeName == "LI") ){
			this.domNode.innerHTML = " <br>";
		}

		this.editingArea = dojo.doc.createElement("div");
		this.domNode.appendChild(this.editingArea);

		if(this.name != "" && (!dojo.config["useXDomain"] || dojo.config["allowXdRichTextSave"])){
			var saveTextarea = dojo.byId(dijit._scopeName + "._editor.RichText.savedContent");
			if(saveTextarea.value != ""){
				var datas = saveTextarea.value.split(this._SEPARATOR), i=0, dat;
				while((dat=datas[i++])){
					var data = dat.split(":");
					if(data[0] == this.name){
						html = data[1];
						datas.splice(i, 1);
						break;
					}
				}
			}

			// FIXME: need to do something different for Opera/Safari
			this.connect(window, "onbeforeunload", "_saveContent");
			// dojo.connect(window, "onunload", this, "_saveContent");
		}

		this.isClosed = false;
		// Safari's selections go all out of whack if we do it inline,
		// so for now IE is our only hero
		//if(typeof dojo.doc.body.contentEditable != "undefined"){
		if(dojo.isIE || dojo.isSafari || dojo.isOpera){ // contentEditable, easy		

			if(dojo.config["useXDomain"] && !dojo.config["dojoBlankHtmlUrl"]){
				console.debug("dijit._editor.RichText: When using cross-domain Dojo builds,"
				+ " please save dojo/resources/blank.html to your domain and set djConfig.dojoBlankHtmlUrl"
				+ " to the path on your domain to blank.html");
			}

			var burl = dojo.config["dojoBlankHtmlUrl"] || (dojo.moduleUrl("dojo", "resources/blank.html")+"");
			var ifr = this.editorObject = this.iframe = dojo.doc.createElement('iframe');
			ifr.id = this.id+"_iframe";
			ifr.src = burl;
			ifr.style.border = "none";
			ifr.style.width = "100%";
			ifr.frameBorder = 0;
			// ifr.style.scrolling = this.height ? "auto" : "vertical";
			this.editingArea.appendChild(ifr);
			var h = null; // set later in non-ie6 branch
			var loadFunc = dojo.hitch( this, function(){
				if(h){ dojo.disconnect(h); h = null; }
				this.window = ifr.contentWindow;
				var d = this.document = this.window.document;
				d.open();
				d.write(this._getIframeDocTxt(html));
				d.close();

				if(dojo.isIE >= 7){
					if(this.height){
						ifr.style.height = this.height;
					}
					if(this.minHeight){
						ifr.style.minHeight = this.minHeight;
					}
				}else{
					ifr.style.height = this.height ? this.height : this.minHeight;
				}

				if(dojo.isIE){
					this._localizeEditorCommands();
				}

				this.onLoad();
				this.savedContent = this.getValue(true);
			});
			if(dojo.isIE && dojo.isIE < 7){ // IE 6 is a steaming pile...
				var t = setInterval(function(){
					if(ifr.contentWindow.isLoaded){
						clearInterval(t);
						loadFunc();
					}
				}, 100);
			}else{ // blissful sanity!
				h = dojo.connect(
					((dojo.isIE) ? ifr.contentWindow : ifr), "onload", loadFunc
				);
			}
		}else{ // designMode in iframe
			this._drawIframe(html);
			this.savedContent = this.getValue(true);
		}

		// TODO: this is a guess at the default line-height, kinda works
		if(this.domNode.nodeName == "LI"){ this.domNode.lastChild.style.marginTop = "-1.2em"; }
		this.domNode.className += " RichTextEditable";
	},

	//static cache variables shared among all instance of this class
	_local2NativeFormatNames: {},
	_native2LocalFormatNames: {},
	_localizedIframeTitles: null,

	_getIframeDocTxt: function(/* String */ html){
		var _cs = dojo.getComputedStyle(this.domNode);
		if(dojo.isIE || (!this.height && !dojo.isMoz)){
			html="<div>"+html+"</div>";
		}
		var font = [ _cs.fontWeight, _cs.fontSize, _cs.fontFamily ].join(" ");

		// line height is tricky - applying a units value will mess things up.
		// if we can't get a non-units value, bail out.
		var lineHeight = _cs.lineHeight;
		if(lineHeight.indexOf("px") >= 0){
			lineHeight = parseFloat(lineHeight)/parseFloat(_cs.fontSize);
			// console.debug(lineHeight);
		}else if(lineHeight.indexOf("em")>=0){
			lineHeight = parseFloat(lineHeight);
		}else{
			lineHeight = "1.0";
		}
		return [
			this.isLeftToRight() ? "<html><head>" : "<html dir='rtl'><head>",
			(dojo.isMoz ? "<title>" + this._localizedIframeTitles.iframeEditTitle + "</title>" : ""),
			"<style>",
			"body,html {",
			"	background:transparent;",
			"	font:", font, ";",
			"	padding: 1em 0 0 0;",
			"	margin: -1em 0 0 0;", // remove extraneous vertical scrollbar on safari and firefox
			"	height: 100%;",
			"}",
			// TODO: left positioning will cause contents to disappear out of view
			//	   if it gets too wide for the visible area
			"body{",
			"	top:0px; left:0px; right:0px;",
				((this.height||dojo.isOpera) ? "" : "position: fixed;"),
			// FIXME: IE 6 won't understand min-height?
			"	min-height:", this.minHeight, ";",
			"	line-height:", lineHeight,
			"}",
			"p{ margin: 1em 0 !important; }",
			(this.height ? // height:auto undoes the height:100%
				"" : "body,html{height:auto;overflow-y:hidden;/*for IE*/} body > div {overflow-x:auto;/*for FF to show vertical scrollbar*/}"
			),
			"li > ul:-moz-first-node, li > ol:-moz-first-node{ padding-top: 1.2em; } ",
			"li{ min-height:1.2em; }",
			"</style>",
			this._applyEditingAreaStyleSheets(),
			"</head><body>"+html+"</body></html>"
		].join(""); // String
	},

	_drawIframe: function(/*String*/html){
		// summary:
		//		Draws an iFrame using the existing one if one exists.
		//		Used by Mozilla, Safari, and Opera

		if(!this.iframe){
			var ifr = this.iframe = dojo.doc.createElement("iframe");
			ifr.id=this.id;
			// this.iframe.src = "about:blank";
			// dojo.doc.body.appendChild(this.iframe);
			// console.debug(this.iframe.contentDocument.open());
			// dojo.body().appendChild(this.iframe);
			var ifrs = ifr.style;
			// ifrs.border = "1px solid black";
			ifrs.border = "none";
			ifrs.lineHeight = "0"; // squash line height
			ifrs.verticalAlign = "bottom";
//			ifrs.scrolling = this.height ? "auto" : "vertical";
			this.editorObject = this.iframe;
			// get screen reader text for mozilla here, too
			this._localizedIframeTitles = dojo.i18n.getLocalization("dijit.form", "Textarea");
			// need to find any associated label element and update iframe document title
			var label=dojo.query('label[for="'+this.id+'"]');
			if(label.length){
				this._localizedIframeTitles.iframeEditTitle = label[0].innerHTML + " " + this._localizedIframeTitles.iframeEditTitle;
			}
		}
		// opera likes this to be outside the with block
		//	this.iframe.src = "javascript:void(0)";//dojo.uri.dojoUri("src/widget/templates/richtextframe.html") + ((dojo.doc.domain != currentDomain) ? ("#"+dojo.doc.domain) : "");
		this.iframe.style.width = this.inheritWidth ? this._oldWidth : "100%";

		if(this.height){
			this.iframe.style.height = this.height;
		}else{
			this.iframe.height = this._oldHeight;
		}

		var tmpContent;
		if(this.textarea){
			tmpContent = this.srcNodeRef;
		}else{
			tmpContent = dojo.doc.createElement('div');
			tmpContent.style.display="none";
			tmpContent.innerHTML = html;
			//append tmpContent to under the current domNode so that the margin
			//calculation below is correct
			this.editingArea.appendChild(tmpContent);
		}

		this.editingArea.appendChild(this.iframe);

		//do we want to show the content before the editing area finish loading here?
		//if external style sheets are used for the editing area, the appearance now
		//and after loading of the editing area won't be the same (and padding/margin
		//calculation above may not be accurate)
		//	tmpContent.style.display = "none";
		//	this.editingArea.appendChild(this.iframe);

		var _iframeInitialized = false;
		// console.debug(this.iframe);
		// var contentDoc = this.iframe.contentWindow.document;


		// note that on Safari lower than 420+, we have to get the iframe
		// by ID in order to get something w/ a contentDocument property

		var contentDoc = this.iframe.contentDocument;
		contentDoc.open();
		if(dojo.isAIR){
			contentDoc.body.innerHTML = html;
		}else{
			contentDoc.write(this._getIframeDocTxt(html));
		}
		contentDoc.close();

		// now we wait for onload. Janky hack!
		var ifrFunc = dojo.hitch(this, function(){
			if(!_iframeInitialized){
				_iframeInitialized = true;
			}else{ return; }
			if(!this.editNode){
				try{
					if(this.iframe.contentWindow){
						this.window = this.iframe.contentWindow;
						this.document = this.iframe.contentWindow.document
					}else if(this.iframe.contentDocument){
						// for opera
						this.window = this.iframe.contentDocument.window;
						this.document = this.iframe.contentDocument;
					}
					if(!this.document.body){
						throw 'Error';
					}
				}catch(e){
					setTimeout(ifrFunc,500);
					_iframeInitialized = false;
					return;
				}

				dojo._destroyElement(tmpContent);
				this.onLoad();
			}else{
				dojo._destroyElement(tmpContent);
				this.editNode.innerHTML = html;
				this.onDisplayChanged();
			}
			this._preDomFilterContent(this.editNode);
		});

		ifrFunc();
	},

	_applyEditingAreaStyleSheets: function(){
		// summary:
		//		apply the specified css files in styleSheets
		var files = [];
		if(this.styleSheets){
			files = this.styleSheets.split(';');
			this.styleSheets = '';
		}

		//empty this.editingAreaStyleSheets here, as it will be filled in addStyleSheet
		files = files.concat(this.editingAreaStyleSheets);
		this.editingAreaStyleSheets = [];

		var text='', i=0, url;
		while((url=files[i++])){
			var abstring = (new dojo._Url(dojo.global.location, url)).toString();
			this.editingAreaStyleSheets.push(abstring);
			text += '<link rel="stylesheet" type="text/css" href="'+abstring+'"/>'
		}
		return text;
	},

	addStyleSheet: function(/*dojo._Url*/uri){
		// summary:
		//		add an external stylesheet for the editing area
		// uri:	a dojo.uri.Uri pointing to the url of the external css file
		var url=uri.toString();

		//if uri is relative, then convert it to absolute so that it can be resolved correctly in iframe
		if(url.charAt(0) == '.' || (url.charAt(0) != '/' && !uri.host)){
			url = (new dojo._Url(dojo.global.location, url)).toString();
		}

		if(dojo.indexOf(this.editingAreaStyleSheets, url) > -1){
//			console.debug("dijit._editor.RichText.addStyleSheet: Style sheet "+url+" is already applied");
			return;
		}

		this.editingAreaStyleSheets.push(url);
		if(this.document.createStyleSheet){ //IE
			this.document.createStyleSheet(url);
		}else{ //other browser
			var head = this.document.getElementsByTagName("head")[0];
			var stylesheet = this.document.createElement("link");
			with(stylesheet){
				rel="stylesheet";
				type="text/css";
				href=url;
			}
			head.appendChild(stylesheet);
		}
	},

	removeStyleSheet: function(/*dojo._Url*/uri){
		// summary:
		//		remove an external stylesheet for the editing area
		var url=uri.toString();
		//if uri is relative, then convert it to absolute so that it can be resolved correctly in iframe
		if(url.charAt(0) == '.' || (url.charAt(0) != '/' && !uri.host)){
			url = (new dojo._Url(dojo.global.location, url)).toString();
		}
		var index = dojo.indexOf(this.editingAreaStyleSheets, url);
		if(index == -1){
//			console.debug("dijit._editor.RichText.removeStyleSheet: Style sheet "+url+" has not been applied");
			return;
		}
		delete this.editingAreaStyleSheets[index];
		dojo.withGlobal(this.window,'query', dojo, ['link:[href="'+url+'"]']).orphan()
	},

	disabled: true,
	_mozSettingProps: ['styleWithCSS','insertBrOnReturn'],
	setDisabled: function(/*Boolean*/ disabled){
		if(dojo.isIE || dojo.isSafari || dojo.isOpera){
			if(dojo.isIE){ this.editNode.unselectable = "on"; } // prevent IE from setting focus
			this.editNode.contentEditable = !disabled;
			if(dojo.isIE){
				var _this = this;
				setTimeout(function(){ _this.editNode.unselectable = "off"; }, 0);
			}
		}else{ //moz
			if(disabled){
				//AP: why isn't this set in the constructor, or put in mozSettingProps as a hash?
				this._mozSettings=[false,this.blockNodeForEnter==='BR'];
			}
			this.document.designMode=(disabled?'off':'on');
			if(!disabled && this._mozSettings){
				dojo.forEach(this._mozSettingProps, function(s,i){
					this.document.execCommand(s,false,this._mozSettings[i]);
				},this);
			}
//			this.document.execCommand('contentReadOnly', false, disabled);
//				if(disabled){
//					this.blur(); //to remove the blinking caret
//				}
		}
		this.disabled = disabled;
	},

/* Event handlers
 *****************/

	_isResized: function(){ return false; },

	onLoad: function(/* Event */ e){
		// summary: handler after the content of the document finishes loading
		this.isLoaded = true;
		if(!this.window.__registeredWindow){
			this.window.__registeredWindow=true;
			dijit.registerWin(this.window);
		}
		if(!dojo.isIE && (this.height || dojo.isMoz)){
			this.editNode=this.document.body;
		}else{
			this.editNode=this.document.body.firstChild;
			var _this = this;
			if(dojo.isIE){ // #4996 IE wants to focus the BODY tag
				var tabStop = this.tabStop = dojo.doc.createElement('<div tabIndex=-1>');
				this.editingArea.appendChild(tabStop);
				this.iframe.onfocus = function(){ _this.editNode.setActive(); }
			}
		}

		try{
			this.setDisabled(false);
		}catch(e){
			// Firefox throws an exception if the editor is initially hidden
			// so, if this fails, try again onClick by adding "once" advice
			var handle = dojo.connect(this, "onClick", this, function(){
				this.setDisabled(false);
				dojo.disconnect(handle);
			});
		}

		this._preDomFilterContent(this.editNode);

		var events=this.events.concat(this.captureEvents),i=0,et;
		while((et=events[i++])){
			this.connect(this.document, et.toLowerCase(), et);
		}
		if(!dojo.isIE){
			try{ // sanity check for Mozilla
			//AP: what's the point of this?
//					this.document.execCommand("useCSS", false, true); // old moz call
				this.document.execCommand("styleWithCSS", false, false); // new moz call
				//this.document.execCommand("insertBrOnReturn", false, false); // new moz call
			}catch(e2){ }
			// FIXME: when scrollbars appear/disappear this needs to be fired
		}else{ // IE contentEditable
			// give the node Layout on IE
			this.connect(this.document, "onmousedown", "_onMouseDown"); // #4996 fix focus
			this.editNode.style.zoom = 1.0;
		}

		if(this.focusOnLoad){
			setTimeout(dojo.hitch(this, "focus"), 0); // have to wait for IE to set unselectable=off
		}

		this.onDisplayChanged(e);
		if(this.onLoadDeferred){
			this.onLoadDeferred.callback(true);
		}
	},

	onKeyDown: function(/* Event */ e){
		// summary: Fired on keydown

		// we need this event at the moment to get the events from control keys
		// such as the backspace. It might be possible to add this to Dojo, so that
		// keyPress events can be emulated by the keyDown and keyUp detection.
		if(dojo.isIE){
			if(e.keyCode == dojo.keys.TAB && e.shiftKey && !e.ctrlKey && !e.altKey){
				// focus the BODY so the browser will tab away from it instead
				this.iframe.focus();
			}else if(e.keyCode == dojo.keys.TAB && !e.shiftKey && !e.ctrlKey && !e.altKey){
				// focus the BODY so the browser will tab away from it instead
				this.tabStop.focus();
			}else if(e.keyCode === dojo.keys.BACKSPACE && this.document.selection.type === "Control"){
				// IE has a bug where if a non-text object is selected in the editor,
		  // hitting backspace would act as if the browser's back button was
		  // clicked instead of deleting the object. see #1069
				dojo.stopEvent(e);
				this.execCommand("delete");
			}else if((65 <= e.keyCode&&e.keyCode <= 90) ||
				(e.keyCode>=37&&e.keyCode<=40) // FIXME: get this from connect() instead!
			){ //arrow keys
				e.charCode = e.keyCode;
				this.onKeyPress(e);
			}
		}else if(dojo.isMoz){
			if(e.keyCode == dojo.keys.TAB && !e.shiftKey && !e.ctrlKey && !e.altKey && this.iframe){
				// update iframe document title for screen reader
				this.iframe.contentDocument.title = this._localizedIframeTitles.iframeFocusTitle;
				
				// Place focus on the iframe. A subsequent tab or shift tab will put focus
				// on the correct control.
				this.iframe.focus();  // this.focus(); won't work
				dojo.stopEvent(e);
			}else if(e.keyCode == dojo.keys.TAB && e.shiftKey){
				// if there is a toolbar, set focus to it, otherwise ignore
				if(this.toolbar){
					this.toolbar.focus();
				}
				dojo.stopEvent(e);
			}
		}
	},

	onKeyUp: function(e){
		// summary: Fired on keyup
		return;
	},

	KEY_CTRL: 1,
	KEY_SHIFT: 2,

	onKeyPress: function(e){
		// summary: Fired on keypress

		// handle the various key events
		var modifiers = (e.ctrlKey && !e.altKey) ? this.KEY_CTRL : 0 | e.shiftKey ? this.KEY_SHIFT : 0;

		var key = e.keyChar || e.keyCode;
		if(this._keyHandlers[key]){
			// console.debug("char:", e.key);
			var handlers = this._keyHandlers[key], i = 0, h;
			while((h = handlers[i++])){
				if(modifiers == h.modifiers){
					if(!h.handler.apply(this,arguments)){
						e.preventDefault();
					}
					break;
				}
			}
		}

		// function call after the character has been inserted
		setTimeout(dojo.hitch(this, function(){
			this.onKeyPressed(e);
		}), 1);
	},

	addKeyHandler: function(/*String*/key, /*Int*/modifiers, /*Function*/handler){
		// summary: add a handler for a keyboard shortcut
		if(!dojo.isArray(this._keyHandlers[key])){ this._keyHandlers[key] = []; }
		this._keyHandlers[key].push({
			modifiers: modifiers || 0,
			handler: handler
		});
	},

	onKeyPressed: function(/*Event*/e){
		this.onDisplayChanged(/*e*/); // can't pass in e
	},

	onClick: function(/*Event*/e){
//		console.info('onClick',this._tryDesignModeOn);
		this.onDisplayChanged(e);
	},

	_onMouseDown: function(/*Event*/e){ // IE only to prevent 2 clicks to focus
		if(!this._focused && !this.disabled){
			this.focus();
		}
	},

	_onBlur: function(e){
		this.inherited(arguments);
		var _c=this.getValue(true);
		if(_c!=this.savedContent){
			this.onChange(_c);
			this.savedContent=_c;
		}
		if(dojo.isMoz && this.iframe){
			this.iframe.contentDocument.title = this._localizedIframeTitles.iframeEditTitle;
		} 
	},
	_initialFocus: true,
	_onFocus: function(/*Event*/e){
		// summary: Fired on focus
		this.inherited(arguments);
		if(dojo.isMoz && this._initialFocus){
			this._initialFocus = false;
			if(this.editNode.innerHTML.replace(/^\s+|\s+$/g, "") == "&nbsp;"){
				this.placeCursorAtStart();
//					this.execCommand("selectall");
//					this.window.getSelection().collapseToStart();
			}
		}
	},

	// TODO: why is this needed - should we deprecate this ?
	blur: function(){
		// summary: remove focus from this instance
		if(!dojo.isIE && this.window.document.documentElement && this.window.document.documentElement.focus){
			this.window.document.documentElement.focus();
		}else if(dojo.doc.body.focus){
			dojo.doc.body.focus();
		}
	},

	focus: function(){
		// summary: move focus to this instance
		if(!dojo.isIE){
			dijit.focus(this.iframe);
		}else if(this.editNode && this.editNode.focus){
			// editNode may be hidden in display:none div, lets just punt in this case
			//this.editNode.focus(); -> causes IE to scroll always (strict and quirks mode) to the top the Iframe 
			// if we fire the event manually and let the browser handle the focusing, the latest  
			// cursor position is focused like in FF                         
			this.iframe.fireEvent('onfocus', document.createEventObject()); // createEventObject only in IE 
//		}else{
// TODO: should we throw here?
//			console.debug("Have no idea how to focus into the editor!");
		}
	},

//		_lastUpdate: 0,
	updateInterval: 200,
	_updateTimer: null,
	onDisplayChanged: function(/*Event*/e){
		// summary:
		//		This event will be fired everytime the display context
		//		changes and the result needs to be reflected in the UI.
		// description:
		//		If you don't want to have update too often,
		//		onNormalizedDisplayChanged should be used instead

//			var _t=new Date();
		if(!this._updateTimer){
//				this._lastUpdate=_t;
			if(this._updateTimer){
				clearTimeout(this._updateTimer);
			}
			this._updateTimer=setTimeout(dojo.hitch(this,this.onNormalizedDisplayChanged),this.updateInterval);
		}
	},
	onNormalizedDisplayChanged: function(){
		// summary:
		//		This event is fired every updateInterval ms or more
		// description:
		//		If something needs to happen immidiately after a
		//		user change, please use onDisplayChanged instead
		this._updateTimer=null;
	},
	onChange: function(newContent){
		// summary:
		//		this is fired if and only if the editor loses focus and
		//		the content is changed

//			console.log('onChange',newContent);
	},
	_normalizeCommand: function(/*String*/cmd){
		// summary:
		//		Used as the advice function by dojo.connect to map our
		//		normalized set of commands to those supported by the target
		//		browser

		var command = cmd.toLowerCase();
		if(command == "hilitecolor" && !dojo.isMoz){
			command = "backcolor";
		}

		return command;
	},

	queryCommandAvailable: function(/*String*/command){
		// summary:
		//		Tests whether a command is supported by the host. Clients SHOULD check
		//		whether a command is supported before attempting to use it, behaviour
		//		for unsupported commands is undefined.
		// command: The command to test for
		var ie = 1;
		var mozilla = 1 << 1;
		var safari = 1 << 2;
		var opera = 1 << 3;
		var safari420 = 1 << 4;

		var gt420 = dojo.isSafari;

		function isSupportedBy(browsers){
			return {
				ie: Boolean(browsers & ie),
				mozilla: Boolean(browsers & mozilla),
				safari: Boolean(browsers & safari),
				safari420: Boolean(browsers & safari420),
				opera: Boolean(browsers & opera)
			}
		}

		var supportedBy = null;

		switch(command.toLowerCase()){
			case "bold": case "italic": case "underline":
			case "subscript": case "superscript":
			case "fontname": case "fontsize":
			case "forecolor": case "hilitecolor":
			case "justifycenter": case "justifyfull": case "justifyleft":
			case "justifyright": case "delete": case "selectall": case "toggledir":
				supportedBy = isSupportedBy(mozilla | ie | safari | opera);
				break;

			case "createlink": case "unlink": case "removeformat":
			case "inserthorizontalrule": case "insertimage":
			case "insertorderedlist": case "insertunorderedlist":
			case "indent": case "outdent": case "formatblock":
			case "inserthtml": case "undo": case "redo": case "strikethrough":
				supportedBy = isSupportedBy(mozilla | ie | opera | safari420);
				break;

			case "blockdirltr": case "blockdirrtl":
			case "dirltr": case "dirrtl":
			case "inlinedirltr": case "inlinedirrtl":
				supportedBy = isSupportedBy(ie);
				break;
			case "cut": case "copy": case "paste":
				supportedBy = isSupportedBy( ie | mozilla | safari420);
				break;

			case "inserttable":
				supportedBy = isSupportedBy(mozilla | ie);
				break;

			case "insertcell": case "insertcol": case "insertrow":
			case "deletecells": case "deletecols": case "deleterows":
			case "mergecells": case "splitcell":
				supportedBy = isSupportedBy(ie | mozilla);
				break;

			default: return false;
		}

		return (dojo.isIE && supportedBy.ie) ||
			(dojo.isMoz && supportedBy.mozilla) ||
			(dojo.isSafari && supportedBy.safari) ||
			(gt420 && supportedBy.safari420) ||
			(dojo.isOpera && supportedBy.opera);  // Boolean return true if the command is supported, false otherwise
	},

	execCommand: function(/*String*/command, argument){
		// summary: Executes a command in the Rich Text area
		// command: The command to execute
		// argument: An optional argument to the command
		var returnValue;

		//focus() is required for IE to work
		//In addition, focus() makes sure after the execution of
		//the command, the editor receives the focus as expected
		this.focus();

		command = this._normalizeCommand(command);
		if(argument != undefined){
			if(command == "heading"){
				throw new Error("unimplemented");
			}else if((command == "formatblock") && dojo.isIE){
				argument = '<'+argument+'>';
			}
		}
		if(command == "inserthtml"){
			argument=this._preFilterContent(argument);
			if(dojo.isIE){
				var insertRange = this.document.selection.createRange();
				if(this.document.selection.type.toUpperCase()=='CONTROL'){
					var n=insertRange.item(0);
					while(insertRange.length){
						insertRange.remove(insertRange.item(0));
					}
					n.outerHTML=argument;
				}else{
					insertRange.pasteHTML(argument);
				}
				insertRange.select();
				//insertRange.collapse(true);
				returnValue=true;
			}else if(dojo.isMoz && !argument.length){
				//mozilla can not inserthtml an empty html to delete current selection
				//so we delete the selection instead in this case
				dojo.withGlobal(this.window,'remove',dijit._editor.selection);
				returnValue=true;
			}else{
				returnValue=this.document.execCommand(command, false, argument);
			}
		}else if(
			(command == "unlink")&&
			(this.queryCommandEnabled("unlink"))&&
			(dojo.isMoz || dojo.isSafari)
		){
			// fix up unlink in Mozilla to unlink the link and not just the selection

			// grab selection
			// Mozilla gets upset if we just store the range so we have to
			// get the basic properties and recreate to save the selection
			var selection = this.window.getSelection();
			//	var selectionRange = selection.getRangeAt(0);
			//	var selectionStartContainer = selectionRange.startContainer;
			//	var selectionStartOffset = selectionRange.startOffset;
			//	var selectionEndContainer = selectionRange.endContainer;
			//	var selectionEndOffset = selectionRange.endOffset;

			// select our link and unlink
			var a = dojo.withGlobal(this.window, "getAncestorElement",dijit._editor.selection, ['a']);
			dojo.withGlobal(this.window, "selectElement", dijit._editor.selection, [a]);

			returnValue=this.document.execCommand("unlink", false, null);
		}else if((command == "hilitecolor")&&(dojo.isMoz)){
//				// mozilla doesn't support hilitecolor properly when useCSS is
//				// set to false (bugzilla #279330)

			this.document.execCommand("styleWithCSS", false, true);
			returnValue = this.document.execCommand(command, false, argument);
			this.document.execCommand("styleWithCSS", false, false);

		}else if((dojo.isIE)&&( (command == "backcolor")||(command == "forecolor") )){
			// Tested under IE 6 XP2, no problem here, comment out
			// IE weirdly collapses ranges when we exec these commands, so prevent it
//				var tr = this.document.selection.createRange();
			argument = arguments.length > 1 ? argument : null;
			returnValue = this.document.execCommand(command, false, argument);

			// timeout is workaround for weird IE behavior were the text
			// selection gets correctly re-created, but subsequent input
			// apparently isn't bound to it
//				setTimeout(function(){tr.select();}, 1);
		}else{
			argument = arguments.length > 1 ? argument : null;
//				if(dojo.isMoz){
//					this.document = this.iframe.contentWindow.document
//				}

			if(argument || command!="createlink"){
				returnValue = this.document.execCommand(command, false, argument);
			}
		}

		this.onDisplayChanged();
		return returnValue;
	},

	queryCommandEnabled: function(/*String*/command){
		// summary: check whether a command is enabled or not

		if(this.disabled){ return false; }
		command = this._normalizeCommand(command);
		if(dojo.isMoz || dojo.isSafari){
			if(command == "unlink"){ // mozilla returns true always
				// console.debug(dojo.withGlobal(this.window, "hasAncestorElement",dijit._editor.selection, ['a']));
				return dojo.withGlobal(this.window, "hasAncestorElement",dijit._editor.selection, ['a']);
			}else if(command == "inserttable"){
				return true;
			}
		}
		//see #4109
		if(dojo.isSafari){
			if(command == "copy"){
				command = "cut";
			}else if(command == "paste"){
				return true;
			}
		}

		// return this.document.queryCommandEnabled(command);
		var elem = dojo.isIE ? this.document.selection.createRange() : this.document;
		return elem.queryCommandEnabled(command);
	},

	queryCommandState: function(command){
		// summary: check the state of a given command

		if(this.disabled){ return false; }
		command = this._normalizeCommand(command);
		return this.document.queryCommandState(command);
	},

	queryCommandValue: function(command){
		// summary: check the value of a given command

		if(this.disabled){ return false; }
		command = this._normalizeCommand(command);
		if(dojo.isIE && command == "formatblock"){
			return this._local2NativeFormatNames[this.document.queryCommandValue(command)];
		}
		return this.document.queryCommandValue(command);
	},

	// Misc.

	placeCursorAtStart: function(){
		// summary:
		//		place the cursor at the start of the editing area
		this.focus();

		//see comments in placeCursorAtEnd
		var isvalid=false;
		if(dojo.isMoz){
			var first=this.editNode.firstChild;
			while(first){
				if(first.nodeType == 3){
					if(first.nodeValue.replace(/^\s+|\s+$/g, "").length>0){
						isvalid=true;
						dojo.withGlobal(this.window, "selectElement", dijit._editor.selection, [first]);
						break;
					}
				}else if(first.nodeType == 1){
					isvalid=true;
					dojo.withGlobal(this.window, "selectElementChildren",dijit._editor.selection, [first]);
					break;
				}
				first = first.nextSibling;
			}
		}else{
			isvalid=true;
			dojo.withGlobal(this.window, "selectElementChildren",dijit._editor.selection, [this.editNode]);
		}
		if(isvalid){
			dojo.withGlobal(this.window, "collapse", dijit._editor.selection, [true]);
		}
	},

	placeCursorAtEnd: function(){
		// summary:
		//		place the cursor at the end of the editing area
		this.focus();

		//In mozilla, if last child is not a text node, we have to use selectElementChildren on this.editNode.lastChild
		//otherwise the cursor would be placed at the end of the closing tag of this.editNode.lastChild
		var isvalid=false;
		if(dojo.isMoz){
			var last=this.editNode.lastChild;
			while(last){
				if(last.nodeType == 3){
					if(last.nodeValue.replace(/^\s+|\s+$/g, "").length>0){
						isvalid=true;
						dojo.withGlobal(this.window, "selectElement",dijit._editor.selection, [last]);
						break;
					}
				}else if(last.nodeType == 1){
					isvalid=true;
					if(last.lastChild){
						dojo.withGlobal(this.window, "selectElement",dijit._editor.selection, [last.lastChild]);
					}else{
						dojo.withGlobal(this.window, "selectElement",dijit._editor.selection, [last]);
					}
					break;
				}
				last = last.previousSibling;
			}
		}else{
			isvalid=true;
			dojo.withGlobal(this.window, "selectElementChildren",dijit._editor.selection, [this.editNode]);
		}
		if(isvalid){
			dojo.withGlobal(this.window, "collapse", dijit._editor.selection, [false]);
		}
	},

	getValue: function(/*Boolean?*/nonDestructive){
		// summary:
		//		return the current content of the editing area (post filters are applied)
		if(this.textarea){
			if(this.isClosed || !this.isLoaded){
				return this.textarea.value;
			}
		}

		return this._postFilterContent(null, nonDestructive);
	},

	setValue: function(/*String*/html){
		// summary:
		//		this function set the content. No undo history is preserved

		if(!this.isLoaded){
			// try again after the editor is finished loading
			this.onLoadDeferred.addCallback(dojo.hitch(this, function(){
				this.setValue(html);
			}));
			return;
		}

		if(this.textarea && (this.isClosed || !this.isLoaded)){
			this.textarea.value=html;
		}else{
			html = this._preFilterContent(html);
			var node = this.isClosed ? this.domNode : this.editNode;
			node.innerHTML = html;
			this._preDomFilterContent(node);
		}

		this.onDisplayChanged();
	},

	replaceValue: function(/*String*/html){
		// summary:
		//		this function set the content while trying to maintain the undo stack
		//		(now only works fine with Moz, this is identical to setValue in all
		//		other browsers)
		if(this.isClosed){
			this.setValue(html);
		}else if(this.window && this.window.getSelection && !dojo.isMoz){ // Safari
			// look ma! it's a totally f'd browser!
			this.setValue(html);
		}else if(this.window && this.window.getSelection){ // Moz
			html = this._preFilterContent(html);
			this.execCommand("selectall");
			if(dojo.isMoz && !html){ html = "&nbsp;" }
			this.execCommand("inserthtml", html);
			this._preDomFilterContent(this.editNode);
		}else if(this.document && this.document.selection){//IE
			//In IE, when the first element is not a text node, say
			//an <a> tag, when replacing the content of the editing
			//area, the <a> tag will be around all the content
			//so for now, use setValue for IE too
			this.setValue(html);
		}
	},

	_preFilterContent: function(/*String*/html){
		// summary:
		//		filter the input before setting the content of the editing area
		var ec = html;
		dojo.forEach(this.contentPreFilters, function(ef){ if(ef){ ec = ef(ec); } });
		return ec;
	},
	_preDomFilterContent: function(/*DomNode*/dom){
		// summary:
		//		filter the input
		dom = dom || this.editNode;
		dojo.forEach(this.contentDomPreFilters, function(ef){
			if(ef && dojo.isFunction(ef)){
				ef(dom);
			}
		}, this);
	},

	_postFilterContent: function(/*DomNode|DomNode[]|String?*/dom,/*Boolean?*/nonDestructive){
		// summary:
		//		filter the output after getting the content of the editing area
		var ec;
		if(!dojo.isString(dom)){
			dom = dom || this.editNode;
			if(this.contentDomPostFilters.length){
				if(nonDestructive && dom['cloneNode']){
					dom = dom.cloneNode(true);
				}
				dojo.forEach(this.contentDomPostFilters, function(ef){
					dom = ef(dom);
				});
			}
			ec = dijit._editor.getChildrenHtml(dom);
		}else{
			ec = dom;
		}
		
		if(!ec.replace(/^(?:\s|\xA0)+/g, "").replace(/(?:\s|\xA0)+$/g,"").length){ ec = ""; }

		//	if(dojo.isIE){
		//		//removing appended <P>&nbsp;</P> for IE
		//		ec = ec.replace(/(?:<p>&nbsp;</p>[\n\r]*)+$/i,"");
		//	}
		dojo.forEach(this.contentPostFilters, function(ef){
			ec = ef(ec);
		});

		return ec;
	},

	_saveContent: function(/*Event*/e){
		// summary:
		//		Saves the content in an onunload event if the editor has not been closed
		var saveTextarea = dojo.byId(dijit._scopeName + "._editor.RichText.savedContent");
		saveTextarea.value += this._SEPARATOR + this.name + ":" + this.getValue();
	},

	escapeXml: function(/*String*/str, /*Boolean*/noSingleQuotes){
		dojo.deprecated('dijit.Editor::escapeXml is deprecated','use dijit._editor.escapeXml instead', 2);
		return dijit._editor.escapeXml(str,noSingleQuotes);
	},

	getNodeHtml: function(/* DomNode */node){
		dojo.deprecated('dijit.Editor::getNodeHtml is deprecated','use dijit._editor.getNodeHtml instead', 2);
		return dijit._editor.getNodeHtml(node);
	},

	getNodeChildrenHtml: function(/* DomNode */dom){
		dojo.deprecated('dijit.Editor::getNodeChildrenHtml is deprecated','use dijit._editor.getChildrenHtml instead', 2);
		return dijit._editor.getChildrenHtml(dom);
	},

	close: function(/*Boolean*/save, /*Boolean*/force){
		// summary:
		//		Kills the editor and optionally writes back the modified contents to the
		//		element from which it originated.
		// save:
		//		Whether or not to save the changes. If false, the changes are discarded.
		// force:
		if(this.isClosed){return false; }

		if(!arguments.length){ save = true; }
		this._content = this.getValue();
		var changed = (this.savedContent != this._content);

		// line height is squashed for iframes
		// FIXME: why was this here? if(this.iframe){ this.domNode.style.lineHeight = null; }

		if(this.interval){ clearInterval(this.interval); }

		if(this.textarea){
			with(this.textarea.style){
				position = "";
				left = top = "";
				if(dojo.isIE){
					overflow = this.__overflow;
					this.__overflow = null;
				}
			}
			this.textarea.value = save ? this._content : this.savedContent;
			dojo._destroyElement(this.domNode);
			this.domNode = this.textarea;
		}else{
//			if(save){
				//why we treat moz differently? comment out to fix #1061
//					if(dojo.isMoz){
//						var nc = dojo.doc.createElement("span");
//						this.domNode.appendChild(nc);
//						nc.innerHTML = this.editNode.innerHTML;
//					}else{
//						this.domNode.innerHTML = this._content;
//					}
//			}
			this.domNode.innerHTML = save ? this._content : this.savedContent;
		}

		dojo.removeClass(this.domNode, "RichTextEditable");
		this.isClosed = true;
		this.isLoaded = false;
		// FIXME: is this always the right thing to do?
		delete this.editNode;

		if(this.window && this.window._frameElement){
			this.window._frameElement = null;
		}

		this.window = null;
		this.document = null;
		this.editingArea = null;
		this.editorObject = null;

		return changed; // Boolean: whether the content has been modified
	},

	destroyRendering: function(){
		// summary: stub	
	}, 

	destroy: function(){
		this.destroyRendering();
		if(!this.isClosed){ this.close(false); }
		this.inherited("destroy",arguments);
		//dijit._editor.RichText.superclass.destroy.call(this);
	},

	_removeMozBogus: function(/* String */ html){
		return html.replace(/\stype="_moz"/gi, '').replace(/\s_moz_dirty=""/gi, ''); // String
	},
	_removeSafariBogus: function(/* String */ html){
		return html.replace(/\sclass="webkit-block-placeholder"/gi, ''); // String
	},
	_fixContentForMoz: function(/* String */ html){
		// summary:
		//		Moz can not handle strong/em tags correctly, convert them to b/i
		return html.replace(/<(\/)?strong([ \>])/gi, '<$1b$2')
			.replace(/<(\/)?em([ \>])/gi, '<$1i$2' ); // String
	},

	_srcInImgRegex	: /(?:(<img(?=\s).*?\ssrc=)("|')(.*?)\2)|(?:(<img\s.*?src=)([^"'][^ >]+))/gi ,
	_hrefInARegex	: /(?:(<a(?=\s).*?\shref=)("|')(.*?)\2)|(?:(<a\s.*?href=)([^"'][^ >]+))/gi ,

	_preFixUrlAttributes: function(/* String */ html){
		return html.replace(this._hrefInARegex, '$1$4$2$3$5$2 _djrealurl=$2$3$5$2')
			.replace(this._srcInImgRegex, '$1$4$2$3$5$2 _djrealurl=$2$3$5$2'); // String
	}
});

}

if(!dojo._hasResource["dijit.Toolbar"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dijit.Toolbar"] = true;
dojo.provide("dijit.Toolbar");





dojo.declare("dijit.Toolbar",
	[dijit._Widget, dijit._Templated, dijit._KeyNavContainer],
	{
	// summary: A Toolbar widget, used to hold things like dijit.Editor buttons

	templateString:
		'<div class="dijit dijitToolbar" waiRole="toolbar" tabIndex="${tabIndex}" dojoAttachPoint="containerNode">' +
		//	'<table style="table-layout: fixed" class="dijitReset dijitToolbarTable">' + // factor out style
		//		'<tr class="dijitReset" dojoAttachPoint="containerNode"></tr>'+
		//	'</table>' +
		'</div>',

	tabIndex: "0",

	postCreate: function(){
		this.connectKeyNavHandlers(
			this.isLeftToRight() ? [dojo.keys.LEFT_ARROW] : [dojo.keys.RIGHT_ARROW],
			this.isLeftToRight() ? [dojo.keys.RIGHT_ARROW] : [dojo.keys.LEFT_ARROW]
		);
	},

	startup: function(){
		if(this._started){ return; }

		this.startupKeyNavChildren();

		this.inherited(arguments);
	}
}
);

// Combine with dijit.MenuSeparator??
dojo.declare("dijit.ToolbarSeparator",
	[ dijit._Widget, dijit._Templated ],
	{
	// summary: A spacer between two Toolbar items
	templateString: '<div class="dijitToolbarSeparator dijitInline"></div>',
	postCreate: function(){ dojo.setSelectable(this.domNode, false); },
	isFocusable: function(){ 
		// summary: This widget isn't focusable, so pass along that fact.
		return false; 
	}

});

}

if(!dojo._hasResource["dijit._editor._Plugin"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dijit._editor._Plugin"] = true;
dojo.provide("dijit._editor._Plugin");




dojo.declare("dijit._editor._Plugin", null, {
	// summary
	//		This represents a "plugin" to the editor, which is basically
	//		a single button on the Toolbar and some associated code
	constructor: function(/*Object?*/args, /*DomNode?*/node){
		if(args){
			dojo.mixin(this, args);
		}
		this._connects=[];
	},

	editor: null,
	iconClassPrefix: "dijitEditorIcon",
	button: null,
	queryCommand: null,
	command: "",
	commandArg: null,
	useDefaultCommand: true,
	buttonClass: dijit.form.Button,
	getLabel: function(key){
		return this.editor.commands[key];
	},
	_initButton: function(props){
		if(this.command.length){
			var label = this.getLabel(this.command);
			var className = this.iconClassPrefix+" "+this.iconClassPrefix + this.command.charAt(0).toUpperCase() + this.command.substr(1);
			if(!this.button){
				props = dojo.mixin({
					label: label,
					showLabel: false,
					iconClass: className,
					dropDown: this.dropDown,
					tabIndex: "-1"
				}, props || {});
				this.button = new this.buttonClass(props);
			}
		}
	},
	destroy: function(f){
		dojo.forEach(this._connects, dojo.disconnect);
	},
	connect: function(o, f, tf){
		this._connects.push(dojo.connect(o, f, this, tf));
	},
	updateState: function(){
		var _e = this.editor;
		var _c = this.command;
		if(!_e){ return; }
		if(!_e.isLoaded){ return; }
		if(!_c.length){ return; }
		if(this.button){
			try{
				var enabled = _e.queryCommandEnabled(_c);
				this.button.setAttribute('disabled', !enabled);
				if(typeof this.button.checked == 'boolean'){
					this.button.setAttribute('checked', _e.queryCommandState(_c));
				}
			}catch(e){
				console.debug(e);
			}
		}
	},
	setEditor: function(/*Widget*/editor){
		// FIXME: detatch from previous editor!!
		this.editor = editor;

		// FIXME: prevent creating this if we don't need to (i.e., editor can't handle our command)
		this._initButton();

		// FIXME: wire up editor to button here!
		if(this.command.length &&
			!this.editor.queryCommandAvailable(this.command)
		){
			// console.debug("hiding:", this.command);
			if(this.button){
				this.button.domNode.style.display = "none";
			}
		}
		if(this.button && this.useDefaultCommand){
			this.connect(this.button, "onClick",
				dojo.hitch(this.editor, "execCommand", this.command, this.commandArg)
			);
		}
		this.connect(this.editor, "onNormalizedDisplayChanged", "updateState");
	},
	setToolbar: function(/*Widget*/toolbar){
		if(this.button){
			toolbar.addChild(this.button);
		}
		// console.debug("adding", this.button, "to:", toolbar);
	}
});

}

if(!dojo._hasResource["dijit.Editor"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dijit.Editor"] = true;
dojo.provide("dijit.Editor");







dojo.declare(
	"dijit.Editor",
	dijit._editor.RichText,
	{
	// summary: A rich-text Editing widget

		// plugins: Array
		//		a list of plugin names (as strings) or instances (as objects)
		//		for this widget.
		plugins: null,

		// extraPlugins: Array
		//		a list of extra plugin names which will be appended to plugins array
		extraPlugins: null,

		constructor: function(){
			if(!dojo.isArray(this.plugins)){
				this.plugins=["undo","redo","|","cut","copy","paste","|","bold","italic","underline","strikethrough","|",
				"insertOrderedList","insertUnorderedList","indent","outdent","|","justifyLeft","justifyRight","justifyCenter","justifyFull"/*"createLink"*/];
			}

			this._plugins=[];
			this._editInterval = this.editActionInterval * 1000;
		},

		postCreate: function(){
			//for custom undo/redo
			if(this.customUndo){
				dojo['require']("dijit._editor.range");
				this._steps=this._steps.slice(0);
				this._undoedSteps=this._undoedSteps.slice(0);
//				this.addKeyHandler('z',this.KEY_CTRL,this.undo);
//				this.addKeyHandler('y',this.KEY_CTRL,this.redo);
			}
			if(dojo.isArray(this.extraPlugins)){
				this.plugins=this.plugins.concat(this.extraPlugins);
			}

//			try{
			this.inherited(arguments);
//			dijit.Editor.superclass.postCreate.apply(this, arguments);

			this.commands = dojo.i18n.getLocalization("dijit._editor", "commands", this.lang);

			if(!this.toolbar){
				// if we haven't been assigned a toolbar, create one
				this.toolbar = new dijit.Toolbar({});
				dojo.place(this.toolbar.domNode, this.editingArea, "before");
			}

			dojo.forEach(this.plugins, this.addPlugin, this);
			this.onNormalizedDisplayChanged(); //update toolbar button status
//			}catch(e){ console.debug(e); }
		},
		destroy: function(){
			dojo.forEach(this._plugins, function(p){
				if(p && p.destroy){
					p.destroy();
				}
			});
			this._plugins=[];
			this.toolbar.destroy(); delete this.toolbar;
			this.inherited(arguments);
		},
		addPlugin: function(/*String||Object*/plugin, /*Integer?*/index){
			//	summary:
			//		takes a plugin name as a string or a plugin instance and
			//		adds it to the toolbar and associates it with this editor
			//		instance. The resulting plugin is added to the Editor's
			//		plugins array. If index is passed, it's placed in the plugins
			//		array at that index. No big magic, but a nice helper for
			//		passing in plugin names via markup.
			//	plugin: String, args object or plugin instance. Required.
			//	args: This object will be passed to the plugin constructor.
			//	index:	
			//		Integer, optional. Used when creating an instance from
			//		something already in this.plugins. Ensures that the new
			//		instance is assigned to this.plugins at that index.
			var args=dojo.isString(plugin)?{name:plugin}:plugin;
			if(!args.setEditor){
				var o={"args":args,"plugin":null,"editor":this};
				dojo.publish(dijit._scopeName + ".Editor.getPlugin",[o]);
				if(!o.plugin){
					var pc = dojo.getObject(args.name);
					if(pc){
						o.plugin=new pc(args);
					}
				}
				if(!o.plugin){
					console.warn('Cannot find plugin',plugin);
					return;
				}
				plugin=o.plugin;
			}
			if(arguments.length > 1){
				this._plugins[index] = plugin;
			}else{
				this._plugins.push(plugin);
			}
			plugin.setEditor(this);
			if(dojo.isFunction(plugin.setToolbar)){
				plugin.setToolbar(this.toolbar);
			}
		},
		/* beginning of custom undo/redo support */

		// customUndo: Boolean
		//		Whether we shall use custom undo/redo support instead of the native
		//		browser support. By default, we only enable customUndo for IE, as it
		//		has broken native undo/redo support. Note: the implementation does
		//		support other browsers which have W3C DOM2 Range API.
		customUndo: dojo.isIE,

		//	editActionInterval: Integer
		//		When using customUndo, not every keystroke will be saved as a step.
		//		Instead typing (including delete) will be grouped together: after
		//		a user stop typing for editActionInterval seconds, a step will be
		//		saved; if a user resume typing within editActionInterval seconds,
		//		the timeout will be restarted. By default, editActionInterval is 3
		//		seconds.
		editActionInterval: 3,
		beginEditing: function(cmd){
			if(!this._inEditing){
				this._inEditing=true;
				this._beginEditing(cmd);
			}
			if(this.editActionInterval>0){
				if(this._editTimer){
					clearTimeout(this._editTimer);
				}
				this._editTimer = setTimeout(dojo.hitch(this, this.endEditing), this._editInterval);
			}
		},
		_steps:[],
		_undoedSteps:[],
		execCommand: function(cmd){
			if(this.customUndo && (cmd=='undo' || cmd=='redo')){
				return this[cmd]();
			}else{
				try{
					if(this.customUndo){
						this.endEditing();
						this._beginEditing();
					}
					var r = this.inherited('execCommand',arguments);
					if(this.customUndo){
						this._endEditing();
					}
					return r;
				}catch(e){
					if(dojo.isMoz && /copy|cut|paste/.test(cmd)){
						// Warn user of platform limitation.  Cannot programmatically access keyboard. See ticket #4136
						var sub = dojo.string.substitute,
							accel = {cut:'X', copy:'C', paste:'V'},
							isMac = navigator.userAgent.indexOf("Macintosh") != -1;
						alert(sub(this.commands.systemShortcutFF,
							[this.commands[cmd], sub(this.commands[isMac ? 'appleKey' : 'ctrlKey'], [accel[cmd]])]));
					}
					return false;
				}
			}
		},
		queryCommandEnabled: function(cmd){
			if(this.customUndo && (cmd=='undo' || cmd=='redo')){
				return cmd=='undo'?(this._steps.length>1):(this._undoedSteps.length>0);
			}else{
				return this.inherited('queryCommandEnabled',arguments);
			}
		},
		_moveToBookmark: function(b){
			var bookmark=b;
			if(dojo.isIE){
				if(dojo.isArray(b)){//IE CONTROL
					bookmark=[];
					dojo.forEach(b,function(n){
						bookmark.push(dijit.range.getNode(n,this.editNode));
					},this);
				}
			}else{//w3c range
				var r=dijit.range.create();
				r.setStart(dijit.range.getNode(b.startContainer,this.editNode),b.startOffset);
				r.setEnd(dijit.range.getNode(b.endContainer,this.editNode),b.endOffset);
				bookmark=r;
			}
			dojo.withGlobal(this.window,'moveToBookmark',dijit,[bookmark]);
		},
		_changeToStep: function(from,to){
			this.setValue(to.text);
			var b=to.bookmark;
			if(!b){ return; }
			this._moveToBookmark(b);
		},
		undo: function(){
//			console.log('undo');
			this.endEditing(true);
			var s=this._steps.pop();
			if(this._steps.length>0){
				this.focus();
				this._changeToStep(s,this._steps[this._steps.length-1]);
				this._undoedSteps.push(s);
				this.onDisplayChanged();
				return true;
			}
			return false;
		},
		redo: function(){
//			console.log('redo');
			this.endEditing(true);
			var s=this._undoedSteps.pop();
			if(s && this._steps.length>0){
				this.focus();
				this._changeToStep(this._steps[this._steps.length-1],s);
				this._steps.push(s);
				this.onDisplayChanged();
				return true;
			}
			return false;
		},
		endEditing: function(ignore_caret){
			if(this._editTimer){
				clearTimeout(this._editTimer);
			}
			if(this._inEditing){
				this._endEditing(ignore_caret);
				this._inEditing=false;
			}
		},
		_getBookmark: function(){
			var b=dojo.withGlobal(this.window,dijit.getBookmark);
			var tmp=[];
			if(dojo.isIE){
				if(dojo.isArray(b)){//CONTROL
					dojo.forEach(b,function(n){
						tmp.push(dijit.range.getIndex(n,this.editNode).o);
					},this);
					b=tmp;
				}
			}else{//w3c range
				tmp=dijit.range.getIndex(b.startContainer,this.editNode).o;
				b={startContainer:tmp,
					startOffset:b.startOffset,
					endContainer:b.endContainer===b.startContainer?tmp:dijit.range.getIndex(b.endContainer,this.editNode).o,
					endOffset:b.endOffset};
			}
			return b;
		},
		_beginEditing: function(cmd){
			if(this._steps.length===0){
				this._steps.push({'text':this.savedContent,'bookmark':this._getBookmark()});
			}
		},
		_endEditing: function(ignore_caret){
			var v=this.getValue(true);

			this._undoedSteps=[];//clear undoed steps
			this._steps.push({text: v, bookmark: this._getBookmark()});
		},
		onKeyDown: function(e){
			if(!this.customUndo){
				this.inherited('onKeyDown',arguments);
				return;
			}
			var k = e.keyCode, ks = dojo.keys;
			if(e.ctrlKey && !e.altKey){//undo and redo only if the special right Alt + z/y are not pressed #5892
				if(k == 90 || k == 122){ //z
					dojo.stopEvent(e);
					this.undo();
					return;
				}else if(k == 89 || k == 121){ //y
					dojo.stopEvent(e);
					this.redo();
					return;
				}
			}
			this.inherited('onKeyDown',arguments);

			switch(k){
					case ks.ENTER:
					case ks.BACKSPACE:
					case ks.DELETE:
						this.beginEditing();
						break;
					case 88: //x
					case 86: //v
						if(e.ctrlKey && !e.altKey && !e.metaKey){
							this.endEditing();//end current typing step if any
							if(e.keyCode == 88){
								this.beginEditing('cut');
								//use timeout to trigger after the cut is complete
								setTimeout(dojo.hitch(this, this.endEditing), 1);
							}else{
								this.beginEditing('paste');
								//use timeout to trigger after the paste is complete
								setTimeout(dojo.hitch(this, this.endEditing), 1);
							}
							break;
						}
						//pass through
					default:
						if(!e.ctrlKey && !e.altKey && !e.metaKey && (e.keyCode<dojo.keys.F1 || e.keyCode>dojo.keys.F15)){
							this.beginEditing();
							break;
						}
						//pass through
					case ks.ALT:
						this.endEditing();
						break;
					case ks.UP_ARROW:
					case ks.DOWN_ARROW:
					case ks.LEFT_ARROW:
					case ks.RIGHT_ARROW:
					case ks.HOME:
					case ks.END:
					case ks.PAGE_UP:
					case ks.PAGE_DOWN:
						this.endEditing(true);
						break;
					//maybe ctrl+backspace/delete, so don't endEditing when ctrl is pressed
					case ks.CTRL:
					case ks.SHIFT:
					case ks.TAB:
						break;
				}	
		},
		_onBlur: function(){
			this.inherited('_onBlur',arguments);
			this.endEditing(true);
		},
		onClick: function(){
			this.endEditing(true);
			this.inherited('onClick',arguments);
		}
		/* end of custom undo/redo support */
	}
);

/* the following code is to registered a handler to get default plugins */
dojo.subscribe(dijit._scopeName + ".Editor.getPlugin",null,function(o){
	if(o.plugin){ return; }
	var args = o.args, p;
	var _p = dijit._editor._Plugin;
	var name = args.name;
	switch(name){
		case "undo": case "redo": case "cut": case "copy": case "paste": case "insertOrderedList":
		case "insertUnorderedList": case "indent": case "outdent": case "justifyCenter":
		case "justifyFull": case "justifyLeft": case "justifyRight": case "delete":
		case "selectAll": case "removeFormat":
		case "insertHorizontalRule":
			p = new _p({ command: name });
			break;

		case "bold": case "italic": case "underline": case "strikethrough":
		case "subscript": case "superscript":
			p = new _p({ buttonClass: dijit.form.ToggleButton, command: name });
			break;
		case "|":
			p = new _p({ button: new dijit.ToolbarSeparator() });
	}
//	console.log('name',name,p);
	o.plugin=p;
});

}

if(!dojo._hasResource["dojox.grid._data.dijitEditors"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojox.grid._data.dijitEditors"] = true;
dojo.provide("dojox.grid._data.dijitEditors");













dojo.declare("dojox.grid.editors.Dijit", dojox.grid.editors.base, {
	editorClass: "dijit.form.TextBox",
	constructor: function(inCell){
		this.editor = null;
		this.editorClass = dojo.getObject(this.cell.editorClass || this.editorClass);
	},
	format: function(inDatum, inRowIndex){
		this.needFormatNode(inDatum, inRowIndex);
		return "<div></div>";
	},
	getValue: function(inRowIndex){
		return this.editor.getValue();
	},
	setValue: function(inRowIndex, inValue){
		if(this.editor&&this.editor.setValue){
			this.editor.setValue(inValue);
		}else{
			this.inherited(arguments);
		}
	},
	getEditorProps: function(inDatum){
		return dojo.mixin({}, this.cell.editorProps||{}, {
			constraints: dojo.mixin({}, this.cell.constraint) || {}, //TODO: really just for ValidationTextBoxes
			value: inDatum
		});
	},
	createEditor: function(inNode, inDatum, inRowIndex){
		return new this.editorClass(this.getEditorProps(inDatum), inNode);

	},
	attachEditor: function(inNode, inDatum, inRowIndex){
		inNode.appendChild(this.editor.domNode);
		this.setValue(inRowIndex, inDatum);
	},
	formatNode: function(inNode, inDatum, inRowIndex){
		if(!this.editorClass){
			return inDatum;
		}
		if(!this.editor){
			this.editor = this.createEditor.apply(this, arguments);
		}else{
			this.attachEditor.apply(this, arguments);
		}
		this.sizeEditor.apply(this, arguments);
		this.cell.grid.rowHeightChanged(inRowIndex);
		this.focus();
	},
	sizeEditor: function(inNode, inDatum, inRowIndex){
		var
			p = this.cell.getNode(inRowIndex),
			box = dojo.contentBox(p);
		dojo.marginBox(this.editor.domNode, {w: box.w});
	},
	focus: function(inRowIndex, inNode){
		if(this.editor){
			setTimeout(dojo.hitch(this.editor, function(){
				dojox.grid.fire(this, "focus");
			}), 0);
		}
	},
	_finish: function(inRowIndex){
		this.inherited(arguments);
		dojox.grid.removeNode(this.editor.domNode);
	}
});

dojo.declare("dojox.grid.editors.ComboBox", dojox.grid.editors.Dijit, {
	editorClass: "dijit.form.ComboBox",
	getEditorProps: function(inDatum){
		var items=[];
		dojo.forEach(this.cell.options, function(o){
			items.push({name: o, value: o});
		});
		var store = new dojo.data.ItemFileReadStore({data: {identifier:"name", items: items}});
		return dojo.mixin({}, this.cell.editorProps||{}, {
			value: inDatum,
			store: store
		});
	},
	getValue: function(){
		var e = this.editor;
		// make sure to apply the displayed value
		e.setDisplayedValue(e.getDisplayedValue());
		return e.getValue();
	}
});

dojo.declare("dojox.grid.editors.DateTextBox", dojox.grid.editors.Dijit, {
	editorClass: "dijit.form.DateTextBox",
	setValue: function(inRowIndex, inValue){
		if(this.editor){
			this.editor.setValue(new Date(inValue));
		}else{
			this.inherited(arguments);
		}
	},
	getEditorProps: function(inDatum){
		return dojo.mixin(this.inherited(arguments), {
			value: new Date(inDatum)
		});
	}
});


dojo.declare("dojox.grid.editors.CheckBox", dojox.grid.editors.Dijit, {
	editorClass: "dijit.form.CheckBox",
	getValue: function(){
		return this.editor.checked;
	},
	setValue: function(inRowIndex, inValue){
		if(this.editor&&this.editor.setAttribute){
			this.editor.setAttribute("checked", inValue);
		}else{
			this.inherited(arguments);
		}
	},
	sizeEditor: function(inNode, inDatum, inRowIndex){
		return;
	}
});


dojo.declare("dojox.grid.editors.Editor", dojox.grid.editors.Dijit, {
	editorClass: "dijit.Editor",
	getEditorProps: function(inDatum){
		return dojo.mixin({}, this.cell.editorProps||{}, {
			height: this.cell.editorHeight || "100px"
		});
	},
	createEditor: function(inNode, inDatum, inRowIndex){
		// editor needs its value set after creation
		var editor = new this.editorClass(this.getEditorProps(inDatum), inNode);
		dojo.connect(editor, 'onLoad', dojo.hitch(this, 'populateEditor'));
		return editor;
	},
	formatNode: function(inNode, inDatum, inRowIndex){
		this.content = inDatum;
		this.inherited(arguments);
		if(dojo.isMoz){
			// FIXME: seem to need to reopen the editor and display the toolbar
			var e = this.editor;
			e.open();
			if(this.cell.editorToolbar){
				dojo.place(e.toolbar.domNode, e.editingArea, "before");
			}
		}
	},
	populateEditor: function(){
		this.editor.setValue(this.content);
		this.editor.placeCursorAtEnd();
	}
});

}

if(!dojo._hasResource["dojox.encoding.digests._base"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojox.encoding.digests._base"] = true;
dojo.provide("dojox.encoding.digests._base");

(function(){
	//TODO: see if it makes sense to meld this into one with the
	//	crypto base enums
	var d=dojox.encoding.digests;
	d.outputTypes={ 
		//	summary
		//	Enumeration for input and output encodings.
		Base64:0, Hex:1, String:2, Raw:3 
	};

	//	word-based addition
	d.addWords=function(/* word */a, /* word */b){
		//	summary
		//	add a pair of words together with rollover
		var l=(a&0xFFFF)+(b&0xFFFF);
		var m=(a>>16)+(b>>16)+(l>>16);
		return (m<<16)|(l&0xFFFF);	//	word
	};

	//	word-based conversion method, for efficiency sake;
	//	most digests operate on words, and this should be faster
	//	than the encoding version (which works on bytes).
	var chrsz=8;
	var mask=(1<<chrsz)-1;
	
	d.stringToWord=function(/* string */s){
		//	summary
		//	convert a string to a word array
		var wa=[];
		for(var i=0, l=s.length*chrsz; i<l; i+=chrsz){
			wa[i>>5]|=(s.charCodeAt(i/chrsz)&mask)<<(i%32);
		}
		return wa;	//	word[]
	};
	
	d.wordToString=function(/* word[] */wa){
		//	summary
		//	convert an array of words to a string
		var s=[];
		for(var i=0, l=wa.length*32; i<l; i+=chrsz){
			s.push(String.fromCharCode((wa[i>>5]>>>(i%32))&mask));
		}
		return s.join("");	//	string
	}
	
	d.wordToHex=function(/* word[] */wa){
		//	summary
		//	convert an array of words to a hex tab
		var h="0123456789abcdef", s=[];
		for(var i=0, l=wa.length*4; i<l; i++){
			s.push(h.charAt((wa[i>>2]>>((i%4)*8+4))&0xF)+h.charAt((wa[i>>2]>>((i%4)*8))&0xF));
		}
		return s.join("");	//	string
	}
	d.wordToBase64=function(/* word[] */wa){
		//	summary
		//	convert an array of words to base64 encoding, should be more efficient
		//		than using dojox.encoding.base64
		var p="=", tab="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/", s=[];
		for(var i=0, l=wa.length*4; i<l; i+=3){
			var t=(((wa[i>>2]>>8*(i%4))&0xFF)<<16)|(((wa[i+1>>2]>>8*((i+1)%4))&0xFF)<<8)|((wa[i+2>>2]>>8*((i+2)%4))&0xFF);
			for(var j=0; j<4; j++){
				if(i*8+j*6>wa.length*32){
					s.push(p);
				} else {
					s.push(tab.charAt((t>>6*(3-j))&0x3F));
				}
			}
		}
		return s.join("");	//	string
	};
})();

}

if(!dojo._hasResource["dojox.encoding.digests.MD5"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojox.encoding.digests.MD5"] = true;
dojo.provide("dojox.encoding.digests.MD5");



/*	A port of Paul Johnstone's MD5 implementation
 *	http://pajhome.org.uk/crypt/md5/index.html
 *
 *	Copyright (C) Paul Johnston 1999 - 2002.
 *	Other contributors: Greg Holt, Andrew Kepert, Ydnar, Lostinet
 * 	Distributed under the BSD License
 *
 *	Dojo port by Tom Trenka
 */
(function(){
	var dxd=dojox.encoding.digests;
	var chrsz=8;

	//	MD5 rounds functions
	function R(n,c){ return (n<<c)|(n>>>(32-c)); }
	function C(q,a,b,x,s,t){ return dxd.addWords(R(dxd.addWords(dxd.addWords(a, q), dxd.addWords(x, t)), s), b); }
	function FF(a,b,c,d,x,s,t){ return C((b&c)|((~b)&d),a,b,x,s,t); }
	function GG(a,b,c,d,x,s,t){ return C((b&d)|(c&(~d)),a,b,x,s,t); }
	function HH(a,b,c,d,x,s,t){ return C(b^c^d,a,b,x,s,t); }
	function II(a,b,c,d,x,s,t){ return C(c^(b|(~d)),a,b,x,s,t); }

	//	the core MD5 rounds method
	function core(x,len){
		x[len>>5]|=0x80<<((len)%32);
		x[(((len+64)>>>9)<<4)+14]=len;
		var a= 1732584193;
		var b=-271733879;
		var c=-1732584194;
		var d= 271733878;
		for(var i=0; i<x.length; i+=16){
			var olda=a;
			var oldb=b;
			var oldc=c;
			var oldd=d;

			a=FF(a,b,c,d,x[i+ 0],7 ,-680876936);
			d=FF(d,a,b,c,x[i+ 1],12,-389564586);
			c=FF(c,d,a,b,x[i+ 2],17, 606105819);
			b=FF(b,c,d,a,x[i+ 3],22,-1044525330);
			a=FF(a,b,c,d,x[i+ 4],7 ,-176418897);
			d=FF(d,a,b,c,x[i+ 5],12, 1200080426);
			c=FF(c,d,a,b,x[i+ 6],17,-1473231341);
			b=FF(b,c,d,a,x[i+ 7],22,-45705983);
			a=FF(a,b,c,d,x[i+ 8],7 , 1770035416);
			d=FF(d,a,b,c,x[i+ 9],12,-1958414417);
			c=FF(c,d,a,b,x[i+10],17,-42063);
			b=FF(b,c,d,a,x[i+11],22,-1990404162);
			a=FF(a,b,c,d,x[i+12],7 , 1804603682);
			d=FF(d,a,b,c,x[i+13],12,-40341101);
			c=FF(c,d,a,b,x[i+14],17,-1502002290);
			b=FF(b,c,d,a,x[i+15],22, 1236535329);

			a=GG(a,b,c,d,x[i+ 1],5 ,-165796510);
			d=GG(d,a,b,c,x[i+ 6],9 ,-1069501632);
			c=GG(c,d,a,b,x[i+11],14, 643717713);
			b=GG(b,c,d,a,x[i+ 0],20,-373897302);
			a=GG(a,b,c,d,x[i+ 5],5 ,-701558691);
			d=GG(d,a,b,c,x[i+10],9 , 38016083);
			c=GG(c,d,a,b,x[i+15],14,-660478335);
			b=GG(b,c,d,a,x[i+ 4],20,-405537848);
			a=GG(a,b,c,d,x[i+ 9],5 , 568446438);
			d=GG(d,a,b,c,x[i+14],9 ,-1019803690);
			c=GG(c,d,a,b,x[i+ 3],14,-187363961);
			b=GG(b,c,d,a,x[i+ 8],20, 1163531501);
			a=GG(a,b,c,d,x[i+13],5 ,-1444681467);
			d=GG(d,a,b,c,x[i+ 2],9 ,-51403784);
			c=GG(c,d,a,b,x[i+ 7],14, 1735328473);
			b=GG(b,c,d,a,x[i+12],20,-1926607734);

			a=HH(a,b,c,d,x[i+ 5],4 ,-378558);
			d=HH(d,a,b,c,x[i+ 8],11,-2022574463);
			c=HH(c,d,a,b,x[i+11],16, 1839030562);
			b=HH(b,c,d,a,x[i+14],23,-35309556);
			a=HH(a,b,c,d,x[i+ 1],4 ,-1530992060);
			d=HH(d,a,b,c,x[i+ 4],11, 1272893353);
			c=HH(c,d,a,b,x[i+ 7],16,-155497632);
			b=HH(b,c,d,a,x[i+10],23,-1094730640);
			a=HH(a,b,c,d,x[i+13],4 , 681279174);
			d=HH(d,a,b,c,x[i+ 0],11,-358537222);
			c=HH(c,d,a,b,x[i+ 3],16,-722521979);
			b=HH(b,c,d,a,x[i+ 6],23, 76029189);
			a=HH(a,b,c,d,x[i+ 9],4 ,-640364487);
			d=HH(d,a,b,c,x[i+12],11,-421815835);
			c=HH(c,d,a,b,x[i+15],16, 530742520);
			b=HH(b,c,d,a,x[i+ 2],23,-995338651);

			a=II(a,b,c,d,x[i+ 0],6 ,-198630844);
			d=II(d,a,b,c,x[i+ 7],10, 1126891415);
			c=II(c,d,a,b,x[i+14],15,-1416354905);
			b=II(b,c,d,a,x[i+ 5],21,-57434055);
			a=II(a,b,c,d,x[i+12],6 , 1700485571);
			d=II(d,a,b,c,x[i+ 3],10,-1894986606);
			c=II(c,d,a,b,x[i+10],15,-1051523);
			b=II(b,c,d,a,x[i+ 1],21,-2054922799);
			a=II(a,b,c,d,x[i+ 8],6 , 1873313359);
			d=II(d,a,b,c,x[i+15],10,-30611744);
			c=II(c,d,a,b,x[i+ 6],15,-1560198380);
			b=II(b,c,d,a,x[i+13],21, 1309151649);
			a=II(a,b,c,d,x[i+ 4],6 ,-145523070);
			d=II(d,a,b,c,x[i+11],10,-1120210379);
			c=II(c,d,a,b,x[i+ 2],15, 718787259);
			b=II(b,c,d,a,x[i+ 9],21,-343485551);

			a=dxd.addWords(a, olda);
			b=dxd.addWords(b, oldb);
			c=dxd.addWords(c, oldc);
			d=dxd.addWords(d, oldd);
		}
		return [a,b,c,d];
	}

	function hmac(data, key){
		var wa=dxd.stringToWord(key);
		if(wa.length>16){ 
			wa=core(wa, key.length*chrsz);
		}
		var l=[], r=[];
		for(var i=0; i<16; i++){
			l[i]=wa[i]^0x36363636;
			r[i]=wa[i]^0x5c5c5c5c;
		}
		var h=core(l.concat(dxd.stringToWord(data)), 512+data.length*chrsz);
		return core(r.concat(h), 640);
	}

	//	public function
	dxd.MD5=function(/* string */data, /* dojox.encoding.digests.outputTypes? */outputType){
		//	summary
		//	computes the digest of data, and returns the result according to type outputType
		var out=outputType || dxd.outputTypes.Base64;
		var wa=core(dxd.stringToWord(data), data.length*chrsz);
		switch(out){
			case dxd.outputTypes.Raw:{
				return wa;	//	word[]
			}
			case dxd.outputTypes.Hex:{
				return dxd.wordToHex(wa);	//	string
			}
			case dxd.outputTypes.String:{
				return dxd.wordToString(wa);	//	string
			}
			default:{
				return dxd.wordToBase64(wa);	//	string
			}
		}
	};

	//	make this private, for later use with a generic HMAC calculator.
	dxd.MD5._hmac=function(/* string */data, /* string */key, /* dojox.encoding.digests.outputTypes? */outputType){
		//	summary
		//	computes the digest of data, and returns the result according to type outputType
		var out=outputType || dxd.outputTypes.Base64;
		var wa=hmac(data, key);
		switch(out){
			case dxd.outputTypes.Raw:{
				return wa;	//	word[]
			}
			case dxd.outputTypes.Hex:{
				return dxd.wordToHex(wa);	//	string
			}
			case dxd.outputTypes.String:{
				return dxd.wordToString(wa);	//	string
			}
			default:{
				return dxd.wordToBase64(wa);	//	string
			}
		}
	};
})();

}

if(!dojo._hasResource["dojo.colors"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojo.colors"] = true;
dojo.provide("dojo.colors");

//TODO: this module appears to break naming conventions

/*=====
dojo.colors = {
	// summary: Color utilities
}
=====*/

(function(){
	// this is a standard conversion prescribed by the CSS3 Color Module
	var hue2rgb = function(m1, m2, h){
		if(h < 0){ ++h; }
		if(h > 1){ --h; }
		var h6 = 6 * h;
		if(h6 < 1){ return m1 + (m2 - m1) * h6; }
		if(2 * h < 1){ return m2; }
		if(3 * h < 2){ return m1 + (m2 - m1) * (2 / 3 - h) * 6; }
		return m1;
	};
	
	dojo.colorFromRgb = function(/*String*/ color, /*dojo.Color?*/ obj){
		// summary:
		//		get rgb(a) array from css-style color declarations
		// description:
		//		this function can handle all 4 CSS3 Color Module formats: rgb,
		//		rgba, hsl, hsla, including rgb(a) with percentage values.
		var m = color.toLowerCase().match(/^(rgba?|hsla?)\(([\s\.\-,%0-9]+)\)/);
		if(m){
			var c = m[2].split(/\s*,\s*/), l = c.length, t = m[1];
			if((t == "rgb" && l == 3) || (t == "rgba" && l == 4)){
				var r = c[0];
				if(r.charAt(r.length - 1) == "%"){
					// 3 rgb percentage values
					var a = dojo.map(c, function(x){
						return parseFloat(x) * 2.56;
					});
					if(l == 4){ a[3] = c[3]; }
					return dojo.colorFromArray(a, obj);	// dojo.Color
				}
				return dojo.colorFromArray(c, obj);	// dojo.Color
			}
			if((t == "hsl" && l == 3) || (t == "hsla" && l == 4)){
				// normalize hsl values
				var H = ((parseFloat(c[0]) % 360) + 360) % 360 / 360,
					S = parseFloat(c[1]) / 100,
					L = parseFloat(c[2]) / 100,
					// calculate rgb according to the algorithm 
					// recommended by the CSS3 Color Module 
					m2 = L <= 0.5 ? L * (S + 1) : L + S - L * S, 
					m1 = 2 * L - m2,
					a = [hue2rgb(m1, m2, H + 1 / 3) * 256,
						hue2rgb(m1, m2, H) * 256, hue2rgb(m1, m2, H - 1 / 3) * 256, 1];
				if(l == 4){ a[3] = c[3]; }
				return dojo.colorFromArray(a, obj);	// dojo.Color
			}
		}
		return null;	// dojo.Color
	};
	
	var confine = function(c, low, high){
		// summary:
		//		sanitize a color component by making sure it is a number,
		//		and clamping it to valid values
		c = Number(c);
		return isNaN(c) ? high : c < low ? low : c > high ? high : c;	// Number
	};
	
	dojo.Color.prototype.sanitize = function(){
		// summary: makes sure that the object has correct attributes
		var t = this;
		t.r = Math.round(confine(t.r, 0, 255));
		t.g = Math.round(confine(t.g, 0, 255));
		t.b = Math.round(confine(t.b, 0, 255));
		t.a = confine(t.a, 0, 1);
		return this;	// dojo.Color
	};
})();


dojo.colors.makeGrey = function(/*Number*/ g, /*Number?*/ a){
	// summary: creates a greyscale color with an optional alpha
	return dojo.colorFromArray([g, g, g, a]);
};

// mixin all CSS3 named colors not already in _base, along with SVG 1.0 variant spellings
dojo.Color.named = dojo.mixin({
	aliceblue:	[240,248,255],
	antiquewhite:	[250,235,215],
	aquamarine:	[127,255,212],
	azure:	[240,255,255],
	beige:	[245,245,220],
	bisque:	[255,228,196],
	blanchedalmond:	[255,235,205],
	blueviolet:	[138,43,226],
	brown:	[165,42,42],
	burlywood:	[222,184,135],
	cadetblue:	[95,158,160],
	chartreuse:	[127,255,0],
	chocolate:	[210,105,30],
	coral:	[255,127,80],
	cornflowerblue:	[100,149,237],
	cornsilk:	[255,248,220],
	crimson:	[220,20,60],
	cyan:	[0,255,255],
	darkblue:	[0,0,139],
	darkcyan:	[0,139,139],
	darkgoldenrod:	[184,134,11],
	darkgray:	[169,169,169],
	darkgreen:	[0,100,0],
	darkgrey:	[169,169,169],
	darkkhaki:	[189,183,107],
	darkmagenta:	[139,0,139],
	darkolivegreen:	[85,107,47],
	darkorange:	[255,140,0],
	darkorchid:	[153,50,204],
	darkred:	[139,0,0],
	darksalmon:	[233,150,122],
	darkseagreen:	[143,188,143],
	darkslateblue:	[72,61,139],
	darkslategray:	[47,79,79],
	darkslategrey:	[47,79,79],
	darkturquoise:	[0,206,209],
	darkviolet:	[148,0,211],
	deeppink:	[255,20,147],
	deepskyblue:	[0,191,255],
	dimgray:	[105,105,105],
	dimgrey:	[105,105,105],
	dodgerblue:	[30,144,255],
	firebrick:	[178,34,34],
	floralwhite:	[255,250,240],
	forestgreen:	[34,139,34],
	gainsboro:	[220,220,220],
	ghostwhite:	[248,248,255],
	gold:	[255,215,0],
	goldenrod:	[218,165,32],
	greenyellow:	[173,255,47],
	grey:	[128,128,128],
	honeydew:	[240,255,240],
	hotpink:	[255,105,180],
	indianred:	[205,92,92],
	indigo:	[75,0,130],
	ivory:	[255,255,240],
	khaki:	[240,230,140],
	lavender:	[230,230,250],
	lavenderblush:	[255,240,245],
	lawngreen:	[124,252,0],
	lemonchiffon:	[255,250,205],
	lightblue:	[173,216,230],
	lightcoral:	[240,128,128],
	lightcyan:	[224,255,255],
	lightgoldenrodyellow:	[250,250,210],
	lightgray:	[211,211,211],
	lightgreen:	[144,238,144],
	lightgrey:	[211,211,211],
	lightpink:	[255,182,193],
	lightsalmon:	[255,160,122],
	lightseagreen:	[32,178,170],
	lightskyblue:	[135,206,250],
	lightslategray:	[119,136,153],
	lightslategrey:	[119,136,153],
	lightsteelblue:	[176,196,222],
	lightyellow:	[255,255,224],
	limegreen:	[50,205,50],
	linen:	[250,240,230],
	magenta:	[255,0,255],
	mediumaquamarine:	[102,205,170],
	mediumblue:	[0,0,205],
	mediumorchid:	[186,85,211],
	mediumpurple:	[147,112,219],
	mediumseagreen:	[60,179,113],
	mediumslateblue:	[123,104,238],
	mediumspringgreen:	[0,250,154],
	mediumturquoise:	[72,209,204],
	mediumvioletred:	[199,21,133],
	midnightblue:	[25,25,112],
	mintcream:	[245,255,250],
	mistyrose:	[255,228,225],
	moccasin:	[255,228,181],
	navajowhite:	[255,222,173],
	oldlace:	[253,245,230],
	olivedrab:	[107,142,35],
	orange:	[255,165,0],
	orangered:	[255,69,0],
	orchid:	[218,112,214],
	palegoldenrod:	[238,232,170],
	palegreen:	[152,251,152],
	paleturquoise:	[175,238,238],
	palevioletred:	[219,112,147],
	papayawhip:	[255,239,213],
	peachpuff:	[255,218,185],
	peru:	[205,133,63],
	pink:	[255,192,203],
	plum:	[221,160,221],
	powderblue:	[176,224,230],
	rosybrown:	[188,143,143],
	royalblue:	[65,105,225],
	saddlebrown:	[139,69,19],
	salmon:	[250,128,114],
	sandybrown:	[244,164,96],
	seagreen:	[46,139,87],
	seashell:	[255,245,238],
	sienna:	[160,82,45],
	skyblue:	[135,206,235],
	slateblue:	[106,90,205],
	slategray:	[112,128,144],
	slategrey:	[112,128,144],
	snow:	[255,250,250],
	springgreen:	[0,255,127],
	steelblue:	[70,130,180],
	tan:	[210,180,140],
	thistle:	[216,191,216],
	tomato:	[255,99,71],
	transparent: [0, 0, 0, 0],
	turquoise:	[64,224,208],
	violet:	[238,130,238],
	wheat:	[245,222,179],
	whitesmoke:	[245,245,245],
	yellowgreen:	[154,205,50]
}, dojo.Color.named);

}

if(!dojo._hasResource["dijit.ColorPalette"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dijit.ColorPalette"] = true;
dojo.provide("dijit.ColorPalette");







dojo.declare("dijit.ColorPalette",
	[dijit._Widget, dijit._Templated],
	{
	// summary: A keyboard accessible color-picking widget
	// description:
	//	Grid showing various colors, so the user can pick a certain color
	//	Can be used standalone, or as a popup.
	//
	// example:
	// |	<div dojoType="dijit.ColorPalette"></div>
	//
	// example:
	// |    var picker = new dijit.ColorPalette({ },srcNode);
	// |	picker.startup();
	//
	// defaultTimeout: Number
	//		number of milliseconds before a held key or button becomes typematic
	defaultTimeout: 500,

	// timeoutChangeRate: Number
	//		fraction of time used to change the typematic timer between events
	//		1.0 means that each typematic event fires at defaultTimeout intervals
	//		< 1.0 means that each typematic event fires at an increasing faster rate
	timeoutChangeRate: 0.90,

	// palette: String
	//		Size of grid, either "7x10" or "3x4".
	palette: "7x10",

	//_value: String
	//		The value of the selected color.
	value: null,

	//_currentFocus: Integer
	//		Index of the currently focused color.
	_currentFocus: 0,

	// _xDim: Integer
	//		This is the number of colors horizontally across.
	_xDim: null,

	// _yDim: Integer
	///		This is the number of colors vertically down.
	_yDim: null,

	// _palettes: Map
	// 		This represents the value of the colors.
	//		The first level is a hashmap of the different arrays available
	//		The next two dimensions represent the columns and rows of colors.
	_palettes: {

		"7x10":	[["white", "seashell", "cornsilk", "lemonchiffon","lightyellow", "palegreen", "paleturquoise", "lightcyan",	"lavender", "plum"],
				["lightgray", "pink", "bisque", "moccasin", "khaki", "lightgreen", "lightseagreen", "lightskyblue", "cornflowerblue", "violet"],
				["silver", "lightcoral", "sandybrown", "orange", "palegoldenrod", "chartreuse", "mediumturquoise", 	"skyblue", "mediumslateblue","orchid"],
				["gray", "red", "orangered", "darkorange", "yellow", "limegreen", 	"darkseagreen", "royalblue", "slateblue", "mediumorchid"],
				["dimgray", "crimson", 	"chocolate", "coral", "gold", "forestgreen", "seagreen", "blue", "blueviolet", "darkorchid"],
				["darkslategray","firebrick","saddlebrown", "sienna", "olive", "green", "darkcyan", "mediumblue","darkslateblue", "darkmagenta" ],
				["black", "darkred", "maroon", "brown", "darkolivegreen", "darkgreen", "midnightblue", "navy", "indigo", 	"purple"]],

		"3x4": [["white", "lime", "green", "blue"],
			["silver", "yellow", "fuchsia", "navy"],
			["gray", "red", "purple", "black"]]	

	},

	// _imagePaths: Map
	//		This is stores the path to the palette images
	_imagePaths: {
		"7x10": dojo.moduleUrl("dijit", "templates/colors7x10.png"),
		"3x4": dojo.moduleUrl("dijit", "templates/colors3x4.png")
	},

	// _paletteCoords: Map
	//		This is a map that is used to calculate the coordinates of the
	//		images that make up the palette.
	_paletteCoords: {
		"leftOffset": 3, "topOffset": 3,
		"cWidth": 20, "cHeight": 20
		
	},

	// templatePath: String
	//		Path to the template of this widget.
	templateString:"<div class=\"dijitInline dijitColorPalette\">\r\n\t<div class=\"dijitColorPaletteInner\" dojoAttachPoint=\"divNode\" waiRole=\"grid\" tabIndex=\"${tabIndex}\">\r\n\t\t<img class=\"dijitColorPaletteUnder\" dojoAttachPoint=\"imageNode\" waiRole=\"presentation\">\r\n\t</div>\t\r\n</div>\r\n",

	// _paletteDims: Object
	//		Size of the supported palettes for alignment purposes.
	_paletteDims: {
		"7x10": {"width": "206px", "height": "145px"},
		"3x4": {"width": "86px", "height": "64px"}
	},

	// tabIndex: String
	//		Widget tabindex.
	tabIndex: "0",

	postCreate: function(){
		// A name has to be given to the colorMap, this needs to be unique per Palette.
		dojo.mixin(this.divNode.style, this._paletteDims[this.palette]);
		this.imageNode.setAttribute("src", this._imagePaths[this.palette]);
		var choices = this._palettes[this.palette];	
		this.domNode.style.position = "relative";
		this._cellNodes = [];	
		this.colorNames = dojo.i18n.getLocalization("dojo", "colors", this.lang);
		var url = dojo.moduleUrl("dojo", "resources/blank.gif"),
            colorObject = new dojo.Color(),
		    coords = this._paletteCoords;
		for(var row=0; row < choices.length; row++){
			for(var col=0; col < choices[row].length; col++) {
                var imgNode = dojo.doc.createElement("img");
                imgNode.src = url;
                dojo.addClass(imgNode, "dijitPaletteImg");
                var color = choices[row][col],
                        colorValue = colorObject.setColor(dojo.Color.named[color]);
                imgNode.alt = this.colorNames[color];
                imgNode.color = colorValue.toHex();
                var imgStyle = imgNode.style;
                imgStyle.color = imgStyle.backgroundColor = imgNode.color;
                var cellNode = dojo.doc.createElement("span");
                cellNode.appendChild(imgNode);
                dojo.forEach(["Dijitclick", "MouseEnter", "Focus", "Blur"], function(handler) {
                    this.connect(cellNode, "on" + handler.toLowerCase(), "_onCell" + handler);
                }, this);
                this.divNode.appendChild(cellNode);
                var cellStyle = cellNode.style;
                cellStyle.top = coords.topOffset + (row * coords.cHeight) + "px";
                cellStyle.left = coords.leftOffset + (col * coords.cWidth) + "px";
                dojo.attr(cellNode, "tabindex", "-1");
                cellNode.title = this.colorNames[color];
                dojo.addClass(cellNode, "dijitPaletteCell");
                dijit.setWaiRole(cellNode, "gridcell");
                cellNode.index = this._cellNodes.length;
                this._cellNodes.push(cellNode);
            }
		}
		this._xDim = choices[0].length;
		this._yDim = choices.length;
		this.connect(this.divNode, "onfocus", "_onDivNodeFocus");

		// Now set all events
		// The palette itself is navigated to with the tab key on the keyboard
		// Keyboard navigation within the Palette is with the arrow keys
		// Spacebar selects the color.
		// For the up key the index is changed by negative the x dimension.		

		var keyIncrementMap = {
			UP_ARROW: -this._xDim,
			// The down key the index is increase by the x dimension.	
			DOWN_ARROW: this._xDim,
			// Right and left move the index by 1.
			RIGHT_ARROW: 1,
			LEFT_ARROW: -1
		};
		for(var key in keyIncrementMap){
			this._connects.push(dijit.typematic.addKeyListener(this.domNode,
				{keyCode:dojo.keys[key], ctrlKey:false, altKey:false, shiftKey:false},
				this,
				function(){
					var increment = keyIncrementMap[key];
					return function(count){ this._navigateByKey(increment, count); };
				}(),
				this.timeoutChangeRate, this.defaultTimeout));
		}
	},

	focus: function(){
		// summary:
		//		Focus this ColorPalette.  Puts focus on the first swatch.
		this._focusFirst();
	},

	onChange: function(color){
		// summary:
		//		Callback when a color is selected.
		// color: String
		//		Hex value corresponding to color.
//		console.debug("Color selected is: "+color);
	},

	_focusFirst: function(){
		this._currentFocus = 0;
		var cellNode = this._cellNodes[this._currentFocus];
		window.setTimeout(function(){dijit.focus(cellNode)}, 0);
	},

	_onDivNodeFocus: function(evt){
		// focus bubbles on Firefox 2, so just make sure that focus has really
		// gone to the container
		if(evt.target === this.divNode){
			this._focusFirst();
		}
	},

	_onFocus: function(){
		// while focus is on the palette, set its tabindex to -1 so that on a
		// shift-tab from a cell, the container is not in the tab order
		dojo.attr(this.divNode, "tabindex", "-1");
	},

	_onBlur: function(){
		this._removeCellHighlight(this._currentFocus);
		// when focus leaves the palette, restore its tabindex, since it was
		// modified by _onFocus().
		dojo.attr(this.divNode, "tabindex", this.tabIndex);
	},

	_onCellDijitclick: function(/*Event*/ evt){
		// summary:
		//		Handler for click, enter key & space key. Selects the color.
		// evt:
		//		The event.
		var target = evt.currentTarget;
		if (this._currentFocus != target.index){
			this._currentFocus = target.index;
			window.setTimeout(function(){dijit.focus(target)}, 0);
		}
		this._selectColor(target);
		dojo.stopEvent(evt);
	},

	_onCellMouseEnter: function(/*Event*/ evt){
		// summary:
		//		Handler for onMouseOver. Put focus on the color under the mouse.
		// evt:
		//		The mouse event.
		var target = evt.currentTarget;
		window.setTimeout(function(){dijit.focus(target)}, 0);
	},

	_onCellFocus: function(/*Event*/ evt){
		// summary:
		//		Handler for onFocus. Removes highlight of
		//		the color that just lost focus, and highlights
		//		the new color.
		// evt:
		//		The focus event.
		this._removeCellHighlight(this._currentFocus);
		this._currentFocus = evt.currentTarget.index;
		dojo.addClass(evt.currentTarget, "dijitPaletteCellHighlight");
	},

	_onCellBlur: function(/*Event*/ evt){
		// summary:
		//		needed for Firefox 2 on Mac OS X
		this._removeCellHighlight(this._currentFocus);
	},

	_removeCellHighlight: function(index){
		dojo.removeClass(this._cellNodes[index], "dijitPaletteCellHighlight");
	},

	_selectColor: function(selectNode){	
		// summary:
		// 		This selects a color. It triggers the onChange event
		// area:
		//		The area node that covers the color being selected.
		var img = selectNode.getElementsByTagName("img")[0];
		this.onChange(this.value = img.color);
	},

	_navigateByKey: function(increment, typeCount){
		// summary:
		// 	  	This is the callback for typematic.
		// 		It changes the focus and the highlighed color.
		// increment:
		// 		How much the key is navigated.
		// typeCount:
		//		How many times typematic has fired.

		// typecount == -1 means the key is released.
		if(typeCount == -1){ return; }

		var newFocusIndex = this._currentFocus + increment;
		if(newFocusIndex < this._cellNodes.length && newFocusIndex > -1)
		{
			var focusNode = this._cellNodes[newFocusIndex];
			focusNode.focus();
		}
	}
});

}

if(!dojo._hasResource["dojox.widget.ColorPicker"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojox.widget.ColorPicker"] = true;
dojo.provide("dojox.widget.ColorPicker");
dojo.experimental("dojox.widget.ColorPicker"); // level: prototype


 
 

dojo.declare("dojox.widget.ColorPicker",
	dijit.form._FormWidget,
	{
	// summary: a HSV color picker - like PhotoShop
	//
	// description: 
	//	provides an interactive HSV ColorPicker similar to
	//	PhotoShop's color selction tool. Will eventually
	//	mixin FormWidget and be used as a suplement or a
	//	'more interactive' replacement for ColorPalette
	//	
	// example:
	//
	//	code: 
	//	var picker = new dojox.widget.ColorPicker({
	//		// a couple of example toggles:
	//		animatePoint:false,
	//		showHsv: false,
	//		webSafe: false,
	//		showRgb: false 	
	//	});
	//	
	//	markup:
	//	<div dojoType="dojox.widget.ColorPicker"></div>
	//

	// showRgb: Boolean
	//	show/update RGB input nodes
	showRgb: true,
	
	// showHsv: Boolean
	//	show/update HSV input nodes
	showHsv: true,
	
	// showHex: Boolean
	//	show/update Hex value field
	showHex: true,

	// webSafe: Boolean
	//	deprecated? or just use a toggle to show/hide that node, too?
	webSafe: true,

	// animatePoint: Boolean
	//	toggle to use slideTo (true) or just place the cursor (false) on click
	animatePoint: true,

	// slideDuration: Integer
	//	time in ms picker node will slide to next location (non-dragging) when animatePoint=true
	slideDuration: 250, 

	_underlay: dojo.moduleUrl("dojox.widget","ColorPicker/images/underlay.png"),
	templateString:"<div class=\"dojoxColorPicker\">\r\n\t<div class=\"dojoxColorPickerBox\">\r\n\t\t<div dojoAttachPoint=\"cursorNode\" class=\"dojoxColorPickerPoint\"></div>\r\n\t\t<img dojoAttachPoint=\"colorUnderlay\" dojoAttachEvent=\"onclick: _setPoint\" class=\"dojoxColorPickerUnderlay\" src=\"${_underlay}\">\r\n\t</div>\r\n\t<div class=\"dojoxHuePicker\">\r\n\t\t<div dojoAttachPoint=\"hueCursorNode\" class=\"dojoxHuePickerPoint\"></div>\r\n\t\t<div dojoAttachPoint=\"hueNode\" class=\"dojoxHuePickerUnderlay\" dojoAttachEvent=\"onclick: _setHuePoint\"></div>\r\n\t</div>\r\n\t<div dojoAttachPoint=\"previewNode\" class=\"dojoxColorPickerPreview\"></div>\r\n\t<div dojoAttachPoint=\"safePreviewNode\" class=\"dojoxColorPickerWebSafePreview\"></div>\r\n\t<div class=\"dojoxColorPickerOptional\">\r\n\t\t<div class=\"dijitInline dojoxColorPickerRgb\" dojoAttachPoint=\"rgbNode\">\r\n\t\t\t<table>\r\n\t\t\t<tr><td>r</td><td><input dojoAttachPoint=\"Rval\" size=\"1\"></td></tr>\r\n\t\t\t<tr><td>g</td><td><input dojoAttachPoint=\"Gval\" size=\"1\"></td></tr>\r\n\t\t\t<tr><td>b</td><td><input dojoAttachPoint=\"Bval\" size=\"1\"></td></tr>\r\n\t\t\t</table>\r\n\t\t</div>\r\n\t\t<div class=\"dijitInline dojoxColorPickerHsv\" dojoAttachPoint=\"hsvNode\">\r\n\t\t\t<table>\r\n\t\t\t<tr><td>h</td><td><input dojoAttachPoint=\"Hval\"size=\"1\"> &deg;</td></tr>\r\n\t\t\t<tr><td>s</td><td><input dojoAttachPoint=\"Sval\" size=\"1\"> %</td></tr>\r\n\t\t\t<tr><td>v</td><td><input dojoAttachPoint=\"Vval\" size=\"1\"> %</td></tr>\r\n\t\t\t</table>\r\n\t\t</div>\r\n\t\t<div class=\"dojoxColorPickerHex\" dojoAttachPoint=\"hexNode\">\t\r\n\t\t\thex: <input dojoAttachPoint=\"hexCode, focusNode\" size=\"6\" class=\"dojoxColorPickerHexCode\">\r\n\t\t</div>\r\n\t</div>\r\n</div>\r\n",

	postCreate: function(){
		// summary: As quickly as we can, set up ie6 alpha-filter support for our
		// 	underlay.  we don't do image handles (done in css), just the 'core' 
		//	of this widget: the underlay. 
		if(dojo.isIE && dojo.isIE<7){ 
			this.colorUnderlay.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(src='"+this._underlay+"', sizingMethod='scale')";
			this.colorUnderlay.src = dojo.moduleUrl("dojo","resources/blank.gif").toString();
		}
		// hide toggle-able nodes:
		if (!this.showRgb){ this.rgbNode.style.display = "none"; }
		if (!this.showHsv){ this.hsvNode.style.display = "none"; }
		if (!this.showHex){ this.hexNode.style.display = "none"; } 
		if (!this.webSafe){ this.safePreviewNode.style.display = "none"; } 
	},

	startup: function(){
		// summary: defer all additional calls until we're started, and our
		// embeded sliders are loaded? (not implemented yet)

		// this._offset = ((dojo.marginBox(this.cursorNode).w)/2); 
		this._offset = 0; 

		this._mover = new dojo.dnd.Moveable(this.cursorNode, {
			mover: dojo.dnd.boxConstrainedMover({ t:0, l:0, w:150, h:150 })
		}); 
		this._hueMover = new dojo.dnd.Moveable(this.hueCursorNode, {
			mover: dojo.dnd.boxConstrainedMover({ t:0, l:0, w:0, h:150 })
		});

		// no dnd/move/move published ... use a timer:
		dojo.subscribe("/dnd/move/stop",dojo.hitch(this,"_clearTimer"));
		dojo.subscribe("/dnd/move/start",dojo.hitch(this,"_setTimer"));

		// ugly scaling calculator.  need a XYslider badly
		this._sc = (1/dojo.coords(this.colorUnderlay).w);  
		this._hueSc = (255/(dojo.coords(this.hueNode).h+this._offset)); 
		
		// initial color
		this._updateColor(); 
		
	},

	_setTimer: function(/* dojo.dnd.Mover */mover){
		this._timer = setInterval(dojo.hitch(this,"_updateColor"),45);	
	},
	_clearTimer: function(/* dojo.dnd.Mover */mover){
		clearInterval(this._timer);
		this.onChange(this.value);
	},

	_setHue: function(/* Decimal */h){
		// summary: sets a natural color background for the 
		// 	underlay image against closest hue value (full saturation) 
		// h: 0..255 

		// this is not a pretty conversion:
		var hue = dojo.colorFromArray(this._hsv2rgb(h,1,1,{ inputRange: 1 })).toHex();
		dojo.style(this.colorUnderlay,"backgroundColor",hue);
	},

	_updateColor: function(){
		// summary: update the previewNode color, and input values [optional]
		var h = Math.round((255+(this._offset))-((dojo.style(this.hueCursorNode,"top")+this._offset)*this._hueSc));
		var s = Math.round((dojo.style(this.cursorNode,"left")*this._sc)*100); 
		var v = Math.round(100-(dojo.style(this.cursorNode,"top")*this._sc)*100);

		// limit hue calculations to only when it changes
		if(h != this._hue){ this._setHue(h); }

		var rgb = this._hsv2rgb(h,s/100,v/100,{ inputRange: 1 }); 
		var hex = (dojo.colorFromArray(rgb).toHex());

		this.previewNode.style.backgroundColor = hex;	
		if(this.webSafe){ this.safePreviewNode.style.backgroundColor = hex; }
		if(this.showHex){ this.hexCode.value = hex; }
		if(this.showRgb){
			this.Rval.value = rgb[0];
			this.Gval.value = rgb[1];	
			this.Bval.value = rgb[2];
		}
		if(this.showHsv){
			this.Hval.value = Math.round((h*360)/255); // convert to 0..360
			this.Sval.value = s;
			this.Vval.value = v;
		}
		this.value=hex;

		// anytime we muck with the color, fire onChange?
		if (!this._timer && !(arguments[1])){
			this.setValue(this.value);	
			this.onChange(this.value);
		}
	},

	_setHuePoint: function(/* Event */evt){ 
		// summary: set the hue picker handle on relative y coordinates
		if(this.animatePoint){
			dojo.fx.slideTo({ 
				node: this.hueCursorNode, 
				duration:this.slideDuration,
				top: evt.layerY,
				left: 0,
				onEnd: dojo.hitch(this,"_updateColor")
			}).play();
		}else{
			dojo.style(this.hueCursorNode,"top",(evt.layerY)+"px");
			this._updateColor(false); 
		}
	},

	_setPoint: function(/* Event */evt){
		// summary: set our picker point based on relative x/y coordinates
		if(this.animatePoint){
			dojo.fx.slideTo({ 
				node: this.cursorNode, 
				duration:this.slideDuration,
				top: evt.layerY-this._offset, 
				left: evt.layerX-this._offset,
				onEnd: dojo.hitch(this,"_updateColor")
			}).play();
		}else{
			dojo.style(this.cursorNode,"left",(evt.layerX-this._offset)+"px");
			dojo.style(this.cursorNode,"top",(evt.layerY-this._offset)+"px");
			this._updateColor(false); 
		}
	},

	// this ported directly from 0.4 dojo.gfx.colors.hsv, with bugs :)
	// FIXME: use ttrenka's HSB ?
	_hsv2rgb: function(/* int || Array */h, /* int */s, /* int */v, /* Object? */options){
		//	summary
		//	converts an HSV value set to RGB, ranges depending on optional options object.
		//	patch for options by Matthew Eernisse 	
		if (dojo.isArray(h)) {
			if(s){
				options = s;
			}
			v = h[2] || 0;
			s = h[1] || 0;
			h = h[0] || 0;
		}
	
		var opt = {
			inputRange:  (options && options.inputRange)  ? options.inputRange : [255, 255, 255],
			outputRange: (options && options.outputRange) ? options.outputRange : 255
		};
	
	    switch(opt.inputRange[0]) { 
			// 0.0-1.0 
			case 1: h = h * 360; break; 
			// 0-100 
			case 100: h = (h / 100) * 360; break; 
			// 0-360 
			case 360: h = h; break; 
			// 0-255 
			default: h = (h / 255) * 360; 
		} 
		if (h == 360){ h = 0;}
	
		//	no need to alter if inputRange[1] = 1
		switch(opt.inputRange[1]){
			case 100: s /= 100; break;
			case 255: s /= 255;
		}
	
		//	no need to alter if inputRange[1] = 1
		switch(opt.inputRange[2]){
			case 100: v /= 100; break;
			case 255: v /= 255;
		}
	
		var r = null;
		var g = null;
		var b = null;
	
		if (s == 0){
			// color is on black-and-white center line
			// achromatic: shades of gray
			r = v;
			g = v;
			b = v;
		}else{
			// chromatic color
			var hTemp = h / 60;		// h is now IN [0,6]
			var i = Math.floor(hTemp);	// largest integer <= h
			var f = hTemp - i;		// fractional part of h
	
			var p = v * (1 - s);
			var q = v * (1 - (s * f));
			var t = v * (1 - (s * (1 - f)));
	
			switch(i){
				case 0: r = v; g = t; b = p; break;
				case 1: r = q; g = v; b = p; break;
				case 2: r = p; g = v; b = t; break;
				case 3: r = p; g = q; b = v; break;
				case 4: r = t; g = p; b = v; break;
				case 5: r = v; g = p; b = q; break;
			}
		}
	
		switch(opt.outputRange){
			case 1:
				r = dojo.math.round(r, 2);
				g = dojo.math.round(g, 2);
				b = dojo.math.round(b, 2);
				break;
			case 100:
				r = Math.round(r * 100);
				g = Math.round(g * 100);
				b = Math.round(b * 100);
				break;
			default:
				r = Math.round(r * 255);
				g = Math.round(g * 255);
				b = Math.round(b * 255);
		}
		return [r, g, b];
	}
});

}

if(!dojo._hasResource["dijit.layout._LayoutWidget"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dijit.layout._LayoutWidget"] = true;
dojo.provide("dijit.layout._LayoutWidget");




dojo.declare("dijit.layout._LayoutWidget",
	[dijit._Widget, dijit._Container, dijit._Contained],
	{
		// summary
		//		Mixin for widgets that contain a list of children like SplitContainer.
		//		Widgets which mixin this code must define layout() to lay out the children

		isLayoutContainer: true,

		postCreate: function(){
			dojo.addClass(this.domNode, "dijitContainer");
		},

		startup: function(){
			// summary:
			//		Called after all the widgets have been instantiated and their
			//		dom nodes have been inserted somewhere under dojo.doc.body.
			//
			//		Widgets should override this method to do any initialization
			//		dependent on other widgets existing, and then call
			//		this superclass method to finish things off.
			//
			//		startup() in subclasses shouldn't do anything
			//		size related because the size of the widget hasn't been set yet.

			if(this._started){ return; }

			dojo.forEach(this.getChildren(), function(child){ child.startup(); });

			// If I am a top level widget
			if(!this.getParent || !this.getParent()){
				// Do recursive sizing and layout of all my descendants
				// (passing in no argument to resize means that it has to glean the size itself)
				this.resize();

				// since my parent isn't a layout container, and my style is width=height=100% (or something similar),
				// then I need to watch when the window resizes, and size myself accordingly
				// (passing in no argument to resize means that it has to glean the size itself)
				this.connect(window, 'onresize', function(){this.resize();});
			}
			
			this.inherited(arguments);
		},

		resize: function(args){
			// summary:
			//		Explicitly set this widget's size (in pixels),
			//		and then call layout() to resize contents (and maybe adjust child widgets)
			//	
			// args: Object?
			//		{w: int, h: int, l: int, t: int}

			var node = this.domNode;

			// set margin box size, unless it wasn't specified, in which case use current size
			if(args){
				dojo.marginBox(node, args);

				// set offset of the node
				if(args.t){ node.style.top = args.t + "px"; }
				if(args.l){ node.style.left = args.l + "px"; }
			}
			// If either height or width wasn't specified by the user, then query node for it.
			// But note that setting the margin box and then immediately querying dimensions may return
			// inaccurate results, so try not to depend on it.
			var mb = dojo.mixin(dojo.marginBox(node), args||{});

//			console.log(this, ": setting size to ", mb);

			// Save the size of my content box.
			this._contentBox = dijit.layout.marginBox2contentBox(node, mb);

			// Callback for widget to adjust size of it's children
			this.layout();
		},

		layout: function(){
			//	summary
			//		Widgets override this method to size & position their contents/children.
			//		When this is called this._contentBox is guaranteed to be set (see resize()).
			//
			//		This is called after startup(), and also when the widget's size has been
			//		changed.
		}
	}
);

dijit.layout.marginBox2contentBox = function(/*DomNode*/ node, /*Object*/ mb){
	// summary:
	//		Given the margin-box size of a node, return it's content box size.
	//		Functions like dojo.contentBox() but is more reliable since it doesn't have
	//		to wait for the browser to compute sizes.
	var cs = dojo.getComputedStyle(node);
	var me=dojo._getMarginExtents(node, cs);
	var pb=dojo._getPadBorderExtents(node, cs);
	return {
		l: dojo._toPixelValue(node, cs.paddingLeft),
		t: dojo._toPixelValue(node, cs.paddingTop),
		w: mb.w - (me.w + pb.w),
		h: mb.h - (me.h + pb.h)
	};
};

(function(){
	var capitalize = function(word){
		return word.substring(0,1).toUpperCase() + word.substring(1);
	};

	var size = function(widget, dim){
		// size the child
		widget.resize ? widget.resize(dim) : dojo.marginBox(widget.domNode, dim);

		// record child's size, but favor our own numbers when we have them.
		// the browser lies sometimes
		dojo.mixin(widget, dojo.marginBox(widget.domNode));
		dojo.mixin(widget, dim);
	};

	dijit.layout.layoutChildren = function(/*DomNode*/ container, /*Object*/ dim, /*Object[]*/ children){
		/**
		 * summary
		 *		Layout a bunch of child dom nodes within a parent dom node
		 * container:
		 *		parent node
		 * dim:
		 *		{l, t, w, h} object specifying dimensions of container into which to place children
		 * children:
		 *		an array like [ {domNode: foo, layoutAlign: "bottom" }, {domNode: bar, layoutAlign: "client"} ]
		 */

		// copy dim because we are going to modify it
		dim = dojo.mixin({}, dim);

		dojo.addClass(container, "dijitLayoutContainer");

		// Move "client" elements to the end of the array for layout.  a11y dictates that the author
		// needs to be able to put them in the document in tab-order, but this algorithm requires that
		// client be last.
		children = dojo.filter(children, function(item){ return item.layoutAlign != "client"; })
			.concat(dojo.filter(children, function(item){ return item.layoutAlign == "client"; }));

		// set positions/sizes
		dojo.forEach(children, function(child){
			var elm = child.domNode,
				pos = child.layoutAlign;

			// set elem to upper left corner of unused space; may move it later
			var elmStyle = elm.style;
			elmStyle.left = dim.l+"px";
			elmStyle.top = dim.t+"px";
			elmStyle.bottom = elmStyle.right = "auto";

			dojo.addClass(elm, "dijitAlign" + capitalize(pos));

			// set size && adjust record of remaining space.
			// note that setting the width of a <div> may affect it's height.
			if(pos=="top" || pos=="bottom"){
				size(child, { w: dim.w });
				dim.h -= child.h;
				if(pos=="top"){
					dim.t += child.h;
				}else{
					elmStyle.top = dim.t + dim.h + "px";
				}
			}else if(pos=="left" || pos=="right"){
				size(child, { h: dim.h });
				dim.w -= child.w;
				if(pos=="left"){
					dim.l += child.w;
				}else{
					elmStyle.left = dim.l + dim.w + "px";
				}
			}else if(pos=="client"){
				size(child, dim);
			}
		});
	};

})();

}

if(!dojo._hasResource["dijit.layout.ContentPane"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dijit.layout.ContentPane"] = true;
dojo.provide("dijit.layout.ContentPane");








dojo.declare(
	"dijit.layout.ContentPane",
	dijit._Widget,
{
	// summary:
	//		A widget that acts as a Container for other widgets, and includes a ajax interface
	// description:
	//		A widget that can be used as a standalone widget
	//		or as a baseclass for other widgets
	//		Handles replacement of document fragment using either external uri or javascript
	//		generated markup or DOM content, instantiating widgets within that content.
	//		Don't confuse it with an iframe, it only needs/wants document fragments.
	//		It's useful as a child of LayoutContainer, SplitContainer, or TabContainer.
	//		But note that those classes can contain any widget as a child.
	// example:
	//		Some quick samples:
	//		To change the innerHTML use .setContent('<b>new content</b>')
	//
	//		Or you can send it a NodeList, .setContent(dojo.query('div [class=selected]', userSelection))
	//		please note that the nodes in NodeList will copied, not moved
	//
	//		To do a ajax update use .setHref('url')
	//
	// href: String
	//		The href of the content that displays now.
	//		Set this at construction if you want to load data externally when the
	//		pane is shown.  (Set preload=true to load it immediately.)
	//		Changing href after creation doesn't have any effect; see setHref();
	href: "",

	// extractContent: Boolean
	//	Extract visible content from inside of <body> .... </body>
	extractContent: false,

	// parseOnLoad: Boolean
	//	parse content and create the widgets, if any
	parseOnLoad:	true,

	// preventCache: Boolean
	//		Cache content retreived externally
	preventCache:	false,

	// preload: Boolean
	//	Force load of data even if pane is hidden.
	preload: false,

	// refreshOnShow: Boolean
	//		Refresh (re-download) content when pane goes from hidden to shown
	refreshOnShow: false,

	// loadingMessage: String
	//	Message that shows while downloading
	loadingMessage: "<span class='dijitContentPaneLoading'>${loadingState}</span>", 

	// errorMessage: String
	//	Message that shows if an error occurs
	errorMessage: "<span class='dijitContentPaneError'>${errorState}</span>", 

	// isLoaded: Boolean
	//	Tells loading status see onLoad|onUnload for event hooks
	isLoaded: false,

	// class: String
	//	Class name to apply to ContentPane dom nodes
	// TODO: this should be called "baseClass" like in the other widgets
	"class": "dijitContentPane",

	// doLayout: String/Boolean
	//	false - don't adjust size of children
	//	true - looks for the first sizable child widget (ie, having resize() method) and sets it's size to
	//			however big the ContentPane is (TODO: implement)
	//	auto - if there is a single sizable child widget (ie, having resize() method), set it's size to
	//			however big the ContentPane is
	doLayout: "auto",

	postCreate: function(){
		// remove the title attribute so it doesn't show up when i hover
		// over a node
		this.domNode.title = "";

		if(!this.containerNode){
			// make getDescendants() work
			this.containerNode = this.domNode;
		}

		if(this.preload){
			this._loadCheck();
		}

		var messages = dojo.i18n.getLocalization("dijit", "loading", this.lang);
		this.loadingMessage = dojo.string.substitute(this.loadingMessage, messages);
		this.errorMessage = dojo.string.substitute(this.errorMessage, messages);
		var curRole = dijit.getWaiRole(this.domNode);
		if (!curRole){
			dijit.setWaiRole(this.domNode, "group");
		}

		// for programatically created ContentPane (with <span> tag), need to muck w/CSS
		// or it's as though overflow:visible is set
		dojo.addClass(this.domNode, this["class"]);
	},

	startup: function(){
		if(this._started){ return; }
		if(this.doLayout != "false" && this.doLayout !== false){
			this._checkIfSingleChild();
			if(this._singleChild){
				this._singleChild.startup();
			}
		}
		this._loadCheck();
		this.inherited(arguments);
	},

	_checkIfSingleChild: function(){
		// summary:
		// 	Test if we have exactly one widget as a child, and if so assume that we are a container for that widget,
		//	and should propogate startup() and resize() calls to it.

		// TODO: if there are two child widgets (a data store and a TabContainer, for example),
		//	should still find the TabContainer
		var childNodes = dojo.query(">", this.containerNode || this.domNode),
			childWidgets = childNodes.filter("[widgetId]");

		if(childNodes.length == 1 && childWidgets.length == 1){
			this.isContainer = true;
			this._singleChild = dijit.byNode(childWidgets[0]);
		}else{
			delete this.isContainer;
			delete this._singleChild;
		}
	},

	refresh: function(){
		// summary:
		//	Force a refresh (re-download) of content, be sure to turn off cache

		// we return result of _prepareLoad here to avoid code dup. in dojox.layout.ContentPane
		return this._prepareLoad(true);
	},

	setHref: function(/*String|Uri*/ href){
		// summary:
		//		Reset the (external defined) content of this pane and replace with new url
		//		Note: It delays the download until widget is shown if preload is false
		//	href:
		//		url to the page you want to get, must be within the same domain as your mainpage
		this.href = href;

		// we return result of _prepareLoad here to avoid code dup. in dojox.layout.ContentPane
		return this._prepareLoad();
	},

	setContent: function(/*String|DomNode|Nodelist*/data){
		// summary:
		//		Replaces old content with data content, include style classes from old content
		//	data:
		//		the new Content may be String, DomNode or NodeList
		//
		//		if data is a NodeList (or an array of nodes) nodes are copied
		//		so you can import nodes from another document implicitly

		// clear href so we cant run refresh and clear content
		// refresh should only work if we downloaded the content
		if(!this._isDownloaded){
			this.href = "";
			this._onUnloadHandler();
		}

		this._setContent(data || "");

		this._isDownloaded = false; // must be set after _setContent(..), pathadjust in dojox.layout.ContentPane

		if(this.parseOnLoad){
			this._createSubWidgets();
		}

		if(this.doLayout != "false" && this.doLayout !== false){
			this._checkIfSingleChild();
			if(this._singleChild && this._singleChild.resize){
				this._singleChild.startup();
				this._singleChild.resize(this._contentBox || dojo.contentBox(this.containerNode || this.domNode));
			}
		}

		this._onLoadHandler();
	},

	cancel: function(){
		// summary:
		//		Cancels a inflight download of content
		if(this._xhrDfd && (this._xhrDfd.fired == -1)){
			this._xhrDfd.cancel();
		}
		delete this._xhrDfd; // garbage collect
	},

	destroy: function(){
		// if we have multiple controllers destroying us, bail after the first
		if(this._beingDestroyed){
			return;
		}
		// make sure we call onUnload
		this._onUnloadHandler();
		this._beingDestroyed = true;
		this.inherited("destroy",arguments);
	},

	resize: function(size){
		dojo.marginBox(this.domNode, size);

		// Compute content box size in case we [later] need to size child
		// If either height or width wasn't specified by the user, then query node for it.
		// But note that setting the margin box and then immediately querying dimensions may return
		// inaccurate results, so try not to depend on it.
		var node = this.containerNode || this.domNode,
			mb = dojo.mixin(dojo.marginBox(node), size||{});

		this._contentBox = dijit.layout.marginBox2contentBox(node, mb);

		// If we have a single widget child then size it to fit snugly within my borders
		if(this._singleChild && this._singleChild.resize){
			this._singleChild.resize(this._contentBox);
		}
	},

	_prepareLoad: function(forceLoad){
		// sets up for a xhrLoad, load is deferred until widget onShow
		// cancels a inflight download
		this.cancel();
		this.isLoaded = false;
		this._loadCheck(forceLoad);
	},

	_isShown: function(){
		// summary: returns true if the content is currently shown
		if("open" in this){
			return this.open;		// for TitlePane, etc.
		}else{
			var node = this.domNode;
			return (node.style.display != 'none')  && (node.style.visibility != 'hidden');
		}
	},

	_loadCheck: function(/*Boolean*/ forceLoad){
		// call this when you change onShow (onSelected) status when selected in parent container
		// it's used as a trigger for href download when this.domNode.display != 'none'

		// sequence:
		// if no href -> bail
		// forceLoad -> always load
		// this.preload -> load when download not in progress, domNode display doesn't matter
		// this.refreshOnShow -> load when download in progress bails, domNode display !='none' AND
		//						this.open !== false (undefined is ok), isLoaded doesn't matter
		// else -> load when download not in progress, if this.open !== false (undefined is ok) AND
		//						domNode display != 'none', isLoaded must be false

		var displayState = this._isShown();

		if(this.href &&	
			(forceLoad ||
				(this.preload && !this._xhrDfd) ||
				(this.refreshOnShow && displayState && !this._xhrDfd) ||
				(!this.isLoaded && displayState && !this._xhrDfd)
			)
		){
			this._downloadExternalContent();
		}
	},

	_downloadExternalContent: function(){
		this._onUnloadHandler();

		// display loading message
		this._setContent(
			this.onDownloadStart.call(this)
		);

		var self = this;
		var getArgs = {
			preventCache: (this.preventCache || this.refreshOnShow),
			url: this.href,
			handleAs: "text"
		};
		if(dojo.isObject(this.ioArgs)){
			dojo.mixin(getArgs, this.ioArgs);
		}

		var hand = this._xhrDfd = (this.ioMethod || dojo.xhrGet)(getArgs);

		hand.addCallback(function(html){
			try{
				self.onDownloadEnd.call(self);
				self._isDownloaded = true;
				self.setContent.call(self, html); // onload event is called from here
			}catch(err){
				self._onError.call(self, 'Content', err); // onContentError
			}
			delete self._xhrDfd;
			return html;
		});

		hand.addErrback(function(err){
			if(!hand.cancelled){
				// show error message in the pane
				self._onError.call(self, 'Download', err); // onDownloadError
			}
			delete self._xhrDfd;
			return err;
		});
	},

	_onLoadHandler: function(){
		this.isLoaded = true;
		try{
			this.onLoad.call(this);
		}catch(e){
			console.error('Error '+this.widgetId+' running custom onLoad code');
		}
	},

	_onUnloadHandler: function(){
		this.isLoaded = false;
		this.cancel();
		try{
			this.onUnload.call(this);
		}catch(e){
			console.error('Error '+this.widgetId+' running custom onUnload code');
		}
	},

	_setContent: function(cont){
		this.destroyDescendants();

		try{
			var node = this.containerNode || this.domNode;
			while(node.firstChild){
				dojo._destroyElement(node.firstChild);
			}
			if(typeof cont == "string"){
				// dijit.ContentPane does only minimal fixes,
				// No pathAdjustments, script retrieval, style clean etc
				// some of these should be available in the dojox.layout.ContentPane
				if(this.extractContent){
					match = cont.match(/<body[^>]*>\s*([\s\S]+)\s*<\/body>/im);
					if(match){ cont = match[1]; }
				}
				node.innerHTML = cont;
			}else{
				// domNode or NodeList
				if(cont.nodeType){ // domNode (htmlNode 1 or textNode 3)
					node.appendChild(cont);
				}else{// nodelist or array such as dojo.Nodelist
					dojo.forEach(cont, function(n){
						node.appendChild(n.cloneNode(true));
					});
				}
			}
		}catch(e){
			// check if a domfault occurs when we are appending this.errorMessage
			// like for instance if domNode is a UL and we try append a DIV
			var errMess = this.onContentError(e);
			try{
				node.innerHTML = errMess;
			}catch(e){
				console.error('Fatal '+this.id+' could not change content due to '+e.message, e);
			}
		}
	},

	_onError: function(type, err, consoleText){
		// shows user the string that is returned by on[type]Error
		// overide on[type]Error and return your own string to customize
		var errText = this['on' + type + 'Error'].call(this, err);
		if(consoleText){
			console.error(consoleText, err);
		}else if(errText){// a empty string won't change current content
			this._setContent.call(this, errText);
		}
	},

	_createSubWidgets: function(){
		// summary: scan my contents and create subwidgets
		var rootNode = this.containerNode || this.domNode;
		try{
			dojo.parser.parse(rootNode, true);
		}catch(e){
			this._onError('Content', e, "Couldn't create widgets in "+this.id
				+(this.href ? " from "+this.href : ""));
		}
	},

	// EVENT's, should be overide-able
	onLoad: function(e){
		// summary:
		//		Event hook, is called after everything is loaded and widgetified
	},

	onUnload: function(e){
		// summary:
		//		Event hook, is called before old content is cleared
	},

	onDownloadStart: function(){
		// summary:
		//		called before download starts
		//		the string returned by this function will be the html
		//		that tells the user we are loading something
		//		override with your own function if you want to change text
		return this.loadingMessage;
	},

	onContentError: function(/*Error*/ error){
		// summary:
		//		called on DOM faults, require fault etc in content
		//		default is to display errormessage inside pane
	},

	onDownloadError: function(/*Error*/ error){
		// summary:
		//		Called when download error occurs, default is to display
		//		errormessage inside pane. Overide function to change that.
		//		The string returned by this function will be the html
		//		that tells the user a error happend
		return this.errorMessage;
	},

	onDownloadEnd: function(){
		// summary:
		//		called when download is finished
	}
});

}

if(!dojo._hasResource["dojox.widget.FisheyeList"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojox.widget.FisheyeList"] = true;
dojo.provide("dojox.widget.FisheyeList");





dojo.declare("dojox.widget.FisheyeList", [dijit._Widget, dijit._Templated, dijit._Container], {
	// summary:
	//	Menu similar to the fish eye menu on the Mac OS
	// example:
	// |	<div dojoType="FisheyeList"
	// |		itemWidth="40" itemHeight="40"
	// |		itemMaxWidth="150" itemMaxHeight="150"
	// |		orientation="horizontal"
	// |		effectUnits="2"
	// |		itemPadding="10"
	// |		attachEdge="center"
	// |		labelEdge="bottom">
	// |
	// |		<div dojoType="FisheyeListItem"
	// |			id="item1"
	// |			onclick="alert('click on' + this.label + '(from widget id ' + this.widgetId + ')!');"
	// |			label="Item 1"
	// |			iconSrc="images/fisheye_1.png">
	// |		</div>
	// |		...
	// |	</div>
	//
	constructor: function(){
		//
		// TODO
		// fix really long labels in vertical mode
		//
	
		this.pos = {'x': -1, 'y': -1};	// current cursor position, relative to the grid
		
		// for conservative trigger mode, when triggered, timerScale is gradually increased from 0 to 1
		this.timerScale = 1.0;
	
	},

	EDGE: {
		CENTER: 0,
		LEFT: 1,
		RIGHT: 2,
		TOP: 3,
		BOTTOM: 4
	},

	templateString: '<div class="dojoxFisheyeListBar" dojoAttachPoint="containerNode"></div>',

	snarfChildDomOutput: true,
	
	// itemWidth: Integer
	//	width of menu item (in pixels) in it's dormant state (when the mouse is far away)
	itemWidth: 40,
	
	// itemHeight: Integer
	//	height of menu item (in pixels) in it's dormant state (when the mouse is far away)
	itemHeight: 40,
	
	// itemMaxWidth: Integer
	//	width of menu item (in pixels) in it's fully enlarged state (when the mouse is directly over it)
	itemMaxWidth: 150,
	
	// itemMaxHeight: Integer
	//	height of menu item (in pixels) in it's fully enlarged state (when the mouse is directly over it)
	itemMaxHeight: 150,

	imgNode: null,
	
	// orientation: String
	//	orientation of the menu, either "horizontal" or "vertical"
	orientation: 'horizontal',

	// isFixed: Boolean
	//	toggle to enable additional listener (window scroll) if FisheyeList is in a fixed postion
	isFixed: false,
	
	// conservativeTrigger: Boolean
	//	if true, don't start enlarging menu items until mouse is over an image;
	//	if false, start enlarging menu items as the mouse moves near them.
	conservativeTrigger: false,
	
	// effectUnits: Number
	//	controls how much reaction the menu makes, relative to the distance of the mouse from the menu
	effectUnits: 2,
		
	// itemPadding: Integer
	//	padding (in pixels) betweeen each menu item
	itemPadding: 10,
	
	// attachEdge: String
	//	controls the border that the menu items don't expand past;
	//	for example, if set to "top", then the menu items will drop downwards as they expand.
	// values
	//	"center", "left", "right", "top", "bottom".
	attachEdge: 'center',

	// labelEdge: String
	//	controls were the labels show up in relation to the menu item icons
	// values
	//	"center", "left", "right", "top", "bottom".
	labelEdge: 'bottom',

	postCreate: function(){
		var e = this.EDGE;
		dojo.setSelectable(this.domNode, false);

		var isHorizontal = this.isHorizontal = (this.orientation == 'horizontal');
		this.selectedNode = -1;

		this.isOver = false;
		this.hitX1 = -1;
		this.hitY1 = -1;
		this.hitX2 = -1;
		this.hitY2 = -1;

		//
		// only some edges make sense...
		//
		this.anchorEdge = this._toEdge(this.attachEdge, e.CENTER);
		this.labelEdge  = this._toEdge(this.labelEdge,  e.TOP);

		if(this.labelEdge == e.CENTER){ this.labelEdge = e.TOP; }

		if(isHorizontal){
			if(this.anchorEdge == e.LEFT){ this.anchorEdge = e.CENTER; }
			if(this.anchorEdge == e.RIGHT){ this.anchorEdge = e.CENTER; }
			if(this.labelEdge == e.LEFT){ this.labelEdge = e.TOP; }
			if(this.labelEdge == e.RIGHT){ this.labelEdge = e.TOP; }
		}else{
			if(this.anchorEdge == e.TOP){ this.anchorEdge = e.CENTER; }
			if(this.anchorEdge == e.BOTTOM){ this.anchorEdge = e.CENTER; }
			if(this.labelEdge == e.TOP){ this.labelEdge = e.LEFT; }
			if(this.labelEdge == e.BOTTOM){ this.labelEdge = e.LEFT; }
		}

		//
		// figure out the proximity size
		//
		var effectUnits = this.effectUnits;
		this.proximityLeft   = this.itemWidth  * (effectUnits - 0.5);
		this.proximityRight  = this.itemWidth  * (effectUnits - 0.5);
		this.proximityTop    = this.itemHeight * (effectUnits - 0.5);
		this.proximityBottom = this.itemHeight * (effectUnits - 0.5);
	
		if(this.anchorEdge == e.LEFT){
			this.proximityLeft = 0;
		}
		if(this.anchorEdge == e.RIGHT){
			this.proximityRight = 0;
		}
		if(this.anchorEdge == e.TOP){
			this.proximityTop = 0;
		}
		if(this.anchorEdge == e.BOTTOM){
			this.proximityBottom = 0;
		}
		if(this.anchorEdge == e.CENTER){
			this.proximityLeft   /= 2;
			this.proximityRight  /= 2;
			this.proximityTop    /= 2;
			this.proximityBottom /= 2;
		}
	},
	
	startup: function(){
		// summary: create our connections and setup our FisheyeList
		this.children = this.getChildren();
		//original postCreate() --tk
		this._initializePositioning();
	
		//
		// in liberal trigger mode, activate menu whenever mouse is close
		//
		if(!this.conservativeTrigger){
			this._onMouseMoveHandle = dojo.connect(document.documentElement, "onmousemove", this, "_onMouseMove");
		}
		if (this.isFixed){
			this._onScrollHandle = dojo.connect(document,"onscroll",this,"_onScroll");
		}
			
		// Deactivate the menu if mouse is moved off screen (doesn't work for FF?)
		this._onMouseOutHandle = dojo.connect(document.documentElement, "onmouseout", this, "_onBodyOut");
		this._addChildHandle = dojo.connect(this, "addChild", this, "_initializePositioning");
		this._onResizeHandle = dojo.connect(window,"onresize", this, "_initializePositioning");
	},
	
	_initializePositioning: function(){
		this.itemCount = this.children.length;
	
		this.barWidth  = (this.isHorizontal ? this.itemCount : 1) * this.itemWidth;
		this.barHeight = (this.isHorizontal ? 1 : this.itemCount) * this.itemHeight;
	
		this.totalWidth  = this.proximityLeft + this.proximityRight  + this.barWidth;
		this.totalHeight = this.proximityTop  + this.proximityBottom + this.barHeight;
	
		//
		// calculate effect ranges for each item
		//

		for(var i=0; i<this.children.length; i++){

			this.children[i].posX = this.itemWidth  * (this.isHorizontal ? i : 0);
			this.children[i].posY = this.itemHeight * (this.isHorizontal ? 0 : i);

			this.children[i].cenX = this.children[i].posX + (this.itemWidth  / 2);
			this.children[i].cenY = this.children[i].posY + (this.itemHeight / 2);

			var isz = this.isHorizontal ? this.itemWidth : this.itemHeight;
			var r = this.effectUnits * isz;
			var c = this.isHorizontal ? this.children[i].cenX : this.children[i].cenY;
			var lhs = this.isHorizontal ? this.proximityLeft : this.proximityTop;
			var rhs = this.isHorizontal ? this.proximityRight : this.proximityBottom;
			var siz = this.isHorizontal ? this.barWidth : this.barHeight;

			var range_lhs = r;
			var range_rhs = r;

			if(range_lhs > c+lhs){ range_lhs = c+lhs; }
			if(range_rhs > (siz-c+rhs)){ range_rhs = siz-c+rhs; }

			this.children[i].effectRangeLeft = range_lhs / isz;
			this.children[i].effectRangeRght = range_rhs / isz;

			//dojo.debug('effect range for '+i+' is '+range_lhs+'/'+range_rhs);
		}

		//
		// create the bar
		//
		this.domNode.style.width = this.barWidth + 'px';
		this.domNode.style.height = this.barHeight + 'px';

		//
		// position the items
		//
		for(var i=0; i<this.children.length; i++){
			var itm = this.children[i];
			var elm = itm.domNode;
			elm.style.left   = itm.posX + 'px';
			elm.style.top    = itm.posY + 'px';
			elm.style.width  = this.itemWidth + 'px';
			elm.style.height = this.itemHeight + 'px';
			
			itm.imgNode.style.left = this.itemPadding+'%';
			itm.imgNode.style.top = this.itemPadding+'%';
			itm.imgNode.style.width = (100 - 2 * this.itemPadding) + '%';
			itm.imgNode.style.height = (100 - 2 * this.itemPadding) + '%';
		}

		//
		// calc the grid
		//
		this._calcHitGrid();
	},

	_overElement: function(/* DomNode|String */node, /* Event */e){
		// summary:
		//	Returns whether the mouse is over the passed element.
		// Node: Must must be display:block (ie, not a <span>)
		node = dojo.byId(node);
		var mouse = {x: e.pageX, y: e.pageY};
		var bb = dojo._getBorderBox(node);
		var absolute = dojo.coords(node, true);
		var top = absolute.y;
		var bottom = top + bb.h;
		var left = absolute.x;
		var right = left + bb.w;

		return (mouse.x >= left
			&& mouse.x <= right
			&& mouse.y >= top
			&& mouse.y <= bottom
		);	//	boolean
	},

	_onBodyOut: function(/*Event*/ e){
		// clicking over an object inside of body causes this event to fire; ignore that case
		if( this._overElement(dojo.body(), e) ){
			return;
		}
		this._setDormant(e);
	},

	_setDormant: function(/*Event*/ e){
		// summary: called when mouse moves out of menu's range

		if(!this.isOver){ return; }	// already dormant?
		this.isOver = false;

		if(this.conservativeTrigger){
			// user can't re-trigger the menu expansion
			// until he mouses over a icon again
			dojo.disconnect(this._onMouseMoveHandle);
		}
		this._onGridMouseMove(-1, -1);
	},

	_setActive: function(/*Event*/ e){
		// summary: called when mouse is moved into menu's range

		if(this.isOver){ return; }	// already activated?
		this.isOver = true;

		if(this.conservativeTrigger){
			// switch event handlers so that we handle mouse events from anywhere near
			// the menu
			this._onMouseMoveHandle = dojo.connect(document.documentElement, "onmousemove", this, "_onMouseMove");

			this.timerScale=0.0;

			// call mouse handler to do some initial necessary calculations/positioning
			this._onMouseMove(e);

			// slowly expand the icon size so it isn't jumpy
			this._expandSlowly();
		}
	},

	_onMouseMove: function(/*Event*/ e){
		// summary: called when mouse is moved
		if(	(e.pageX >= this.hitX1) && (e.pageX <= this.hitX2) &&
			(e.pageY >= this.hitY1) && (e.pageY <= this.hitY2)	){
			if(!this.isOver){
				this._setActive(e);
			}
			this._onGridMouseMove(e.pageX-this.hitX1, e.pageY-this.hitY1);
		}else{
			if(this.isOver){
				this._setDormant(e);
			}
		}
	},

	_onScroll: function(){
		this._calcHitGrid();	
	},

	onResized: function(){
		this._calcHitGrid();
	},

	_onGridMouseMove: function(x, y){
		// summary: called when mouse is moved in the vicinity of the menu
		this.pos = {x:x, y:y};
		this._paint();
	},

	_paint: function(){
		var x=this.pos.x;
		var y=this.pos.y;

		if(this.itemCount <= 0){ return; }

		//
		// figure out our main index
		//
		var pos = this.isHorizontal ? x : y;
		var prx = this.isHorizontal ? this.proximityLeft : this.proximityTop;
		var siz = this.isHorizontal ? this.itemWidth : this.itemHeight;
		var sim = this.isHorizontal ? 
			(1.0-this.timerScale)*this.itemWidth + this.timerScale*this.itemMaxWidth :
			(1.0-this.timerScale)*this.itemHeight + this.timerScale*this.itemMaxHeight ;

		var cen = ((pos - prx) / siz) - 0.5;
		var max_off_cen = (sim / siz) - 0.5;

		if(max_off_cen > this.effectUnits){ max_off_cen = this.effectUnits; }

		//
		// figure out our off-axis weighting
		//
		var off_weight = 0;

		if(this.anchorEdge == this.EDGE.BOTTOM){
			var cen2 = (y - this.proximityTop) / this.itemHeight;
			off_weight = (cen2 > 0.5) ? 1 : y / (this.proximityTop + (this.itemHeight / 2));
		}
		if(this.anchorEdge == this.EDGE.TOP){
			var cen2 = (y - this.proximityTop) / this.itemHeight;
			off_weight = (cen2 < 0.5) ? 1 : (this.totalHeight - y) / (this.proximityBottom + (this.itemHeight / 2));
		}
		if(this.anchorEdge == this.EDGE.RIGHT){
			var cen2 = (x - this.proximityLeft) / this.itemWidth;
			off_weight = (cen2 > 0.5) ? 1 : x / (this.proximityLeft + (this.itemWidth / 2));
		}
		if(this.anchorEdge == this.EDGE.LEFT){
			var cen2 = (x - this.proximityLeft) / this.itemWidth;
			off_weight = (cen2 < 0.5) ? 1 : (this.totalWidth - x) / (this.proximityRight + (this.itemWidth / 2));
		}
		if(this.anchorEdge == this.EDGE.CENTER){
			if(this.isHorizontal){
				off_weight = y / (this.totalHeight);
			}else{
				off_weight = x / (this.totalWidth);
			}

			if(off_weight > 0.5){
				off_weight = 1 - off_weight;
			}

			off_weight *= 2;
		}

		//
		// set the sizes
		//
		for(var i=0; i<this.itemCount; i++){
			var weight = this._weighAt(cen, i);
			if(weight < 0){weight = 0;}
			this._setItemSize(i, weight * off_weight);
		}

		//
		// set the positions
		//

		var main_p = Math.round(cen);
		var offset = 0;

		if(cen < 0){

			main_p = 0;

		}else if(cen > this.itemCount - 1){

			main_p = this.itemCount -1;

		}else{

			offset = (cen - main_p) * ((this.isHorizontal ? this.itemWidth : this.itemHeight) - this.children[main_p].sizeMain);
		}

		this._positionElementsFrom(main_p, offset);
	},

	_weighAt: function(/*Integer*/ cen, /*Integer*/ i){
		var dist = Math.abs(cen - i);
		var limit = ((cen - i) > 0) ? this.children[i].effectRangeRght : this.children[i].effectRangeLeft;
		return (dist > limit) ? 0 : (1 - dist / limit); // Integer
	},

	_setItemSize: function(p, scale){
		scale *= this.timerScale;
		var w = Math.round(this.itemWidth  + ((this.itemMaxWidth  - this.itemWidth ) * scale));
		var h = Math.round(this.itemHeight + ((this.itemMaxHeight - this.itemHeight) * scale));

		if(this.isHorizontal){

			this.children[p].sizeW = w;
			this.children[p].sizeH = h;

			this.children[p].sizeMain = w;
			this.children[p].sizeOff  = h;

			var y = 0;
			if(this.anchorEdge == this.EDGE.TOP){
				y = (this.children[p].cenY - (this.itemHeight / 2));
			}else if(this.anchorEdge == this.EDGE.BOTTOM){
				y = (this.children[p].cenY - (h - (this.itemHeight / 2)));
			}else{
				y = (this.children[p].cenY - (h / 2));
			}

			this.children[p].usualX = Math.round(this.children[p].cenX - (w / 2));
			this.children[p].domNode.style.top  = y + 'px';
			this.children[p].domNode.style.left  = this.children[p].usualX + 'px';

		}else{

			this.children[p].sizeW = w;
			this.children[p].sizeH = h;

			this.children[p].sizeOff  = w;
			this.children[p].sizeMain = h;

			var x = 0;
			if(this.anchorEdge == this.EDGE.LEFT){
				x = this.children[p].cenX - (this.itemWidth / 2);
			}else if (this.anchorEdge == this.EDGE.RIGHT){
				x = this.children[p].cenX - (w - (this.itemWidth / 2));
			}else{
				x = this.children[p].cenX - (w / 2);
			}

			this.children[p].domNode.style.left = x + 'px';
			this.children[p].usualY = Math.round(this.children[p].cenY - (h / 2));

			this.children[p].domNode.style.top  = this.children[p].usualY + 'px';
		}

		this.children[p].domNode.style.width  = w + 'px';
		this.children[p].domNode.style.height = h + 'px';

		if(this.children[p].svgNode){
			this.children[p].svgNode.setSize(w, h);
		}
	},

	_positionElementsFrom: function(p, offset){
		var pos = 0;

		if(this.isHorizontal){
			pos = Math.round(this.children[p].usualX + offset);
			this.children[p].domNode.style.left = pos + 'px';
		}else{
			pos = Math.round(this.children[p].usualY + offset);
			this.children[p].domNode.style.top = pos + 'px';
		}
		this._positionLabel(this.children[p]);

		// position before
		var bpos = pos;
		for(var i=p-1; i>=0; i--){
			bpos -= this.children[i].sizeMain;

			if (this.isHorizontal){
				this.children[i].domNode.style.left = bpos + 'px';
			}else{
				this.children[i].domNode.style.top = bpos + 'px';
			}
			this._positionLabel(this.children[i]);
		}

		// position after
		var apos = pos;
		for(var i=p+1; i<this.itemCount; i++){
			apos += this.children[i-1].sizeMain;
			if(this.isHorizontal){
				this.children[i].domNode.style.left = apos + 'px';
			}else{
				this.children[i].domNode.style.top = apos + 'px';
			}
			this._positionLabel(this.children[i]);
		}

	},

	_positionLabel: function(itm){
		var x = 0;
		var y = 0;
		
		var mb = dojo.marginBox(itm.lblNode);

		if(this.labelEdge == this.EDGE.TOP){
			x = Math.round((itm.sizeW / 2) - (mb.w / 2));
			y = -mb.h;
		}

		if(this.labelEdge == this.EDGE.BOTTOM){
			x = Math.round((itm.sizeW / 2) - (mb.w / 2));
			y = itm.sizeH;
		}

		if(this.labelEdge == this.EDGE.LEFT){
			x = -mb.w;
			y = Math.round((itm.sizeH / 2) - (mb.h / 2));
		}

		if(this.labelEdge == this.EDGE.RIGHT){
			x = itm.sizeW;
			y = Math.round((itm.sizeH / 2) - (mb.h / 2));
		}

		itm.lblNode.style.left = x + 'px';
		itm.lblNode.style.top  = y + 'px';
	},

	_calcHitGrid: function(){

		var pos = dojo.coords(this.domNode, true);

		this.hitX1 = pos.x - this.proximityLeft;
		this.hitY1 = pos.y - this.proximityTop;
		this.hitX2 = this.hitX1 + this.totalWidth;
		this.hitY2 = this.hitY1 + this.totalHeight;

	},

	_toEdge: function(inp, def){
		return this.EDGE[inp.toUpperCase()] || def;
	},

	_expandSlowly: function(){
		// summary: slowly expand the image to user specified max size
		if(!this.isOver){ return; }
		this.timerScale += 0.2;
		this._paint();
		if(this.timerScale<1.0){
			setTimeout(dojo.hitch(this, "_expandSlowly"), 10);
		}
	},

	destroyRecursive: function(){
		// need to disconnect when we destroy
		dojo.disconnect(this._onMouseOutHandle);
		dojo.disconnect(this._onMouseMoveHandle);
		dojo.disconnect(this._addChildHandle);
		if (this.isFixed) { dojo.disconnect(this._onScrollHandle); }
		dojo.disconnect(this._onResizeHandle); 
		this.inherited("destroyRecursive",arguments);
	}
});

dojo.declare("dojox.widget.FisheyeListItem", [dijit._Widget, dijit._Templated, dijit._Contained], {
	/*
	 * summary
	 *	Menu item inside of a FisheyeList.
	 *	See FisheyeList documentation for details on usage.
	 */

	// iconSrc: String
	//	pathname to image file (jpg, gif, png, etc.) of icon for this menu item
	iconSrc: "",

	// label: String
	//	label to print next to the icon, when it is moused-over
	label: "",

	// id: String
	//	will be set to the id of the orginal div element
	id: "",

	_blankImgPath: dojo.moduleUrl("dojo", "resources/blank.gif"),

	templateString:
		'<div class="dojoxFisheyeListItem">' +
		'  <img class="dojoxFisheyeListItemImage" dojoAttachPoint="imgNode" dojoAttachEvent="onmouseover:onMouseOver,onmouseout:onMouseOut,onclick:onClick">' +
		'  <div class="dojoxFisheyeListItemLabel" dojoAttachPoint="lblNode"></div>' +
		'</div>',

	_isNode: function(/* object */wh){
		//	summary:
		//		checks to see if wh is actually a node.
		if(typeof Element == "function") {
			try{
				return wh instanceof Element;	//	boolean
			}catch(e){}
		}else{
			// best-guess
			return wh && !isNaN(wh.nodeType);	//	boolean
		}
	},

	_hasParent: function(/*Node*/node){
		//	summary:
		//		returns whether or not node is a child of another node.
		return Boolean(node && node.parentNode && this._isNode(node.parentNode));	//	boolean
	},

	postCreate: function() {

		// set image
		if((this.iconSrc.toLowerCase().substring(this.iconSrc.length-4)==".png")&&(dojo.isIE)&&(dojo.isIE<7)){
			/* we set the id of the new fisheyeListItem to the id of the div defined in the HTML */
			if(this._hasParent(this.imgNode) && this.id != ""){
				var parent = this.imgNode.parentNode;
				parent.setAttribute("id", this.id);
			}
			this.imgNode.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(src='"+this.iconSrc+"', sizingMethod='scale')";
			this.imgNode.src = this._blankImgPath.toString();
		}else{
			if(this._hasParent(this.imgNode) && this.id != ""){
				var parent = this.imgNode.parentNode;
				parent.setAttribute("id", this.id);
			}
			this.imgNode.src = this.iconSrc;
		}

		// Label
		if(this.lblNode){
			this.lblNode.appendChild(document.createTextNode(this.label));
		}
		dojo.setSelectable(this.domNode, false);
		this.startup();
	},

	startup: function(){
		this.parent = this.getParent();
	},
	
	onMouseOver: function(/*Event*/ e){
		// summary: callback when user moves mouse over this menu item
		// in conservative mode, don't activate the menu until user mouses over an icon
		if(!this.parent.isOver){
			this.parent._setActive(e);
		}
		if(this.label != "" ){
			dojo.addClass(this.lblNode, "dojoxFishSelected");
			this.parent._positionLabel(this);
		}
	},
	
	onMouseOut: function(/*Event*/ e){
		// summary: callback when user moves mouse off of this menu item
		dojo.removeClass(this.lblNode, "dojoxFishSelected");
	},

	onClick: function(/*Event*/ e){
		// summary: user overridable callback when user clicks this menu item
	}
});

}

if(!dojo._hasResource["dojox.widget.Toaster"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojox.widget.Toaster"] = true;
dojo.provide("dojox.widget.Toaster");





dojo.declare("dojox.widget.Toaster", [dijit._Widget, dijit._Templated], {
		// summary:
		//		Message that slides in from the corner of the screen, used for notifications
		//		like "new email".
		
		templateString: '<div dojoAttachPoint="clipNode"><div dojoAttachPoint="containerNode" dojoAttachEvent="onclick:onSelect"><div dojoAttachPoint="contentNode"></div></div></div>',

		// messageTopic: String
		//		Name of topic; anything published to this topic will be displayed as a message.
		//		Message format is either String or an object like
		//		{message: "hello word", type: "error", duration: 500}
		messageTopic: "",

		_uniqueId: 0,
		
		// messageTypes: Enumeration
		//		Possible message types.
		messageTypes: {
			MESSAGE: "message",
			WARNING: "warning",
			ERROR: "error",
			FATAL: "fatal"
		},

		// defaultType: String
		//		If message type isn't specified (see "messageTopic" parameter),
		//		then display message as this type.
		//		Possible values in messageTypes enumeration ("message", "warning", "error", "fatal")
		defaultType: "message",

		// positionDirection: String
		//		Position from which message slides into screen, one of
		//		["br-up", "br-left", "bl-up", "bl-right", "tr-down", "tr-left", "tl-down", "tl-right"]
		positionDirection: "br-up",
		
		// positionDirectionTypes: Array
		//		Possible values for positionDirection parameter
		positionDirectionTypes: ["br-up", "br-left", "bl-up", "bl-right", "tr-down", "tr-left", "tl-down", "tl-right"],

		// duration: Integer
		//		Number of milliseconds to show message
		duration: "2000",

		//separator: String
		//		String used to separate messages if consecutive calls are made to setContent before previous messages go away
		separator: "<hr></hr>",

		postCreate: function(){
			this.inherited(arguments);
			this.hide();

			this.clipNode.className = "dijitToasterClip";
			this.containerNode.className += " dijitToasterContainer";
			this.contentNode.className = "dijitToasterContent";
			if(this.messageTopic){
				dojo.subscribe(this.messageTopic, this, "_handleMessage");
			}
		},

		_handleMessage: function(/*String|Object*/message){
			if(dojo.isString(message)){
				this.setContent(message);
			}else{
				this.setContent(message.message, message.type, message.duration);
			}
		},

		_capitalize: function(/* String */w){
				return w.substring(0,1).toUpperCase() + w.substring(1);
		},

		setContent: function(/*String*/message, /*String*/messageType, /*int?*/duration){
			// summary:
			//		sets and displays the given message and show duration
			// message:
			//		the message
			// messageType:
			//		type of message; possible values in messageTypes enumeration ("message", "warning", "error", "fatal")
			// duration:
			//		duration in milliseconds to display message before removing it. Widget has default value.
			duration = duration||this.duration;
			// sync animations so there are no ghosted fades and such
			if(this.slideAnim){
				if(this.slideAnim.status() != "playing"){
					this.slideAnim.stop();
				}
				if(this.slideAnim.status() == "playing" || (this.fadeAnim && this.fadeAnim.status() == "playing")){
					setTimeout(dojo.hitch(this, function(){
						this.setContent(message, messageType, duration);
					}), 50);
					return;
				}
			}

			// determine type of content and apply appropriately
			for(var type in this.messageTypes){
				dojo.removeClass(this.containerNode, "dijitToaster" + this._capitalize(this.messageTypes[type]));
			}

			dojo.style(this.containerNode, "opacity", 1);

			if(message && this.isVisible){
				message = this.contentNode.innerHTML + this.separator + message;
			}
			this.contentNode.innerHTML = message;

			dojo.addClass(this.containerNode, "dijitToaster" + this._capitalize(messageType || this.defaultType));

			// now do funky animation of widget appearing from
			// bottom right of page and up
			this.show();
			var nodeSize = dojo.marginBox(this.containerNode);
			this._cancelHideTimer();
			if(this.isVisible){
				this._placeClip();
				//update hide timer if no sticky message in stack 
				if (!this._stickyMessage)
					this._setHideTimer(duration);

			}else{
				var style = this.containerNode.style;
				var pd = this.positionDirection;
				// sets up initial position of container node and slide-out direction
				if(pd.indexOf("-up") >= 0){
					style.left=0+"px";
					style.top=nodeSize.h + 10 + "px";
				}else if(pd.indexOf("-left") >= 0){
					style.left=nodeSize.w + 10 +"px";
					style.top=0+"px";
				}else if(pd.indexOf("-right") >= 0){
					style.left = 0 - nodeSize.w - 10 + "px";
					style.top = 0+"px";
				}else if(pd.indexOf("-down") >= 0){
					style.left = 0+"px";
					style.top = 0 - nodeSize.h - 10 + "px";
				}else{
					throw new Error(this.id + ".positionDirection is invalid: " + pd);
				}
				this.slideAnim = dojo.fx.slideTo({
					node: this.containerNode,
					top: 0, left: 0,
					duration: 450});
				this.connect(this.slideAnim, "onEnd", function(nodes, anim){
						//we build the fadeAnim here so we dont have to duplicate it later
						// can't do a fadeHide because we're fading the
						// inner node rather than the clipping node
						this.fadeAnim = dojo.fadeOut({
							node: this.containerNode,
							duration: 1000});
						this.connect(this.fadeAnim, "onEnd", function(evt){
							this.isVisible = false;
							this.hide();
						});
						this._setHideTimer(duration);
						this.connect(this, 'onSelect', function(evt){
							this._cancelHideTimer();
							//force clear sticky message
							this._stickyMessage=false;
							this.fadeAnim.play();
						});

						this.isVisible = true;
					});
				this.slideAnim.play();
			}
		},
		
		_cancelHideTimer:function(){
			if (this._hideTimer){
				clearTimeout(this._hideTimer);
				this._hideTimer=null;
			}
		},
		
		_setHideTimer:function(duration){
			this._cancelHideTimer();
			//if duration == 0 we keep the message displayed until clicked
			if(duration>0){
				this._cancelHideTimer();
				this._hideTimer=setTimeout(dojo.hitch(this, function(evt){
					// we must hide the iframe in order to fade
					// TODO: figure out how to fade with a BackgroundIframe
					if(this.bgIframe && this.bgIframe.iframe){
						this.bgIframe.iframe.style.display="none";
					}
					this._hideTimer=null;
					//force clear sticky message
					this._stickyMessage=false;
					this.fadeAnim.play();
				}), duration);
			}
			else
				this._stickyMessage=true;
		},
		
		_placeClip: function(){
			var view = dijit.getViewport();

			var nodeSize = dojo.marginBox(this.containerNode);

			var style = this.clipNode.style;
			// sets up the size of the clipping node
			style.height = nodeSize.h+"px";
			style.width = nodeSize.w+"px";

			// sets up the position of the clipping node
			var pd = this.positionDirection;
			if(pd.match(/^t/)){
				style.top = view.t+"px";
			}else if(pd.match(/^b/)){
				style.top = (view.h - nodeSize.h - 2 + view.t)+"px";
			}
			if(pd.match(/^[tb]r-/)){
				style.left = (view.w - nodeSize.w - 1 - view.l)+"px";
			}else if(pd.match(/^[tb]l-/)){
				style.left = 0 + "px";
			}

			style.clip = "rect(0px, " + nodeSize.w + "px, " + nodeSize.h + "px, 0px)";
			if(dojo.isIE){
				if(!this.bgIframe){
					this.clipNode.id = "__dojoXToaster_"+this._uniqueId++;
					this.bgIframe = new dijit.BackgroundIframe(this.clipNode);
				}
				var iframe = this.bgIframe.iframe;
				if(iframe){ iframe.style.display="block"; }
			}
		},

		onSelect: function(/*Event*/e){
			// summary: callback for when user clicks the message
		},

		show: function(){
			// summary: show the Toaster
			dojo.style(this.domNode, 'display', 'block');

			this._placeClip();

			if(!this._scrollConnected){
				this._scrollConnected = dojo.connect(window, "onscroll", this, this._placeClip);
			}
		},

		hide: function(){
			// summary: hide the Toaster

			dojo.style(this.domNode, 'display', 'none');

			if(this._scrollConnected){
				dojo.disconnect(this._scrollConnected);
				this._scrollConnected = false;
			}

			dojo.style(this.containerNode, "opacity", 1);
		}
	}
);

}

if(!dojo._hasResource["dojox.grid._grid.lib"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojox.grid._grid.lib"] = true;
dojo.provide("dojox.grid._grid.lib");
// summary: grid utility library
dojo.mixin(dojox.grid,{

	na: '...',

	nop: function(){
		// summary: a null function?
	},

	getTdIndex: function(td){
		return td.cellIndex >=0 ? td.cellIndex : dojo.indexOf(td.parentNode.cells, td);
	},
	
	getTrIndex: function(tr){
		return tr.rowIndex >=0 ? tr.rowIndex : dojo.indexOf(tr.parentNode.childNodes, tr);
	},
	
	getTr: function(rowOwner, index){
		return rowOwner && ((rowOwner.rows||0)[index] || rowOwner.childNodes[index]);
	},
	
	getTd: function(rowOwner, rowIndex, cellIndex){
		return (dojox.grid.getTr(inTable, rowIndex)||0)[cellIndex];
	},
	
	findTable: function(node){
		for (var n=node; n && n.tagName!='TABLE'; n=n.parentNode);
		return n;
	},
	
	ascendDom: function(inNode, inWhile){
		for (var n=inNode; n && inWhile(n); n=n.parentNode);
		return n;
	},
	
	makeNotTagName: function(inTagName){
		var name = inTagName.toUpperCase();
		return function(node){ return node.tagName != name; };
	},
	
	fire: function(ob, ev, args){
		var fn = ob && ev && ob[ev];
		return fn && (args ? fn.apply(ob, args) : ob[ev]());
	},
	
	// from lib.js
	setStyleText: function(inNode, inStyleText){
		if(inNode.style.cssText == undefined){
			inNode.setAttribute("style", inStyleText);
		}else{
			inNode.style.cssText = inStyleText;
		}
	},
	
	getStyleText: function(inNode, inStyleText){
		return (inNode.style.cssText == undefined ? inNode.getAttribute("style") : inNode.style.cssText);
	},
	
	setStyle: function(inElement, inStyle, inValue){
		if(inElement && inElement.style[inStyle] != inValue){
			inElement.style[inStyle] = inValue;
		}
	},
	
	setStyleHeightPx: function(inElement, inHeight){
		if(inHeight >= 0){
			dojox.grid.setStyle(inElement, 'height', inHeight + 'px');
		}
	},
	
	mouseEvents: [ 'mouseover', 'mouseout', /*'mousemove',*/ 'mousedown', 'mouseup', 'click', 'dblclick', 'contextmenu' ],

	keyEvents: [ 'keyup', 'keydown', 'keypress' ],

	funnelEvents: function(inNode, inObject, inMethod, inEvents){
		var evts = (inEvents ? inEvents : dojox.grid.mouseEvents.concat(dojox.grid.keyEvents));
		for (var i=0, l=evts.length; i<l; i++){
			dojo.connect(inNode, 'on' + evts[i], inObject, inMethod);
		}
	},

	removeNode: function(inNode){
		inNode = dojo.byId(inNode);
		inNode && inNode.parentNode && inNode.parentNode.removeChild(inNode);
		return inNode;
	},
	
	getScrollbarWidth: function(){
		if(this._scrollBarWidth){
			return this._scrollBarWidth;
		}
		this._scrollBarWidth = 18;
		try{
			var e = document.createElement("div");
			e.style.cssText = "top:0;left:0;width:100px;height:100px;overflow:scroll;position:absolute;visibility:hidden;";
			document.body.appendChild(e);
			this._scrollBarWidth = e.offsetWidth - e.clientWidth;
			document.body.removeChild(e);
			delete e;
		}catch (ex){}
		return this._scrollBarWidth;
	},
	
	// needed? dojo has _getProp
	getRef: function(name, create, context){
		var obj=context||dojo.global, parts=name.split("."), prop=parts.pop();
		for(var i=0, p; obj&&(p=parts[i]); i++){
			obj = (p in obj ? obj[p] : (create ? obj[p]={} : undefined));
		}
		return { obj: obj, prop: prop }; 
	},
	
	getProp: function(name, create, context){
		with(dojox.grid.getRef(name, create, context)){
			return (obj)&&(prop)&&(prop in obj ? obj[prop] : (create ? obj[prop]={} : undefined));
		}
	},
	
	indexInParent: function(inNode){
		var i=0, n, p=inNode.parentNode;
		while((n = p.childNodes[i++])){
			if(n == inNode){
				return i - 1;
			}
		}
		return -1;
	},
	
	cleanNode: function(inNode){
		if(!inNode){
			return;
		}
		var filter = function(inW){
			return inW.domNode && dojo.isDescendant(inW.domNode, inNode, true);
		}
		var ws = dijit.registry.filter(filter);
		for(var i=0, w; (w=ws[i]); i++){
			w.destroy();
		}
		delete ws;
	},
	
	getTagName: function(inNodeOrId){
		var node = dojo.byId(inNodeOrId);
		return (node && node.tagName ? node.tagName.toLowerCase() : '');
	},
	
	nodeKids: function(inNode, inTag){
		var result = [];
		var i=0, n;
		while((n = inNode.childNodes[i++])){
			if(dojox.grid.getTagName(n) == inTag){
				result.push(n);
			}
		}
		return result;
	},
	
	divkids: function(inNode){
		return dojox.grid.nodeKids(inNode, 'div');
	},
	
	focusSelectNode: function(inNode){
		try{
			dojox.grid.fire(inNode, "focus");
			dojox.grid.fire(inNode, "select");
		}catch(e){// IE sux bad
		}
	},
	
	whenIdle: function(/*inContext, inMethod, args ...*/){
		setTimeout(dojo.hitch.apply(dojo, arguments), 0);
	},
	
	arrayCompare: function(inA, inB){
		for(var i=0,l=inA.length; i<l; i++){
			if(inA[i] != inB[i]){return false;}
		}
		return (inA.length == inB.length);
	},
	
	arrayInsert: function(inArray, inIndex, inValue){
		if(inArray.length <= inIndex){
			inArray[inIndex] = inValue;
		}else{
			inArray.splice(inIndex, 0, inValue);
		}
	},
	
	arrayRemove: function(inArray, inIndex){
		inArray.splice(inIndex, 1);
	},
	
	arraySwap: function(inArray, inI, inJ){
		var cache = inArray[inI];
		inArray[inI] = inArray[inJ];
		inArray[inJ] = cache;
	},
	
	initTextSizePoll: function(inInterval) {
		var f = document.createElement("div");
		with (f.style) {
			top = "0px";
			left = "0px";
			position = "absolute";
			visibility = "hidden";
		}
		f.innerHTML = "TheQuickBrownFoxJumpedOverTheLazyDog";
		document.body.appendChild(f);
		var fw = f.offsetWidth;
		var job = function() {
			if (f.offsetWidth != fw) {
				fw = f.offsetWidth;
				dojox.grid.textSizeChanged();
			}
		}
		window.setInterval(job, inInterval||200);
		dojox.grid.initTextSizePoll = dojox.grid.nop;
	},
	
	textSizeChanged: function() {

	}
});

dojox.grid.jobs = {

	cancel: function(inHandle){
		if(inHandle){
			window.clearTimeout(inHandle);
		}
	},

	jobs: [],

	job: function(inName, inDelay, inJob){
		dojox.grid.jobs.cancelJob(inName);
		var job = function(){
			delete dojox.grid.jobs.jobs[inName];
			inJob();
		}
		dojox.grid.jobs.jobs[inName] = setTimeout(job, inDelay);
	},

	cancelJob: function(inName){
		dojox.grid.jobs.cancel(dojox.grid.jobs.jobs[inName]);
	}

}

}

if(!dojo._hasResource['dojox.grid._grid.scroller']){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource['dojox.grid._grid.scroller'] = true;
dojo.provide('dojox.grid._grid.scroller');

dojo.declare('dojox.grid.scroller.base', null, {
	// summary:
	//	virtual scrollbox, abstract class
	//	Content must in /rows/
	//	Rows are managed in contiguous sets called /pages/
	//	There are a fixed # of rows per page
	//	The minimum rendered unit is a page
	constructor: function(){
		this.pageHeights = [];
		this.stack = [];
	},
	// specified
	rowCount: 0, // total number of rows to manage
	defaultRowHeight: 10, // default height of a row
	keepRows: 100, // maximum number of rows that should exist at one time
	contentNode: null, // node to contain pages
	scrollboxNode: null, // node that controls scrolling
	// calculated
	defaultPageHeight: 0, // default height of a page
	keepPages: 10, // maximum number of pages that should exists at one time
	pageCount: 0,
	windowHeight: 0,
	firstVisibleRow: 0,
	lastVisibleRow: 0,
	// private
	page: 0,
	pageTop: 0,
	// init
	init: function(inRowCount, inKeepRows, inRowsPerPage){
		switch(arguments.length){
			case 3: this.rowsPerPage = inRowsPerPage;
			case 2: this.keepRows = inKeepRows;
			case 1: this.rowCount = inRowCount;
		}
		this.defaultPageHeight = this.defaultRowHeight * this.rowsPerPage;
		//this.defaultPageHeight = this.defaultRowHeight * Math.min(this.rowsPerPage, this.rowCount);
		this.pageCount = Math.ceil(this.rowCount / this.rowsPerPage);
		this.setKeepInfo(this.keepRows);
		this.invalidate();
		if(this.scrollboxNode){
			this.scrollboxNode.scrollTop = 0;
			this.scroll(0);
			this.scrollboxNode.onscroll = dojo.hitch(this, 'onscroll');
		}
	},
	setKeepInfo: function(inKeepRows){
		this.keepRows = inKeepRows;
		this.keepPages = !this.keepRows ? this.keepRows : Math.max(Math.ceil(this.keepRows / this.rowsPerPage), 2);
	},
	// updating
	invalidate: function(){
		this.invalidateNodes();
		this.pageHeights = [];
		this.height = (this.pageCount ? (this.pageCount - 1)* this.defaultPageHeight + this.calcLastPageHeight() : 0);
		this.resize();
	},
	updateRowCount: function(inRowCount){
		this.invalidateNodes();
		this.rowCount = inRowCount;
		// update page count, adjust document height
		oldPageCount = this.pageCount;
		this.pageCount = Math.ceil(this.rowCount / this.rowsPerPage);
		if(this.pageCount < oldPageCount){
			for(var i=oldPageCount-1; i>=this.pageCount; i--){
				this.height -= this.getPageHeight(i);
				delete this.pageHeights[i]
			}
		}else if(this.pageCount > oldPageCount){
			this.height += this.defaultPageHeight * (this.pageCount - oldPageCount - 1) + this.calcLastPageHeight();
		}
		this.resize();
	},
	// abstract interface
	pageExists: function(inPageIndex){
	},
	measurePage: function(inPageIndex){
	},
	positionPage: function(inPageIndex, inPos){
	},
	repositionPages: function(inPageIndex){
	},
	installPage: function(inPageIndex){
	},
	preparePage: function(inPageIndex, inPos, inReuseNode){
	},
	renderPage: function(inPageIndex){
	},
	removePage: function(inPageIndex){
	},
	pacify: function(inShouldPacify){
	},
	// pacification
	pacifying: false,
	pacifyTicks: 200,
	setPacifying: function(inPacifying){
		if(this.pacifying != inPacifying){
			this.pacifying = inPacifying;
			this.pacify(this.pacifying);
		}
	},
	startPacify: function(){
		this.startPacifyTicks = new Date().getTime();
	},
	doPacify: function(){
		var result = (new Date().getTime() - this.startPacifyTicks) > this.pacifyTicks;
		this.setPacifying(true);
		this.startPacify();
		return result;
	},
	endPacify: function(){
		this.setPacifying(false);
	},
	// default sizing implementation
	resize: function(){
		if(this.scrollboxNode){
			this.windowHeight = this.scrollboxNode.clientHeight;
		}
		dojox.grid.setStyleHeightPx(this.contentNode, this.height);
	},
	calcLastPageHeight: function(){
		if(!this.pageCount){
			return 0;
		}
		var lastPage = this.pageCount - 1;
		var lastPageHeight = ((this.rowCount % this.rowsPerPage)||(this.rowsPerPage)) * this.defaultRowHeight;
		this.pageHeights[lastPage] = lastPageHeight;
		return lastPageHeight;
	},
	updateContentHeight: function(inDh){
		this.height += inDh;
		this.resize();
	},
	updatePageHeight: function(inPageIndex){
		if(this.pageExists(inPageIndex)){
			var oh = this.getPageHeight(inPageIndex);
			var h = (this.measurePage(inPageIndex))||(oh);
			this.pageHeights[inPageIndex] = h;
			if((h)&&(oh != h)){
				this.updateContentHeight(h - oh)
				this.repositionPages(inPageIndex);
			}
		}
	},
	rowHeightChanged: function(inRowIndex){
		this.updatePageHeight(Math.floor(inRowIndex / this.rowsPerPage));
	},
	// scroller core
	invalidateNodes: function(){
		while(this.stack.length){
			this.destroyPage(this.popPage());
		}
	},
	createPageNode: function(){
		var p = document.createElement('div');
		p.style.position = 'absolute';
		//p.style.width = '100%';
		p.style[dojo._isBodyLtr() ? "left" : "right"] = '0';
		return p;
	},
	getPageHeight: function(inPageIndex){
		var ph = this.pageHeights[inPageIndex];
		return (ph !== undefined ? ph : this.defaultPageHeight);
	},
	// FIXME: this is not a stack, it's a FIFO list
	pushPage: function(inPageIndex){
		return this.stack.push(inPageIndex);
	},
	popPage: function(){
		return this.stack.shift();
	},
	findPage: function(inTop){
		var i = 0, h = 0;
		for(var ph = 0; i<this.pageCount; i++, h += ph){
			ph = this.getPageHeight(i);
			if(h + ph >= inTop){
				break;
			}
		}
		this.page = i;
		this.pageTop = h;
	},
	buildPage: function(inPageIndex, inReuseNode, inPos){
		this.preparePage(inPageIndex, inReuseNode);
		this.positionPage(inPageIndex, inPos);
		// order of operations is key below
		this.installPage(inPageIndex);
		this.renderPage(inPageIndex);
		// order of operations is key above
		this.pushPage(inPageIndex);
	},
	needPage: function(inPageIndex, inPos){
		var h = this.getPageHeight(inPageIndex), oh = h;
		if(!this.pageExists(inPageIndex)){
			this.buildPage(inPageIndex, this.keepPages&&(this.stack.length >= this.keepPages), inPos);
			h = this.measurePage(inPageIndex) || h;
			this.pageHeights[inPageIndex] = h;
			if(h && (oh != h)){
				this.updateContentHeight(h - oh)
			}
		}else{
			this.positionPage(inPageIndex, inPos);
		}
		return h;
	},
	onscroll: function(){
		this.scroll(this.scrollboxNode.scrollTop);
	},
	scroll: function(inTop){
		this.startPacify();
		this.findPage(inTop);
		var h = this.height;
		var b = this.getScrollBottom(inTop);
		for(var p=this.page, y=this.pageTop; (p<this.pageCount)&&((b<0)||(y<b)); p++){
			y += this.needPage(p, y);
		}
		this.firstVisibleRow = this.getFirstVisibleRow(this.page, this.pageTop, inTop);
		this.lastVisibleRow = this.getLastVisibleRow(p - 1, y, b);
		// indicates some page size has been updated
		if(h != this.height){
			this.repositionPages(p-1);
		}
		this.endPacify();
	},
	getScrollBottom: function(inTop){
		return (this.windowHeight >= 0 ? inTop + this.windowHeight : -1);
	},
	// events
	processNodeEvent: function(e, inNode){
		var t = e.target;
		while(t && (t != inNode) && t.parentNode && (t.parentNode.parentNode != inNode)){
			t = t.parentNode;
		}
		if(!t || !t.parentNode || (t.parentNode.parentNode != inNode)){
			return false;
		}
		var page = t.parentNode;
		e.topRowIndex = page.pageIndex * this.rowsPerPage;
		e.rowIndex = e.topRowIndex + dojox.grid.indexInParent(t);
		e.rowTarget = t;
		return true;
	},
	processEvent: function(e){
		return this.processNodeEvent(e, this.contentNode);
	},
	dummy: 0
});

dojo.declare('dojox.grid.scroller', dojox.grid.scroller.base, {
	// summary:
	//	virtual scroller class, makes no assumption about shape of items being scrolled
	constructor: function(){
		this.pageNodes = [];
	},
	// virtual rendering interface
	renderRow: function(inRowIndex, inPageNode){
	},
	removeRow: function(inRowIndex){
	},
	// page node operations
	getDefaultNodes: function(){
		return this.pageNodes;
	},
	getDefaultPageNode: function(inPageIndex){
		return this.getDefaultNodes()[inPageIndex];
	},
	positionPageNode: function(inNode, inPos){
		inNode.style.top = inPos + 'px';
	},
	getPageNodePosition: function(inNode){
		return inNode.offsetTop;
	},
	repositionPageNodes: function(inPageIndex, inNodes){
		var last = 0;
		for(var i=0; i<this.stack.length; i++){
			last = Math.max(this.stack[i], last);
		}
		//
		var n = inNodes[inPageIndex];
		var y = (n ? this.getPageNodePosition(n) + this.getPageHeight(inPageIndex) : 0);
		//console.log('detected height change, repositioning from #%d (%d) @ %d ', inPageIndex + 1, last, y, this.pageHeights[0]);
		//
		for(var p=inPageIndex+1; p<=last; p++){
			n = inNodes[p];
			if(n){
				//console.log('#%d @ %d', inPageIndex, y, this.getPageNodePosition(n));
				if(this.getPageNodePosition(n) == y){
					return;
				}
				//console.log('placing page %d at %d', p, y);
				this.positionPage(p, y);
			}
			y += this.getPageHeight(p);
		}
	},
	invalidatePageNode: function(inPageIndex, inNodes){
		var p = inNodes[inPageIndex];
		if(p){
			delete inNodes[inPageIndex];
			this.removePage(inPageIndex, p);
			dojox.grid.cleanNode(p);
			p.innerHTML = '';
		}
		return p;
	},
	preparePageNode: function(inPageIndex, inReusePageIndex, inNodes){
		var p = (inReusePageIndex === null ? this.createPageNode() : this.invalidatePageNode(inReusePageIndex, inNodes));
		p.pageIndex = inPageIndex;
		p.id = (this._pageIdPrefix || "") + 'page-' + inPageIndex;
		inNodes[inPageIndex] = p;
	},
	// implementation for page manager
	pageExists: function(inPageIndex){
		return Boolean(this.getDefaultPageNode(inPageIndex));
	},
	measurePage: function(inPageIndex){
		return this.getDefaultPageNode(inPageIndex).offsetHeight;
	},
	positionPage: function(inPageIndex, inPos){
		this.positionPageNode(this.getDefaultPageNode(inPageIndex), inPos);
	},
	repositionPages: function(inPageIndex){
		this.repositionPageNodes(inPageIndex, this.getDefaultNodes());
	},
	preparePage: function(inPageIndex, inReuseNode){
		this.preparePageNode(inPageIndex, (inReuseNode ? this.popPage() : null), this.getDefaultNodes());
	},
	installPage: function(inPageIndex){
		this.contentNode.appendChild(this.getDefaultPageNode(inPageIndex));
	},
	destroyPage: function(inPageIndex){
		var p = this.invalidatePageNode(inPageIndex, this.getDefaultNodes());
		dojox.grid.removeNode(p);
	},
	// rendering implementation
	renderPage: function(inPageIndex){
		var node = this.pageNodes[inPageIndex];
		for(var i=0, j=inPageIndex*this.rowsPerPage; (i<this.rowsPerPage)&&(j<this.rowCount); i++, j++){
			this.renderRow(j, node);
		}
	},
	removePage: function(inPageIndex){
		for(var i=0, j=inPageIndex*this.rowsPerPage; i<this.rowsPerPage; i++, j++){
			this.removeRow(j);
		}
	},
	// scroll control
	getPageRow: function(inPage){
		return inPage * this.rowsPerPage;
	},
	getLastPageRow: function(inPage){
		return Math.min(this.rowCount, this.getPageRow(inPage + 1)) - 1;
	},
	getFirstVisibleRowNodes: function(inPage, inPageTop, inScrollTop, inNodes){
		var row = this.getPageRow(inPage);
		var rows = dojox.grid.divkids(inNodes[inPage]);
		for(var i=0,l=rows.length; i<l && inPageTop<inScrollTop; i++, row++){
			inPageTop += rows[i].offsetHeight;
		}
		return (row ? row - 1 : row);
	},
	getFirstVisibleRow: function(inPage, inPageTop, inScrollTop){
		if(!this.pageExists(inPage)){
			return 0;
		}
		return this.getFirstVisibleRowNodes(inPage, inPageTop, inScrollTop, this.getDefaultNodes());
	},
	getLastVisibleRowNodes: function(inPage, inBottom, inScrollBottom, inNodes){
		var row = this.getLastPageRow(inPage);
		var rows = dojox.grid.divkids(inNodes[inPage]);
		for(var i=rows.length-1; i>=0 && inBottom>inScrollBottom; i--, row--){
			inBottom -= rows[i].offsetHeight;
		}
		return row + 1;
	},
	getLastVisibleRow: function(inPage, inBottom, inScrollBottom){
		if(!this.pageExists(inPage)){
			return 0;
		}
		return this.getLastVisibleRowNodes(inPage, inBottom, inScrollBottom, this.getDefaultNodes());
	},
	findTopRowForNodes: function(inScrollTop, inNodes){
		var rows = dojox.grid.divkids(inNodes[this.page]);
		for(var i=0,l=rows.length,t=this.pageTop,h; i<l; i++){
			h = rows[i].offsetHeight;
			t += h;
			if(t >= inScrollTop){
				this.offset = h - (t - inScrollTop);
				return i + this.page * this.rowsPerPage;
			}
		}
		return -1;
	},
	findScrollTopForNodes: function(inRow, inNodes){
		var rowPage = Math.floor(inRow / this.rowsPerPage);
		var t = 0;
		for(var i=0; i<rowPage; i++){
			t += this.getPageHeight(i);
		}
		this.pageTop = t;
		this.needPage(rowPage, this.pageTop);
		var rows = dojox.grid.divkids(inNodes[rowPage]);
		var r = inRow - this.rowsPerPage * rowPage;
		for(var i=0,l=rows.length; i<l && i<r; i++){
			t += rows[i].offsetHeight;
		}
		return t;
	},
	findTopRow: function(inScrollTop){
		return this.findTopRowForNodes(inScrollTop, this.getDefaultNodes());
	},
	findScrollTop: function(inRow){
		return this.findScrollTopForNodes(inRow, this.getDefaultNodes());
	},
	dummy: 0
});

dojo.declare('dojox.grid.scroller.columns', dojox.grid.scroller, {
	// summary:
	//	Virtual scroller class that scrolls list of columns. Owned by grid and used internally
	//	for virtual scrolling.
	constructor: function(inContentNodes){
		this.setContentNodes(inContentNodes);
	},
	// nodes
	setContentNodes: function(inNodes){
		this.contentNodes = inNodes;
		this.colCount = (this.contentNodes ? this.contentNodes.length : 0);
		this.pageNodes = [];
		for(var i=0; i<this.colCount; i++){
			this.pageNodes[i] = [];
		}
	},
	getDefaultNodes: function(){
		return this.pageNodes[0] || [];
	},
	scroll: function(inTop) {
		if(this.colCount){
			dojox.grid.scroller.prototype.scroll.call(this, inTop);
		}
	},
	// resize
	resize: function(){
		if(this.scrollboxNode){
			this.windowHeight = this.scrollboxNode.clientHeight;
		}
		for(var i=0; i<this.colCount; i++){
			dojox.grid.setStyleHeightPx(this.contentNodes[i], this.height);
		}
	},
	// implementation for page manager
	positionPage: function(inPageIndex, inPos){
		for(var i=0; i<this.colCount; i++){
			this.positionPageNode(this.pageNodes[i][inPageIndex], inPos);
		}
	},
	preparePage: function(inPageIndex, inReuseNode){
		var p = (inReuseNode ? this.popPage() : null);
		for(var i=0; i<this.colCount; i++){
			this.preparePageNode(inPageIndex, p, this.pageNodes[i]);
		}
	},
	installPage: function(inPageIndex){
		for(var i=0; i<this.colCount; i++){
			this.contentNodes[i].appendChild(this.pageNodes[i][inPageIndex]);
		}
	},
	destroyPage: function(inPageIndex){
		for(var i=0; i<this.colCount; i++){
			dojox.grid.removeNode(this.invalidatePageNode(inPageIndex, this.pageNodes[i]));
		}
	},
	// rendering implementation
	renderPage: function(inPageIndex){
		var nodes = [];
		for(var i=0; i<this.colCount; i++){
			nodes[i] = this.pageNodes[i][inPageIndex];
		}
		//this.renderRows(inPageIndex*this.rowsPerPage, this.rowsPerPage, nodes);
		for(var i=0, j=inPageIndex*this.rowsPerPage; (i<this.rowsPerPage)&&(j<this.rowCount); i++, j++){
			this.renderRow(j, nodes);
		}
	}
});

}

if(!dojo._hasResource["dojox.grid._grid.drag"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojox.grid._grid.drag"] = true;
dojo.provide("dojox.grid._grid.drag");

// summary:
//	utility functions for dragging as used in grid.
// begin closure
(function(){

var dgdrag = dojox.grid.drag = {};

dgdrag.dragging = false;
dgdrag.hysteresis = 2;

dgdrag.capture = function(inElement) {
	//console.debug('dojox.grid.drag.capture');
	if (inElement.setCapture)
		inElement.setCapture();
	else {
		document.addEventListener("mousemove", inElement.onmousemove, true);
		document.addEventListener("mouseup", inElement.onmouseup, true);
		document.addEventListener("click", inElement.onclick, true);
	}
}

dgdrag.release = function(inElement) {
	//console.debug('dojox.grid.drag.release');
	if(inElement.releaseCapture){
		inElement.releaseCapture();
	}else{
		document.removeEventListener("click", inElement.onclick, true);
		document.removeEventListener("mouseup", inElement.onmouseup, true);
		document.removeEventListener("mousemove", inElement.onmousemove, true);
	}
}

dgdrag.start = function(inElement, inOnDrag, inOnEnd, inEvent, inOnStart){
	if(/*dgdrag.elt ||*/ !inElement || dgdrag.dragging){
		console.debug('failed to start drag: bad input node or already dragging');
		return;
	}
	dgdrag.dragging = true;
	dgdrag.elt = inElement;
	dgdrag.events = {
		drag: inOnDrag || dojox.grid.nop, 
		end: inOnEnd || dojox.grid.nop, 
		start: inOnStart || dojox.grid.nop, 
		oldmove: inElement.onmousemove, 
		oldup: inElement.onmouseup, 
		oldclick: inElement.onclick 
	};
	dgdrag.positionX = (inEvent && ('screenX' in inEvent) ? inEvent.screenX : false);
	dgdrag.positionY = (inEvent && ('screenY' in inEvent) ? inEvent.screenY : false);
	dgdrag.started = (dgdrag.position === false);
	inElement.onmousemove = dgdrag.mousemove;
	inElement.onmouseup = dgdrag.mouseup;
	inElement.onclick = dgdrag.click;
	dgdrag.capture(dgdrag.elt);
}

dgdrag.end = function(){
	//console.debug("dojox.grid.drag.end");
	dgdrag.release(dgdrag.elt);
	dgdrag.elt.onmousemove = dgdrag.events.oldmove;
	dgdrag.elt.onmouseup = dgdrag.events.oldup;
	dgdrag.elt.onclick = dgdrag.events.oldclick;
	dgdrag.elt = null;
	try{
		if(dgdrag.started){
			dgdrag.events.end();
		}
	}finally{
		dgdrag.dragging = false;
	}
}

dgdrag.calcDelta = function(inEvent){
	inEvent.deltaX = inEvent.screenX - dgdrag.positionX;
	inEvent.deltaY = inEvent.screenY - dgdrag.positionY;
}

dgdrag.hasMoved = function(inEvent){
	return Math.abs(inEvent.deltaX) + Math.abs(inEvent.deltaY) > dgdrag.hysteresis;
}

dgdrag.mousemove = function(inEvent){
	inEvent = dojo.fixEvent(inEvent);
	dojo.stopEvent(inEvent);
	dgdrag.calcDelta(inEvent);
	if((!dgdrag.started)&&(dgdrag.hasMoved(inEvent))){
		dgdrag.events.start(inEvent);
		dgdrag.started = true;
	}
	if(dgdrag.started){
		dgdrag.events.drag(inEvent);
	}
}

dgdrag.mouseup = function(inEvent){
	//console.debug("dojox.grid.drag.mouseup");
	dojo.stopEvent(dojo.fixEvent(inEvent));
	dgdrag.end();
}

dgdrag.click = function(inEvent){
	dojo.stopEvent(dojo.fixEvent(inEvent));
	//dgdrag.end();
}

})();
// end closure

}

if(!dojo._hasResource["dojox.grid._grid.builder"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojox.grid._grid.builder"] = true;
dojo.provide("dojox.grid._grid.builder");


dojo.declare("dojox.grid.Builder",
	null,
	{
	// summary:
	//		Base class to produce html for grid content.
	//		Also provide event decoration, providing grid related information inside the event object
	// 		passed to grid events.
	constructor: function(inView){
		this.view = inView;
		this.grid = inView.grid;
	},
	
	view: null,
	// boilerplate HTML
	_table: '<table class="dojoxGrid-row-table" border="0" cellspacing="0" cellpadding="0" role="wairole:presentation">',

	// generate starting tags for a cell
	generateCellMarkup: function(inCell, inMoreStyles, inMoreClasses, isHeader){
		var result = [], html;
		if (isHeader){
			html = [ '<th tabIndex="-1" role="wairole:columnheader"' ];
		}else{
			html = [ '<td tabIndex="-1" role="wairole:gridcell"' ];
		}
		inCell.colSpan && html.push(' colspan="', inCell.colSpan, '"');
		inCell.rowSpan && html.push(' rowspan="', inCell.rowSpan, '"');
		html.push(' class="dojoxGrid-cell ');
		inCell.classes && html.push(inCell.classes, ' ');
		inMoreClasses && html.push(inMoreClasses, ' ');
		// result[0] => td opener, style
		result.push(html.join(''));
		// SLOT: result[1] => td classes 
		result.push('');
		html = ['" idx="', inCell.index, '" style="'];
		html.push(inCell.styles, inMoreStyles||'');
		inCell.unitWidth && html.push('width:', inCell.unitWidth, ';');
		// result[2] => markup
		result.push(html.join(''));
		// SLOT: result[3] => td style 
		result.push('');
		html = [ '"' ];
		inCell.attrs && html.push(" ", inCell.attrs);
		html.push('>');
		// result[4] => td postfix
		result.push(html.join(''));
		// SLOT: result[5] => content
		result.push('');
		// result[6] => td closes
		result.push('</td>');
		return result; // Array
	},

	// cell finding
	isCellNode: function(inNode){
		return Boolean(inNode && inNode.getAttribute && inNode.getAttribute("idx"));
	},
	
	getCellNodeIndex: function(inCellNode){
		return inCellNode ? Number(inCellNode.getAttribute("idx")) : -1;
	},
	
	getCellNode: function(inRowNode, inCellIndex){
		for(var i=0, row; row=dojox.grid.getTr(inRowNode.firstChild, i); i++){
			for(var j=0, cell; cell=row.cells[j]; j++){
				if(this.getCellNodeIndex(cell) == inCellIndex){
					return cell;
				}
			}
		}
	},
	
	findCellTarget: function(inSourceNode, inTopNode){
		var n = inSourceNode;
		while(n && (!this.isCellNode(n) || (dojox.grid.gridViewTag in n.offsetParent.parentNode && n.offsetParent.parentNode[dojox.grid.gridViewTag] != this.view.id)) && (n!=inTopNode)){
			n = n.parentNode;
		}
		return n!=inTopNode ? n : null 
	},
	
	// event decoration
	baseDecorateEvent: function(e){
		e.dispatch = 'do' + e.type;
		e.grid = this.grid;
		e.sourceView = this.view;
		e.cellNode = this.findCellTarget(e.target, e.rowNode);
		e.cellIndex = this.getCellNodeIndex(e.cellNode);
		e.cell = (e.cellIndex >= 0 ? this.grid.getCell(e.cellIndex) : null);
	},
	
	// event dispatch
	findTarget: function(inSource, inTag){
		var n = inSource;
		while(n && (n!=this.domNode) && (!(inTag in n) || (dojox.grid.gridViewTag in n && n[dojox.grid.gridViewTag] != this.view.id))){
			n = n.parentNode;
		}
		return (n != this.domNode) ? n : null; 
	},

	findRowTarget: function(inSource){
		return this.findTarget(inSource, dojox.grid.rowIndexTag);
	},

	isIntraNodeEvent: function(e){
		try{
			return (e.cellNode && e.relatedTarget && dojo.isDescendant(e.relatedTarget, e.cellNode));
		}catch(x){
			// e.relatedTarget has permission problem in FF if it's an input: https://bugzilla.mozilla.org/show_bug.cgi?id=208427
			return false;
		}
	},

	isIntraRowEvent: function(e){
		try{
			var row = e.relatedTarget && this.findRowTarget(e.relatedTarget);
			return !row && (e.rowIndex==-1) || row && (e.rowIndex==row.gridRowIndex);			
		}catch(x){
			// e.relatedTarget on INPUT has permission problem in FF: https://bugzilla.mozilla.org/show_bug.cgi?id=208427
			return false;
		}
	},

	dispatchEvent: function(e){
		if(e.dispatch in this){
			return this[e.dispatch](e);
		}
	},

	// dispatched event handlers
	domouseover: function(e){
		if(e.cellNode && (e.cellNode!=this.lastOverCellNode)){
			this.lastOverCellNode = e.cellNode;
			this.grid.onMouseOver(e);
		}
		this.grid.onMouseOverRow(e);
	},

	domouseout: function(e){
		if(e.cellNode && (e.cellNode==this.lastOverCellNode) && !this.isIntraNodeEvent(e, this.lastOverCellNode)){
			this.lastOverCellNode = null;
			this.grid.onMouseOut(e);
			if(!this.isIntraRowEvent(e)){
				this.grid.onMouseOutRow(e);
			}
		}
	},
	
	domousedown: function(e){
		if (e.cellNode)
			this.grid.onMouseDown(e);
		this.grid.onMouseDownRow(e)
	}

});

dojo.declare("dojox.grid.contentBuilder",
	dojox.grid.Builder,
	{
	// summary:
	//		Produces html for grid data content. Owned by grid and used internally 
	//		for rendering data. Override to implement custom rendering.
	update: function(){
		this.prepareHtml();
	},

	// cache html for rendering data rows
	prepareHtml: function(){
		var defaultGet=this.grid.get, rows=this.view.structure.rows;
		for(var j=0, row; (row=rows[j]); j++){
			for(var i=0, cell; (cell=row[i]); i++){
				cell.get = cell.get || (cell.value == undefined) && defaultGet;
				cell.markup = this.generateCellMarkup(cell, cell.cellStyles, cell.cellClasses, false);
			}
		}
	},

	// time critical: generate html using cache and data source
	generateHtml: function(inDataIndex, inRowIndex){
		var
			html = [ this._table ],
			v = this.view,
			obr = v.onBeforeRow,
			rows = v.structure.rows;

		obr && obr(inRowIndex, rows);
		for(var j=0, row; (row=rows[j]); j++){
			if(row.hidden || row.header){
				continue;
			}
			html.push(!row.invisible ? '<tr>' : '<tr class="dojoxGrid-invisible">');
			for(var i=0, cell, m, cc, cs; (cell=row[i]); i++){
				m = cell.markup, cc = cell.customClasses = [], cs = cell.customStyles = [];
				// content (format can fill in cc and cs as side-effects)
				m[5] = cell.format(inDataIndex);
				// classes
				m[1] = cc.join(' ');
				// styles
				m[3] = cs.join(';');
				// in-place concat
				html.push.apply(html, m);
			}
			html.push('</tr>');
		}
		html.push('</table>');
		return html.join(''); // String
	},

	decorateEvent: function(e){
		e.rowNode = this.findRowTarget(e.target);
		if(!e.rowNode){return false};
		e.rowIndex = e.rowNode[dojox.grid.rowIndexTag];
		this.baseDecorateEvent(e);
		e.cell = this.grid.getCell(e.cellIndex);
		return true; // Boolean
	}
	
});

dojo.declare("dojox.grid.headerBuilder",
	dojox.grid.Builder,
	{
	// summary:
	//		Produces html for grid header content. Owned by grid and used internally 
	//		for rendering data. Override to implement custom rendering.

	bogusClickTime: 0,
	overResizeWidth: 4,
	minColWidth: 1,
	
	// FIXME: isn't this getting mixed from dojox.grid.Builder, -1 character?
	_table: '<table class="dojoxGrid-row-table" border="0" cellspacing="0" cellpadding="0" role="wairole:presentation"',

	update: function(){
		this.tableMap = new dojox.grid.tableMap(this.view.structure.rows);
	},

	generateHtml: function(inGetValue, inValue){
		var html = [this._table], rows = this.view.structure.rows;
		
		// render header with appropriate width, if possible so that views with flex columns are correct height
		if(this.view.viewWidth){
			html.push([' style="width:', this.view.viewWidth, ';"'].join(''));
		}
		html.push('>');
		dojox.grid.fire(this.view, "onBeforeRow", [-1, rows]);
		for(var j=0, row; (row=rows[j]); j++){
			if(row.hidden){
				continue;
			}
			html.push(!row.invisible ? '<tr>' : '<tr class="dojoxGrid-invisible">');
			for(var i=0, cell, markup; (cell=row[i]); i++){
				cell.customClasses = [];
				cell.customStyles = [];
				markup = this.generateCellMarkup(cell, cell.headerStyles, cell.headerClasses, true);
				// content
				markup[5] = (inValue != undefined ? inValue : inGetValue(cell));
				// styles
				markup[3] = cell.customStyles.join(';');
				// classes
				markup[1] = cell.customClasses.join(' '); //(cell.customClasses ? ' ' + cell.customClasses : '');
				html.push(markup.join(''));
			}
			html.push('</tr>');
		}
		html.push('</table>');
		return html.join('');
	},

	// event helpers
	getCellX: function(e){
		var x = e.layerX;
		if(dojo.isMoz){
			var n = dojox.grid.ascendDom(e.target, dojox.grid.makeNotTagName("th"));
			x -= (n && n.offsetLeft) || 0;
			var t = e.sourceView.getScrollbarWidth();
			if(!dojo._isBodyLtr() && e.sourceView.headerNode.scrollLeft < t)
				x -= t;
			//x -= getProp(ascendDom(e.target, mkNotTagName("td")), "offsetLeft") || 0;
		}
		var n = dojox.grid.ascendDom(e.target, function(){
			if(!n || n == e.cellNode){
				return false;
			}
			// Mozilla 1.8 (FF 1.5) has a bug that makes offsetLeft = -parent border width
			// when parent has border, overflow: hidden, and is positioned
			// handle this problem here ... not a general solution!
			x += (n.offsetLeft < 0 ? 0 : n.offsetLeft);
			return true;
		});
		return x;
	},

	// event decoration
	decorateEvent: function(e){
		this.baseDecorateEvent(e);
		e.rowIndex = -1;
		e.cellX = this.getCellX(e);
		return true;
	},

	// event handlers
	// resizing
	prepareResize: function(e, mod){
		var i = dojox.grid.getTdIndex(e.cellNode);
		e.cellNode = (i ? e.cellNode.parentNode.cells[i+mod] : null);
		e.cellIndex = (e.cellNode ? this.getCellNodeIndex(e.cellNode) : -1);
		return Boolean(e.cellNode);
	},

	canResize: function(e){
		if(!e.cellNode || e.cellNode.colSpan > 1){
			return false;
		}
		var cell = this.grid.getCell(e.cellIndex); 
		return !cell.noresize && !cell.isFlex();
	},

	overLeftResizeArea: function(e){
		if(dojo._isBodyLtr()){
			return (e.cellIndex>0) && (e.cellX < this.overResizeWidth) && this.prepareResize(e, -1);
		}
		return t = e.cellNode && (e.cellX < this.overResizeWidth);
	},

	overRightResizeArea: function(e){
		if(dojo._isBodyLtr()){
			return e.cellNode && (e.cellX >= e.cellNode.offsetWidth - this.overResizeWidth);
		}
		return (e.cellIndex>0) && (e.cellX >= e.cellNode.offsetWidth - this.overResizeWidth) && this.prepareResize(e, -1);
	},

	domousemove: function(e){
		//console.log(e.cellIndex, e.cellX, e.cellNode.offsetWidth);
		var c = (this.overRightResizeArea(e) ? 'e-resize' : (this.overLeftResizeArea(e) ? 'w-resize' : ''));
		if(c && !this.canResize(e)){
			c = 'not-allowed';
		}
		e.sourceView.headerNode.style.cursor = c || ''; //'default';
		if (c)
			dojo.stopEvent(e);
	},

	domousedown: function(e){
		if(!dojox.grid.drag.dragging){
			if((this.overRightResizeArea(e) || this.overLeftResizeArea(e)) && this.canResize(e)){
				this.beginColumnResize(e);
			}else{
				this.grid.onMouseDown(e);
				this.grid.onMouseOverRow(e);
			}
			//else{
			//	this.beginMoveColumn(e);
			//}
		}
	},

	doclick: function(e) {
		if (new Date().getTime() < this.bogusClickTime) {
			dojo.stopEvent(e);
			return true;
		}
	},

	// column resizing
	beginColumnResize: function(e){
		dojo.stopEvent(e);
		var spanners = [], nodes = this.tableMap.findOverlappingNodes(e.cellNode);
		for(var i=0, cell; (cell=nodes[i]); i++){
			spanners.push({ node: cell, index: this.getCellNodeIndex(cell), width: cell.offsetWidth });
			//console.log("spanner: " + this.getCellNodeIndex(cell));
		}
		var drag = {
			scrollLeft: e.sourceView.headerNode.scrollLeft,
			view: e.sourceView,
			node: e.cellNode,
			index: e.cellIndex,
			w: e.cellNode.clientWidth,
			spanners: spanners
		};
		//console.log(drag.index, drag.w);
		dojox.grid.drag.start(e.cellNode, dojo.hitch(this, 'doResizeColumn', drag), dojo.hitch(this, 'endResizeColumn', drag), e);
	},

	doResizeColumn: function(inDrag, inEvent){
		var isLtr = dojo._isBodyLtr();
		if(isLtr){
			var w = inDrag.w + inEvent.deltaX;
		}else{
			var w = inDrag.w - inEvent.deltaX;
		}
		if(w >= this.minColWidth){
			for(var i=0, s, sw; (s=inDrag.spanners[i]); i++){
				if(isLtr){
					sw = s.width + inEvent.deltaX;
				}else{
					sw = s.width - inEvent.deltaX;
				}
				s.node.style.width = sw + 'px';
				inDrag.view.setColWidth(s.index, sw);
				//console.log('setColWidth', '#' + s.index, sw + 'px');
			}
			inDrag.node.style.width = w + 'px';
			inDrag.view.setColWidth(inDrag.index, w);
			if(!isLtr){
				inDrag.view.headerNode.scrollLeft = (inDrag.scrollLeft - inEvent.deltaX);
			}
		}
		if(inDrag.view.flexCells && !inDrag.view.testFlexCells()){
			var t = dojox.grid.findTable(inDrag.node);
			t && (t.style.width = '');
		}
	},

	endResizeColumn: function(inDrag){
		this.bogusClickTime = new Date().getTime() + 30;
		setTimeout(dojo.hitch(inDrag.view, "update"), 50);
	}

});

dojo.declare("dojox.grid.tableMap",
	null,
	{
	// summary:
	//		Maps an html table into a structure parsable for information about cell row and col spanning.
	//		Used by headerBuilder
	constructor: function(inRows){
		this.mapRows(inRows);
	},
	
	map: null,

	mapRows: function(inRows){
		// summary: Map table topography

		//console.log('mapRows');
		// # of rows
		var rowCount = inRows.length;
		if(!rowCount){
			return;
		}
		// map which columns and rows fill which cells
		this.map = [ ];
		for(var j=0, row; (row=inRows[j]); j++){
			this.map[j] = [];
		}
		for(var j=0, row; (row=inRows[j]); j++){
			for(var i=0, x=0, cell, colSpan, rowSpan; (cell=row[i]); i++){
				while (this.map[j][x]){x++};
				this.map[j][x] = { c: i, r: j };
				rowSpan = cell.rowSpan || 1;
				colSpan = cell.colSpan || 1;
				for(var y=0; y<rowSpan; y++){
					for(var s=0; s<colSpan; s++){
						this.map[j+y][x+s] = this.map[j][x];
					}
				}
				x += colSpan;
			}
		}
		//this.dumMap();
	},

	dumpMap: function(){
		for(var j=0, row, h=''; (row=this.map[j]); j++,h=''){
			for(var i=0, cell; (cell=row[i]); i++){
				h += cell.r + ',' + cell.c + '   ';
			}
			console.log(h);
		}
	},

	getMapCoords: function(inRow, inCol){
		// summary: Find node's map coords by it's structure coords
		for(var j=0, row; (row=this.map[j]); j++){
			for(var i=0, cell; (cell=row[i]); i++){
				if(cell.c==inCol && cell.r == inRow){
					return { j: j, i: i };
				}
				//else{console.log(inRow, inCol, ' : ', i, j, " : ", cell.r, cell.c); };
			}
		}
		return { j: -1, i: -1 };
	},
	
	getNode: function(inTable, inRow, inCol){
		// summary: Find a node in inNode's table with the given structure coords
		var row = inTable && inTable.rows[inRow];
		return row && row.cells[inCol];
	},
	
	_findOverlappingNodes: function(inTable, inRow, inCol){
		var nodes = [];
		var m = this.getMapCoords(inRow, inCol);
		//console.log("node j: %d, i: %d", m.j, m.i);
		var row = this.map[m.j];
		for(var j=0, row; (row=this.map[j]); j++){
			if(j == m.j){ continue; }
			with(row[m.i]){
				//console.log("overlaps: r: %d, c: %d", r, c);
				var n = this.getNode(inTable, r, c);
				if(n){ nodes.push(n); }
			}
		}
		//console.log(nodes);
		return nodes;
	},
	
	findOverlappingNodes: function(inNode){
		return this._findOverlappingNodes(dojox.grid.findTable(inNode), dojox.grid.getTrIndex(inNode.parentNode), dojox.grid.getTdIndex(inNode));
	}
	
});

dojox.grid.rowIndexTag = "gridRowIndex";
dojox.grid.gridViewTag = "gridView";

}

if(!dojo._hasResource["dojox.grid._grid.view"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojox.grid._grid.view"] = true;
dojo.provide("dojox.grid._grid.view");





dojo.declare('dojox.GridView',
	[dijit._Widget, dijit._Templated],
	{
	// summary:
	//		A collection of grid columns. A grid is comprised of a set of views that stack horizontally.
	//		Grid creates views automatically based on grid's layout structure.
	//		Users should typically not need to access individual views directly.
	//
	// defaultWidth: String
	//		Default widget of the view
	defaultWidth: "18em",

	// viewWidth: String
	// 		Width for the view, in valid css unit
	viewWidth: "",

	templateString:"<div class=\"dojoxGrid-view\">\r\n\t<div class=\"dojoxGrid-header\" dojoAttachPoint=\"headerNode\">\r\n\t\t<div dojoAttachPoint=\"headerNodeContainer\" style=\"width:9000em\">\r\n\t\t\t<div dojoAttachPoint=\"headerContentNode\"></div>\r\n\t\t</div>\r\n\t</div>\r\n\t<input type=\"checkbox\" class=\"dojoxGrid-hidden-focus\" dojoAttachPoint=\"hiddenFocusNode\" />\r\n\t<input type=\"checkbox\" class=\"dojoxGrid-hidden-focus\" />\r\n\t<div class=\"dojoxGrid-scrollbox\" dojoAttachPoint=\"scrollboxNode\">\r\n\t\t<div class=\"dojoxGrid-content\" dojoAttachPoint=\"contentNode\" hidefocus=\"hidefocus\"></div>\r\n\t</div>\r\n</div>\r\n",
	
	themeable: false,
	classTag: 'dojoxGrid',
	marginBottom: 0,
	rowPad: 2,

	postMixInProperties: function(){
		this.rowNodes = [];
	},

	postCreate: function(){
		this.connect(this.scrollboxNode,"onscroll","doscroll");
		dojox.grid.funnelEvents(this.contentNode, this, "doContentEvent", [ 'mouseover', 'mouseout', 'click', 'dblclick', 'contextmenu', 'mousedown' ]);
		dojox.grid.funnelEvents(this.headerNode, this, "doHeaderEvent", [ 'dblclick', 'mouseover', 'mouseout', 'mousemove', 'mousedown', 'click', 'contextmenu' ]);
		this.content = new dojox.grid.contentBuilder(this);
		this.header = new dojox.grid.headerBuilder(this);
		//BiDi: in RTL case, style width='9000em' causes scrolling problem in head node
		if(!dojo._isBodyLtr()){
			this.headerNodeContainer.style.width = "";
		}
	},

	destroy: function(){
		dojox.grid.removeNode(this.headerNode);
		this.inherited("destroy", arguments);
	},

	// focus 
	focus: function(){
		if(dojo.isSafari || dojo.isOpera){
			this.hiddenFocusNode.focus();
		}else{
			this.scrollboxNode.focus();
		}
	},

	setStructure: function(inStructure){
		var vs = this.structure = inStructure;
		// FIXME: similar logic is duplicated in layout
		if(vs.width && !isNaN(vs.width)){
			this.viewWidth = vs.width + 'em';
		}else{
			this.viewWidth = vs.width || this.viewWidth; //|| this.defaultWidth;
		}
		this.onBeforeRow = vs.onBeforeRow;
		this.noscroll = vs.noscroll;
		if(this.noscroll){
			this.scrollboxNode.style.overflow = "hidden";
		}
		// bookkeeping
		this.testFlexCells();
		// accomodate new structure
		this.updateStructure();
	},

	testFlexCells: function(){
		// FIXME: cheater, this function does double duty as initializer and tester
		this.flexCells = false;
		for(var j=0, row; (row=this.structure.rows[j]); j++){
			for(var i=0, cell; (cell=row[i]); i++){
				cell.view = this;
				this.flexCells = this.flexCells || cell.isFlex();
			}
		}
		return this.flexCells;
	},

	updateStructure: function(){
		// header builder needs to update table map
		this.header.update();
		// content builder needs to update markup cache
		this.content.update();
	},

	getScrollbarWidth: function(){
		return (this.noscroll ? 0 : dojox.grid.getScrollbarWidth()); // Integer
	},

	getColumnsWidth: function(){
		return this.headerContentNode.firstChild.offsetWidth; // Integer
	},

	getWidth: function(){
		return this.viewWidth || (this.getColumnsWidth()+this.getScrollbarWidth()) +'px'; // String
	},

	getContentWidth: function(){
		return Math.max(0, dojo._getContentBox(this.domNode).w - this.getScrollbarWidth()) + 'px'; // String
	},

	render: function(){
		this.scrollboxNode.style.height = '';
		this.renderHeader();
	},

	renderHeader: function(){
		this.headerContentNode.innerHTML = this.header.generateHtml(this._getHeaderContent);
	},

	// note: not called in 'view' context
	_getHeaderContent: function(inCell){
		var n = inCell.name || inCell.grid.getCellName(inCell);
		if(inCell.index != inCell.grid.getSortIndex()){
			return n;
		}
		return [ '<div class="', inCell.grid.sortInfo > 0 ? 'dojoxGrid-sort-down' : 'dojoxGrid-sort-up', '"><div class="gridArrowButtonChar">', inCell.grid.sortInfo > 0 ? '&#9660;' : '&#9650;', '</div>', n, '</div>' ].join('');
	},

	resize: function(){
		this.adaptHeight();
		this.adaptWidth();
	},

	hasScrollbar: function(){
		return (this.scrollboxNode.clientHeight != this.scrollboxNode.offsetHeight); // Boolean
	},

	adaptHeight: function(){
		if(!this.grid.autoHeight){
			var h = this.domNode.clientHeight;
			if(!this.hasScrollbar()){ // no scrollbar is rendered
				h -= dojox.grid.getScrollbarWidth();
			}
			dojox.grid.setStyleHeightPx(this.scrollboxNode, h);
		}
	},

	adaptWidth: function(){
		if(this.flexCells){
			// the view content width
			this.contentWidth = this.getContentWidth();
			this.headerContentNode.firstChild.style.width = this.contentWidth;
		}
		// FIXME: it should be easier to get w from this.scrollboxNode.clientWidth, 
		// but clientWidth seemingly does not include scrollbar width in some cases
		var w = this.scrollboxNode.offsetWidth - this.getScrollbarWidth();
		w = Math.max(w, this.getColumnsWidth()) + 'px';
		with(this.contentNode){
			style.width = '';
			offsetWidth;
			style.width = w;
		}
	},

	setSize: function(w, h){
		with(this.domNode.style){
			if(w){
				width = w;
			}
			height = (h >= 0 ? h + 'px' : '');
		}
		with(this.headerNode.style){
			if(w){
				width = w;
			}
		}
	},

	renderRow: function(inRowIndex, inHeightPx){
		var rowNode = this.createRowNode(inRowIndex);
		this.buildRow(inRowIndex, rowNode, inHeightPx);
		this.grid.edit.restore(this, inRowIndex);
		return rowNode;
	},

	createRowNode: function(inRowIndex){
		var node = document.createElement("div");
		node.className = this.classTag + '-row';
		node[dojox.grid.gridViewTag] = this.id;
		node[dojox.grid.rowIndexTag] = inRowIndex;
		this.rowNodes[inRowIndex] = node;
		return node;
	},

	buildRow: function(inRowIndex, inRowNode){
		this.buildRowContent(inRowIndex, inRowNode);
		this.styleRow(inRowIndex, inRowNode);
	},

	buildRowContent: function(inRowIndex, inRowNode){
		inRowNode.innerHTML = this.content.generateHtml(inRowIndex, inRowIndex); 
		if(this.flexCells){
			// FIXME: accessing firstChild here breaks encapsulation
			inRowNode.firstChild.style.width = this.contentWidth;
		}
	},

	rowRemoved:function(inRowIndex){
		this.grid.edit.save(this, inRowIndex);
		delete this.rowNodes[inRowIndex];
	},

	getRowNode: function(inRowIndex){
		return this.rowNodes[inRowIndex];
	},

	getCellNode: function(inRowIndex, inCellIndex){
		var row = this.getRowNode(inRowIndex);
		if(row){
			return this.content.getCellNode(row, inCellIndex);
		}
	},

	// styling
	styleRow: function(inRowIndex, inRowNode){
		inRowNode._style = dojox.grid.getStyleText(inRowNode);
		this.styleRowNode(inRowIndex, inRowNode);
	},

	styleRowNode: function(inRowIndex, inRowNode){
		if(inRowNode){
			this.doStyleRowNode(inRowIndex, inRowNode);
		}
	},

	doStyleRowNode: function(inRowIndex, inRowNode){
		this.grid.styleRowNode(inRowIndex, inRowNode);
	},

	// updating
	updateRow: function(inRowIndex, inHeightPx, inPageNode){
		var rowNode = this.getRowNode(inRowIndex);
		if(rowNode){
			rowNode.style.height = '';
			this.buildRow(inRowIndex, rowNode);
		}
		return rowNode;
	},

	updateRowStyles: function(inRowIndex){
		this.styleRowNode(inRowIndex, this.getRowNode(inRowIndex));
	},

	// scrolling
	lastTop: 0,
	firstScroll:0,

	doscroll: function(inEvent){
		//var s = dojo.marginBox(this.headerContentNode.firstChild);
		var isLtr = dojo._isBodyLtr();
		if(this.firstScroll < 2){
			if((!isLtr && this.firstScroll == 1) || (isLtr && this.firstScroll == 0)){
				var s = dojo.marginBox(this.headerNodeContainer);
				if(dojo.isIE){
					this.headerNodeContainer.style.width = s.w + this.getScrollbarWidth() + 'px';
				}else if(dojo.isMoz){
					//TODO currently only for FF, not sure for safari and opera
					this.headerNodeContainer.style.width = s.w - this.getScrollbarWidth() + 'px';
					//this.headerNodeContainer.style.width = s.w + 'px';
					//set scroll to right in FF
					if(isLtr){
						this.scrollboxNode.scrollLeft = this.scrollboxNode.scrollWidth - this.scrollboxNode.clientWidth;
					}else{
						this.scrollboxNode.scrollLeft = this.scrollboxNode.clientWidth - this.scrollboxNode.scrollWidth;
					}
				}
			}
			this.firstScroll++;
		}
		this.headerNode.scrollLeft = this.scrollboxNode.scrollLeft;
		// 'lastTop' is a semaphore to prevent feedback-loop with setScrollTop below
		var top = this.scrollboxNode.scrollTop;
		if(top != this.lastTop){
			this.grid.scrollTo(top);
		}
	},

	setScrollTop: function(inTop){
		// 'lastTop' is a semaphore to prevent feedback-loop with doScroll above
		this.lastTop = inTop;
		this.scrollboxNode.scrollTop = inTop;
		return this.scrollboxNode.scrollTop;
	},

	// event handlers (direct from DOM)
	doContentEvent: function(e){
		if(this.content.decorateEvent(e)){
			this.grid.onContentEvent(e);
		}
	},

	doHeaderEvent: function(e){
		if(this.header.decorateEvent(e)){
			this.grid.onHeaderEvent(e);
		}
	},

	// event dispatch(from Grid)
	dispatchContentEvent: function(e){
		return this.content.dispatchEvent(e);
	},

	dispatchHeaderEvent: function(e){
		return this.header.dispatchEvent(e);
	},

	// column resizing
	setColWidth: function(inIndex, inWidth){
		this.grid.setCellWidth(inIndex, inWidth + 'px');
	},

	update: function(){
		var left = this.scrollboxNode.scrollLeft;
		this.content.update();
		this.grid.update();
		this.scrollboxNode.scrollLeft = left;
		this.headerNode.scrollLeft = left;
	}
});

}

if(!dojo._hasResource["dojox.grid._grid.views"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojox.grid._grid.views"] = true;
dojo.provide("dojox.grid._grid.views");

dojo.declare('dojox.grid.views', null, {
	// summary:
	//		A collection of grid views. Owned by grid and used internally for managing grid views.
	// description:
	//		Grid creates views automatically based on grid's layout structure.
	//		Users should typically not need to access individual views or the views collection directly.
	constructor: function(inGrid){
		this.grid = inGrid;
	},

	defaultWidth: 200,

	views: [],

	// operations
	resize: function(){
		this.onEach("resize");
	},

	render: function(){
		this.onEach("render");
	},

	// views
	addView: function(inView){
		inView.idx = this.views.length;
		this.views.push(inView);
	},

	destroyViews: function(){
		for (var i=0, v; v=this.views[i]; i++)
			v.destroy();
		this.views = [];
	},

	getContentNodes: function(){
		var nodes = [];
		for(var i=0, v; v=this.views[i]; i++){
			nodes.push(v.contentNode);
		}
		return nodes;
	},

	forEach: function(inCallback){
		for(var i=0, v; v=this.views[i]; i++){
			inCallback(v, i);
		}
	},

	onEach: function(inMethod, inArgs){
		inArgs = inArgs || [];
		for(var i=0, v; v=this.views[i]; i++){
			if(inMethod in v){
				v[inMethod].apply(v, inArgs);
			}
		}
	},

	// layout
	normalizeHeaderNodeHeight: function(){
		var rowNodes = [];
		for(var i=0, v; (v=this.views[i]); i++){
			if(v.headerContentNode.firstChild){
				rowNodes.push(v.headerContentNode)
			};
		}
		this.normalizeRowNodeHeights(rowNodes);
	},

	normalizeRowNodeHeights: function(inRowNodes){
		var h = 0; 
		for(var i=0, n, o; (n=inRowNodes[i]); i++){
			h = Math.max(h, (n.firstChild.clientHeight)||(n.firstChild.offsetHeight));
		}
		h = (h >= 0 ? h : 0);
		//
		var hpx = h + 'px';
		for(var i=0, n; (n=inRowNodes[i]); i++){
			if(n.firstChild.clientHeight!=h){
				n.firstChild.style.height = hpx;
			}
		}
		//
		//console.log('normalizeRowNodeHeights ', h);
		//
		// querying the height here seems to help scroller measure the page on IE
		if(inRowNodes&&inRowNodes[0]){
			inRowNodes[0].parentNode.offsetHeight;
		}
	},
	
	resetHeaderNodeHeight: function(){
		for(var i=0, v, n; (v=this.views[i]); i++){
			n = v.headerContentNode.firstChild;
			if(n)
				n.style.height = "";
		}
	},

	renormalizeRow: function(inRowIndex){
		var rowNodes = [];
		for(var i=0, v, n; (v=this.views[i])&&(n=v.getRowNode(inRowIndex)); i++){
			n.firstChild.style.height = '';
			rowNodes.push(n);
		}
		this.normalizeRowNodeHeights(rowNodes);
	},

	getViewWidth: function(inIndex){
		return this.views[inIndex].getWidth() || this.defaultWidth;
	},

	// must be called after view widths are properly set or height can be miscalculated
	// if there are flex columns
	measureHeader: function(){
		// need to reset view header heights so they are properly measured.
		this.resetHeaderNodeHeight();
		this.forEach(function(inView){
			inView.headerContentNode.style.height = '';
		});
		var h = 0;
		// calculate maximum view header height
		this.forEach(function(inView){
			h = Math.max(inView.headerNode.offsetHeight, h);
		});
		return h;
	},

	measureContent: function(){
		var h = 0;
		this.forEach(function(inView) {
			h = Math.max(inView.domNode.offsetHeight, h);
		});
		return h;
	},

	findClient: function(inAutoWidth){
		// try to use user defined client
		var c = this.grid.elasticView || -1;
		// attempt to find implicit client
		if(c < 0){
			for(var i=1, v; (v=this.views[i]); i++){
				if(v.viewWidth){
					for(i=1; (v=this.views[i]); i++){
						if(!v.viewWidth){
							c = i;
							break;
						}
					}
					break;
				}
			}
		}
		// client is in the middle by default
		if(c < 0){
			c = Math.floor(this.views.length / 2);
		}
		return c;
	},

	arrange: function(l, w){
		var i, v, vw, len = this.views.length;
		// find the client
		var c = (w <= 0 ? len : this.findClient());
		// layout views
		var setPosition = function(v, l){
			with(v.domNode.style){
				if(!dojo._isBodyLtr()){
					right = l + 'px';
				}else{
				 	left = l + 'px';
				}
				top = 0 + 'px';
			}
			with(v.headerNode.style){
				if(!dojo._isBodyLtr()){
					right = l + 'px';
				}else{
					left = l + 'px';
				}
				top = 0;
			}
		}
		// for views left of the client
		//BiDi TODO: The left and right should not appear in BIDI environment. Should be replaced with 
		//leading and tailing concept.
		for(i=0; (v=this.views[i])&&(i<c); i++){
			// get width
			vw = this.getViewWidth(i);
			// process boxes
			v.setSize(vw, 0);
			setPosition(v, l);
			vw = v.domNode.offsetWidth;
			// update position
			l += vw;
		}
		// next view (is the client, i++ == c) 
		i++;
		// start from the right edge
		var r = w;
		// for views right of the client (iterated from the right)
		for(var j=len-1; (v=this.views[j])&&(i<=j); j--){
			// get width
			vw = this.getViewWidth(j);
			// set size
			v.setSize(vw, 0);
			// measure in pixels
			vw = v.domNode.offsetWidth;
			// update position
			r -= vw;
			// set position
			setPosition(v, r);
		}
		if(c<len){
			v = this.views[c];
			// position the client box between left and right boxes	
			vw = Math.max(1, r-l);
			// set size
			v.setSize(vw + 'px', 0);
			setPosition(v, l);
		}
		return l;
	},

	// rendering
	renderRow: function(inRowIndex, inNodes){
		var rowNodes = [];
		for(var i=0, v, n, rowNode; (v=this.views[i])&&(n=inNodes[i]); i++){
			rowNode = v.renderRow(inRowIndex);
			n.appendChild(rowNode);
			rowNodes.push(rowNode);
		}
		this.normalizeRowNodeHeights(rowNodes);
	},
	
	rowRemoved: function(inRowIndex){
		this.onEach("rowRemoved", [ inRowIndex ]);
	},
	
	// updating
	updateRow: function(inRowIndex, inHeight){
		for(var i=0, v; v=this.views[i]; i++){
			v.updateRow(inRowIndex, inHeight);
		}
		this.renormalizeRow(inRowIndex);
	},
	
	updateRowStyles: function(inRowIndex){
		this.onEach("updateRowStyles", [ inRowIndex ]);
	},
	
	// scrolling
	setScrollTop: function(inTop){
		var top = inTop;
		for(var i=0, v; v=this.views[i]; i++){
			top = v.setScrollTop(inTop);
		}
		return top;
		//this.onEach("setScrollTop", [ inTop ]);
	},
	
	getFirstScrollingView: function(){
		// summary: Returns the first grid view with a scroll bar 
		for(var i=0, v; (v=this.views[i]); i++){
			if(v.hasScrollbar()){
				return v;
			}
		}
	}
	
});

}

if(!dojo._hasResource["dojox.grid._grid.cell"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojox.grid._grid.cell"] = true;
dojo.provide("dojox.grid._grid.cell");

dojo.declare("dojox.grid.cell", null, {
	// summary:
	//	Respresents a grid cell and contains information about column options and methods
	//	for retrieving cell related information.
	//	Each column in a grid layout has a cell object and most events and many methods
	//	provide access to these objects.
	styles: '',
	constructor: function(inProps){
		dojo.mixin(this, inProps);
		if(this.editor){this.editor = new this.editor(this);}
	},
	// data source
	format: function(inRowIndex){
		// summary:
		//	provides the html for a given grid cell.
		// inRowIndex: int
		// grid row index
		// returns: html for a given grid cell
		var f, i=this.grid.edit.info, d=this.get ? this.get(inRowIndex) : this.value;
		if(this.editor && (this.editor.alwaysOn || (i.rowIndex==inRowIndex && i.cell==this))){
			return this.editor.format(d, inRowIndex);
		}else{
			return (f = this.formatter) ? f.call(this, d, inRowIndex) : d;
		}
	},
	// utility
	getNode: function(inRowIndex){
		// summary:
		//	gets the dom node for a given grid cell.
		// inRowIndex: int
		// grid row index
		// returns: dom node for a given grid cell
		return this.view.getCellNode(inRowIndex, this.index);
	},
	isFlex: function(){
		var uw = this.unitWidth;
		return uw && (uw=='auto' || uw.slice(-1)=='%');
	},
	// edit support
	applyEdit: function(inValue, inRowIndex){
		this.grid.edit.applyCellEdit(inValue, this, inRowIndex);
	},
	cancelEdit: function(inRowIndex){
		this.grid.doCancelEdit(inRowIndex);
	},
	_onEditBlur: function(inRowIndex){
		if(this.grid.edit.isEditCell(inRowIndex, this.index)){
			//console.log('editor onblur', e);
			this.grid.edit.apply();
		}
	},
	registerOnBlur: function(inNode, inRowIndex){
		if(this.commitOnBlur){
			dojo.connect(inNode, "onblur", function(e){
				// hack: if editor still thinks this editor is current some ms after it blurs, assume we've focused away from grid
				setTimeout(dojo.hitch(this, "_onEditBlur", inRowIndex), 250);
			});
		}
	}
});

}

if(!dojo._hasResource["dojox.grid._grid.layout"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojox.grid._grid.layout"] = true;
dojo.provide("dojox.grid._grid.layout");


dojo.declare("dojox.grid.layout", null, {
	// summary:
	//	Controls grid cell layout. Owned by grid and used internally.
	constructor: function(inGrid){
		this.grid = inGrid;
	},
	// flat array of grid cells
	cells: [],
	// structured array of grid cells
	structure: null,
	// default cell width
	defaultWidth: '6em',
	// methods
	setStructure: function(inStructure){
		this.fieldIndex = 0;
		this.cells = [];
		var s = this.structure = [];
		for(var i=0, viewDef, rows; (viewDef=inStructure[i]); i++){
			s.push(this.addViewDef(viewDef));
		}
		this.cellCount = this.cells.length;
	},
	addViewDef: function(inDef){
		this._defaultCellProps = inDef.defaultCell || {};
		return dojo.mixin({}, inDef, {rows: this.addRowsDef(inDef.rows || inDef.cells)});
	},
	addRowsDef: function(inDef){
		var result = [];
		for(var i=0, row; inDef && (row=inDef[i]); i++){
			result.push(this.addRowDef(i, row));
		}
		return result;
	},
	addRowDef: function(inRowIndex, inDef){
		var result = [];
		for(var i=0, def, cell; (def=inDef[i]); i++){
			cell = this.addCellDef(inRowIndex, i, def);
			result.push(cell);
			this.cells.push(cell);
		}
		return result;
	},
	addCellDef: function(inRowIndex, inCellIndex, inDef){
		var w = 0;
		if(inDef.colSpan > 1){
			w = 0;
		}else if(!isNaN(inDef.width)){
			w = inDef.width + "em";
		}else{
			w = inDef.width || this.defaultWidth;
		}
		// fieldIndex progresses linearly from the last indexed field
		// FIXME: support generating fieldIndex based a text field name (probably in Grid)
		var fieldIndex = inDef.field != undefined ? inDef.field : (inDef.get ? -1 : this.fieldIndex);
		if((inDef.field != undefined) || !inDef.get){
			this.fieldIndex = (inDef.field > -1 ? inDef.field : this.fieldIndex) + 1; 
		}
		return new dojox.grid.cell(
			dojo.mixin({}, this._defaultCellProps, inDef, {
				grid: this.grid,
				subrow: inRowIndex,
				layoutIndex: inCellIndex,
				index: this.cells.length,
				fieldIndex: fieldIndex,
				unitWidth: w
			}));
	}
});

}

if(!dojo._hasResource["dojox.grid._grid.rows"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojox.grid._grid.rows"] = true;
dojo.provide("dojox.grid._grid.rows");

dojo.declare("dojox.grid.rows", null, {
	//	Stores information about grid rows. Owned by grid and used internally.
	constructor: function(inGrid){
		this.grid = inGrid;
	},
	linesToEms: 2,
	defaultRowHeight: 1, // lines
	overRow: -2,
	// metrics
	getHeight: function(inRowIndex){
		return '';
	},
	getDefaultHeightPx: function(){
		// summmary:
		// retrieves the default row height
		// returns: int, default row height
		return 32;
		//return Math.round(this.defaultRowHeight * this.linesToEms * this.grid.contentPixelToEmRatio);
	},
	// styles
	prepareStylingRow: function(inRowIndex, inRowNode){
		return {
			index: inRowIndex, 
			node: inRowNode,
			odd: Boolean(inRowIndex&1),
			selected: this.grid.selection.isSelected(inRowIndex),
			over: this.isOver(inRowIndex),
			customStyles: "",
			customClasses: "dojoxGrid-row"
		}
	},
	styleRowNode: function(inRowIndex, inRowNode){
		var row = this.prepareStylingRow(inRowIndex, inRowNode);
		this.grid.onStyleRow(row);
		this.applyStyles(row);
	},
	applyStyles: function(inRow){
		with(inRow){
			node.className = customClasses;
			var h = node.style.height;
			dojox.grid.setStyleText(node, customStyles + ';' + (node._style||''));
			node.style.height = h;
		}
	},
	updateStyles: function(inRowIndex){
		this.grid.updateRowStyles(inRowIndex);
	},
	// states and events
	setOverRow: function(inRowIndex){
		var last = this.overRow;
		this.overRow = inRowIndex;
		if((last!=this.overRow)&&(last >=0)){
			this.updateStyles(last);
		}
		this.updateStyles(this.overRow);
	},
	isOver: function(inRowIndex){
		return (this.overRow == inRowIndex);
	}
});

}

if(!dojo._hasResource["dojox.grid._grid.focus"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojox.grid._grid.focus"] = true;
dojo.provide("dojox.grid._grid.focus");

// focus management
dojo.declare("dojox.grid.focus", null, {
	// summary:
	//	Controls grid cell focus. Owned by grid and used internally for focusing.
	//	Note: grid cell actually receives keyboard input only when cell is being edited.
	constructor: function(inGrid){
		this.grid = inGrid;
		this.cell = null;
		this.rowIndex = -1;
		dojo.connect(this.grid.domNode, "onfocus", this, "doFocus");
	},
	tabbingOut: false,
	focusClass: "dojoxGrid-cell-focus",
	focusView: null,
	initFocusView: function(){
		this.focusView = this.grid.views.getFirstScrollingView();
	},
	isFocusCell: function(inCell, inRowIndex){
		// summary:
		//	states if the given cell is focused
		// inCell: object
		//	grid cell object
		// inRowIndex: int
		//	grid row index
		// returns:
		//	true of the given grid cell is focused
		return (this.cell == inCell) && (this.rowIndex == inRowIndex);
	},
	isLastFocusCell: function(){
		return (this.rowIndex == this.grid.rowCount-1) && (this.cell.index == this.grid.layout.cellCount-1);
	},
	isFirstFocusCell: function(){
		return (this.rowIndex == 0) && (this.cell.index == 0);
	},
	isNoFocusCell: function(){
		return (this.rowIndex < 0) || !this.cell;
	},
	_focusifyCellNode: function(inBork){
		var n = this.cell && this.cell.getNode(this.rowIndex);
		if(n){
			dojo.toggleClass(n, this.focusClass, inBork);
			if (inBork){
				this.scrollIntoView();
				try{
					if(!this.grid.edit.isEditing())
						dojox.grid.fire(n, "focus");
				}catch(e){}
			}
		}
	},
	scrollIntoView: function() {
		if(!this.cell){
			return;
		}
		var 
			c = this.cell,
			s = c.view.scrollboxNode,
			sr = {
				w: s.clientWidth,
				l: s.scrollLeft,
				t: s.scrollTop,
				h: s.clientHeight
			},
			n = c.getNode(this.rowIndex),
			r = c.view.getRowNode(this.rowIndex),
			rt = this.grid.scroller.findScrollTop(this.rowIndex);
		// place cell within horizontal view
		if(n.offsetLeft + n.offsetWidth > sr.l + sr.w){
			s.scrollLeft = n.offsetLeft + n.offsetWidth - sr.w;
		}else if(n.offsetLeft < sr.l){
			s.scrollLeft = n.offsetLeft;
		}
		// place cell within vertical view
		if(rt + r.offsetHeight > sr.t + sr.h){
			this.grid.setScrollTop(rt + r.offsetHeight - sr.h);
		}else if(rt < sr.t){
			this.grid.setScrollTop(rt);
		}
	},
	styleRow: function(inRow){
		return;
	},
	setFocusIndex: function(inRowIndex, inCellIndex){
		// summary:
		//	focuses the given grid cell
		// inRowIndex: int
		//	grid row index
		// inCellIndex: int
		//	grid cell index
		this.setFocusCell(this.grid.getCell(inCellIndex), inRowIndex);
	},
	setFocusCell: function(inCell, inRowIndex){
		// summary:
		//	focuses the given grid cell
		// inCell: object
		//	grid cell object
		// inRowIndex: int
		//	grid row index
		if(inCell && !this.isFocusCell(inCell, inRowIndex)){
			this.tabbingOut = false;
			this.focusGridView();
			this._focusifyCellNode(false);
			this.cell = inCell;
			this.rowIndex = inRowIndex;
			this._focusifyCellNode(true);
		}
		// even if this cell isFocusCell, the document focus may need to be rejiggered
		// call opera on delay to prevent keypress from altering focus
		if(dojo.isOpera){
			setTimeout(dojo.hitch(this.grid, 'onCellFocus', this.cell, this.rowIndex), 1);
		}else{
			this.grid.onCellFocus(this.cell, this.rowIndex);
		}
	},
	next: function(){
		// summary:
		//	focus next grid cell
		var row=this.rowIndex, col=this.cell.index+1, cc=this.grid.layout.cellCount-1, rc=this.grid.rowCount-1;
		if(col > cc){
			col = 0;
			row++;
		}
		if(row > rc){
			col = cc;
			row = rc;
		}
		this.setFocusIndex(row, col);
	},
	previous: function(){
		// summary:
		//	focus previous grid cell
		var row=(this.rowIndex || 0), col=(this.cell.index || 0) - 1;
		if(col < 0){
			col = this.grid.layout.cellCount-1;
			row--;
		}
		if(row < 0){
			row = 0;
			col = 0;
		}
		this.setFocusIndex(row, col);
	},
	move: function(inRowDelta, inColDelta) {
		// summary:
		//	focus grid cell based on position relative to current focus
		// inRowDelta: int
		// vertical distance from current focus
		// inColDelta: int
		// horizontal distance from current focus
		var
			rc = this.grid.rowCount-1,
			cc = this.grid.layout.cellCount-1,
			r = this.rowIndex,
			i = this.cell.index,
			row = Math.min(rc, Math.max(0, r+inRowDelta)),
			col = Math.min(cc, Math.max(0, i+inColDelta));
		this.setFocusIndex(row, col);
		if(inRowDelta){
			this.grid.updateRow(r);
		}
	},
	previousKey: function(e){
		if(this.isFirstFocusCell()){
			this.tabOut(this.grid.domNode);
		}else{
			dojo.stopEvent(e);
			this.previous();
		}
	},
	nextKey: function(e) {
		if(this.isLastFocusCell()){
			this.tabOut(this.grid.lastFocusNode);
		}else{
			dojo.stopEvent(e);
			this.next();
		}
	},
	tabOut: function(inFocusNode){
		this.tabbingOut = true;
		inFocusNode.focus();
	},
	focusGridView: function(){
		dojox.grid.fire(this.focusView, "focus");
	},
	focusGrid: function(inSkipFocusCell){
		this.focusGridView();
		this._focusifyCellNode(true);
	},
	doFocus: function(e){
		// trap focus only for grid dom node
		if(e && e.target != e.currentTarget){
			return;
		}
		// do not focus for scrolling if grid is about to blur
		if(!this.tabbingOut && this.isNoFocusCell()){
			// establish our virtual-focus, if necessary
			this.setFocusIndex(0, 0);
		}
		this.tabbingOut = false;
	}
});

}

if(!dojo._hasResource['dojox.grid._grid.selection']){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource['dojox.grid._grid.selection'] = true;
dojo.provide('dojox.grid._grid.selection');

dojo.declare("dojox.grid.selection",
	null,
	{
	// summary:
	//		Manages row selection for grid. Owned by grid and used internally 
	//		for selection. Override to implement custom selection.

	constructor: function(inGrid){
		this.grid = inGrid;
		this.selected = [];
	},
	
	multiSelect: true,
	selected: null,
	updating: 0,
	selectedIndex: -1,

	onCanSelect: function(inIndex){
		return this.grid.onCanSelect(inIndex);
	},

	onCanDeselect: function(inIndex){
		return this.grid.onCanDeselect(inIndex);
	},

	onSelected: function(inIndex){
		return this.grid.onSelected(inIndex);
	},

	onDeselected: function(inIndex){
		return this.grid.onDeselected(inIndex);
	},

	//onSetSelected: function(inIndex, inSelect) { };
	onChanging: function(){
	},

	onChanged: function(){
		return this.grid.onSelectionChanged();
	},

	isSelected: function(inIndex){
		return this.selected[inIndex];
	},

	getFirstSelected: function(){
		for(var i=0, l=this.selected.length; i<l; i++){
			if(this.selected[i]){
				return i;
			}
		}
		return -1;
	},

	getNextSelected: function(inPrev){
		for(var i=inPrev+1, l=this.selected.length; i<l; i++){
			if(this.selected[i]){
				return i;
			}
		}
		return -1;
	},

	getSelected: function(){
		var result = [];
		for(var i=0, l=this.selected.length; i<l; i++){
			if(this.selected[i]){
				result.push(i);
			}
		}
		return result;
	},

	getSelectedCount: function(){
		var c = 0;
		for(var i=0; i<this.selected.length; i++){
			if(this.selected[i]){
				c++;
			}
		}
		return c;
	},

	beginUpdate: function(){
		if(this.updating == 0){
			this.onChanging();
		}
		this.updating++;
	},

	endUpdate: function(){
		this.updating--;
		if(this.updating == 0){
			this.onChanged();
		}
	},

	select: function(inIndex){
		this.unselectAll(inIndex);
		this.addToSelection(inIndex);
	},

	addToSelection: function(inIndex){
		inIndex = Number(inIndex);
		if(this.selected[inIndex]){
			this.selectedIndex = inIndex;
		}else{
			if(this.onCanSelect(inIndex) !== false){
				this.selectedIndex = inIndex;
				this.beginUpdate();
				this.selected[inIndex] = true;
				this.grid.onSelected(inIndex);
				//this.onSelected(inIndex);
				//this.onSetSelected(inIndex, true);
				this.endUpdate();
			}
		}
	},

	deselect: function(inIndex){
		inIndex = Number(inIndex);
		if(this.selectedIndex == inIndex){
			this.selectedIndex = -1;
		}
		if(this.selected[inIndex]){
			if(this.onCanDeselect(inIndex) === false){
				return;
			}
			this.beginUpdate();
			delete this.selected[inIndex];
			this.grid.onDeselected(inIndex);
			//this.onDeselected(inIndex);
			//this.onSetSelected(inIndex, false);
			this.endUpdate();
		}
	},

	setSelected: function(inIndex, inSelect){
		this[(inSelect ? 'addToSelection' : 'deselect')](inIndex);
	},

	toggleSelect: function(inIndex){
		this.setSelected(inIndex, !this.selected[inIndex])
	},

	insert: function(inIndex){
		this.selected.splice(inIndex, 0, false);
		if(this.selectedIndex >= inIndex){
			this.selectedIndex++;
		}
	},

	remove: function(inIndex){
		this.selected.splice(inIndex, 1);
		if(this.selectedIndex >= inIndex){
			this.selectedIndex--;
		}
	},

	unselectAll: function(inExcept){
		for(var i in this.selected){
			if((i!=inExcept)&&(this.selected[i]===true)){
				this.deselect(i);
			}
		}
	},

	shiftSelect: function(inFrom, inTo){
		var s = (inFrom >= 0 ? inFrom : inTo), e = inTo;
		if(s > e){
			e = s;
			s = inTo;
		}
		for(var i=s; i<=e; i++){
			this.addToSelection(i);
		}
	},

	clickSelect: function(inIndex, inCtrlKey, inShiftKey){
		this.beginUpdate();
		if(!this.multiSelect){
			this.select(inIndex);
		}else{
			var lastSelected = this.selectedIndex;
			if(!inCtrlKey){
				this.unselectAll(inIndex);
			}
			if(inShiftKey){
				this.shiftSelect(lastSelected, inIndex);
			}else if(inCtrlKey){
				this.toggleSelect(inIndex);
			}else{
				this.addToSelection(inIndex)
			}
		}
		this.endUpdate();
	},

	clickSelectEvent: function(e){
		this.clickSelect(e.rowIndex, e.ctrlKey, e.shiftKey);
	},

	clear: function(){
		this.beginUpdate();
		this.unselectAll();
		this.endUpdate();
	}

});

}

if(!dojo._hasResource["dojox.grid._grid.edit"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojox.grid._grid.edit"] = true;
dojo.provide("dojox.grid._grid.edit");

dojo.declare("dojox.grid.edit", null, {
	// summary:
	//		Controls grid cell editing process. Owned by grid and used internally for editing.
	constructor: function(inGrid){
		// inGrid: dojox.Grid
		//		The dojox.Grid this editor should be attached to
		this.grid = inGrid;
		this.connections = [];
		if(dojo.isIE){
			this.connections.push(dojo.connect(document.body, "onfocus", dojo.hitch(this, "_boomerangFocus")));
		}
	},
	
	info: {},

	destroy: function(){
		dojo.forEach(this.connections,dojo.disconnect);
	},

	cellFocus: function(inCell, inRowIndex){
		// summary:
		//		Invoke editing when cell is focused
		// inCell: cell object
		//		Grid cell object
		// inRowIndex: Integer
		//		Grid row index
		if(this.grid.singleClickEdit || this.isEditRow(inRowIndex)){
			// if same row or quick editing, edit
			this.setEditCell(inCell, inRowIndex);
		}else{
			// otherwise, apply any pending row edits
			this.apply();
		}
		// if dynamic or static editing...
		if(this.isEditing() || (inCell && (inCell.editor||0).alwaysOn)){
			// let the editor focus itself as needed
			this._focusEditor(inCell, inRowIndex);
		}
	},

	rowClick: function(e){
		if(this.isEditing() && !this.isEditRow(e.rowIndex)){
			this.apply();
		}
	},

	styleRow: function(inRow){
		if(inRow.index == this.info.rowIndex){
			inRow.customClasses += ' dojoxGrid-row-editing';
		}
	},

	dispatchEvent: function(e){
		var c = e.cell, ed = c && c.editor;
		return ed && ed.dispatchEvent(e.dispatch, e);
	},

	// Editing
	isEditing: function(){
		// summary:
		//		Indicates editing state of the grid.
		// returns: Boolean
		//	 	True if grid is actively editing
		return this.info.rowIndex !== undefined;
	},

	isEditCell: function(inRowIndex, inCellIndex){
		// summary:
		//		Indicates if the given cell is being edited.
		// inRowIndex: Integer
		//		Grid row index
		// inCellIndex: Integer
		//		Grid cell index
		// returns: Boolean
		//	 	True if given cell is being edited
		return (this.info.rowIndex === inRowIndex) && (this.info.cell.index == inCellIndex);
	},

	isEditRow: function(inRowIndex){
		// summary:
		//		Indicates if the given row is being edited.
		// inRowIndex: Integer
		//		Grid row index
		// returns: Boolean
		//	 	True if given row is being edited
		return this.info.rowIndex === inRowIndex;
	},

	setEditCell: function(inCell, inRowIndex){
		// summary:
		//		Set the given cell to be edited
		// inRowIndex: Integer
		//		Grid row index
		// inCell: Object
		//		Grid cell object
		if(!this.isEditCell(inRowIndex, inCell.index) && this.grid.canEdit(inCell, inRowIndex)){
			this.start(inCell, inRowIndex, this.isEditRow(inRowIndex) || inCell.editor);
		}
	},

	_focusEditor: function(inCell, inRowIndex){
		dojox.grid.fire(inCell.editor, "focus", [inRowIndex]);
	},

	focusEditor: function(){
		if(this.isEditing()){
			this._focusEditor(this.info.cell, this.info.rowIndex);
		}
	},

	// implement fix for focus boomerang effect on IE
	_boomerangWindow: 500,
	_shouldCatchBoomerang: function(){
		return this._catchBoomerang > new Date().getTime();
	},
	_boomerangFocus: function(){
		//console.log("_boomerangFocus");
		if(this._shouldCatchBoomerang()){
			// make sure we don't utterly lose focus
			this.grid.focus.focusGrid();
			// let the editor focus itself as needed
			this.focusEditor();
			// only catch once
			this._catchBoomerang = 0;
		}
	},
	_doCatchBoomerang: function(){
		// give ourselves a few ms to boomerang IE focus effects
		if(dojo.isIE){this._catchBoomerang = new Date().getTime() + this._boomerangWindow;}
	},
	// end boomerang fix API

	start: function(inCell, inRowIndex, inEditing){
		this.grid.beginUpdate();
		this.editorApply();
		if(this.isEditing() && !this.isEditRow(inRowIndex)){
			this.applyRowEdit();
			this.grid.updateRow(inRowIndex);
		}
		if(inEditing){
			this.info = { cell: inCell, rowIndex: inRowIndex };
			this.grid.doStartEdit(inCell, inRowIndex); 
			this.grid.updateRow(inRowIndex);
		}else{
			this.info = {};
		}
		this.grid.endUpdate();
		// make sure we don't utterly lose focus
		this.grid.focus.focusGrid();
		// let the editor focus itself as needed
		this._focusEditor(inCell, inRowIndex);
		// give ourselves a few ms to boomerang IE focus effects
		this._doCatchBoomerang();
	},

	_editorDo: function(inMethod){
		var c = this.info.cell
		//c && c.editor && c.editor[inMethod](c, this.info.rowIndex);
		c && c.editor && c.editor[inMethod](this.info.rowIndex);
	},

	editorApply: function(){
		this._editorDo("apply");
	},

	editorCancel: function(){
		this._editorDo("cancel");
	},

	applyCellEdit: function(inValue, inCell, inRowIndex){
		if(this.grid.canEdit(inCell, inRowIndex)){
			this.grid.doApplyCellEdit(inValue, inRowIndex, inCell.fieldIndex);
		}
	},

	applyRowEdit: function(){
		this.grid.doApplyEdit(this.info.rowIndex);
	},

	apply: function(){
		// summary:
		//		Apply a grid edit
		if(this.isEditing()){
			this.grid.beginUpdate();
			this.editorApply();
			this.applyRowEdit();
			this.info = {};
			this.grid.endUpdate();
			this.grid.focus.focusGrid();
			this._doCatchBoomerang();
		}
	},

	cancel: function(){
		// summary:
		//		Cancel a grid edit
		if(this.isEditing()){
			this.grid.beginUpdate();
			this.editorCancel();
			this.info = {};
			this.grid.endUpdate();
			this.grid.focus.focusGrid();
			this._doCatchBoomerang();
		}
	},

	save: function(inRowIndex, inView){
		// summary:
		//		Save the grid editing state
		// inRowIndex: Integer
		//		Grid row index
		// inView: Object
		//		Grid view
		var c = this.info.cell;
		if(this.isEditRow(inRowIndex) && (!inView || c.view==inView) && c.editor){
			c.editor.save(c, this.info.rowIndex);
		}
	},

	restore: function(inView, inRowIndex){
		// summary:
		//		Restores the grid editing state
		// inRowIndex: Integer
		//		Grid row index
		// inView: Object
		//		Grid view
		var c = this.info.cell;
		if(this.isEditRow(inRowIndex) && c.view == inView && c.editor){
			c.editor.restore(c, this.info.rowIndex);
		}
	}
});

}

if(!dojo._hasResource["dojox.grid._grid.rowbar"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojox.grid._grid.rowbar"] = true;
dojo.provide("dojox.grid._grid.rowbar");


dojo.declare('dojox.GridRowView', dojox.GridView, {
	// summary:
	//	Custom grid view. If used in a grid structure, provides a small selectable region for grid rows.
	defaultWidth: "3em",
	noscroll: true,
	padBorderWidth: 2,
	buildRendering: function(){
		this.inherited('buildRendering', arguments);
		this.scrollboxNode.style.overflow = "hidden";
		this.headerNode.style.visibility = "hidden";
	},	
	getWidth: function(){
		return this.viewWidth || this.defaultWidth;
	},
	buildRowContent: function(inRowIndex, inRowNode){
		var w = this.contentNode.offsetWidth - this.padBorderWidth 
		inRowNode.innerHTML = '<table style="width:' + w + 'px;" role="wairole:presentation"><tr><td class="dojoxGrid-rowbar-inner"></td></tr></table>';
	},
	renderHeader: function(){
	},
	resize: function(){
		this.adaptHeight();
	},
	adaptWidth: function(){
	},
	// styling
	doStyleRowNode: function(inRowIndex, inRowNode){
		var n = [ "dojoxGrid-rowbar" ];
		if(this.grid.rows.isOver(inRowIndex)){
			n.push("dojoxGrid-rowbar-over");
		}
		if(this.grid.selection.isSelected(inRowIndex)){
			n.push("dojoxGrid-rowbar-selected");
		}
		inRowNode.className = n.join(" ");
	},
	// event handlers
	domouseover: function(e){
		this.grid.onMouseOverRow(e);
	},
	domouseout: function(e){
		if(!this.isIntraRowEvent(e)){
			this.grid.onMouseOutRow(e);
		}
	}
});

}

if(!dojo._hasResource["dojox.grid._grid.publicEvents"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojox.grid._grid.publicEvents"] = true;
dojo.provide("dojox.grid._grid.publicEvents");

dojox.grid.publicEvents = {
	// summary:
	//		VirtualGrid mixin that provides default implementations for grid events.
	// description: 
	//		Default ynthetic events dispatched for VirtualGrid. dojo.connect to events to
	//		retain default implementation or override them for custom handling.
	
	// cellOverClass: String
	// 		css class to apply to grid cells over which the cursor is placed.
	cellOverClass: "dojoxGrid-cell-over",
	
	onKeyEvent: function(e){
		// summary: top level handler for Key Events
		this.dispatchKeyEvent(e);
	},

	onContentEvent: function(e){
		// summary: Top level handler for Content events
		this.dispatchContentEvent(e);
	},

	onHeaderEvent: function(e){
		// summary: Top level handler for header events
		this.dispatchHeaderEvent(e);
	},

	onStyleRow: function(inRow){
		// summary:
		//		Perform row styling on a given row. Called whenever row styling is updated.
		//
		// inRow: Object
		// 		Object containing row state information: selected, true if the row is selcted; over:
		// 		true of the mouse is over the row; odd: true if the row is odd. Use customClasses and
		// 		customStyles to control row css classes and styles; both properties are strings.
		//
		// example: onStyleRow({ selected: true, over:true, odd:false })
		with(inRow){
			customClasses += (odd?" dojoxGrid-row-odd":"") + (selected?" dojoxGrid-row-selected":"") + (over?" dojoxGrid-row-over":"");
		}
		this.focus.styleRow(inRow);
		this.edit.styleRow(inRow);
	},
	
	onKeyDown: function(e){
		// summary:
		// 		Grid key event handler. By default enter begins editing and applies edits, escape cancels and edit,
		// 		tab, shift-tab, and arrow keys move grid cell focus.
		if(e.altKey || e.ctrlKey || e.metaKey){
			return;
		}
		var dk = dojo.keys;
		switch(e.keyCode){
			case dk.ESCAPE:
				this.edit.cancel();
				break;
			case dk.ENTER:
				if(!e.shiftKey){
					var isEditing = this.edit.isEditing();
					this.edit.apply();
					if(!isEditing){
						this.edit.setEditCell(this.focus.cell, this.focus.rowIndex);
					}
				}
				break;
			case dk.TAB:
				this.focus[e.shiftKey ? 'previousKey' : 'nextKey'](e);
				break;
			case dk.LEFT_ARROW:
			case dk.RIGHT_ARROW:
				if(!this.edit.isEditing()){
					dojo.stopEvent(e);
					var offset = (e.keyCode == dk.LEFT_ARROW) ? 1 : -1;
					if(dojo._isBodyLtr()){ offset *= -1; }
					this.focus.move(0, offset);
				}
				break;
			case dk.UP_ARROW:
				if(!this.edit.isEditing() && this.focus.rowIndex != 0){
					dojo.stopEvent(e);
					this.focus.move(-1, 0);
				}
				break;
			case dk.DOWN_ARROW:
				if(!this.edit.isEditing() && this.focus.rowIndex+1 != this.model.count){
					dojo.stopEvent(e);
					this.focus.move(1, 0);
				}
				break;
			case dk.PAGE_UP:
				if(!this.edit.isEditing() && this.focus.rowIndex != 0){
					dojo.stopEvent(e);
					if(this.focus.rowIndex != this.scroller.firstVisibleRow+1){
						this.focus.move(this.scroller.firstVisibleRow-this.focus.rowIndex, 0);
					}else{
						this.setScrollTop(this.scroller.findScrollTop(this.focus.rowIndex-1));
						this.focus.move(this.scroller.firstVisibleRow-this.scroller.lastVisibleRow+1, 0);
					}
				}
				break;
			case dk.PAGE_DOWN:
				if(!this.edit.isEditing() && this.focus.rowIndex+1 != this.model.count){
					dojo.stopEvent(e);
					if(this.focus.rowIndex != this.scroller.lastVisibleRow-1){
						this.focus.move(this.scroller.lastVisibleRow-this.focus.rowIndex-1, 0);
					}else{
						this.setScrollTop(this.scroller.findScrollTop(this.focus.rowIndex+1));
						this.focus.move(this.scroller.lastVisibleRow-this.scroller.firstVisibleRow-1, 0);
					}
				}
				break;
		}
	},
	
	onMouseOver: function(e){
		// summary:
		//		Event fired when mouse is over the grid.
		// e: Event
		//		Decorated event object contains reference to grid, cell, and rowIndex
		e.rowIndex == -1 ? this.onHeaderCellMouseOver(e) : this.onCellMouseOver(e);
	},
	
	onMouseOut: function(e){
		// summary:
		//		Event fired when mouse moves out of the grid.
		// e: Event
		//		Decorated event object that contains reference to grid, cell, and rowIndex
		e.rowIndex == -1 ? this.onHeaderCellMouseOut(e) : this.onCellMouseOut(e);
	},
	
	onMouseDown: function(e){
		// summary:
		//		Event fired when mouse is down inside grid.
		// e: Event
		//		Decorated event object that contains reference to grid, cell, and rowIndex
		e.rowIndex == -1 ? this.onHeaderCellMouseDown(e) : this.onCellMouseDown(e);
	},
	
	onMouseOverRow: function(e){
		// summary:
		//		Event fired when mouse is over any row (data or header).
		// e: Event
		//		Decorated event object contains reference to grid, cell, and rowIndex
		if(!this.rows.isOver(e.rowIndex)){
			this.rows.setOverRow(e.rowIndex);
			e.rowIndex == -1 ? this.onHeaderMouseOver(e) : this.onRowMouseOver(e);
		}
	},
	onMouseOutRow: function(e){
		// summary:
		//		Event fired when mouse moves out of any row (data or header).
		// e: Event
		//		Decorated event object contains reference to grid, cell, and rowIndex
		if(this.rows.isOver(-1)){
			this.onHeaderMouseOut(e);
		}else if(!this.rows.isOver(-2)){
			this.rows.setOverRow(-2);
			this.onRowMouseOut(e);
		}
	},
	
	onMouseDownRow: function(e){
		// summary:
		//		Event fired when mouse is down inside grid row
		// e: Event
		//		Decorated event object that contains reference to grid, cell, and rowIndex
		if(e.rowIndex != -1)
			this.onRowMouseDown(e);
	},

	// cell events
	onCellMouseOver: function(e){
		// summary:
		//		Event fired when mouse is over a cell.
		// e: Event
		//		Decorated event object contains reference to grid, cell, and rowIndex
		dojo.addClass(e.cellNode, this.cellOverClass);
	},
	
	onCellMouseOut: function(e){
		// summary:
		//		Event fired when mouse moves out of a cell.
		// e: Event
		//		Decorated event object which contains reference to grid, cell, and rowIndex
		dojo.removeClass(e.cellNode, this.cellOverClass);
	},
	
	onCellMouseDown: function(e){
		// summary:
		//		Event fired when mouse is down in a header cell.
		// e: Event
		// 		Decorated event object which contains reference to grid, cell, and rowIndex
	},

	onCellClick: function(e){
		// summary:
		//		Event fired when a cell is clicked.
		// e: Event
		//		Decorated event object which contains reference to grid, cell, and rowIndex
		this._click[0] = this._click[1];
		this._click[1] = e;
		if(!this.edit.isEditCell(e.rowIndex, e.cellIndex)){
			this.focus.setFocusCell(e.cell, e.rowIndex);
		}
		this.onRowClick(e);
	},

	onCellDblClick: function(e){
		// summary:
		//		Event fired when a cell is double-clicked.
		// e: Event
		//		Decorated event object contains reference to grid, cell, and rowIndex
		if(dojo.isIE){
			this.edit.setEditCell(this._click[1].cell, this._click[1].rowIndex);
		}else if(this._click[0].rowIndex != this._click[1].rowIndex){
			this.edit.setEditCell(this._click[0].cell, this._click[0].rowIndex);
		}else{
			this.edit.setEditCell(e.cell, e.rowIndex);
		}
		this.onRowDblClick(e);
	},

	onCellContextMenu: function(e){
		// summary:
		//		Event fired when a cell context menu is accessed via mouse right click.
		// e: Event
		//		Decorated event object which contains reference to grid, cell, and rowIndex
		this.onRowContextMenu(e);
	},

	onCellFocus: function(inCell, inRowIndex){
		// summary:
		//		Event fired when a cell receives focus.
		// inCell: Object
		//		Cell object containing properties of the grid column.
		// inRowIndex: Integer
		//		Index of the grid row
		this.edit.cellFocus(inCell, inRowIndex);
	},

	// row events
	onRowClick: function(e){
		// summary:
		//		Event fired when a row is clicked.
		// e: Event
		//		Decorated event object which contains reference to grid, cell, and rowIndex
		this.edit.rowClick(e);
		this.selection.clickSelectEvent(e);
	},

	onRowDblClick: function(e){
		// summary:
		//		Event fired when a row is double clicked.
		// e: Event
		//		decorated event object which contains reference to grid, cell, and rowIndex
	},

	onRowMouseOver: function(e){
		// summary:
		//		Event fired when mouse moves over a data row.
		// e: Event
		//		Decorated event object which contains reference to grid, cell, and rowIndex
	},

	onRowMouseOut: function(e){
		// summary:
		//		Event fired when mouse moves out of a data row.
		// e: Event
		// 		Decorated event object contains reference to grid, cell, and rowIndex
	},
	
	onRowMouseDown: function(e){
		// summary:
		//		Event fired when mouse is down in a row.
		// e: Event
		// 		Decorated event object which contains reference to grid, cell, and rowIndex
	},

	onRowContextMenu: function(e){
		// summary:
		//		Event fired when a row context menu is accessed via mouse right click.
		// e: Event
		// 		Decorated event object which contains reference to grid, cell, and rowIndex
		dojo.stopEvent(e);
	},

	// header events
	onHeaderMouseOver: function(e){
		// summary:
		//		Event fired when mouse moves over the grid header.
		// e: Event
		// 		Decorated event object contains reference to grid, cell, and rowIndex
	},

	onHeaderMouseOut: function(e){
		// summary:
		//		Event fired when mouse moves out of the grid header.
		// e: Event
		// 		Decorated event object which contains reference to grid, cell, and rowIndex
	},

	onHeaderCellMouseOver: function(e){
		// summary:
		//		Event fired when mouse moves over a header cell.
		// e: Event
		// 		Decorated event object which contains reference to grid, cell, and rowIndex
		dojo.addClass(e.cellNode, this.cellOverClass);
	},

	onHeaderCellMouseOut: function(e){
		// summary:
		//		Event fired when mouse moves out of a header cell.
		// e: Event
		// 		Decorated event object which contains reference to grid, cell, and rowIndex
		dojo.removeClass(e.cellNode, this.cellOverClass);
	},
	
	onHeaderCellMouseDown: function(e) {
		// summary:
		//		Event fired when mouse is down in a header cell.
		// e: Event
		// 		Decorated event object which contains reference to grid, cell, and rowIndex
	},

	onHeaderClick: function(e){
		// summary:
		//		Event fired when the grid header is clicked.
		// e: Event
		// Decorated event object which contains reference to grid, cell, and rowIndex
	},

	onHeaderCellClick: function(e){
		// summary:
		//		Event fired when a header cell is clicked.
		// e: Event
		//		Decorated event object which contains reference to grid, cell, and rowIndex
		this.setSortIndex(e.cell.index);
		this.onHeaderClick(e);
	},

	onHeaderDblClick: function(e){
		// summary:
		//		Event fired when the grid header is double clicked.
		// e: Event
		//		Decorated event object which contains reference to grid, cell, and rowIndex
	},

	onHeaderCellDblClick: function(e){
		// summary:
		//		Event fired when a header cell is double clicked.
		// e: Event
		//		Decorated event object which contains reference to grid, cell, and rowIndex
		this.onHeaderDblClick(e);
	},

	onHeaderCellContextMenu: function(e){
		// summary:
		//		Event fired when a header cell context menu is accessed via mouse right click.
		// e: Event
		//		Decorated event object which contains reference to grid, cell, and rowIndex
		this.onHeaderContextMenu(e);
	},

	onHeaderContextMenu: function(e){
		// summary:
		//		Event fired when the grid header context menu is accessed via mouse right click.
		// e: Event
		//		Decorated event object which contains reference to grid, cell, and rowIndex
		dojo.stopEvent(e);
	},

	// editing
	onStartEdit: function(inCell, inRowIndex){
		// summary:
		//		Event fired when editing is started for a given grid cell
		// inCell: Object
		//		Cell object containing properties of the grid column.
		// inRowIndex: Integer
		//		Index of the grid row
	},

	onApplyCellEdit: function(inValue, inRowIndex, inFieldIndex){
		// summary:
		//		Event fired when editing is applied for a given grid cell
		// inValue: String
		//		Value from cell editor
		// inRowIndex: Integer
		//		Index of the grid row
		// inFieldIndex: Integer
		//		Index in the grid's data model
	},

	onCancelEdit: function(inRowIndex){
		// summary:
		//		Event fired when editing is cancelled for a given grid cell
		// inRowIndex: Integer
		//		Index of the grid row
	},

	onApplyEdit: function(inRowIndex){
		// summary:
		//		Event fired when editing is applied for a given grid row
		// inRowIndex: Integer
		//		Index of the grid row
	},

	onCanSelect: function(inRowIndex){
		// summary:
		//		Event to determine if a grid row may be selected
		// inRowIndex: Integer
		//		Index of the grid row
		// returns: Boolean
		//		true if the row can be selected
		return true;
	},

	onCanDeselect: function(inRowIndex){
		// summary:
		//		Event to determine if a grid row may be deselected
		// inRowIndex: Integer
		//		Index of the grid row
		// returns: Boolean
		//		true if the row can be deselected
		return true;
	},

	onSelected: function(inRowIndex){
		// summary:
		//		Event fired when a grid row is selected
		// inRowIndex: Integer
		//		Index of the grid row
		this.updateRowStyles(inRowIndex);
	},

	onDeselected: function(inRowIndex){
		// summary:
		//		Event fired when a grid row is deselected
		// inRowIndex: Integer
		//		Index of the grid row
		this.updateRowStyles(inRowIndex);
	},

	onSelectionChanged: function(){
	}

}

}

if(!dojo._hasResource["dojox.grid.VirtualGrid"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojox.grid.VirtualGrid"] = true;
dojo.provide("dojox.grid.VirtualGrid");













dojo.declare('dojox.VirtualGrid', 
	[ dijit._Widget, dijit._Templated ], 
	{
	// summary:
	// 		A grid widget with virtual scrolling, cell editing, complex rows,
	// 		sorting, fixed columns, sizeable columns, etc.
	//
	//	description:
	//		VirtualGrid provides the full set of grid features without any
	//		direct connection to a data store.
	//
	//		The grid exposes a get function for the grid, or optionally
	//		individual columns, to populate cell contents.
	//
	//		The grid is rendered based on its structure, an object describing
	//		column and cell layout.
	//
	//	example:
	//		A quick sample:
	//		
	//		define a get function
	//	|	function get(inRowIndex){ // called in cell context
	//	|		return [this.index, inRowIndex].join(', ');
	//	|	}
	//		
	//		define the grid structure:
	//	|	var structure = [ // array of view objects
	//	|		{ cells: [// array of rows, a row is an array of cells
	//	|			[
	//	|				{ name: "Alpha", width: 6 }, 
	//	|				{ name: "Beta" }, 
	//	|				{ name: "Gamma", get: get }]
	//	|		]}
	//	|	];
	//		
	//	|	<div id="grid" 
	//	|		rowCount="100" get="get" 
	//	|		structure="structure" 
	//	|		dojoType="dojox.VirtualGrid"></div>

	templateString:"<div class=\"dojoxGrid\" hidefocus=\"hidefocus\" role=\"wairole:grid\">\r\n\t<div class=\"dojoxGrid-master-header\" dojoAttachPoint=\"viewsHeaderNode\"></div>\r\n\t<div class=\"dojoxGrid-master-view\" dojoAttachPoint=\"viewsNode\"></div>\r\n\t<span dojoAttachPoint=\"lastFocusNode\" tabindex=\"0\"></span>\r\n</div>\r\n",
	
	// classTag: String
	// 		CSS class applied to the grid's domNode
	classTag: 'dojoxGrid',

	get: function(inRowIndex){
		// summary: Default data getter. 
		// description:
		//		Provides data to display in a grid cell. Called in grid cell context.
		//		So this.cell.index is the column index.
		// inRowIndex: Integer
		//		Row for which to provide data
		// returns:
		//		Data to display for a given grid cell.
	},
	
	// settings
	// rowCount: Integer
	//		Number of rows to display. 
	rowCount: 5,

	// keepRows: Integer
	//		Number of rows to keep in the rendering cache.
	keepRows: 75,
	
	// rowsPerPage: Integer
	//		Number of rows to render at a time.
	rowsPerPage: 25,

	// autoWidth: Boolean
	//		If autoWidth is true, grid width is automatically set to fit the data.
	autoWidth: false,
	
	// autoHeight: Boolean
	//		If autoHeight is true, grid height is automatically set to fit the data.
	autoHeight: false,
	
	// autoRender: Boolean
	//		If autoRender is true, grid will render itself after initialization.
	autoRender: true,

	// defaultHeight: String
	//		default height of the grid, measured in any valid css unit.
	defaultHeight: '15em',

	// structure: Object|String
	//		View layout defintion. Can be set to a layout object, or to the (string) name of a layout object.
	structure: '',

	// elasticView: Integer
	//	Override defaults and make the indexed grid view elastic, thus filling available horizontal space.
	elasticView: -1,
	
	// singleClickEdit: boolean
	//		Single-click starts editing. Default is double-click
	singleClickEdit: false,

	// Used to store the last two clicks, to ensure double-clicking occurs based on the intended row
	_click: null,
	
	// private
	sortInfo: 0,
	themeable: true,

	// initialization
	buildRendering: function(){
		this.inherited(arguments);
		// reset get from blank function (needed for markup parsing) to null, if not changed
		if(this.get == dojox.VirtualGrid.prototype.get){
			this.get = null;
		}
		if(!this.domNode.getAttribute('tabIndex')){
			this.domNode.tabIndex = "0";
		}
		this.createScroller();
		this.createLayout();
		this.createViews();
		this.createManagers();
		dojox.grid.initTextSizePoll();
		this.connect(dojox.grid, "textSizeChanged", "textSizeChanged");
		dojox.grid.funnelEvents(this.domNode, this, 'doKeyEvent', dojox.grid.keyEvents);
		this.connect(this, "onShow", "renderOnIdle");
	},
	postCreate: function(){
		// replace stock styleChanged with one that triggers an update
		this.styleChanged = this._styleChanged;
		this.setStructure(this.structure);
		this._click = [];
	},
	
	destroy: function(){
		this.domNode.onReveal = null;
		this.domNode.onSizeChange = null;
		this.edit.destroy();
		this.views.destroyViews();
		this.inherited(arguments);
	},
	
	styleChanged: function(){
		this.setStyledClass(this.domNode, '');
	},
	
	_styleChanged: function(){
		this.styleChanged();
		this.update();
	},
	
	textSizeChanged: function(){
		setTimeout(dojo.hitch(this, "_textSizeChanged"), 1);
	},
	
	_textSizeChanged: function(){
		if(this.domNode){
			this.views.forEach(function(v){
				v.content.update();
			});
			this.render();
		}
	},
	
	sizeChange: function(){
		dojox.grid.jobs.job(this.id + 'SizeChange', 50, dojo.hitch(this, "update"));
	},
	
	renderOnIdle: function() {
		setTimeout(dojo.hitch(this, "render"), 1);
	},
	
	createManagers: function(){
		// summary:
		//		create grid managers for various tasks including rows, focus, selection, editing
		
		// row manager
		this.rows = new dojox.grid.rows(this);
		// focus manager
		this.focus = new dojox.grid.focus(this);
		// selection manager
		this.selection = new dojox.grid.selection(this);
		// edit manager
		this.edit = new dojox.grid.edit(this);
	},

	createScroller: function(){
		// summary: Creates a new virtual scroller
		this.scroller = new dojox.grid.scroller.columns();
		this.scroller._pageIdPrefix = this.id + '-';
		this.scroller.renderRow = dojo.hitch(this, "renderRow");
		this.scroller.removeRow = dojo.hitch(this, "rowRemoved");
	},

	createLayout: function(){
		// summary: Creates a new Grid layout
		this.layout = new dojox.grid.layout(this);
	},

	// views
	createViews: function(){
		this.views = new dojox.grid.views(this);
		this.views.createView = dojo.hitch(this, "createView");
	},
	
	createView: function(inClass){
		if(dojo.isAIR){
			var obj = window;
			var names = inClass.split('.');
			for(var i=0;i<names.length;i++){
				if(typeof obj[names[i]]=='undefined'){
					var undefstring = names[0];
					for(var j=1;j<=i;j++){
						undefstring+="."+names[j];
					}
					throw new Error(undefstring+" is undefined");
				}
				obj = obj[names[i]];
			}
			var c = obj;
		}else{
			var c = eval(inClass);
		}
		var view = new c({ grid: this });
		this.viewsNode.appendChild(view.domNode);
		this.viewsHeaderNode.appendChild(view.headerNode);
		this.views.addView(view);
		return view;
	},

	buildViews: function(){
		for(var i=0, vs; (vs=this.layout.structure[i]); i++){
			this.createView(vs.type || dojox._scopeName + ".GridView").setStructure(vs);
		}
		this.scroller.setContentNodes(this.views.getContentNodes());
	},
	
	setStructure: function(inStructure){
		// summary:
		//		Install a new structure and rebuild the grid.
		// inStructure: Object
		//		Structure object defines the grid layout and provides various
		//		options for grid views and columns
		//	description:
		//		A grid structure is an array of view objects. A view object can
		//		specify a view type (view class), width, noscroll (boolean flag
		//		for view scrolling), and cells. Cells is an array of objects
		//		corresponding to each grid column. The view cells object is an
		//		array of subrows comprising a single row. Each subrow is an
		//		array of column objects. A column object can have a name,
		//		width, value (default), get function to provide data, styles,
		//		and span attributes (rowSpan, colSpan).

		this.views.destroyViews();
		this.structure = inStructure;
		if((this.structure)&&(dojo.isString(this.structure))){
			this.structure=dojox.grid.getProp(this.structure);
		}
		if(!this.structure){
			this.structure=window["layout"];
		}
		if(!this.structure){
			return;
		}
		this.layout.setStructure(this.structure);
		this._structureChanged();
	},

	_structureChanged: function() {
		this.buildViews();
		if(this.autoRender){
			this.render();
		}
	},

	hasLayout: function() {
		return this.layout.cells.length;
	},

	// sizing
	resize: function(sizeBox){
	// summary:
		//		Update the grid's rendering dimensions and resize it
		// sizeBox: Object?
		//		{w: int, h: int, l: int, t: int}
		
		// FIXME: If grid is not sized explicitly, sometimes bogus scrollbars 
		// can appear in our container, which may require an extra call to 'resize'
		// to sort out.
		this._sizeBox = sizeBox;
		this._resize();
		this.sizeChange();
	},
	
	_getPadBorder: function() {
		this._padBorder = this._padBorder || dojo._getPadBorderExtents(this.domNode);
		return this._padBorder;
	},
	
	_resize: function(){
		// if we have set up everything except the DOM, we cannot resize
		if(!this.domNode.parentNode || this.domNode.parentNode.nodeType != 1 || !this.hasLayout()){
			return;
		}
		// useful measurement
		var padBorder = this._getPadBorder();
		// grid height
		if(this.autoHeight){
			this.domNode.style.height = 'auto';
			this.viewsNode.style.height = '';
		}else if(this.flex > 0){
		}else if(this.domNode.clientHeight <= padBorder.h){
			if(this.domNode.parentNode == document.body){
				this.domNode.style.height = this.defaultHeight;
			}else{
				this.fitTo = "parent";
			}
		}
		// if we are given dimensions, size the grid's domNode to those dimensions
		if(this._sizeBox){
			dojo.contentBox(this.domNode, this._sizeBox);
		}else if(this.fitTo == "parent"){
			var h = dojo._getContentBox(this.domNode.parentNode).h;
			dojo.marginBox(this.domNode, { h: Math.max(0, h) });
		}
		
		var h = dojo._getContentBox(this.domNode).h;
		if(h == 0 && !this.autoHeight){
			// We need to hide the header, since the Grid is essentially hidden.
			this.viewsHeaderNode.style.display = "none";
		}else{
			// Otherwise, show the header and give it an appropriate height.
			this.viewsHeaderNode.style.display = "block";
		}
		
		// NOTE: it is essential that width be applied before height
		// Header height can only be calculated properly after view widths have been set.
		// This is because flex column width is naturally 0 in Firefox.
		// Therefore prior to width sizing flex columns with spaces are maximally wrapped 
		// and calculated to be too tall.
		this.adaptWidth();
		this.adaptHeight();
		
		// default row height (FIXME: use running average(?), remove magic #)
		this.scroller.defaultRowHeight = this.rows.getDefaultHeightPx() + 1;
		this.postresize();
	},

	adaptWidth: function() {
		// private: sets width and position for views and update grid width if necessary
		var
			w = this.autoWidth ? 0 : this.domNode.clientWidth || (this.domNode.offsetWidth - this._getPadBorder().w);
			vw = this.views.arrange(1, w);
		this.views.onEach("adaptWidth");
		if (this.autoWidth)
			this.domNode.style.width = vw + "px";
	},

	adaptHeight: function(){
		// private: measures and normalizes header height, then sets view heights, and then updates scroller
		var vns = this.viewsHeaderNode.style, t = vns.display == "none" ? 0 : this.views.measureHeader();
		vns.height = t + 'px';
		// header heights are reset during measuring so must be normalized after measuring.
		this.views.normalizeHeaderNodeHeight();
		// content extent
		var h = (this.autoHeight ? -1 : Math.max(this.domNode.clientHeight - t, 0) || 0);
		this.views.onEach('setSize', [0, h]);
		this.views.onEach('adaptHeight');
		this.scroller.windowHeight = h; 
	},

	// render 
	render: function(){
		// summary:
		//	Render the grid, headers, and views. Edit and scrolling states are reset. To retain edit and 
		// scrolling states, see Update.

		if(!this.domNode){return;}
		
		if(!this.hasLayout()) {
			this.scroller.init(0, this.keepRows, this.rowsPerPage);
			return;
		}
		//
		this.update = this.defaultUpdate;
		this.scroller.init(this.rowCount, this.keepRows, this.rowsPerPage);
		this.prerender();
		this.setScrollTop(0);
		this.postrender();
	},

	prerender: function(){
		// if autoHeight, make sure scroller knows not to virtualize; everything must be rendered.
		this.keepRows = this.autoHeight ? 0 : this.constructor.prototype.keepRows;
		this.scroller.setKeepInfo(this.keepRows);
		this.views.render();
		this._resize();
	},

	postrender: function(){
		this.postresize();
		this.focus.initFocusView();
		// make rows unselectable
		dojo.setSelectable(this.domNode, false);
	},

	postresize: function(){
		// views are position absolute, so they do not inflate the parent
		if(this.autoHeight){
			this.viewsNode.style.height = this.views.measureContent() + 'px';
		}
	},

	renderRow: function(inRowIndex, inNodes){
		// summary: private, used internally to render rows
		this.views.renderRow(inRowIndex, inNodes);
	},

	rowRemoved: function(inRowIndex){
		// summary: private, used internally to remove rows
		this.views.rowRemoved(inRowIndex);
	},

	invalidated: null,

	updating: false,

	beginUpdate: function(){
		// summary:
		//		Use to make multiple changes to rows while queueing row updating.
		// NOTE: not currently supporting nested begin/endUpdate calls
		this.invalidated = [];
		this.updating = true;
	},

	endUpdate: function(){
		// summary:
		//		Use after calling beginUpdate to render any changes made to rows.
		this.updating = false;
		var i = this.invalidated;
		if(i.all){
			this.update();
		}else if(i.rowCount != undefined){
			this.updateRowCount(i.rowCount);
		}else{
			for(r in i){
				this.updateRow(Number(r));
			}
		}
		this.invalidated = null;
	},

	// update
	defaultUpdate: function(){
		// note: initial update calls render and subsequently this function.
		if(!this.domNode){return;}
		if(this.updating){
			this.invalidated.all = true;
			return;
		}
		//this.edit.saveState(inRowIndex);
		this.prerender();
		this.scroller.invalidateNodes();
		this.setScrollTop(this.scrollTop);
		this.postrender();
		//this.edit.restoreState(inRowIndex);
	},

	update: function(){
		// summary:
		//		Update the grid, retaining edit and scrolling states.
		this.render();
	},

	updateRow: function(inRowIndex){
		// summary:
		//		Render a single row.
		// inRowIndex: Integer
		//		Index of the row to render
		inRowIndex = Number(inRowIndex);
		if(this.updating){
			this.invalidated[inRowIndex]=true;
		}else{
			this.views.updateRow(inRowIndex, this.rows.getHeight(inRowIndex));
			this.scroller.rowHeightChanged(inRowIndex);
		}
	},

	updateRowCount: function(inRowCount){
		//summary: 
		//	Change the number of rows.
		// inRowCount: int
		//	Number of rows in the grid.
		if(this.updating){
			this.invalidated.rowCount = inRowCount;
		}else{
			this.rowCount = inRowCount;
			if(this.layout.cells.length){
				this.scroller.updateRowCount(inRowCount);
				this.setScrollTop(this.scrollTop);
			}
			this._resize();
		}
	},

	updateRowStyles: function(inRowIndex){
		// summary:
		//		Update the styles for a row after it's state has changed.
		this.views.updateRowStyles(inRowIndex);
	},

	rowHeightChanged: function(inRowIndex){
		// summary: 
		//		Update grid when the height of a row has changed. Row height is handled automatically as rows
		//		are rendered. Use this function only to update a row's height outside the normal rendering process.
		// inRowIndex: Integer
		// 		index of the row that has changed height
		
		this.views.renormalizeRow(inRowIndex);
		this.scroller.rowHeightChanged(inRowIndex);
	},
	
	// fastScroll: Boolean
	//		flag modifies vertical scrolling behavior. Defaults to true but set to false for slower 
	//		scroll performance but more immediate scrolling feedback
	fastScroll: true,
	
	delayScroll: false,

	// scrollRedrawThreshold: int
	//	pixel distance a user must scroll vertically to trigger grid scrolling.
	scrollRedrawThreshold: (dojo.isIE ? 100 : 50),

	// scroll methods
	scrollTo: function(inTop){
		// summary:
		//		Vertically scroll the grid to a given pixel position
		// inTop: Integer
		//		vertical position of the grid in pixels
		if(!this.fastScroll){
			this.setScrollTop(inTop);
			return;
		}
		var delta = Math.abs(this.lastScrollTop - inTop);
		this.lastScrollTop = inTop;
		if(delta > this.scrollRedrawThreshold || this.delayScroll){
			this.delayScroll = true;
			this.scrollTop = inTop;
			this.views.setScrollTop(inTop);
			dojox.grid.jobs.job('dojoxGrid-scroll', 200, dojo.hitch(this, "finishScrollJob"));
		}else{
			this.setScrollTop(inTop);
		}
	},
	
	finishScrollJob: function(){
		this.delayScroll = false;
		this.setScrollTop(this.scrollTop);
	},
	
	setScrollTop: function(inTop){
		this.scrollTop = this.views.setScrollTop(inTop);
		this.scroller.scroll(this.scrollTop);
	},
	
	scrollToRow: function(inRowIndex){
		// summary:
		//		Scroll the grid to a specific row.
		// inRowIndex: Integer
		// 		grid row index
		this.setScrollTop(this.scroller.findScrollTop(inRowIndex) + 1);
	},
	
	// styling (private, used internally to style individual parts of a row)
	styleRowNode: function(inRowIndex, inRowNode){
		if(inRowNode){
			this.rows.styleRowNode(inRowIndex, inRowNode);
		}
	},

	// cells
	getCell: function(inIndex){
		// summary:
		//		Retrieves the cell object for a given grid column.
		// inIndex: Integer
		// 		Grid column index of cell to retrieve
		// returns:
		//		a grid cell
		return this.layout.cells[inIndex];
	},

	setCellWidth: function(inIndex, inUnitWidth) {
		this.getCell(inIndex).unitWidth = inUnitWidth;
	},

	getCellName: function(inCell){
		// summary: Returns the cell name of a passed cell
		return "Cell " + inCell.index; // String
	},

	// sorting
	canSort: function(inSortInfo){
		// summary:
		//		Determines if the grid can be sorted
		// inSortInfo: Integer
		//		Sort information, 1-based index of column on which to sort, positive for an ascending sort
		// 		and negative for a descending sort
		// returns: Boolean
		//		True if grid can be sorted on the given column in the given direction
	},
	
	sort: function(){
	},
	
	getSortAsc: function(inSortInfo){
		// summary:
		//		Returns true if grid is sorted in an ascending direction.
		inSortInfo = inSortInfo == undefined ? this.sortInfo : inSortInfo;
		return Boolean(inSortInfo > 0); // Boolean
	},
	
	getSortIndex: function(inSortInfo){
		// summary:
		//		Returns the index of the column on which the grid is sorted
		inSortInfo = inSortInfo == undefined ? this.sortInfo : inSortInfo;
		return Math.abs(inSortInfo) - 1; // Integer
	},
	
	setSortIndex: function(inIndex, inAsc){
		// summary:
		// 		Sort the grid on a column in a specified direction
		// inIndex: Integer
		// 		Column index on which to sort.
		// inAsc: Boolean
		// 		If true, sort the grid in ascending order, otherwise in descending order
		var si = inIndex +1;
		if(inAsc != undefined){
			si *= (inAsc ? 1 : -1);
		} else if(this.getSortIndex() == inIndex){
			si = -this.sortInfo;
		}
		this.setSortInfo(si);
	},
	
	setSortInfo: function(inSortInfo){
		if(this.canSort(inSortInfo)){
			this.sortInfo = inSortInfo;
			this.sort();
			this.update();
		}
	},
	
	// DOM event handler
	doKeyEvent: function(e){
		e.dispatch = 'do' + e.type;
		this.onKeyEvent(e);
	},

	// event dispatch
	//: protected
	_dispatch: function(m, e){
		if(m in this){
			return this[m](e);
		}
	},

	dispatchKeyEvent: function(e){
		this._dispatch(e.dispatch, e);
	},
	
	dispatchContentEvent: function(e){
		this.edit.dispatchEvent(e) || e.sourceView.dispatchContentEvent(e) || this._dispatch(e.dispatch, e);
	},
	
	dispatchHeaderEvent: function(e){
		e.sourceView.dispatchHeaderEvent(e) || this._dispatch('doheader' + e.type, e);
	},
	
	dokeydown: function(e){
		this.onKeyDown(e);
	},
	
	doclick: function(e){
		if(e.cellNode){
			this.onCellClick(e);
		}else{
			this.onRowClick(e);
		}
	},
	
	dodblclick: function(e){
		if(e.cellNode){
			this.onCellDblClick(e);
		}else{
			this.onRowDblClick(e);
		}
	},
	
	docontextmenu: function(e){
		if(e.cellNode){
			this.onCellContextMenu(e);
		}else{
			this.onRowContextMenu(e);
		}
	},
	
	doheaderclick: function(e){
		if(e.cellNode){
			this.onHeaderCellClick(e);
		}else{
			this.onHeaderClick(e);
		}
	},
	
	doheaderdblclick: function(e){
		if(e.cellNode){
			this.onHeaderCellDblClick(e);
		}else{
			this.onHeaderDblClick(e);
		}
	},
	
	doheadercontextmenu: function(e){
		if(e.cellNode){
			this.onHeaderCellContextMenu(e);
		}else{
			this.onHeaderContextMenu(e);
		}
	},
	
	// override to modify editing process
	doStartEdit: function(inCell, inRowIndex){
		this.onStartEdit(inCell, inRowIndex);
	},
	
	doApplyCellEdit: function(inValue, inRowIndex, inFieldIndex){
		this.onApplyCellEdit(inValue, inRowIndex, inFieldIndex);
	},
	
	doCancelEdit: function(inRowIndex){
		this.onCancelEdit(inRowIndex);
	},
	
	doApplyEdit: function(inRowIndex){
		this.onApplyEdit(inRowIndex);
	},
	
	// row editing
	addRow: function(){
		// summary:
		//		Add a row to the grid.
		this.updateRowCount(this.rowCount+1);
	},
	
	removeSelectedRows: function(){
		// summary:
		//		Remove the selected rows from the grid.
		this.updateRowCount(Math.max(0, this.rowCount - this.selection.getSelected().length));
		this.selection.clear();
	}

});

dojo.mixin(dojox.VirtualGrid.prototype, dojox.grid.publicEvents);

}

if(!dojo._hasResource["dojox.grid._data.fields"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojox.grid._data.fields"] = true;
dojo.provide("dojox.grid._data.fields");

dojo.declare("dojox.grid.data.Mixer", null, {
	// summary:
	//	basic collection class that provides a default value for items
	
	constructor: function(){
		this.defaultValue = {};
		this.values = [];
	},
	count: function(){
		return this.values.length;
	},
	clear: function(){
		this.values = [];
	},
	build: function(inIndex){
		var result = dojo.mixin({owner: this}, this.defaultValue);
		result.key = inIndex;
		this.values[inIndex] = result;
		return result;
	},
	getDefault: function(){
		return this.defaultValue;
	},
	setDefault: function(inField /*[, inField2, ... inFieldN] */){
		for(var i=0, a; (a = arguments[i]); i++){
			dojo.mixin(this.defaultValue, a);
		}
	},
	get: function(inIndex){
		return this.values[inIndex] || this.build(inIndex);
	},
	_set: function(inIndex, inField /*[, inField2, ... inFieldN] */){
		// each field argument can be a single field object of an array of field objects
		var v = this.get(inIndex);
		for(var i=1; i<arguments.length; i++){
			dojo.mixin(v, arguments[i]);
		}
		this.values[inIndex] = v;
	},
	set: function(/* inIndex, inField [, inField2, ... inFieldN] | inArray */){
		if(arguments.length < 1){
			return;
		}
		var a = arguments[0];
		if(!dojo.isArray(a)){
			this._set.apply(this, arguments);
		}else{
			if(a.length && a[0]["default"]){
				this.setDefault(a.shift());
			}
			for(var i=0, l=a.length; i<l; i++){
				this._set(i, a[i]);
			}
		}
	},
	insert: function(inIndex, inProps){
		if (inIndex >= this.values.length){
			this.values[inIndex] = inProps;
		}else{
			this.values.splice(inIndex, 0, inProps);
		}
	},
	remove: function(inIndex){
		this.values.splice(inIndex, 1);
	},
	swap: function(inIndexA, inIndexB){
		dojox.grid.arraySwap(this.values, inIndexA, inIndexB);
	},
	move: function(inFromIndex, inToIndex){
		dojox.grid.arrayMove(this.values, inFromIndex, inToIndex);
	}
});

dojox.grid.data.compare = function(a, b){
	return (a > b ? 1 : (a == b ? 0 : -1));
}

dojo.declare('dojox.grid.data.Field', null, {
	constructor: function(inName){
		this.name = inName;
		this.compare = dojox.grid.data.compare;
	},
	na: dojox.grid.na
});

dojo.declare('dojox.grid.data.Fields', dojox.grid.data.Mixer, {
	constructor: function(inFieldClass){
		var fieldClass = inFieldClass ? inFieldClass : dojox.grid.data.Field;
		this.defaultValue = new fieldClass();
	},
	indexOf: function(inKey){
		for(var i=0; i<this.values.length; i++){
			var v = this.values[i];
			if(v && v.key == inKey){return i;}
		}
		return -1;
	}
});

}

if(!dojo._hasResource['dojox.grid._data.model']){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource['dojox.grid._data.model'] = true;
dojo.provide('dojox.grid._data.model');


dojo.declare("dojox.grid.data.Model", null, {
	// summary:
	//	Base abstract grid data model.
	//	Makes no assumptions about the structure of grid data.
	constructor: function(inFields, inData){
		this.observers = [];
		this.fields = new dojox.grid.data.Fields();
		if(inFields){
			this.fields.set(inFields);
		}
		this.setData(inData);
	},
	count: 0,
	updating: 0,
	// observers 
	observer: function(inObserver, inPrefix){
		this.observers.push({o: inObserver, p: inPrefix||'model' });
	},
	notObserver: function(inObserver){
		for(var i=0, m, o; (o=this.observers[i]); i++){
			if(o.o==inObserver){
				this.observers.splice(i, 1);
				return;
			}
		}
	},
	notify: function(inMsg, inArgs){
		if(!this.isUpdating()){
			var a = inArgs || [];
			for(var i=0, m, o; (o=this.observers[i]); i++){
				m = o.p + inMsg; o = o.o;
				(m in o)&&(o[m].apply(o, a));
			}
		}
	},
	// updates
	clear: function(){
		this.fields.clear();
		this.clearData();
	},
	beginUpdate: function(){
		this.updating++;
	},
	endUpdate: function(){
		if(this.updating){
			this.updating--;
		}
		/*if(this.updating){
			if(!(--this.updating)){
				this.change();
			}
		}
		}*/
	},
	isUpdating: function(){
		return Boolean(this.updating);
	},
	// data
	clearData: function(){
		this.setData(null);
	},
	// observer events
	change: function(){
		this.notify("Change", arguments);
	},
	insertion: function(/* index */){
		this.notify("Insertion", arguments);
		this.notify("Change", arguments);
	},
	removal: function(/* keys */){
		this.notify("Removal", arguments);
		this.notify("Change", arguments);
	},
	// insert
	insert: function(inData /*, index */){
		if(!this._insert.apply(this, arguments)){
			return false;
		}
		this.insertion.apply(this, dojo._toArray(arguments, 1));
		return true;
	},
	// remove
	remove: function(inData /*, index */){
		if(!this._remove.apply(this, arguments)){
			return false;
		}
		this.removal.apply(this, arguments);
		return true;
	},
	// sort
	canSort: function(/* (+|-)column_index+1, ... */){
		return this.sort != null;
	},
	generateComparator: function(inCompare, inField, inTrueForAscend, inSubCompare){
		return function(a, b){
			var ineq = inCompare(a[inField], b[inField]);
			return ineq ? (inTrueForAscend ? ineq : -ineq) : inSubCompare && inSubCompare(a, b);
		}
	},
	makeComparator: function(inIndices){
		var idx, col, field, result = null;
		for(var i=inIndices.length-1; i>=0; i--){
			idx = inIndices[i];
			col = Math.abs(idx) - 1;
			if(col >= 0){
				field = this.fields.get(col);
				result = this.generateComparator(field.compare, field.key, idx > 0, result);
			}
		}
		return result;
	},
	sort: null,
	dummy: 0
});

dojo.declare("dojox.grid.data.Rows", dojox.grid.data.Model, {
	// observer events
	allChange: function(){
		this.notify("AllChange", arguments);
		this.notify("Change", arguments);
	},
	rowChange: function(){
		this.notify("RowChange", arguments);
	},
	datumChange: function(){
		this.notify("DatumChange", arguments);
	},
	// copyRow: function(inRowIndex); // abstract
	// update
	beginModifyRow: function(inRowIndex){
		if(!this.cache[inRowIndex]){
			this.cache[inRowIndex] = this.copyRow(inRowIndex);
		}
	},
	endModifyRow: function(inRowIndex){
		var cache = this.cache[inRowIndex];
		if(cache){
			var data = this.getRow(inRowIndex);
			if(!dojox.grid.arrayCompare(cache, data)){
				this.update(cache, data, inRowIndex);
			}
			delete this.cache[inRowIndex];
		}
	},
	cancelModifyRow: function(inRowIndex){
		var cache = this.cache[inRowIndex];
		if(cache){
			this.setRow(cache, inRowIndex);
			delete this.cache[inRowIndex];
		}
	}
});

dojo.declare("dojox.grid.data.Table", dojox.grid.data.Rows, {
	// summary:
	//	Basic grid data model for static data in the form of an array of rows
	//	that are arrays of cell data
	constructor: function(){
		this.cache = [];
	},
	colCount: 0, // tables introduce cols
	data: null,
	cache: null,
	// morphology
	measure: function(){
		this.count = this.getRowCount();
		this.colCount = this.getColCount();
		this.allChange();
		//this.notify("Measure");
	},
	getRowCount: function(){
		return (this.data ? this.data.length : 0);
	},
	getColCount: function(){
		return (this.data && this.data.length ? this.data[0].length : this.fields.count());
	},
	badIndex: function(inCaller, inDescriptor){
		console.debug('dojox.grid.data.Table: badIndex');
	},
	isGoodIndex: function(inRowIndex, inColIndex){
		return (inRowIndex >= 0 && inRowIndex < this.count && (arguments.length < 2 || (inColIndex >= 0 && inColIndex < this.colCount)));
	},
	// access
	getRow: function(inRowIndex){
		return this.data[inRowIndex];
	},
	copyRow: function(inRowIndex){
		return this.getRow(inRowIndex).slice(0);
	},
	getDatum: function(inRowIndex, inColIndex){
		return this.data[inRowIndex][inColIndex];
	},
	get: function(){
		throw('Plain "get" no longer supported. Use "getRow" or "getDatum".');
	},
	setData: function(inData){
		this.data = (inData || []);
		this.allChange();
	},
	setRow: function(inData, inRowIndex){
		this.data[inRowIndex] = inData;
		this.rowChange(inData, inRowIndex);
		this.change();
	},
	setDatum: function(inDatum, inRowIndex, inColIndex){
		this.data[inRowIndex][inColIndex] = inDatum;
		this.datumChange(inDatum, inRowIndex, inColIndex);
	},
	set: function(){
		throw('Plain "set" no longer supported. Use "setData", "setRow", or "setDatum".');
	},
	setRows: function(inData, inRowIndex){
		for(var i=0, l=inData.length, r=inRowIndex; i<l; i++, r++){
			this.setRow(inData[i], r);
		}
	},
	// update
	update: function(inOldData, inNewData, inRowIndex){
		//delete this.cache[inRowIndex];	
		//this.setRow(inNewData, inRowIndex);
		return true;
	},
	// insert
	_insert: function(inData, inRowIndex){
		dojox.grid.arrayInsert(this.data, inRowIndex, inData);
		this.count++;
		return true;
	},
	// remove
	_remove: function(inKeys){
		for(var i=inKeys.length-1; i>=0; i--){
			dojox.grid.arrayRemove(this.data, inKeys[i]);
		}
		this.count -= inKeys.length;
		return true;
	},
	// sort
	sort: function(/* (+|-)column_index+1, ... */){
		this.data.sort(this.makeComparator(arguments));
	},
	swap: function(inIndexA, inIndexB){
		dojox.grid.arraySwap(this.data, inIndexA, inIndexB);
		this.rowChange(this.getRow(inIndexA), inIndexA);
		this.rowChange(this.getRow(inIndexB), inIndexB);
		this.change();
	},
	dummy: 0
});

dojo.declare("dojox.grid.data.Objects", dojox.grid.data.Table, {
	constructor: function(inFields, inData, inKey){
		if(!inFields){
			this.autoAssignFields();
		}
	},
	allChange: function(){
		this.notify("FieldsChange");
		this.inherited(arguments);
	},
	autoAssignFields: function(){
		var d = this.data[0], i = 0, field;
		for(var f in d){
			field = this.fields.get(i++);
			if (!dojo.isString(field.key)){
				field.key = f;
			}
		}
	},
	setData: function(inData){
		this.data = (inData || []);
		this.autoAssignFields();
		this.allChange();
	},
	getDatum: function(inRowIndex, inColIndex){
		return this.data[inRowIndex][this.fields.get(inColIndex).key];
	}
});

dojo.declare("dojox.grid.data.Dynamic", dojox.grid.data.Table, {
	// summary:
	//	Grid data model for dynamic data such as data retrieved from a server.
	//	Retrieves data automatically when requested and provides notification when data is received
	constructor: function(){
		this.page = [];
		this.pages = [];
	},
	page: null,
	pages: null,
	rowsPerPage: 100,
	requests: 0,
	bop: -1,
	eop: -1,
	// data
	clearData: function(){
		this.pages = [];
		this.bop = this.eop = -1;
		this.setData([]);
	},
	getRowCount: function(){
		return this.count;
	},
	getColCount: function(){
		return this.fields.count();
	},
	setRowCount: function(inCount){
		this.count = inCount;
		this.change();
	},
	// paging
	requestsPending: function(inBoolean){
	},
	rowToPage: function(inRowIndex){
		return (this.rowsPerPage ? Math.floor(inRowIndex / this.rowsPerPage) : inRowIndex);
	},
	pageToRow: function(inPageIndex){
		return (this.rowsPerPage ? this.rowsPerPage * inPageIndex : inPageIndex);
	},
	requestRows: function(inRowIndex, inCount){
		// summary:
		//		stub. Fill in to perform actual data row fetching logic. The
		//		returning logic must provide the data back to the system via
		//		setRow
	},
	rowsProvided: function(inRowIndex, inCount){
		this.requests--;
		if(this.requests == 0){
			this.requestsPending(false);
		}
	},
	requestPage: function(inPageIndex){
		var row = this.pageToRow(inPageIndex);
		var count = Math.min(this.rowsPerPage, this.count - row);
		if(count > 0){
			this.requests++;
			this.requestsPending(true);
			setTimeout(dojo.hitch(this, "requestRows", row, count), 1);
			//this.requestRows(row, count);
		}
	},
	needPage: function(inPageIndex){
		if(!this.pages[inPageIndex]){
			this.pages[inPageIndex] = true;
			this.requestPage(inPageIndex);
		}
	},
	preparePage: function(inRowIndex, inColIndex){
		if(inRowIndex < this.bop || inRowIndex >= this.eop){
			var pageIndex = this.rowToPage(inRowIndex);
			this.needPage(pageIndex);
			this.bop = pageIndex * this.rowsPerPage;
			this.eop = this.bop + (this.rowsPerPage || this.count);
		}
	},
	isRowLoaded: function(inRowIndex){
		return Boolean(this.data[inRowIndex]);
	},
	// removal
	removePages: function(inRowIndexes){
		for(var i=0, r; ((r=inRowIndexes[i]) != undefined); i++){
			this.pages[this.rowToPage(r)] = false;
		}
		this.bop = this.eop =-1;
	},
	remove: function(inRowIndexes){
		this.removePages(inRowIndexes);
		dojox.grid.data.Table.prototype.remove.apply(this, arguments);
	},
	// access
	getRow: function(inRowIndex){
		var row = this.data[inRowIndex];
		if(!row){
			this.preparePage(inRowIndex);
		}
		return row;
	},
	getDatum: function(inRowIndex, inColIndex){
		var row = this.getRow(inRowIndex);
		return (row ? row[inColIndex] : this.fields.get(inColIndex).na);
	},
	setDatum: function(inDatum, inRowIndex, inColIndex){
		var row = this.getRow(inRowIndex);
		if(row){
			row[inColIndex] = inDatum;
			this.datumChange(inDatum, inRowIndex, inColIndex);
		}else{
			console.debug('[' + this.declaredClass + '] dojox.grid.data.dynamic.set: cannot set data on an non-loaded row');
		}
	},
	// sort
	canSort: function(){
		return false;
	}
});

// FIXME: deprecated: (included for backward compatibility only)
dojox.grid.data.table = dojox.grid.data.Table;
dojox.grid.data.dynamic = dojox.grid.data.Dynamic;

// we treat dojo.data stores as dynamic stores because no matter how they got
// here, they should always fill that contract
dojo.declare("dojox.grid.data.DojoData", dojox.grid.data.Dynamic, {
	//	summary:
	//		A grid data model for dynamic data retreived from a store which
	//		implements the dojo.data API set. Retrieves data automatically when
	//		requested and provides notification when data is received
	//	description:
	//		This store subclasses the Dynamic grid data object in order to
	//		provide paginated data access support, notification and view
	//		updates for stores which support those features, and simple
	//		field/column mapping for all dojo.data stores.
	constructor: function(inFields, inData, args){
		this.count = 1;
		this._rowIdentities = {};
		this._currentlyProcessing = [];
		if(args){
			dojo.mixin(this, args);
		}
		if(this.store){
			var f = this.store.getFeatures();
			this._canNotify = f['dojo.data.api.Notification'];
			this._canWrite = f['dojo.data.api.Write'];
			this._canIdentify = f['dojo.data.api.Identity'];
			if(this._canNotify){
				dojo.connect(this.store, "onSet", this, "_storeDatumChange");
				dojo.connect(this.store, "onDelete", this, "_storeDatumDelete");
				dojo.connect(this.store, "onNew", this, "_storeDatumNew");
			}
			if(this._canWrite) {
				dojo.connect(this.store, "revert", this, "refresh");
			}
		}
	},
	markupFactory: function(args, node){
		return new dojox.grid.data.DojoData(null, null, args);
	},
	query: { name: "*" }, // default, stupid query
	store: null,
	_currentlyProcessing: null,
	_canNotify: false,
	_canWrite: false,
	_canIdentify: false,
	_rowIdentities: {},
	clientSort: false,
	sortFields: null,
	queryOptions: null,

	// data
	setData: function(inData){
		this.store = inData;
		this.data = [];
		this.allChange();
	},
	setRowCount: function(inCount){
		//console.debug("inCount:", inCount);
		this.count = inCount;
		this.allChange();
	},
	beginReturn: function(inCount){
		if(this.count != inCount){
			// this.setRowCount(0);
			// this.clear();
			// console.debug(this.count, inCount);
			this.setRowCount(inCount);
		}
	},
	_setupFields: function(dataItem){
		// abort if we already have setup fields
		if(this.fields._nameMaps){
			return;
		}
		// set up field/index mappings
		var m = {};
		//console.debug("setting up fields", m);
		var fields = dojo.map(this.store.getAttributes(dataItem),
			function(item, idx){ 
				m[item] = idx;
				m[idx+".idx"] = item;
				// name == display name, key = property name
				return { name: item, key: item };
			},
			this
		);
		this.fields._nameMaps = m;
		// console.debug("new fields:", fields);
		this.fields.set(fields);
		this.notify("FieldsChange");
	},
	_getRowFromItem: function(item){
		// gets us the row object (and row index) of an item
	},
	_createRow: function(item){
		var row = {}; 
		row.__dojo_data_item = item;
		dojo.forEach(this.fields.values, function(a){
			value = this.store.getValue(item, a.name);
			row[a.name] = (value === undefined || value === null)?"":value;
		}, this);
		return row;
	},
	processRows: function(items, request){
		// console.debug(arguments);
		if(!items || items.length == 0){ return; }
		this._setupFields(items[0]);
		dojo.forEach(items, function(item, idx){
			var row = this._createRow(item);
			this._setRowId(item, request.start, idx);
			this.setRow(row, request.start+idx);
		}, this);
		// FIXME: 
		//	Q: scott, steve, how the hell do we actually get this to update
		//		the visible UI for these rows?
		//	A: the goal is that Grid automatically updates to reflect changes
		//		in model. In this case, setRow -> rowChanged -> (observed by) Grid -> modelRowChange -> updateRow
	},
	// request data 
	requestRows: function(inRowIndex, inCount){
		var row  = inRowIndex || 0;
		var params = { 
			start: row,
			count: this.rowsPerPage,
			query: this.query,
			sort: this.sortFields,
			queryOptions: this.queryOptions,
			onBegin: dojo.hitch(this, "beginReturn"),
			onComplete: dojo.hitch(this, "processRows"), // add to deferred?
			onError: dojo.hitch(this, "processError")
		};
		this.store.fetch(params);
	},
	getDatum: function(inRowIndex, inColIndex){
		//console.debug("getDatum", inRowIndex, inColIndex);
		var row = this.getRow(inRowIndex);
		var field = this.fields.values[inColIndex];
		return row && field ? row[field.name] : field ? field.na : '?';
		//var idx = row && this.fields._nameMaps[inColIndex+".idx"];
		//return (row ? row[idx] : this.fields.get(inColIndex).na);
	},
	setDatum: function(inDatum, inRowIndex, inColIndex){
		var n = this.fields._nameMaps[inColIndex+".idx"];
		// console.debug("setDatum:", "n:"+n, inDatum, inRowIndex, inColIndex);
		if(n){
			this.data[inRowIndex][n] = inDatum;
			this.datumChange(inDatum, inRowIndex, inColIndex);
		}
	},
	// modification, update and store eventing
	copyRow: function(inRowIndex){
		var row = {};
		var backstop = {};
		var src = this.getRow(inRowIndex);
		for(var x in src){
			if(src[x] != backstop[x]){
				row[x] = src[x];
			}
		}
		return row;
	},
	_attrCompare: function(cache, data){
		dojo.forEach(this.fields.values, function(a){
			if(cache[a.name] != data[a.name]){ return false; }
		}, this);
		return true;
	},
	endModifyRow: function(inRowIndex){
		var cache = this.cache[inRowIndex];
		if(cache){
			var data = this.getRow(inRowIndex);
			if(!this._attrCompare(cache, data)){
				this.update(cache, data, inRowIndex);
			}
			delete this.cache[inRowIndex];
		}
	},
	cancelModifyRow: function(inRowIndex){
		// console.debug("cancelModifyRow", arguments);
		var cache = this.cache[inRowIndex];
		if(cache){
			this.setRow(cache, inRowIndex);
			delete this.cache[inRowIndex];
		}
	},
	_setRowId: function(item, offset, idx){
		// FIXME: where else do we need to keep this in sync?
		//Handle stores that implement identity and try to handle those that do not.
		if (this._canIdentify) {
			this._rowIdentities[this.store.getIdentity(item)] = {rowId: offset+idx, item: item};
		}else{
			var identity = dojo.toJson(this.query) + ":start:" + offset + ":idx:" + idx + ":sort:" + dojo.toJson(this.sortFields);
			this._rowIdentities[identity] = {rowId: offset+idx, item: item};
		}
	},
	_getRowId: function(item, isNotItem){
		//	summary:
		//		Function determine the row index for a particular item
		//	item:
		//		The store item to examine to determine row index.
		//	isNotItem:
		//		Boolean flag to indicate if the item passed is a store item or not.
		var rowId = null;
		//Handle identity and nonidentity capable stores.
		if(this._canIdentify && !isNotItem){
			rowId = this._rowIdentities[this.store.getIdentity(item)].rowId;
		}else{
			//Not efficient, but without identity support, 
			//not a better way to do it.  Basically, do our best to locate it
			//This may or may not work, but best we can do here.
			var id;
			for(id in this._rowIdentities){
				if(this._rowIdentities[id].item === item){
					rowId = this._rowIdentities[id].rowId;
					break;
				}
			}
		}
		return rowId;
	},
	_storeDatumChange: function(item, attr, oldVal, newVal){
		// the store has changed some data under us, need to update the display
		var rowId = this._getRowId(item);
		var row = this.getRow(rowId);
		if(row){
			row[attr] = newVal;
			var colId = this.fields._nameMaps[attr];
			this.notify("DatumChange", [ newVal, rowId, colId ]);
		}
	},
	_storeDatumDelete: function(item){
		if(dojo.indexOf(this._currentlyProcessing, item) != -1)
			return;
		// the store has deleted some item under us, need to remove that item from
		// the view if possible.  It may be the deleted item isn't even in the grid.
		var rowId = this._getRowId(item, true);
		if(rowId != null){
			this._removeItems([rowId]);
		}
	},
	_storeDatumNew: function(item){
		if(this._disableNew){
			return;
		}
		// the store has added some item under us, need to add it to the view.
		this._insertItem(item, this.data.length);
	},
	insert: function(item, index){
		// Push the given item back to the store
		this._disableNew = true;
		var i = this.store.newItem(item);
		this._disableNew = false;
		this._insertItem(i, index);
	},
	_insertItem: function(storeItem, index){
		// Set up our fields if we haven't already 
		if(!this.fields._nameMaps){
			this._setupFields(storeItem);
		}
		var row = this._createRow(storeItem);
		for(var i in this._rowIdentities){ //increment all the remaining row ids up one
			var rowIdentity = this._rowIdentities[i];
			if(rowIdentity.rowId >= index){
				rowIdentity.rowId++;
			}
		}
		this._setRowId(storeItem, 0, index);
		dojox.grid.data.Dynamic.prototype.insert.apply(this, [row, index]);
	},
	datumChange: function(value, rowIdx, colIdx){
		if(this._canWrite){
			// we're chaning some data, which means we need to write back
			var row = this.getRow(rowIdx);
			var field = this.fields._nameMaps[colIdx+".idx"];
			this.store.setValue(row.__dojo_data_item, field, value);
			// we don't need to call DatumChange, an eventing store will tell
			// us about the row change events
		}else{
			// we can't write back, so just go ahead and change our local copy
			// of the data
			this.notify("DatumChange", arguments);
		}
	},
	insertion: function(/* index */){
		console.debug("Insertion", arguments);
		this.notify("Insertion", arguments);
		this.notify("Change", arguments);
	},
	removal: function(/* keys */){
		console.debug("Removal", arguments);
		this.notify("Removal", arguments);
		this.notify("Change", arguments);
	},
	remove: function(inRowIndexes){
		//	summary:
		//		Function to remove a set of items from the store based on the row index.
		//	inRowIndexes:
		//		An array of row indexes from the grid to remove from the store.
		/* Call delete on the store */ 
		for(var i=inRowIndexes.length-1; i>=0; i--){
			// Need to find the item, then remove each from the data store
			var item = this.data[inRowIndexes[i]].__dojo_data_item;
			this._currentlyProcessing.push(item);
			this.store.deleteItem(item);
		}
		/* Remove from internal data structure and the view */
		this._removeItems(inRowIndexes);
		this._currentlyProcessing = [];
	},
	_removeItems: function(inRowIndexes /*array*/){
		//	summary:
		//		Function to remove a set of items from the store based on the row index.
		//	inRowIndexes:
		//		An array of row indexes from the grid to remove from the store.
		dojox.grid.data.Dynamic.prototype.remove.apply(this, arguments);
		// Rebuild _rowIdentities
		this._rowIdentities = {};
		for (var i = 0; i < this.data.length; i++){
			this._setRowId(this.data[i].__dojo_data_item, 0, i);
		}
	},
	canSort: function(){
		// Q: Return true and re-issue the queries?
		// A: Return true only. Re-issue the query in 'sort'.
		// Note, above are original comments :)
		return true;
	},
	sort: function(colIndex){
		var col = Math.abs(colIndex) - 1;
		this.sortFields = [{'attribute': this.fields.values[col].name, 'descending': (colIndex>0)}];
		
		// Since we're relying on the data store to sort, we have to refresh our data.
		this.refresh();
	},
	refresh: function(){
		//	summary:
		//		Function to cause the model to re-query the store and rebuild the current viewport.
		this.clearData(true);
		this.requestRows();
	},
	clearData: function(/* boolean */ keepStore){
		this._rowIdentities = {};
		this.pages = [];
		this.bop = this.eop = -1;
		this.count = 0;
		this.setData((keepStore?this.store:[]));
	},
	processError: function(error, request){
		//	summary:
		//		Hook function to trap error messages from the store and emit them.  
		//		Intended for connecting to and handling the error object or at least reporting it.
		//
		//	error:
		//		The error object returned by the store when a problem occurred.
		//	request:
		//		The request object that caused the error.
		console.log(error);
	}
});

}

if(!dojo._hasResource["dojox.grid.Grid"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojox.grid.Grid"] = true;
dojo.provide("dojox.grid.Grid");





// FIXME: 
//		we are at the wrong location! 

dojo.declare('dojox.Grid', dojox.VirtualGrid, {
	//	summary:
	//		A grid widget with virtual scrolling, cell editing, complex rows,
	//		sorting, fixed columns, sizeable columns, etc.
	//	description:
	//		Grid is a subclass of VirtualGrid, providing binding to a data
	//		store.
	//	example:
	//		define the grid structure:
	//	|	var structure = [ // array of view objects
	//	|		{ cells: [// array of rows, a row is an array of cells
	//	|			[	{ name: "Alpha", width: 6 }, 
	//	|				{ name: "Beta" }, 
	//	|				{ name: "Gamma", get: formatFunction }
	//	|			]
	//	|		]}
	//	|	];
	//	  	
	//		define a grid data model
	//	|	var model = new dojox.grid.data.table(null, data);
	//	|
	//	|	<div id="grid" model="model" structure="structure" 
	//	|		dojoType="dojox.VirtualGrid"></div>
	//	

	//	model:
	//		string or object grid data model
	model: 'dojox.grid.data.Table',
	
	// life cycle
	postCreate: function(){
		if(this.model){
			var m = this.model;
			if(dojo.isString(m)){
				m = dojo.getObject(m);
			}
			this.model = (dojo.isFunction(m)) ? new m() : m;
			this._setModel(this.model);
		}
		this.inherited(arguments);
	},
	
	destroy: function(){
		this.setModel(null);
		this.inherited(arguments);
	},
	
	// structure
	_structureChanged: function() {
		this.indexCellFields();
		this.inherited(arguments);
	},
	
	// model
	_setModel: function(inModel){
		// if(!inModel){ return; }
		this.model = inModel;
		if(this.model){
			this.model.observer(this);
			this.model.measure();
			this.indexCellFields();
		}
	},
	
	setModel: function(inModel){
		// summary:
		//		Set the grid's data model
		// inModel: Object
		//		Model object, usually an instance of a dojox.grid.data.Model
		//		subclass
		if(this.model){
			this.model.notObserver(this);
		}
		this._setModel(inModel);
	},
	

	get: function(inRowIndex){
		// summary: data socket (called in cell's context)	
		return this.grid.model.getDatum(inRowIndex, this.fieldIndex);
	},

	// model modifications
	modelAllChange: function(){
		this.rowCount = (this.model ? this.model.getRowCount() : 0);
		this.updateRowCount(this.rowCount);
	},

	modelRowChange: function(inData, inRowIndex){
		this.updateRow(inRowIndex);
	},

	modelDatumChange: function(inDatum, inRowIndex, inFieldIndex){
		this.updateRow(inRowIndex);
	},

	modelFieldsChange: function() {
		this.indexCellFields();
		this.render();
	},

	// model insertion
	modelInsertion: function(inRowIndex){
		this.updateRowCount(this.model.getRowCount());
	},

	// model removal
	modelRemoval: function(inKeys){
		this.updateRowCount(this.model.getRowCount());
	},

	// cells
	getCellName: function(inCell){
		var v = this.model.fields.values, i = inCell.fieldIndex;
		return i>=0 && i<v.length && v[i].name || this.inherited(arguments);
	},

	indexCellFields: function(){
		var cells = this.layout.cells;
		for(var i=0, c; cells && (c=cells[i]); i++){
			if(dojo.isString(c.field)){
				c.fieldIndex = this.model.fields.indexOf(c.field);
			}
		}
	},

	// utility
	refresh: function(){
		// summary:
		//		Re-render the grid, getting new data from the model
		this.edit.cancel();
		this.model.measure();
	},

	// sorting
	canSort: function(inSortInfo){
		var f = this.getSortField(inSortInfo);
		// 0 is not a valid sort field
		return f && this.model.canSort(f);
	},

	getSortField: function(inSortInfo){
		// summary:
		//		Retrieves the model field on which to sort data.
		// inSortInfo: Integer
		//		1-based grid column index; positive if sort is ascending, otherwise negative
		var c = this.getCell(this.getSortIndex(inSortInfo));
		// we expect c.fieldIndex == -1 for non model fields
		// that yields a getSortField value of 0, which can be detected as invalid
		return (c.fieldIndex+1) * (this.sortInfo > 0 ? 1 : -1);
	},

	sort: function(){
		this.edit.apply();
		this.model.sort(this.getSortField());
	},

	// row editing
	addRow: function(inRowData, inIndex){
		this.edit.apply();
		var i = inIndex || -1;
		if(i<0){
			i = this.selection.getFirstSelected() || 0;
		}
		if(i<0){
			i = 0;
		}
		this.model.insert(inRowData, i);
		this.model.beginModifyRow(i);
		// begin editing row
		// FIXME: add to edit
		for(var j=0, c; ((c=this.getCell(j)) && !c.editor); j++){}
		if(c&&c.editor){
			this.edit.setEditCell(c, i);
			this.focus.setFocusCell(c, i);
		}else{
			this.focus.setFocusCell(this.getCell(0), i);
		}
	},

	removeSelectedRows: function(){
		this.edit.apply();
		var s = this.selection.getSelected();
		if(s.length){
			this.model.remove(s);
			this.selection.clear();
		}
	},

	//: protected
	// editing
	canEdit: function(inCell, inRowIndex){
		// summary: 
		//		Determines if a given cell may be edited
		// inCell: Object
		//		A grid cell
		// inRowIndex: Integer
		//		Grid row index
		// returns: Boolean
		//		True if given cell may be edited
		return (this.model.canModify ? this.model.canModify(inRowIndex) : true);
	},

	doStartEdit: function(inCell, inRowIndex){
		this.model.beginModifyRow(inRowIndex);
		this.onStartEdit(inCell, inRowIndex);
	},

	doApplyCellEdit: function(inValue, inRowIndex, inFieldIndex){
		this.model.setDatum(inValue, inRowIndex, inFieldIndex);
		this.onApplyCellEdit(inValue, inRowIndex, inFieldIndex);
	},

	doCancelEdit: function(inRowIndex){
		this.model.cancelModifyRow(inRowIndex);
		this.onCancelEdit.apply(this, arguments);
	},

	doApplyEdit: function(inRowIndex){
		this.model.endModifyRow(inRowIndex);
		this.onApplyEdit(inRowIndex);
	},

	styleRowState: function(inRow){
		// summary: Perform row styling
		if(this.model.getState){
			var states=this.model.getState(inRow.index), c='';
			for(var i=0, ss=["inflight", "error", "inserting"], s; s=ss[i]; i++){
				if(states[s]){
					c = ' dojoxGrid-row-' + s;
					break;
				}
			}
			inRow.customClasses += c;
		}
	},

	onStyleRow: function(inRow){
		this.styleRowState(inRow);
		this.inherited(arguments);
	}

});

dojox.Grid.markupFactory = function(props, node, ctor){
	// handle setting up a data model for a store if one
	// isn't provided. There are some caveats:
	//		* we only really handle dojo.data sources well. They're the future
	//		  so it's no big deal, but it's something to be aware of.
	//		* I'm pretty sure that colgroup introspection is missing some of
	//		  the available settable properties. 
	//		* No handling of cell formatting and content getting is done
	var d = dojo;
	var widthFromAttr = function(n){
		var w = d.attr(n, "width")||"auto";
		if((w != "auto")&&(w.substr(-2) != "em")){
			w = parseInt(w)+"px";
		}
		return w;
	}
	if(!props.model && d.hasAttr(node, "store")){
		// if a model isn't specified and we point to a store, assume
		// we're also folding the definition for a model up into the
		// inline ctor for the Grid. This will then take properties
		// like "query", "rowsPerPage", and "clientSort" from the grid
		// definition.
		var mNode = node.cloneNode(false);
		d.attr(mNode, {
			"jsId": null,
			"dojoType": d.attr(node, "dataModelClass") || "dojox.grid.data.DojoData"
		});
		props.model = d.parser.instantiate([mNode])[0];
	}
	// if(!props.model){ console.debug("no model!"); }
	// if a structure isn't referenced, do we have enough
	// data to try to build one automatically?
	if(	!props.structure && 
		node.nodeName.toLowerCase() == "table"){

		// try to discover a structure
		props.structure = d.query("> colgroup", node).map(function(cg){
			var sv = d.attr(cg, "span");
			var v = { 
				noscroll: (d.attr(cg, "noscroll") == "true") ? true : false,
				__span: (!!sv ? parseInt(sv) : 1),
				cells: []
			};
			if(d.hasAttr(cg, "width")){
				v.width = widthFromAttr(cg);
			}
			return v; // for vendetta
		});
		if(!props.structure.length){
			props.structure.push({
				__span: Infinity,
				cells: [] // catch-all view
			}); 
		}
		// check to see if we're gonna have more than one view
		
		// for each tr in our th, create a row of cells
		d.query("thead > tr", node).forEach(function(tr, tr_idx){
			var cellCount = 0;
			var viewIdx = 0;
			var lastViewIdx;
			var cView = null;
			d.query("> th", tr).map(function(th){
				// what view will this cell go into?

				// NOTE:
				//		to prevent extraneous iteration, we start counters over
				//		for each row, incrementing over the surface area of the
				//		structure that colgroup processing generates and
				//		creating cell objects for each <th> to place into those
				//		cell groups.  There's a lot of state-keepking logic
				//		here, but it is what it has to be.
				if(!cView){ // current view book keeping
					lastViewIdx = 0;
					cView = props.structure[0];
				}else if(cellCount >= (lastViewIdx+cView.__span)){
					viewIdx++;
					// move to allocating things into the next view
					lastViewIdx += cView.__span;
					lastView = cView;
					cView = props.structure[viewIdx];
				}

				// actually define the cell from what markup hands us
				var cell = {
					name: d.trim(d.attr(th, "name")||th.innerHTML),
					field: d.trim(d.attr(th, "field")||""),
					colSpan: parseInt(d.attr(th, "colspan")||1)
				};
				cellCount += cell.colSpan;
				cell.field = cell.field||cell.name;
				cell.width = widthFromAttr(th);
				if(!cView.cells[tr_idx]){
					cView.cells[tr_idx] = [];
				}
				cView.cells[tr_idx].push(cell);
			});
		});
		// console.debug(dojo.toJson(props.structure, true));
	}
	return new dojox.Grid(props, node);
}


// alias us to the right location
dojox.grid.Grid = dojox.Grid;

}

if(!dojo._hasResource["dojo.data.api.Request"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojo.data.api.Request"] = true;
dojo.provide("dojo.data.api.Request");

dojo.declare("dojo.data.api.Request", null, {
	//	summary:
	//		This class defines out the semantics of what a 'Request' object looks like
	//		when returned from a fetch() method.  In general, a request object is
	//		nothing more than the original keywordArgs from fetch with an abort function 
	//		attached to it to allow users to abort a particular request if they so choose. 
	//		No other functions are required on a general Request object return.  That does not
	//		inhibit other store implementations from adding extentions to it, of course.
	//
	//		This is an abstract API that data provider implementations conform to.  
	//		This file defines methods signatures and intentionally leaves all the
	//		methods unimplemented.
	//
	//		For more details on fetch, see dojo.data.api.Read.fetch().

	abort: function(){
		//	summary:
		//		This function is a hook point for stores to provide as a way for 
		//		a fetch to be halted mid-processing.
		//	description:
		//		This function is a hook point for stores to provide as a way for 
		//		a fetch to be halted mid-processing.  For more details on the fetch() api,
		//		please see dojo.data.api.Read.fetch().
		throw new Error('Unimplemented API: dojo.data.api.Request.abort');
	}
});

}

if(!dojo._hasResource["dojo.data.api.Read"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojo.data.api.Read"] = true;
dojo.provide("dojo.data.api.Read");


dojo.declare("dojo.data.api.Read", null, {
	//	summary:
	//		This is an abstract API that data provider implementations conform to.  
	//		This file defines methods signatures and intentionally leaves all the
	//		methods unimplemented.  For more information on the dojo.data APIs, 
	//		please visit: http://www.dojotoolkit.org/node/98

	getValue: function(	/* item */ item, 
						/* attribute-name-string */ attribute, 
						/* value? */ defaultValue){
		//	summary:
		//		Returns a single attribute value.
		//		Returns defaultValue if and only if *item* does not have a value for *attribute*.
		//		Returns null if and only if null was explicitly set as the attribute value.
		//		Returns undefined if and only if the item does not have a value for the
		//		given attribute (which is the same as saying the item does not have the attribute). 
		// description:
		//		Saying that an "item x does not have a value for an attribute y"
		//		is identical to saying that an "item x does not have attribute y". 
		//		It is an oxymoron to say "that attribute is present but has no values" 
		//		or "the item has that attribute but does not have any attribute values".
		//		If store.hasAttribute(item, attribute) returns false, then
		//		store.getValue(item, attribute) will return undefined.
		//
		//	item:
		//		The item to access values on.
		//	attribute:
		//		The attribute to access represented as a string.
		//	defaultValue:
		//		Optional.  A default value to use for the getValue return in the attribute does not exist or has no value.
		//
		//	exceptions:
		//		Throws an exception if *item* is not an item, or *attribute* is not a string
		//	example:
		//	|	var darthVader = store.getValue(lukeSkywalker, "father");
		var attributeValue = null;
		throw new Error('Unimplemented API: dojo.data.api.Read.getValue');
		return attributeValue; // a literal, an item, null, or undefined (never an array)
	},

	getValues: function(/* item */ item,
						/* attribute-name-string */ attribute){
		//	summary:
		// 		This getValues() method works just like the getValue() method, but getValues()
		//		always returns an array rather than a single attribute value.  The array
		//		may be empty, may contain a single attribute value, or may contain
		//		many attribute values.
		//		If the item does not have a value for the given attribute, then getValues()
		//		will return an empty array: [].  (So, if store.hasAttribute(item, attribute)
		//		has a return of false, then store.getValues(item, attribute) will return [].)
		//
		//	item:
		//		The item to access values on.
		//	attribute:
		//		The attribute to access represented as a string.
		//
		//	exceptions:
		//		Throws an exception if *item* is not an item, or *attribute* is not a string
		//	example:
		//	|	var friendsOfLuke = store.getValues(lukeSkywalker, "friends");
		var array = [];
		throw new Error('Unimplemented API: dojo.data.api.Read.getValues');
		return array; // an array that may contain literals and items
	},

	getAttributes: function(/* item */ item){
		//	summary:
		//		Returns an array with all the attributes that this item has.  This
		//		method will always return an array; if the item has no attributes
		//		at all, getAttributes() will return an empty array: [].
		//
		//	item:
		//		The item to access attributes on.
		//
		//	exceptions:
		//		Throws an exception if *item* is not an item, or *attribute* is not a string
		//	example:
		//	|	var array = store.getAttributes(kermit);
		var array = [];
		throw new Error('Unimplemented API: dojo.data.api.Read.getAttributes');
		return array; // array
	},

	hasAttribute: function(	/* item */ item,
							/* attribute-name-string */ attribute){
		//	summary:
		//		Returns true if the given *item* has a value for the given *attribute*.
		//
		//	item:
		//		The item to access attributes on.
		//	attribute:
		//		The attribute to access represented as a string.
		//
		//	exceptions:
		//		Throws an exception if *item* is not an item, or *attribute* is not a string
		//	example:
		//	|	var trueOrFalse = store.hasAttribute(kermit, "color");
		throw new Error('Unimplemented API: dojo.data.api.Read.hasAttribute');
		return false; // boolean
	},

	containsValue: function(/* item */ item,
							/* attribute-name-string */ attribute, 
							/* anything */ value){
		//	summary:
		//		Returns true if the given *value* is one of the values that getValues()
		//		would return.
		//
		//	item:
		//		The item to access values on.
		//	attribute:
		//		The attribute to access represented as a string.
		//	value:
		//		The value to match as a value for the attribute.
		//
		//	exceptions:
		//		Throws an exception if *item* is not an item, or *attribute* is not a string
		//	example:
		//	|	var trueOrFalse = store.containsValue(kermit, "color", "green");
		throw new Error('Unimplemented API: dojo.data.api.Read.containsValue');
		return false; // boolean
	},

	isItem: function(/* anything */ something){
		//	summary:
		//		Returns true if *something* is an item and came from the store instance.  
		//		Returns false if *something* is a literal, an item from another store instance, 
		//		or is any object other than an item.
		//
		//	something:
		//		Can be anything.
		//
		//	example:
		//	|	var yes = store.isItem(store.newItem());
		//	|	var no  = store.isItem("green");
		throw new Error('Unimplemented API: dojo.data.api.Read.isItem');
		return false; // boolean
	},

	isItemLoaded: function(/* anything */ something) {
		//	summary:
		//		Returns false if isItem(something) is false.  Returns false if
		//		if isItem(something) is true but the the item is not yet loaded
		//		in local memory (for example, if the item has not yet been read
		//		from the server).
		//
		//	something:
		//		Can be anything.
		//
		//	example:
		//	|	var yes = store.isItemLoaded(store.newItem());
		//	|	var no  = store.isItemLoaded("green");
		throw new Error('Unimplemented API: dojo.data.api.Read.isItemLoaded');
		return false; // boolean
	},

	loadItem: function(/* object */ keywordArgs){
		//	summary:
		//		Given an item, this method loads the item so that a subsequent call
		//		to store.isItemLoaded(item) will return true.  If a call to
		//		isItemLoaded() returns true before loadItem() is even called,
		//		then loadItem() need not do any work at all and will not even invoke
		//		the callback handlers.  So, before invoking this method, check that
		//		the item has not already been loaded.  
		// 	keywordArgs:
		//		An anonymous object that defines the item to load and callbacks to invoke when the 
		//		load has completed.  The format of the object is as follows:
		//		{
		//			item: object,
		//			onItem: Function,
		//			onError: Function,
		//			scope: object
		//		}
		//	The *item* parameter.
		//		The item parameter is an object that represents the item in question that should be
		//		contained by the store.  This attribute is required.
		
		//	The *onItem* parameter.
		//		Function(item)
		//		The onItem parameter is the callback to invoke when the item has been loaded.  It takes only one
		//		parameter, the fully loaded item.
		//
		//	The *onError* parameter.
		//		Function(error)
		//		The onError parameter is the callback to invoke when the item load encountered an error.  It takes only one
		//		parameter, the error object
		//
		//	The *scope* parameter.
		//		If a scope object is provided, all of the callback functions (onItem, 
		//		onError, etc) will be invoked in the context of the scope object.
		//		In the body of the callback function, the value of the "this"
		//		keyword will be the scope object.   If no scope object is provided,
		//		the callback functions will be called in the context of dojo.global().
		//		For example, onItem.call(scope, item, request) vs. 
		//		onItem.call(dojo.global(), item, request)
		if (!this.isItemLoaded(keywordArgs.item)) {
			throw new Error('Unimplemented API: dojo.data.api.Read.loadItem');
		}
	},

	fetch: function(/* Object */ keywordArgs){
		//	summary:
		//		Given a query and set of defined options, such as a start and count of items to return,
		//		this method executes the query and makes the results available as data items.
		//		The format and expectations of stores is that they operate in a generally asynchronous 
		//		manner, therefore callbacks are always used to return items located by the fetch parameters.
		//
		//	description:
		//		A Request object will always be returned and is returned immediately.
		//		The basic request is nothing more than the keyword args passed to fetch and 
		//		an additional function attached, abort().  The returned request object may then be used 
		//		to cancel a fetch.  All data items returns are passed through the callbacks defined in the 
		//		fetch parameters and are not present on the 'request' object.
		//
		//		This does not mean that custom stores can not add methods and properties to the request object
		//		returned, only that the API does not require it.  For more info about the Request API, 
		//		see dojo.data.api.Request
		//
		//	keywordArgs:
		//		The keywordArgs parameter may either be an instance of 
		//		conforming to dojo.data.api.Request or may be a simple anonymous object
		//		that may contain any of the following:
		//		{ 
		//			query: query-object or query-string,
		//			queryOptions: object,
		//			onBegin: Function,
		//			onItem: Function,
		//			onComplete: Function,
		//			onError: Function,
		//			scope: object,
		//			start: int
		//			count: int
		//			sort: array
		//		}
		//		All implementations should accept keywordArgs objects with any of
		//		the 9 standard properties: query, onBegin, onItem, onComplete, onError 
		//		scope, sort, start, and count.  Some implementations may accept additional 
		//		properties in the keywordArgs object as valid parameters, such as 
		//		{includeOutliers:true}.         
		//
		//	The *query* parameter.
		//		The query may be optional in some data store implementations.
		//		The dojo.data.api.Read API does not specify the syntax or semantics
		//		of the query itself -- each different data store implementation
		//		may have its own notion of what a query should look like.
		//		However, as of dojo 0.9, 1.0, and 1.1, all the provided datastores in dojo.data
		//		and dojox.data support an object structure query, where the object is a set of 
		//		name/value parameters such as { attrFoo: valueBar, attrFoo1: valueBar1}.  Most of the
		//		dijit widgets, such as ComboBox assume this to be the case when working with a datastore 
		//		when they dynamically update the query.  Therefore, for maximum compatibility with dijit 
		//		widgets the recommended query parameter is a key/value object.  That does not mean that the
		//		the datastore may not take alternative query forms, such as a simple string, a Date, a number, 
		//		or a mix of such.  Ultimately, The dojo.data.api.Read API is agnostic about what the query 
		//		format.  
		//		Further note:  In general for query objects that accept strings as attribute 
		//		value matches, the store should also support basic filtering capability, such as * 
		//		(match any character) and ? (match single character).  An example query that is a query object
		//		would be like: { attrFoo: "value*"}.  Which generally means match all items where they have 
		//		an attribute named attrFoo, with a value that starts with 'value'.
		//
		//	The *queryOptions* parameter
		//		The queryOptions parameter is an optional parameter used to specify optiosn that may modify
		//		the query in some fashion, such as doing a case insensitive search, or doing a deep search
		//		where all items in a hierarchical representation of data are scanned instead of just the root 
		//		items.  It currently defines two options that all datastores should attempt to honor if possible:
		//		{
		//			ignoreCase: boolean, //Whether or not the query should match case sensitively or not.  Default behaviour is false.
		//			deep: boolean 	//Whether or not a fetch should do a deep search of items and all child 
		//							//items instead of just root-level items in a datastore.  Default is false.
		//		}
		//
		//	The *onBegin* parameter.
		//		function(size, request);
		//		If an onBegin callback function is provided, the callback function
		//		will be called just once, before the first onItem callback is called.
		//		The onBegin callback function will be passed two arguments, the
		//		the total number of items identified and the Request object.  If the total number is
		//		unknown, then size will be -1.  Note that size is not necessarily the size of the 
		//		collection of items returned from the query, as the request may have specified to return only a 
		//		subset of the total set of items through the use of the start and count parameters.
		//
		//	The *onItem* parameter.
		//		function(item, request);
		//		If an onItem callback function is provided, the callback function
		//		will be called as each item in the result is received. The callback 
		//		function will be passed two arguments: the item itself, and the
		//		Request object.
		//
		//	The *onComplete* parameter.
		//		function(items, request);
		//
		//		If an onComplete callback function is provided, the callback function
		//		will be called just once, after the last onItem callback is called.
		//		Note that if the onItem callback is not present, then onComplete will be passed
		//		an array containing all items which matched the query and the request object.  
		//		If the onItem callback is present, then onComplete is called as: 
		//		onComplete(null, request).
		//
		//	The *onError* parameter.
		//		function(errorData, request); 
		//		If an onError callback function is provided, the callback function
		//		will be called if there is any sort of error while attempting to
		//		execute the query.
		//		The onError callback function will be passed two arguments:
		//		an Error object and the Request object.
		//
		//	The *scope* parameter.
		//		If a scope object is provided, all of the callback functions (onItem, 
		//		onComplete, onError, etc) will be invoked in the context of the scope
		//		object.  In the body of the callback function, the value of the "this"
		//		keyword will be the scope object.   If no scope object is provided,
		//		the callback functions will be called in the context of dojo.global().  
		//		For example, onItem.call(scope, item, request) vs. 
		//		onItem.call(dojo.global(), item, request)
		//
		//	The *start* parameter.
		//		If a start parameter is specified, this is a indication to the datastore to 
		//		only start returning items once the start number of items have been located and
		//		skipped.  When this parameter is paired withh 'count', the store should be able
		//		to page across queries with millions of hits by only returning subsets of the 
		//		hits for each query
		//
		//	The *count* parameter.
		//		If a count parameter is specified, this is a indication to the datastore to 
		//		only return up to that many items.  This allows a fetch call that may have 
		//		millions of item matches to be paired down to something reasonable.  
		//
		//	The *sort* parameter.
		//		If a sort parameter is specified, this is a indication to the datastore to 
		//		sort the items in some manner before returning the items.  The array is an array of 
		//		javascript objects that must conform to the following format to be applied to the
		//		fetching of items:
		//		{
		//			attribute: attribute || attribute-name-string,
		//			descending: true|false;   // Optional.  Default is false.
		//		}
		//		Note that when comparing attributes, if an item contains no value for the attribute
		//		(undefined), then it the default ascending sort logic should push it to the bottom 
		//		of the list.  In the descending order case, it such items should appear at the top of the list.
		// 
		//	returns:
		//		The fetch() method will return a javascript object conforming to the API
		//		defined in dojo.data.api.Request.  In general, it will be the keywordArgs
		//		object returned with the required functions in Request.js attached.
		//		Its general purpose is to provide a convenient way for a caller to abort an
		//		ongoing fetch.  
		// 
		//		The Request object may also have additional properties when it is returned
		//		such as request.store property, which is a pointer to the datastore object that 
		//		fetch() is a method of.
		//
		//	exceptions:
		//		Throws an exception if the query is not valid, or if the query
		//		is required but was not supplied.
		//
		//	example:
		//		Fetch all books identified by the query and call 'showBooks' when complete
		//		|	var request = store.fetch({query:"all books", onComplete: showBooks});
		//	example:
		//		Fetch all items in the story and call 'showEverything' when complete.
		//		|	var request = store.fetch(onComplete: showEverything);
		//	example:
		//		Fetch only 10 books that match the query 'all books', starting at the fifth book found during the search.
		//		This demonstrates how paging can be done for specific queries.  
		//		|	var request = store.fetch({query:"all books", start: 4, count: 10, onComplete: showBooks});
		//	example:
		//		Fetch all items that match the query, calling 'callback' each time an item is located.
		//		|	var request = store.fetch({query:"foo/bar", onItem:callback});
		//	example:
		//		Fetch the first 100 books by author King, call showKing when up to 100 items have been located.
		//		|	var request = store.fetch({query:{author:"King"}, start: 0, count:100, onComplete: showKing});
		//	example:
		//		Locate the books written by Author King, sort it on title and publisher, then return the first 100 items from the sorted items.
		//		|	var request = store.fetch({query:{author:"King"}, sort: [{ attribute: "title", descending: true}, {attribute: "publisher"}], ,start: 0, count:100, onComplete: 'showKing'});
		//	example:
		//		Fetch the first 100 books by authors starting with the name King, then call showKing when up to 100 items have been located.
		//		|	var request = store.fetch({query:{author:"King*"}, start: 0, count:100, onComplete: showKing});
		//	example:
		//		Fetch the first 100 books by authors ending with 'ing', but only have one character before it (King, Bing, Ling, Sing, etc.), then call showBooks when up to 100 items have been located.
		//		|	var request = store.fetch({query:{author:"?ing"}, start: 0, count:100, onComplete: showBooks});
		//	example:
		//		Fetch the first 100 books by author King, where the name may appear as King, king, KING, kInG, and so on, then call showKing when up to 100 items have been located.
		//		|	var request = store.fetch({query:{author:"King"}, queryOptions:(ignoreCase: true}, start: 0, count:100, onComplete: showKing});
		//	example:
		//		Paging
		//		|	var store = new dojo.data.LargeRdbmsStore({url:"jdbc:odbc:foobar"});
		//		|	var fetchArgs = {
		//		|		query: {type:"employees", name:"Hillary *"}, // string matching
		//		|		sort: [{attribute:"department", descending:true}],
		//		|		start: 0,
		//		|		count: 20,
		//		|		scope: displayer,
		//		|		onBegin: showThrobber,
		//		|		onItem: displayItem,
		//		|		onComplete: stopThrobber,
		//		|		onError: handleFetchError,
		//		|	};
		//		|	store.fetch(fetchArgs);
		//		|	...
		//		and then when the user presses the "Next Page" button...
		//		|	fetchArgs.start += 20;
		//		|	store.fetch(fetchArgs);  // get the next 20 items
		var request = null; 
		throw new Error('Unimplemented API: dojo.data.api.Read.fetch');
		return request; // an object conforming to the dojo.data.api.Request API
	},

	getFeatures: function(){
		//	summary:
		//		The getFeatures() method returns an simple keyword values object 
		//		that specifies what interface features the datastore implements.  
		//		A simple CsvStore may be read-only, and the only feature it 
		//		implements will be the 'dojo.data.api.Read' interface, so the
		//		getFeatures() method will return an object like this one:
		//		{'dojo.data.api.Read': true}.
		//		A more sophisticated datastore might implement a variety of
		//		interface features, like 'dojo.data.api.Read', 'dojo.data.api.Write', 
		//		'dojo.data.api.Identity', and 'dojo.data.api.Attribution'.
		return {
			'dojo.data.api.Read': true
		};
	},

	close: function(/*dojo.data.api.Request || keywordArgs || null */ request){
		//	summary:
		//		The close() method is intended for instructing the store to 'close' out 
		//		any information associated with a particular request.
		//
		//	description:
		//		The close() method is intended for instructing the store to 'close' out 
		//		any information associated with a particular request.  In general, this API
		//		expects to recieve as a parameter a request object returned from a fetch.  
		//		It will then close out anything associated with that request, such as 
		//		clearing any internal datastore caches and closing any 'open' connections.
		//		For some store implementations, this call may be a no-op.
		//
		//	request:
		//		An instance of a request for the store to use to identify what to close out.
		//		If no request is passed, then the store should clear all internal caches (if any)
		//		and close out all 'open' connections.  It does not render the store unusable from
		//		there on, it merely cleans out any current data and resets the store to initial 
		//		state.
		//
		//	example:
		//	|	var request = store.fetch({onComplete: doSomething});
		//	|	...
		//	|	store.close(request);
		throw new Error('Unimplemented API: dojo.data.api.Read.close');
	},

	getLabel: function(/* item */ item){
		//	summary:
		//		Method to inspect the item and return a user-readable 'label' for the item
		//		that provides a general/adequate description of what the item is. 
		//
		//	description:
		//		Method to inspect the item and return a user-readable 'label' for the item
		//		that provides a general/adequate description of what the item is.  In general
		//		most labels will be a specific attribute value or collection of the attribute
		//		values that combine to label the item in some manner.  For example for an item
		//		that represents a person it may return the label as:  "firstname lastlame" where
		//		the firstname and lastname are attributes on the item.  If the store is unable 
		//		to determine an adequate human readable label, it should return undefined.  Users that wish
		//		to customize how a store instance labels items should replace the getLabel() function on 
		//		their instance of the store, or extend the store and replace the function in 
		//		the extension class.
		//
		//	item:
	   	//		The item to return the label for.
		//
		//	returns: 
		//		A user-readable string representing the item or undefined if no user-readable label can 
		//		be generated.
		throw new Error('Unimplemented API: dojo.data.api.Read.getLabel');
		return undefined;
	},

	getLabelAttributes: function(/* item */ item){
		//	summary:
		//		Method to inspect the item and return an array of what attributes of the item were used 
		//		to generate its label, if any.
		//
		//	description:
		//		Method to inspect the item and return an array of what attributes of the item were used 
		//		to generate its label, if any.  This function is to assist UI developers in knowing what
		//		attributes can be ignored out of the attributes an item has when displaying it, in cases
		//		where the UI is using the label as an overall identifer should they wish to hide 
		//		redundant information.
		//
		//	item:
	   	//		The item to return the list of label attributes for.
		//
		//	returns: 
		//		An array of attribute names that were used to generate the label, or null if public attributes 
		//		were not used to generate the label.
		throw new Error('Unimplemented API: dojo.data.api.Read.getLabelAttributes');
		return null;
	}
});

}

if(!dojo._hasResource["dojo.data.api.Identity"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojo.data.api.Identity"] = true;
dojo.provide("dojo.data.api.Identity");


dojo.declare("dojo.data.api.Identity", dojo.data.api.Read, {
	//	summary:
	//		This is an abstract API that data provider implementations conform to.
	//		This file defines methods signatures and intentionally leaves all the
	//		methods unimplemented.

	getFeatures: function(){
		//	summary: 
		//		See dojo.data.api.Read.getFeatures()
		return {
			 'dojo.data.api.Read': true,
			 'dojo.data.api.Identity': true
		};
	},

	getIdentity: function(/* item */ item){
		//	summary:
		//		Returns a unique identifier for an item.  The return value will be
		//		either a string or something that has a toString() method (such as,
		//		for example, a dojox.uuid.Uuid object).
		//	item:
		//		The item from the store from which to obtain its identifier.
		//	exceptions:
		//		Conforming implementations may throw an exception or return null if
		//		item is not an item.
		//	example:
		//	|	var itemId = store.getIdentity(kermit);
		//	|	assert(kermit === store.findByIdentity(store.getIdentity(kermit)));
		throw new Error('Unimplemented API: dojo.data.api.Identity.getIdentity');
		var itemIdentityString = null;
		return itemIdentityString; // string
	},

	getIdentityAttributes: function(/* item */ item){
		//	summary:
		//		Returns an array of attribute names that are used to generate the identity. 
		//		For most stores, this is a single attribute, but for some complex stores
		//		such as RDB backed stores that use compound (multi-attribute) identifiers
		//		it can be more than one.  If the identity is not composed of attributes
		//		on the item, it will return null.  This function is intended to identify
		//		the attributes that comprise the identity so that so that during a render
		//		of all attributes, the UI can hide the the identity information if it 
		//		chooses.
		//	item:
		//		The item from the store from which to obtain the array of public attributes that 
		//		compose the identifier, if any.
		//	example:
		//	|	var itemId = store.getIdentity(kermit);
		//	|	var identifiers = store.getIdentityAttributes(itemId);
		//	|	assert(typeof identifiers === "array" || identifiers === null);
		throw new Error('Unimplemented API: dojo.data.api.Identity.getIdentityAttributes');
		return null; // string
	},


	fetchItemByIdentity: function(/* object */ keywordArgs){
		//	summary:
		//		Given the identity of an item, this method returns the item that has 
		//		that identity through the onItem callback.  Conforming implementations 
		//		should return null if there is no item with the given identity.  
		//		Implementations of fetchItemByIdentity() may sometimes return an item 
		//		from a local cache and may sometimes fetch an item from a remote server, 
		//
		// 	keywordArgs:
		//		An anonymous object that defines the item to locate and callbacks to invoke when the 
		//		item has been located and load has completed.  The format of the object is as follows:
		//		{
		//			identity: string|object,
		//			onItem: Function,
		//			onError: Function,
		//			scope: object
		//		}
		//	The *identity* parameter.
		//		The identity parameter is the identity of the item you wish to locate and load
		//		This attribute is required.  It should be a string or an object that toString() 
		//		can be called on.
		//		
		//	The *onItem* parameter.
		//		Function(item)
		//		The onItem parameter is the callback to invoke when the item has been loaded.  It takes only one
		//		parameter, the item located, or null if none found.
		//
		//	The *onError* parameter.
		//		Function(error)
		//		The onError parameter is the callback to invoke when the item load encountered an error.  It takes only one
		//		parameter, the error object
		//
		//	The *scope* parameter.
		//		If a scope object is provided, all of the callback functions (onItem, 
		//		onError, etc) will be invoked in the context of the scope object.
		//		In the body of the callback function, the value of the "this"
		//		keyword will be the scope object.   If no scope object is provided,
		//		the callback functions will be called in the context of dojo.global.
		//		For example, onItem.call(scope, item, request) vs. 
		//		onItem.call(dojo.global, item, request)
		if (!this.isItemLoaded(keywordArgs.item)) {
			throw new Error('Unimplemented API: dojo.data.api.Identity.fetchItemByIdentity');
		}
	}
});

}


dojo.i18n._preloadLocalizations("dojo.nls.tatami", ["he","nl","tr","no","ko","el","en","en-gb","ROOT","zh-cn","hu","es","fi-fi","pt-br","fi","he-il","xx","ru","it","fr","cs","de-de","fr-fr","it-it","es-es","ja","da","pl","de","sv","pt","zh-tw","pt-pt","nl-nl","ko-kr","ar","en-us","zh","ja-jp"]);
