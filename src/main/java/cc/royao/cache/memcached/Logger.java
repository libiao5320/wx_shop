/**
 * MemCached Java Client Logger
 * Copyright (c) 2007
 *
 * This module is Copyright (c) 2005 Greg Whalin
 * All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later
 * version.
 *
 * This library is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE.  See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA
 *
 * @author Greg Whalin <greg@meetup.com> 
 * @version 1.5
 */
package cc.royao.cache.memcached;

import java.util.*;


/** 
 * This is a generic logger class for use in logging.
 *
 * This can easily be swapped out for any other logging package in the main code.
 * For now, this is just a quick and dirty logger which will allow you to specify
 * log levels, but only wraps system.out.println.
 * 
 * @author Greg Whalin <greg@meetup.com>
 * @version 1.5
 */
public class Logger {

	public static final int LEVEL_DEBUG   = 0;
	public static final int LEVEL_INFO    = 1;
	public static final int LEVEL_WARN    = 2;
	public static final int LEVEL_ERROR   = 3;
	public static final int LEVEL_FATAL   = 4;

	private static Map<String,Logger> loggers =
		new HashMap<String,Logger>();

	private String name;
	private int level;

	public void setLevel( int level ) { this.level = level; }
	public int getLevel() { return this.level; }

	protected Logger( String name, int level ) {
		this.name  = name;
		this.level = level;
	}

	protected Logger( String name ) {
		this.name  = name;
		this.level = LEVEL_INFO;
	}

	/** 
	 * Gets a Logger obj for given name and level. 
	 * 
	 * @param name 
	 * @param level 
	 * @return 
	 */
	public static synchronized Logger getLogger( String name, int level ) {
		Logger log = getLogger( name );
		if ( log.getLevel() != level )
			log.setLevel( level );

		return log;
	}

	/** 
	 * Gets a Logger obj for given name
	 * and sets default level. 
	 * 
	 * @param name 
	 * @return 
	 */
	public static synchronized Logger getLogger( String name ) {

		Logger log = null;
		if ( loggers.containsKey( name ) ) {
			log = loggers.get( name );
		}
		else {
			log = new Logger( name );
			loggers.put( name, log );
		}

		return log;
	}

	/** 
	 * logs mesg to std out and prints stack trace if exception passed in 
	 * 
	 * @param mesg 
	 * @param ex 
	 */
	private void log( String mesg, Exception ex ) {
		System.out.println( name + " " + new Date() + " - " + mesg );
		if ( ex != null )
			ex.printStackTrace( System.out );
	}

	/** 
	 * logs a debug mesg 
	 * 
	 * @param mesg 
	 * @param ex 
	 */
	public void debug( String mesg, Exception ex ) {
		if ( this.level > LEVEL_DEBUG )
			return;

		log( mesg, ex );
	}

	public void debug( String mesg ) {
		debug( mesg, null );
	}

	/** 
	 * logs info mesg 
	 * 
	 * @param mesg 
	 * @param ex 
	 */
	public void info( String mesg, Exception ex ) {
		if ( this.level > LEVEL_INFO )
			return;

		log( mesg, ex );
	}

	public void info( String mesg ) {
		info( mesg, null );
	}

	/** 
	 * logs warn mesg 
	 * 
	 * @param mesg 
	 * @param ex 
	 */
	public void warn( String mesg, Exception ex ) {
		if ( this.level > LEVEL_WARN )
			return;

		log( mesg, ex );
	}

	public void warn( String mesg ) {
		warn( mesg, null );
	}

	/** 
	 * logs error mesg 
	 * 
	 * @param mesg 
	 * @param ex 
	 */
	public void error( String mesg, Exception ex ) {
		if ( this.level > LEVEL_ERROR )
			return;

		log( mesg, ex );
	}

	public void error( String mesg ) {
		error( mesg, null );
	}

	/** 
	 * logs fatal mesg
	 * 
	 * @param mesg 
	 * @param ex 
	 */
	public void fatal( String mesg, Exception ex ) {
		if ( this.level > LEVEL_FATAL )
			return;

		log( mesg, ex );
	}

	public void fatal( String mesg ) {
		fatal( mesg, null );
	}
}
