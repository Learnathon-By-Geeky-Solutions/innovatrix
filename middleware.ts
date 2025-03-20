// filepath: /e:/Dev/Learnathon3.0/PracticeFrontend/frontend/middleware.ts
import { NextResponse } from 'next/server'
import type { NextRequest } from 'next/server'

export function middleware(request: NextRequest) {
  const { pathname } = request.nextUrl
  const token = request.cookies.get('token')?.value
  const userType = request.cookies.get('userType')?.value
  const publicPaths = ['/auth/login', '/auth/register']

  // If accessing root path, redirect to login
  if (pathname === '/') {
    return NextResponse.redirect(new URL('/auth/login', request.url))
  }

  // If user is authenticated and tries to access auth pages, redirect to appropriate dashboard
  if (token && publicPaths.includes(pathname)) {
    if (userType === 'owner') {
      return NextResponse.redirect(new URL('/owner/dashboard', request.url))
    } else {
      return NextResponse.redirect(new URL('/home', request.url))
    }
  }

  // If user is not authenticated and tries to access protected routes, redirect to login
  if (!token && !publicPaths.includes(pathname)) {
    const loginUrl = new URL('/auth/login', request.url)
    loginUrl.searchParams.set('from', pathname)
    return NextResponse.redirect(loginUrl)
  }

  // Route protection based on user type
  if (pathname.startsWith('/owner') && userType !== 'owner') {
    return NextResponse.redirect(new URL('/home', request.url))
  }

  return NextResponse.next()
}

// Add config to specify which paths should be handled by middleware
export const config = {
  matcher: [
    /*
     * Match all paths except for:
     * 1. /api (API routes)
     * 2. /_next (Next.js internals)
     * 3. /_static (inside /public)
     * 4. /_vercel (Vercel internals)
     * 5. all root files inside /public (e.g. /favicon.ico)
     */
    '/((?!api|_next|_static|_vercel|[\\w-]+\\.\\w+).*)',
  ],
}