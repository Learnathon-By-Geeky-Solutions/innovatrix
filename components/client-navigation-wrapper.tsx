"use client"

import { Navigation } from "@/components/navigation"

export function ClientNavigationWrapper({ children }: { children: React.ReactNode }) {
  const isAuthPage = typeof window !== 'undefined' ? window.location.pathname.startsWith('/auth') : false

  return (
    <div className="min-h-screen bg-[#FFF5F2]">
      {!isAuthPage && <Navigation />}
      <div className="container mx-auto px-4">
        {children}
      </div>
    </div>
  )
}
