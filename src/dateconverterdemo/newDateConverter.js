test = (time)=>{
                                                                
        var oldTime = time;  
        var date;
        
        var ACTUAL_YEAR = "2018.";
        
        var patternA = /á/gi;
        var patternU = /ú/gi;
        var patternMaj = /maj/gi;
        var patternSep = /sze/gi;
        var patternOkt = /okt/gi;
        var patternNA = /Na,|f[.]|sz[.]|ír/gi;
        var patternAZ = /[A-Za-zá-úÁ-Ú]/gi;
        
        var jan = /jan[á-úa-z]+/gi;
        var feb = /feb\w+/gi;
        var marc = /mar\w+|már\w+/gi;
        var apr = /apr\w+|ápr\w+/gi;
        var may = /maj\w+|máj\w+/gi;
        var jun = /jun\w+|jún\w+/gi;
        var jul = /jul\w+|júl\w+/gi;
        var aug = /aug\w+|aug\w+/gi;
        var sept = /szep\w+/gi;
        var oct = /okt[á-úa-z]+/gi;
        var nov = /nov\w+/gi;
        var dec = /dec\w+/gi;
        var patternDate = /\d{4}.\s\d{2}.\s\d{2}.\s|\d{4}.\d{2}.\d{2}|\d{4}.\s\d{2}.\s\d{2}.|\d{4}.\s\d{2}.\s\d{1}.|\d{4}-\d{2}-\d{2}/;
        var partOfDate = /\d\d. \d{1,2}.,/;
        
        oldTime = oldTime.replace(jan, "01.");
        oldTime = oldTime.replace(feb, "02.");
        oldTime = oldTime.replace(marc, "03.");
        oldTime = oldTime.replace(apr, "04.");
        oldTime = oldTime.replace(may, "05.");
        oldTime = oldTime.replace(jun, "06.");
        oldTime = oldTime.replace(jul, "07.");
        oldTime = oldTime.replace(aug, "08.");
        oldTime = oldTime.replace(sept, "09.");
        oldTime = oldTime.replace(oct, "10.");
        oldTime = oldTime.replace(nov, "11.");
        oldTime = oldTime.replace(dec, "12.");
        
        oldTime = oldTime.replace(patternAZ, '' );
        
         oldTime = oldTime.replace(patternNA, '' );
         
         var lookingForDate = "";
        
        lookingForDate = oldTime.match(patternDate);
        if ( lookingForDate != null && lookingForDate.length > 0 ){
            date = new Date(lookingForDate);
            return date.toLocaleDateString();
        } else  {
            lookingForDate = oldTime.match(partOfDate);
            if (  lookingForDate != null && lookingForDate.length > 0 ){
                lookingForDate = "" + ACTUAL_YEAR + lookingForDate;
                date = new Date(lookingForDate);
                return date.toLocaleDateString();
            }
        }


        var convertTime = oldTime.split(' ');
        if(convertTime.length >= 3 ){
                oldTime = convertTime[0]+convertTime[1].substring(0,3)+ '.' + convertTime[2];
        }
				
		date = new Date(oldTime);				
				
                                if ( date.toLocaleDateString() === 'Invalid Date'){
                                    oldTime = time;
                                    oldTime = oldTime.match(patternDate);
                                    date = new Date(oldTime);
                                }
                               
                                    return date.toLocaleDateString();
                               
                                 
}