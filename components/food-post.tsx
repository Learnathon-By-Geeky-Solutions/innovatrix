/* eslint-disable @next/next/no-img-element */
"use client"

import { useState } from "react"
import { Card, CardContent } from "@/components/ui/card"
import { Button } from "@/components/ui/button"
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar"
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { Heart, MessageCircle, Share2, MoreHorizontal, Bookmark, Send } from 'lucide-react'
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu"
import { Input } from "@/components/ui/input"
import { useToast } from "@/components/ui/use-toast"

interface FoodPostProps {
  username: string
  avatar: string
  image: string
  caption: string
  likes: number
  comments: number
  timeAgo?: string
  location?: string
  size?: "small" | "medium" | "large"
}

export function FoodPost({
  username,
  avatar,
  image,
  caption,
  likes: initialLikes,
  comments: initialComments,
  timeAgo = "1h",
  location,
  size = "medium",
}: FoodPostProps) {
  const [isLiked, setIsLiked] = useState(false)
  const [likes, setLikes] = useState(initialLikes)
  const [isSaved, setIsSaved] = useState(false)
  const [showComments, setShowComments] = useState(false)
  const [isExpanded, setIsExpanded] = useState(false)
  const { toast } = useToast()

  const handleLike = () => {
    setIsLiked(!isLiked)
    setLikes(isLiked ? likes - 1 : likes + 1)
  }

  const handleSave = () => {
    setIsSaved(!isSaved)
    toast({
      title: isSaved ? "Removed from saved" : "Saved to collection",
      description: isSaved ? "Post removed from your saved items" : "Post added to your saved items",
    })
  }

  const truncateCaption = (text: string) => {
    if (text.length <= 125) return text
    return isExpanded ? text : `${text.slice(0, 125)}...`
  }

  const sizeClasses = {
    small: "max-w-[300px]",
    medium: "max-w-[400px]",
    large: "max-w-[470px]",
  }

  return (
    <Card className={`mx-auto border-b ${sizeClasses[size]}`}>
      <CardContent className="p-0">
        <div className="flex items-center justify-between p-3">
          <div className="flex items-center gap-2">
            <Avatar className="w-8 h-8">
              <AvatarImage src={avatar} />
              <AvatarFallback>{username[0].toUpperCase()}</AvatarFallback>
            </Avatar>
            <div className="flex flex-col">
              <span className="text-sm font-semibold">{username}</span>
              {location && (
                <span className="text-xs text-muted-foreground">{location}</span>
              )}
            </div>
          </div>
          <DropdownMenu>
            <DropdownMenuTrigger asChild>
              <Button variant="ghost" size="icon" className="h-8 w-8">
                <MoreHorizontal className="h-5 w-5" />
              </Button>
            </DropdownMenuTrigger>
            <DropdownMenuContent align="end">
              <DropdownMenuItem>Report</DropdownMenuItem>
              <DropdownMenuItem>Unfollow</DropdownMenuItem>
              <DropdownMenuItem>Copy link</DropdownMenuItem>
            </DropdownMenuContent>
          </DropdownMenu>
        </div>

        <div className={`relative ${size === 'small' ? 'h-[250px]' : 'aspect-square'}`}>
          <img
            src={image}
            alt="Food post"
            className="w-full h-full object-cover"
          />
        </div>

        <div className="p-3 space-y-2">
          <div className="flex items-center justify-between">
            <div className="flex items-center gap-4">
              <Button
                variant="ghost"
                size="icon"
                className={`h-8 w-8 ${isLiked ? "text-red-500" : ""}`}
                onClick={handleLike}
              >
                <Heart className={`h-5 w-5 ${isLiked ? "fill-current" : ""}`} />
              </Button>
              <Button
                variant="ghost"
                size="icon"
                className="h-8 w-8"
                onClick={() => setShowComments(!showComments)}
              >
                <MessageCircle className="h-5 w-5" />
              </Button>
              <Button variant="ghost" size="icon" className="h-8 w-8">
                <Send className="h-5 w-5" />
              </Button>
            </div>
            <Button
              variant="ghost"
              size="icon"
              className={`h-8 w-8 ${isSaved ? "text-yellow-500" : ""}`}
              onClick={handleSave}
            >
              <Bookmark className={`h-5 w-5 ${isSaved ? "fill-current" : ""}`} />
            </Button>
          </div>

          <div className="text-sm font-semibold">{likes.toLocaleString()} likes</div>

          <div className="text-sm">
            <span className="font-semibold mr-2">{username}</span>
            <span>
              {truncateCaption(caption)}
              {caption.length > 125 && (
                <button
                  className="text-muted-foreground ml-1"
                  onClick={() => setIsExpanded(!isExpanded)}
                >
                  {isExpanded ? "less" : "more"}
                </button>
              )}
            </span>
          </div>

          <button
            className="text-sm text-muted-foreground"
            onClick={() => setShowComments(!showComments)}
          >
            View all {initialComments} comments
          </button>

          {showComments && (
            <div className="space-y-2 pt-2">
              <div className="text-sm space-y-1">
                {[
                  { user: "foodie_lover", comment: "Looks delicious! 😋" },
                  { user: "spice_master", comment: "Great find! Need to try this" },
                ].map((comment, i) => (
                  <div key={i} className="flex gap-2">
                    <span className="font-semibold">{comment.user}</span>
                    <span>{comment.comment}</span>
                  </div>
                ))}
              </div>
              <div className="flex gap-2 pt-2">
                <Input
                  placeholder="Add a comment..."
                  className="text-sm h-8"
                />
                <Button variant="ghost" size="sm">
                  Post
                </Button>
              </div>
            </div>
          )}

          <div className="text-xs text-muted-foreground uppercase">
            {timeAgo}
          </div>
        </div>
      </CardContent>
    </Card>
  )
}

