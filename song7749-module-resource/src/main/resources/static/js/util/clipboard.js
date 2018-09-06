function copy_clip(meintext) {
	if (window.clipboardData) {

		// the IE-manier
		window.clipboardData.setData("Text", meintext);

		// waarschijnlijk niet de beste manier om Moz/NS te detecteren;
		// het is mij echter onbekend vanaf welke versie dit precies werkt:
	} else if (window.netscape) {

		// dit is belangrijk maar staat nergens duidelijk vermeld:
		// you have to sign the code to enable this, or see notes below
		netscape.security.PrivilegeManager
				.enablePrivilege('UniversalXPConnect');

		// maak een interface naar het clipboard
		var clip = Components.classes['@mozilla.org/widget/clipboard;1']
				.createInstance(Components.interfaces.nsIClipboard);
		if (!clip)
			return;

		// maak een transferable
		var trans = Components.classes['@mozilla.org/widget/transferable;1']
				.createInstance(Components.interfaces.nsITransferable);
		if (!trans)
			return;

		// specificeer wat voor soort data we op willen halen; text in dit geval
		trans.addDataFlavor('text/unicode');

		// om de data uit de transferable te halen hebben we 2 nieuwe objecten
		// nodig om het in op te slaan
		var str = new Object();
		var len = new Object();

		var str = Components.classes["@mozilla.org/supports-string;1"]
				.createInstance(Components.interfaces.nsISupportsString);

		var copytext = meintext;

		str.data = copytext;

		trans.setTransferData("text/unicode", str, copytext.length * 2);

		var clipid = Components.interfaces.nsIClipboard;

		if (!clip)
			return false;

		clip.setData(trans, null, clipid.kGlobalClipboard);

	}
	return false;
}