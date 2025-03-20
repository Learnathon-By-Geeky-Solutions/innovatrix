"use client"

import * as React from "react"
import Link from "next/link"
import { usePathname } from "next/navigation"
import { cn } from "@/lib/utils"
import {
  NavigationMenu,
  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  NavigationMenuContent,
  NavigationMenuItem,
  NavigationMenuLink,
  NavigationMenuList,
  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  NavigationMenuTrigger,
} from "@/components/ui/navigation-menu"
import { Button } from "@/components/ui/button"
import { Search, Menu, MapPin } from 'lucide-react'
import {
  Sheet,
  SheetContent,
  SheetHeader,
  SheetTitle,
  SheetTrigger,
} from "@/components/ui/sheet"
import { Input } from "@/components/ui/input"
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu"
import Cookies from "js-cookie"
import { useRouter } from "next/navigation"

export function Navigation() {
  const pathname = usePathname()
  const router = useRouter()
  const [location, setLocation] = React.useState("Dhaka, Bangladesh")

  const handleSignOut = () => {
    // Remove auth cookies
    Cookies.remove('token')
    Cookies.remove('userType')
    // Redirect to login page
    router.push('/auth/login')
  }

  // Hide navigation on auth pages
  if (pathname?.startsWith('/auth/')) {
    return null
  }

  return (
    <div className="sticky top-0 z-50 w-full border-b bg-white/95 backdrop-blur supports-[backdrop-filter]:bg-white/60 shadow-sm">
      <div className="container mx-auto">
        <div className="flex h-16 items-center px-4">
          <Sheet>
            <SheetTrigger asChild>
              <Button variant="ghost" className="md:hidden">
                <Menu className="h-6 w-6" />
              </Button>
            </SheetTrigger>
            <SheetContent side="left">
              <SheetHeader>
                <SheetTitle>Ahar</SheetTitle>
              </SheetHeader>
              <nav className="flex flex-col gap-4 mt-4">
                <Link 
                  href="/restaurants" 
                  className={cn(
                    "transition-colors hover:text-[#FF6B35]",
                    pathname === "/restaurants" ? "text-[#FF6B35]" : ""
                  )}
                >
                  Restaurants
                </Link>
                <Link 
                  href="/street-food"
                  className={cn(
                    "transition-colors hover:text-[#FF6B35]",
                    pathname === "/street-food" ? "text-[#FF6B35]" : ""
                  )}
                >
                  Street Food
                </Link>
                <Link 
                  href="/local-guide"
                  className={cn(
                    "transition-colors hover:text-[#FF6B35]",
                    pathname === "/local-guide" ? "text-[#FF6B35]" : ""
                  )}
                >
                  Local Guide
                </Link>
                <Link 
                  href="/community"
                  className={cn(
                    "transition-colors hover:text-[#FF6B35]",
                    pathname === "/community" ? "text-[#FF6B35]" : ""
                  )}
                >
                  Community
                </Link>
                <Link 
                  href="/community/profile" 
                  className={cn(
                    "transition-colors hover:text-[#FF6B35]",
                    pathname === "/community/profile" ? "text-[#FF6B35]" : ""
                  )}
                >
                  Profile
                </Link>
                <Link 
                  href="/community/settings"
                  className={cn(
                    "transition-colors hover:text-[#FF6B35]",
                    pathname === "/community/settings" ? "text-[#FF6B35]" : ""
                  )}
                >
                  Settings
                </Link>
              </nav>
            </SheetContent>
          </Sheet>
          
          <Link href="/home" className="text-2xl font-bold text-[#FF6B35] mr-8">
            Ahar
          </Link>

          <NavigationMenu className="hidden md:flex mx-6">
            <NavigationMenuList>
              <NavigationMenuItem>
                <Link href="/restaurants" legacyBehavior passHref>
                  <NavigationMenuLink 
                    className={cn(
                      "group inline-flex h-9 w-max items-center justify-center rounded-md bg-background px-4 py-2 text-sm font-medium transition-colors hover:bg-accent hover:text-accent-foreground focus:bg-accent focus:text-accent-foreground focus:outline-none disabled:pointer-events-none disabled:opacity-50 data-[active]:bg-accent/50 data-[state=open]:bg-accent/50",
                      pathname === "/restaurants" ? "bg-accent" : ""
                    )}
                  >
                    Restaurants
                  </NavigationMenuLink>
                </Link>
              </NavigationMenuItem>
              <NavigationMenuItem>
                <Link href="/street-food" legacyBehavior passHref>
                  <NavigationMenuLink 
                    className={cn(
                      "group inline-flex h-9 w-max items-center justify-center rounded-md bg-background px-4 py-2 text-sm font-medium transition-colors hover:bg-accent hover:text-accent-foreground focus:bg-accent focus:text-accent-foreground focus:outline-none disabled:pointer-events-none disabled:opacity-50 data-[active]:bg-accent/50 data-[state=open]:bg-accent/50",
                      pathname === "/street-food" ? "bg-accent" : ""
                    )}
                  >
                    Street Food
                  </NavigationMenuLink>
                </Link>
              </NavigationMenuItem>
              <NavigationMenuItem>
                <Link href="/local-guide" legacyBehavior passHref>
                  <NavigationMenuLink 
                    className={cn(
                      "group inline-flex h-9 w-max items-center justify-center rounded-md bg-background px-4 py-2 text-sm font-medium transition-colors hover:bg-accent hover:text-accent-foreground focus:bg-accent focus:text-accent-foreground focus:outline-none disabled:pointer-events-none disabled:opacity-50 data-[active]:bg-accent/50 data-[state=open]:bg-accent/50",
                      pathname === "/local-guide" ? "bg-accent" : ""
                    )}
                  >
                    Local Guide
                  </NavigationMenuLink>
                </Link>
              </NavigationMenuItem>
              <NavigationMenuItem>
                <Link href="/community" legacyBehavior passHref>
                  <NavigationMenuLink 
                    className={cn(
                      "group inline-flex h-9 w-max items-center justify-center rounded-md bg-background px-4 py-2 text-sm font-medium transition-colors hover:bg-accent hover:text-accent-foreground focus:bg-accent focus:text-accent-foreground focus:outline-none disabled:pointer-events-none disabled:opacity-50 data-[active]:bg-accent/50 data-[state=open]:bg-accent/50",
                      pathname === "/community" ? "bg-accent" : ""
                    )}
                  >
                    Community
                  </NavigationMenuLink>
                </Link>
              </NavigationMenuItem>
            </NavigationMenuList>
          </NavigationMenu>

          <div className="ml-auto flex items-center space-x-6">
            <DropdownMenu>
              <DropdownMenuTrigger asChild>
                <Button variant="ghost" className="flex gap-2">
                  <MapPin className="h-4 w-4" />
                  <span className="hidden md:inline">{location}</span>
                </Button>
              </DropdownMenuTrigger>
              <DropdownMenuContent>
                <DropdownMenuItem onClick={() => setLocation("Dhaka, Bangladesh")}>
                  Dhaka, Bangladesh
                </DropdownMenuItem>
                <DropdownMenuItem onClick={() => setLocation("Chittagong, Bangladesh")}>
                  Chittagong, Bangladesh
                </DropdownMenuItem>
                <DropdownMenuItem onClick={() => setLocation("Sylhet, Bangladesh")}>
                  Sylhet, Bangladesh
                </DropdownMenuItem>
              </DropdownMenuContent>
            </DropdownMenu>
            <div className="hidden md:flex relative">
              <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 h-4 w-4 text-gray-400" />
              <Input 
                className="pl-9 w-[250px] bg-gray-50 border-gray-200 focus:bg-white" 
                placeholder="Search..." 
              />
            </div>
            <Button 
              onClick={handleSignOut}
              className="bg-[#FF6B35] hover:bg-[#FF8B55] px-6"
            >
              Sign Out
            </Button>
          </div>
        </div>
      </div>
    </div>
  )
}
