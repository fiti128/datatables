$.fn.dataTableExt.oPagination.four_button = {
    "fnInit": function ( oSettings, nPaging, fnCallbackDraw )
    {
        nFirst = document.createElement( 'a' );
        nPrevious = document.createElement( 'a' );
        nNext = document.createElement( 'a' );
        nLast = document.createElement( 'a' );
        /*  
        nFirst.appendChild( document.createTextNode( oSettings.oLanguage.oPaginate.sFirst ) );
        nPrevious.appendChild( document.createTextNode( oSettings.oLanguage.oPaginate.sPrevious ) );
        nNext.appendChild( document.createTextNode( oSettings.oLanguage.oPaginate.sNext ) );
        nLast.appendChild( document.createTextNode( oSettings.oLanguage.oPaginate.sLast ) );
          */
        nFirst.className = "paginate_button first";
        nPrevious.className = "paginate_button previous";
        nNext.className="paginate_button next";
        nLast.className = "paginate_button last";
        
        nFirstImg = document.createElement( 'img');
        nPreviousImg = document.createElement( 'img');
        nNextImg = document.createElement( 'img');
        nLastImg = document.createElement( 'img');
        
        nFirstImg.setAttribute('src', contextPath+ '/resources/images/resultset_first.png');
        nPreviousImg.setAttribute('src', contextPath+ '/resources/images/resultset_previous.png');
        nNextImg.setAttribute('src', contextPath+ '/resources/images/resultset_next.png');
        nLastImg.setAttribute('src', contextPath+ '/resources/images/resultset_last.png');
        
        nFirst.appendChild( nFirstImg );
        nPrevious.appendChild( nPreviousImg );
        nNext.appendChild( nNextImg );
        nLast.appendChild( nLastImg );
        
        /*  
        nPaging.appendChild( nFirstImg );
        nPaging.appendChild( nPreviousImg );
        nPaging.appendChild( nNextImg );
        nPaging.appendChild( nLastImg );
        */
        
        nPaging.appendChild( nFirst );
        nPaging.appendChild( nPrevious );
        nPaging.appendChild( nNext );
        nPaging.appendChild( nLast );
        
        $(nFirst).click( function (e) {
        	e.preventDefault();
            oSettings.oApi._fnPageChange( oSettings, "first" );
            fnCallbackDraw( oSettings );
        } );
          
        $(nPrevious).click( function(e) {
        	e.preventDefault();
            oSettings.oApi._fnPageChange( oSettings, "previous" );
            fnCallbackDraw( oSettings );
        } );
          
        $(nNext).click( function(e) {
        	e.preventDefault();
            oSettings.oApi._fnPageChange( oSettings, "next" );
            fnCallbackDraw( oSettings );
        } );
          
        $(nLast).click( function(e) {
        	e.preventDefault();
            oSettings.oApi._fnPageChange( oSettings, "last" );
            fnCallbackDraw( oSettings );
        } );
          
        /* Disallow text selection */
        $(nFirst).bind( 'selectstart', function () { return false; } );
        $(nPrevious).bind( 'selectstart', function () { return false; } );
        $(nNext).bind( 'selectstart', function () { return false; } );
        $(nLast).bind( 'selectstart', function () { return false; } );
    },
     
     
    "fnUpdate": function ( oSettings, fnCallbackDraw )
    {
        if ( !oSettings.aanFeatures.p )
        {
            return;
        }
          
        /* Loop over each instance of the pager */
        var an = oSettings.aanFeatures.p;
        for ( var i=0, iLen=an.length ; i<iLen ; i++ )
        {
            var buttons = an[i].getElementsByTagName('a');
            if ( oSettings._iDisplayStart === 0 )
            {
                buttons[0].className = "paginate_disabled_previous";
                buttons[1].className = "paginate_disabled_previous";
            }
            else
            {
                buttons[0].className = "paginate_enabled_previous";
                buttons[1].className = "paginate_enabled_previous";
            }
              
            if ( oSettings.fnDisplayEnd() == oSettings.fnRecordsDisplay() )
            {
                buttons[2].className = "paginate_disabled_next";
                buttons[3].className = "paginate_disabled_next";
            }
            else
            {
                buttons[2].className = "paginate_enabled_next";
                buttons[3].className = "paginate_enabled_next";
            }
        }
    }
};